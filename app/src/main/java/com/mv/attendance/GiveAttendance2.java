package com.mv.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.zxing.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GiveAttendance2 extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    private TextView scannerTV;
    private SeekBar zoomSeekBar;

    FirebaseDatabase database;
    DatabaseReference reference;

    //List<AttendanceSession> ListAttendanceSession;
    //Integer countInListAttendance;
    //AttendanceSession attendanceSession;

    //SharedPreferences sh;
    //SharedPreferences.Editor myEdit;


    private static final int CAMERA_PERMISSION_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_attendance2);

        checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);

        scannerTV = findViewById(R.id.idTVScannedData2_Mode2);

        scannerTV.setText("Scan the code shown by your teacher");

        zoomSeekBar = findViewById(R.id.zoomSeekBar);
        zoomSeekBar.setProgress(0);

        CodeScannerView scannerView = findViewById(R.id.camView_Mode2);
        mCodeScanner = new CodeScanner(this, scannerView);
        //mCodeScanner.setZoom(1);
        Log.d("QWERT", "Zoom - " + mCodeScanner.getZoom());
        mCodeScanner.startPreview();
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        long unixTimeStamp = System.currentTimeMillis() / 1000L;
                        String textFromQRCode = result.getText();
                        Log.d("QWERT", textFromQRCode);

                        if(Mode2QRCodeProperties.check_textFromQRCode_isCorrect(textFromQRCode, unixTimeStamp) == true){
                            //QR Code is in correct format. Now we need to up-load it

                            Mode2QRCodeProperties QRProperties = new Mode2QRCodeProperties(textFromQRCode, unixTimeStamp);


                            if(QRProperties.checkIfTimeInBuffer(2)){

                                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                String nameOfStudent = sh.getString("Name", "");
                                int RollNoOfStudent = Integer.parseInt(sh.getString("Roll No", ""));
                                String divOfStudent = sh.getString("Div", "");

                                Student std = new Student(RollNoOfStudent,divOfStudent,nameOfStudent);

                                //Lecture Lecture1 = new Lecture(Mode2QRCodeProperties.Teacher1,Mode2QRCodeProperties.Title1);

                                database = FirebaseDatabase.getInstance();
                                reference = database.getReference("Lectures");


                                Map<String, Object> addToFirebase = new HashMap<String,Object>();
                                addToFirebase.put(nameOfStudent, QRProperties.QR_CODE_Encoded_time);
                                reference.child(Mode2QRCodeProperties.Teacher1).child(Mode2QRCodeProperties.Title1).child("Student").updateChildren(addToFirebase);


                                Toast.makeText(GiveAttendance2.this, "Attendance marked  successfully!", Toast.LENGTH_SHORT).show();





                            }


                        }


                        mCodeScanner.startPreview();
                        }
                    });
                }

            });

        zoomSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mCodeScanner.setZoom(1+i/2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        }

    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(GiveAttendance2.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(GiveAttendance2.this, new String[] { permission }, requestCode);
        }
        else {
            //Toast.makeText(GiveAttendance2.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {

            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Showing the toast message
                Toast.makeText(GiveAttendance2.this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(GiveAttendance2.this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    protected void onStop() {
        mCodeScanner.releaseResources();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }
}

    //After scanning successfully ://
