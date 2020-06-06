package com.example.attendanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.attendanceapp.XYValue;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UploadAttendance extends AppCompatActivity {

    private static final String TAG = "UploadAttendance";
    // Declare variables
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    File file;
    Button btnUpDirectory,btnSDCard;
    ArrayList<String> pathHistory;
    String lastDirectory;
    int count = 0;
    ArrayList<XYValue> uploadData;
    ListView lvInternalStorage;
    ProgressBar progressBar;
    long startRowText;
    long endRowText;
    int rollNumberText;
    int nameText;
    int attendanceText;
    String divisionText;
    String subjectText;
    String yearText;
    String selectedMonth;
    FirebaseAuth mAuth;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_attendance);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        lvInternalStorage = findViewById(R.id.lvInternalStorage);
        btnUpDirectory = findViewById(R.id.btnUpDirectory);
        btnSDCard = findViewById(R.id.btnViewSDCard);
        uploadData = new ArrayList<XYValue>();
        progressBar = findViewById(R.id.progressBar);
        //need to check the permissions
        checkFilePermissions();
        lvInternalStorage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lastDirectory = pathHistory.get(count);
                if(lastDirectory.equals(adapterView.getItemAtPosition(i))){
                    Log.d(TAG, "lvInternalStorage: Selected a file for upload: " + lastDirectory);
                    Toast.makeText(UploadAttendance.this, "Selected file for upload: "+lastDirectory, Toast.LENGTH_SHORT).show();
                    //Execute method for reading the excel data.
                    readExcelData(lastDirectory);
                }else
                {
                    count++;
                    pathHistory.add(count,(String) adapterView.getItemAtPosition(i));
                    checkInternalStorage();
                    Log.d(TAG, "lvInternalStorage: " + pathHistory.get(count));
                }
            }
        });

        //Goes up one directory level
        btnUpDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count == 0){
                    Log.d(TAG, "btnUpDirectory: You have reached the highest level directory.");
                }else{
                    pathHistory.remove(count);
                    count--;
                    checkInternalStorage();
                    Log.d(TAG, "btnUpDirectory: " + pathHistory.get(count));
                }
            }
        });

        //Opens the SDCard or phone memory
        btnSDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 0;
                pathHistory = new ArrayList<String>();
                pathHistory.add(count,System.getenv("EXTERNAL_STORAGE"));
                Log.d(TAG, "btnSDCard: " + pathHistory.get(count));
                checkInternalStorage();
            }
        });
        startRowText = Integer.parseInt(getIntent().getStringExtra("Start_Row_Number"));
        endRowText = Integer.parseInt(getIntent().getStringExtra("End_Row_Number"));
        rollNumberText = Integer.parseInt(getIntent().getStringExtra("Roll_Number_Column"));
        nameText = Integer.parseInt(getIntent().getStringExtra("Name_Column"));
        attendanceText = Integer.parseInt(getIntent().getStringExtra("Attendance_Column"));
        divisionText = getIntent().getStringExtra("Attendance_Division").toUpperCase();
        subjectText = getIntent().getStringExtra("Attendance_Subject").toUpperCase();
        yearText = getIntent().getStringExtra("Attendance_Year");
        selectedMonth = getIntent().getStringExtra("Attendance_Month");
    }
    private void readExcelData(String filePath) {
        Log.d(TAG, "readExcelData: Reading Excel File.");
        File inputFile = new File(filePath);
        try {
            InputStream inputStream = new FileInputStream(inputFile);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowsCount = (int) (endRowText - startRowText);
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            StringBuilder sb = new StringBuilder();
            int rollNumberTextInteger = Integer.parseInt(String.valueOf(rollNumberText));
            int nameTextInteger = Integer.parseInt(String.valueOf(nameText));
            int attendanceTextInteger = Integer.parseInt(String.valueOf(attendanceText));
            //outer loop, loops through rows
            for (int r = Integer.parseInt(String.valueOf(startRowText))-1; r <= Integer.parseInt(String.valueOf(endRowText))-1; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                //inner loop, loops through columns
                for (int c = 0; c <= attendanceTextInteger; c++) {
                    if (c == (rollNumberTextInteger - 1) || c == (nameTextInteger - 1) || c == (attendanceTextInteger - 1)) {
                        String value = getCellAsString(row, c, formulaEvaluator);
                        String cellInfo = "r:" + r + "; c:" + c + "; v:" + value;
                        Log.d(TAG, "readExcelData: Data from row: " + cellInfo);
                        sb.append(value + ", ");
                    }
                }
                sb.append(":");
            }
            Log.d(TAG, "readExcelData: STRINGBUILDER: " + sb.toString());
            parseStringBuilder(sb);
        }
        catch (IOException e) {
            Log.e(TAG, "readExcelData: Error reading inputstream. " + e.getMessage() );
        }
    }
    /**
     * Method for parsing imported data and storing in ArrayList<XYValue>
     */
    public void parseStringBuilder(StringBuilder mStringBuilder){
        Log.d(TAG, "parseStringBuilder: Started parsing.");

        // splits the sb into rows.
        String[] rows = mStringBuilder.toString().split(":");
        //Add to the ArrayList<XYValue> row by row
        for(int i = 0; i <rows.length; i++) {
            //Split the columns of the rows
            String[] columns = rows[i].split(",");
            //use try catch to make sure there are no "" that try to parse into doubles.
            try{
                progressBar.setVisibility(View.VISIBLE);
                int rollNumberTextInteger = Integer.parseInt(String.valueOf(rollNumberText));
                int nameTextInteger = Integer.parseInt(String.valueOf(nameText));
                int attendanceTextInteger = Integer.parseInt(String.valueOf(attendanceText));
                Log.i("RollNumber", String.valueOf(rollNumberTextInteger));
                Log.i("NameText", String.valueOf(nameTextInteger));
                Log.i("AttendanceText", String.valueOf(attendanceTextInteger));
                String x = String.valueOf(columns[0]).split("\\.")[0];
                String y = columns[1];
                String z = String.valueOf(columns[2]).split("\\.")[0];
                String cellInfo = "(x,y,z): (" + x + "," + y + "," + z + ")";
                Log.d(TAG, "ParseStringBuilder: Data from row: " + cellInfo);
                FirebaseUser user = mAuth.getCurrentUser();
                String emailID = user.getEmail();
                myRef.child(yearText).child(selectedMonth).child(divisionText).child(subjectText).child(x).child("Name").setValue(y);
                myRef.child(yearText).child(selectedMonth).child(divisionText).child(subjectText).child(x).child("TeacherEmail").setValue(emailID);
                myRef.child(yearText).child(selectedMonth).child(divisionText).child(subjectText).child(x).child("Attendance").setValue(z);
            }catch (NumberFormatException e){
                Log.e(TAG, "parseStringBuilder: NumberFormatException: " + e.getMessage());
            }
        }
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(UploadAttendance.this,"Data has been uploaded successfully",Toast.LENGTH_SHORT).show();
       //printDataToLog();
    }

    private void printDataToLog() {
        Log.d(TAG, "printDataToLog: Printing data to log...");

        for(int i = 0; i< uploadData.size(); i++){
            int x = uploadData.get(i).getX();
            String y = uploadData.get(i).getY();
            int z = uploadData.get(i).getZ();
            Log.d(TAG, "printDataToLog: (x,y,z): (" + x + "," + y + "," + z + ")");
        }
    }

    /**
     * Returns the cell as a string from the excel file
     * @param row
     * @param c
     * @param formulaEvaluator
     * @return
     */
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

    private void checkInternalStorage() {
        Log.d(TAG, "checkInternalStorage: Started.");
        try{
            if (!Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                toastMessage("No SD card found.");
            }
            else{
                // Locate the image folder in your SD Car;d
                file = new File(pathHistory.get(count));
                Log.d(TAG, "checkInternalStorage: directory path: " + pathHistory.get(count));
            }

            listFile = file.listFiles();

            // Create a String array for FilePathStrings
            FilePathStrings = new String[listFile.length];

            // Create a String array for FileNameStrings
            FileNameStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++) {
                // Get the path of the image file
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                // Get the name image file
                FileNameStrings[i] = listFile[i].getName();
            }

            for (int i = 0; i < listFile.length; i++)
            {
                Log.d("Files", "FileName:" + listFile[i].getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, FilePathStrings);
            lvInternalStorage.setAdapter(adapter);

        }catch(NullPointerException e){
            Log.e(TAG, "checkInternalStorage: NULLPOINTEREXCEPTION " + e.getMessage() );
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkFilePermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        }else{
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}