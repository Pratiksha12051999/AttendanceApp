package com.example.attendanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
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
import java.util.Arrays;
import java.util.Calendar;

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

    String startDateText;
    String endDateText;
    String divisionText;
    String selectedMonth;
    String numberOfStudents;
    String[] subjects;
    int  yearText;
    int c,r;
    int r1,c1;
    int r2,c2;
    String value = "";
    String value1 = "";
    String value2 = "";

    FirebaseAuth mAuth;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference students;
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
        startDateText = getIntent().getStringExtra("startDateText");
        endDateText = getIntent().getStringExtra("endDateText");
        divisionText = getIntent().getStringExtra("divisionText").toUpperCase();
        selectedMonth = getIntent().getStringExtra("selectedMonth");
        numberOfStudents = getIntent().getStringExtra("numberOfStudents");
    }
    private void readExcelData(String filePath) {
        Log.d(TAG, "readExcelData: Reading Excel File.");
        File inputFile = new File(filePath);
        try {
            InputStream inputStream = new FileInputStream(inputFile);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowsCount = sheet.getPhysicalNumberOfRows();
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            StringBuilder sb = new StringBuilder();
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();

            //outer loop, loops through rows
            outerloop:
            for (r = 0; r < 50; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                //inner loop, loops through columns
                for (c = 0; c < cellsCount; c++) {
                    //handles if there are to many columns on the excel sheet.
                    if (c > 2) {
                        Log.e(TAG, "readExcelData: ERROR. Excel File Format is incorrect! ");
                        toastMessage("ERROR: Excel File Format is incorrect!");
                        break;
                    } else {
                        String value = getCellAsString(row, c, formulaEvaluator);
                        String cellInfo = "r:" + r + "; c:" + c + "; v:" + value;
                        Log.d(TAG, "readExcelData: Data from row: " + cellInfo);
                        sb.append(value + ", ");
                        if (value.equals("Roll \n" +
                                "No.")) {
                            break outerloop;
                        }
                    }
                }
                sb.append(":");
            }
            Log.d(TAG, "Row No." + r);
            Log.d(TAG, "Column No." + c);
            Log.d(TAG, "readExcelData: STRINGBUILDER: " + sb.toString());

            outerloop1:
            for (r1 = r; r1 < r+1; r1++) {
                Row row1 = sheet.getRow(r1);
                int cellsCount1 = row1.getPhysicalNumberOfCells();
                //inner loop, loops through columns
                for (c1 = 0; c1 < cellsCount1; c1++) {
                    String value1 = getCellAsString(row1, c1, formulaEvaluator);
                    String cellInfo1 = "r:" + r1 + "; c:" + c1 + "; v:" + value1;
                    Log.d(TAG, "readExcelData: Data from row: " + cellInfo1);
                    sb1.append(value + ", ");
                    if (value1.equals("TOTAL")) {
                        break outerloop1;
                    }
                }
                sb1.append(":");
            }
            //  parseStringBuilder(sb);

            Log.d(TAG, "Row No." + r1);
            Log.d(TAG, "Column No." + c1);
            Log.d(TAG, "readExcelData: STRINGBUILDER: " + sb1.toString());
            int size = (c1+5)/3;
            int[] myArray = new int [size+1];
            myArray[0] = 0;
            myArray[1]=1;
            int columnNo = c+4;
            for(int k=2; k <= size; k++){
                myArray[k] = columnNo;
                columnNo = columnNo +3;
            }

            int subjectSize = (c1)/3 + 1;
            subjects = new String [subjectSize];
            int subjectNo = 0;
            for (int rowSubject = r; rowSubject < r+1; rowSubject++){
                Row subjectRow = sheet.getRow(rowSubject);
                for(int columnSubject = c+2 ; columnSubject <= c1 ; columnSubject = columnSubject + 3 )
                {
                    String valueSubject = getCellAsString(subjectRow, columnSubject, formulaEvaluator);
                    subjects[subjectNo] = valueSubject;
                    subjectNo = subjectNo + 1;
                    String cellInfoSubject = "r:" + rowSubject + "; c:" + columnSubject + "; v:" + valueSubject;
                    Log.d(TAG, "readExcelData: Data from row: " + cellInfoSubject);
                }
            }

            //Log.d("this is my array", "arr: " + Arrays.toString(subjects));

            outerloop2:
            for (r2 = r+3; r2 <= Integer.parseInt(numberOfStudents) + r + 2; r2++) {
                Row row2 = sheet.getRow(r2);
                int cellsCount2 = row2.getPhysicalNumberOfCells();
                //inner loop, loops through columns
                for (c2 = 0; c2 < size+1 ; c2++) {
                    String value2 = getCellAsString(row2, myArray[c2], formulaEvaluator);
                    String cellInfo2 = "r:" + r2 + "; c:" + myArray[c2] + "; v:" + value2;
                    Log.d(TAG, "readExcelData: Data from row: " + cellInfo2);
                    sb2.append(value2 + ", ");
                }
                sb2.append(":");
            }
            Log.d(TAG, "readExcelData: STRINGBUILDER: " + sb2.toString());
            parseStringBuilder(sb2);
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

                String x = String.valueOf(columns[0]).split("\\.")[0];
                String y = columns[1].replaceAll("\\[", "").replaceAll("\\]","");
                String z = String.valueOf(columns[2]).split("\\.")[0];
                String a = String.valueOf(columns[3]).split("\\.")[0];
                String b = String.valueOf(columns[4]).split("\\.")[0];
                String c = String.valueOf(columns[5]).split("\\.")[0];
                String d = String.valueOf(columns[6]).split("\\.")[0];
                String e = String.valueOf(columns[7]).split("\\.")[0];
                String f = String.valueOf(columns[8]).split("\\.")[0];

                String cellInfo2 = "(x,y,z): (" + x + "," + y + "," + z + "," + a + "," + b + "," + c + "," + d + "," + e + "," + f + ")";
                Log.d(TAG, "ParseStringBuilder: Data from row: " + cellInfo2);
                FirebaseUser user = mAuth.getCurrentUser();
                String emailID = user.getEmail();
                Calendar calendar = Calendar.getInstance();
                yearText = calendar.get(Calendar.YEAR);
                students = FirebaseDatabase.getInstance().getReference("Attendance");
                String id = students.push().getKey();
                Log.d("this is my array", "arr: " + Arrays.toString(subjects));
                students.child(id).child("Year").setValue("2020");
                students.child(id).child("Month").setValue(selectedMonth);
                students.child(id).child("Division").setValue(divisionText);
                students.child(id).child("Roll_No").setValue(x);
                students.child(id).child("Name").setValue(y);
                students.child(id).child("Subjects").child(subjects[0]).setValue(z);
                students.child(id).child("Subjects").child(subjects[1]).setValue(a);
                students.child(id).child("Subjects").child(subjects[2]).setValue(b);
                students.child(id).child("Subjects").child(subjects[3]).setValue(c);
                students.child(id).child("Subjects").child(subjects[4]).setValue(d);
                students.child(id).child("Subjects").child(subjects[5]).setValue(e);
                students.child(id).child("Total_Attendance").setValue(f);
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