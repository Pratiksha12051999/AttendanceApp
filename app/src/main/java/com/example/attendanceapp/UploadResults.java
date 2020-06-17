package com.example.attendanceapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.Manifest;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.attendanceapp.R;
import com.firebase.client.Firebase;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UploadResults extends AppCompatActivity {

    private static final String TAG = "UploadResults";
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    File file;
    Button btnUpDirectory,btnSDCard;
    ArrayList<String> pathHistory;
    String lastDirectory;
    int count = 0;
    ArrayList<ResultValue> uploadData;
    ListView lvInternalStorage;
    ProgressBar progressBar;
    int grno,admission_no;
    int am1ia,am1se,ap1ia,ap1se,ac1ia,ac1se,beeia,beese,emia,emse,evsia,evsse;
    //    int am2ia,am2se,ap2ia,ap2se,ac2ia,ac2se,edis,edse,spaia,spase,csia,csse;
//        int am3ia,am3se,dldaia,dldase,dmia,dmse,eccfia,eccfse,dsia,dsse,oopm;
//        int am4ia,am4se,aoaia,aoase,coaia,coase,cgia,cgse,osia,osse;
//        int mpia,mpse,dbmsia,dbmsse,cnia,cnse,tcsia,tcsse,dloc1ia,dloc1se,bce;
//        int seia,sese,spccia,spccse,dwmia,dwmse,cssia,cssse,dloc2ia,dlocse,miniproject;
//        int dsipia,dsipse,mccia,mccse,aiscia,aiscse,dloc3ia,dloc3se,iloc1se,iloc1ia,majorproject1;
//        int hmiia,hmise,dcia,dcse,dloc4ia,dloc4se,iloc2ia,iloc2se,majorproject2;
    FirebaseAuth mAuth;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_results);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        lvInternalStorage = (ListView) findViewById(R.id.lvInternalStorage);
        btnUpDirectory = (Button) findViewById(R.id.btnUpDirectory);
        btnSDCard = (Button) findViewById(R.id.btnViewSDCard);
        uploadData = new ArrayList<>();
        progressBar =findViewById(R.id.progressBar);
        //need to check the permissions
        checkFilePermissions();

        lvInternalStorage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lastDirectory = pathHistory.get(count);
                if(lastDirectory.equals(adapterView.getItemAtPosition(i))){
                    Log.d(TAG, "lvInternalStorage: Selected a file for upload: " + lastDirectory);
                    Toast.makeText(UploadResults.this, "Select Sheet To be uploaded: "+lastDirectory, Toast.LENGTH_SHORT).show();
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

    }
    //////

    private void readExcelData(String filePath) {
        Log.d(TAG, "readExcelData: Reading Excel File.");

        //decarle input file
        File inputFile = new File(filePath);

        try {
            InputStream inputStream = new FileInputStream(inputFile);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowsCount = sheet.getPhysicalNumberOfRows();
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            StringBuilder sb = new StringBuilder();

            //outter loop, loops through rows
            for (int r = 1; r < rowsCount; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                //inner loop, loops through columns
                for (int c = 0; c < cellsCount; c++) {
                    //handles if there are to many columns on the excel sheet.
                    if(c>2){
                        Log.e(TAG, "readExcelData: ERROR. Excel File Format is incorrect! " );
                        toastMessage("ERROR: Excel File Format is incorrect!");
                        break;
                    }else{
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

        }catch (FileNotFoundException e) {
            Log.e(TAG, "readExcelData: FileNotFoundException. " + e.getMessage() );
        } catch (IOException e) {
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
        for(int i=0; i<rows.length; i++) {
            //Split the columns of the rows
            String[] columns = rows[i].split(",");

            //use try catch to make sure there are no "" that try to parse into doubles.
            try{
                progressBar.setVisibility(View.VISIBLE);
                int am1ia=Integer.parseInt(columns[3]);
                int am1se=Integer.parseInt(columns[4]);
                int ap1ia=Integer.parseInt(columns[5]);
                int ap1se=Integer.parseInt(columns[6]);
                int ac1ia=Integer.parseInt(columns[7]);
                int ac1se=Integer.parseInt(columns[8]);
                int beeia=Integer.parseInt(columns[9]);
                int beese=Integer.parseInt(columns[10]);
                int emia=Integer.parseInt(columns[11]);
                int emse=Integer.parseInt(columns[12]);
                int evsia=Integer.parseInt(columns[13]);
                int evsse=Integer.parseInt(columns[14]);

//                    String cellInfo = "(x,y): (" + x + "," + y + ")";
//                    Log.d(TAG, "ParseStringBuilder: Data from row: " + cellInfo);

                //add the the uploadData ArrayList
                // uploadData.add(new ResultValue(am1ia,am1se,ap1ia,ap1se,ac1ia,ac1se,beeia,beese,emia,emse,evsia,evsse));

            }catch (NumberFormatException e){

                Log.e(TAG, "parseStringBuilder: NumberFormatException: " + e.getMessage());

            }
        }
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(UploadResults.this,"Data has been uploaded successfully",Toast.LENGTH_SHORT).show();
        //printDataToLog();
    }

    private void printDataToLog() {
        Log.d(TAG, "printDataToLog: Printing data to log...");

        for(int x = 0; x< uploadData.size(); x++){
            int a = uploadData.get(x).getA();
            int b = uploadData.get(x).getB();
            int c = uploadData.get(x).getC();
            int d = uploadData.get(x).getD();
            int e = uploadData.get(x).getE();
            int f = uploadData.get(x).getF();
            int g = uploadData.get(x).getG();
            int h = uploadData.get(x).getH();
            int i = uploadData.get(x).getI();
            int j = uploadData.get(x).getJ();
            int k = uploadData.get(x).getK();
            int l = uploadData.get(x).getL();
            Log.d(TAG, "printDataToLog: (a,b,c,d,e,f,g,h,i,j,k,l): (" + a + "," + b + "," + c + "," + d + "," + e + "," + f + "," + g + ", " + h + "," + i + "," + j + "," + k + "," + l + ")");
        }
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
            int permissionCheck = this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE") + this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
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