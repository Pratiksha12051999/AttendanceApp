package com.example.attendanceapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UploadResults extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "";
    Intent filepath;
    Button bfile;
    DatabaseReference mRef;
    AutoCompleteTextView acSubjects;
    private DrawerLayout drawer;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_results);

        bfile=(Button)findViewById(R.id.buttonFile);
        bfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filepath=new Intent(Intent.ACTION_GET_CONTENT);
                filepath.setType("*/*");
                startActivityForResult(filepath, 10);
            }
        });

        //CardView
        CardView cv= findViewById(R.id.cv);
        cv.setTranslationY(+10000);
        cv.animate().translationYBy(-10000).setDuration(1000);
        cv.setContentPadding(30, 30, 30, 30);

        //AutoCompleteView
        String subjects[]=getResources().getStringArray(R.array.subjects);

        acSubjects=findViewById(R.id.autoCompleteTextView);
        ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1, subjects);
        acSubjects.setAdapter(arrayAdapter);

        //NavBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //Excel Data Code

    private String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
        String value = "";
        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    value = ""+cellValue.getBooleanValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    double numericValue = cellValue.getNumberValue();
                    if(HSSFDateUtil.isCellDateFormatted(cell)) {
                        double date = cellValue.getNumberValue();
                        SimpleDateFormat formatter =
                                new SimpleDateFormat("MM/dd/yy");
                        value = formatter.format(HSSFDateUtil.getJavaDate(date));
                    } else {
                        value = ""+numericValue;
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = ""+cellValue.getStringValue();
                    break;
                default:
            }
        } catch (NullPointerException e) {

            Log.e(TAG, "getCellAsString: NullPointerException: " + e.getMessage() );
        }
        return value;
    }

    public void readData(Uri uri) {
        StringBuilder sb=new StringBuilder();
        try {
            InputStream inputStream = new FileInputStream(FileChooser.getPath(this,uri));
            XSSFWorkbook workbook=new XSSFWorkbook(inputStream);
            XSSFSheet sheet=workbook.getSheetAt(0);
            FormulaEvaluator formulaEvaluator=workbook.getCreationHelper().createFormulaEvaluator();

            ArrayList<String> headers=new ArrayList<String>();

            int rowCount=sheet.getPhysicalNumberOfRows();
            Row row=sheet.getRow(0);
            int cellCount=row.getPhysicalNumberOfCells();

            for(int c=0; c<cellCount; c++){
                if(row.getCell(c)!=null){headers.add(getCellAsString(row, c, formulaEvaluator));}else{headers.add("");};
            }

            System.out.println(headers);

            for(int r=1; r<rowCount; r++) {
                row = sheet.getRow(r);
                if (row.getCell(0) != null) {
                    String id = row.getCell(0).getStringCellValue();
                    mRef = FirebaseDatabase.getInstance().getReference().child("1xdYDgDXnj1ZVODHEdcSAv51ex58GuGVPBrBTQVFOrUs").child("Sheet1").child(id);
                    //Name
                    if (row.getCell(1) != null) {
                        mRef.child(headers.get(1)).setValue(getCellAsString(row, 1, formulaEvaluator));
                    } else {
                        mRef.child(headers.get(1)).setValue("");
                    }
                    //Marks1
                    if (row.getCell(2) != null) {
                        mRef.child(headers.get(2)).setValue(getCellAsString(row, 2, formulaEvaluator));
                    } else {
                        mRef.child(headers.get(2)).setValue("");
                    }
                    //Marks2
                    if (row.getCell(3) != null) {
                        mRef.child(headers.get(3)).setValue(getCellAsString(row, 3, formulaEvaluator));
                    } else {
                        mRef.child(headers.get(3)).setValue("");
                    }
                    //Marks3
                    if (row.getCell(4) != null) {
                        mRef.child(headers.get(4)).setValue(getCellAsString(row, 4, formulaEvaluator));
                    } else {
                        mRef.child(headers.get(4)).setValue("");
                    }
                    //Marks4
                    if (row.getCell(5) != null) {
                        mRef.child(headers.get(5)).setValue(getCellAsString(row, 5, formulaEvaluator));
                    } else {
                        mRef.child(headers.get(5)).setValue("");
                    }
                    //Marks5
                    if (row.getCell(6) != null) {
                        mRef.child(headers.get(6)).setValue(getCellAsString(row, 6, formulaEvaluator));
                    } else {
                        mRef.child(headers.get(6)).setValue("");
                    }
                    ;

                    Toast.makeText(this, "successful", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {
                    Uri path = data.getData();
                    readData(path);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //Excel Data Code Ends

    //NavBar Code

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mAuth = FirebaseAuth.getInstance();
        switch (item.getItemId()) {
            case R.id.nav_announcement:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_contact:
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Intent goBackToLogin = new Intent(UploadResults.this, MainActivity.class);
                startActivity(goBackToLogin);
        }
        return true;
    }

    //NavBar Code Ends
}