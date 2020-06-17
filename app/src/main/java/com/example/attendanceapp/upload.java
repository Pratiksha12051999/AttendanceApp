package com.example.attendanceapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class upload extends AppCompatActivity {

    private static final String TAG = "";
    Intent filepath;
    Button bfile;
    TextView tfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        bfile=(Button)findViewById(R.id.buttonFile);
        bfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filepath=new Intent(Intent.ACTION_GET_CONTENT);
                filepath.setType("*/*");
                startActivityForResult(filepath, 10);
            }
        });

    }

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

    public void readData(String filePath) {
        File inputFile=new File(filePath);
        StringBuilder sb=new StringBuilder();
        try {
            System.out.println(1);
            Uri u=Uri.parse(filePath);
            Uri uri= ContentUris.withAppendedId(u, 0);
            ContentResolver res = this.getContentResolver();
            InputStream inputStream= res.openInputStream(uri);
            System.out.println(2);
            XSSFWorkbook workbook=new XSSFWorkbook(inputStream);
            System.out.println(3);
            XSSFSheet sheet=workbook.getSheetAt(0);
            System.out.println(4);
            int rowCount=sheet.getPhysicalNumberOfRows();
            System.out.println(5);
            FormulaEvaluator formulaEvaluator=workbook.getCreationHelper().createFormulaEvaluator();
            System.out.println(6);

            for(int r=1; r<rowCount; r++){
                Row row=sheet.getRow(r);
                int cellCount=row.getPhysicalNumberOfCells();
                for(int c=0; c<cellCount; c++){
                    String value=getCellAsString(row, c, formulaEvaluator);
                    sb.append(value+",");
                }
            }
            Toast.makeText(this, sb, Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //tfile.setText(sb);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {
                    String path = data.getData().getPath();
                    Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                    readData(path);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}