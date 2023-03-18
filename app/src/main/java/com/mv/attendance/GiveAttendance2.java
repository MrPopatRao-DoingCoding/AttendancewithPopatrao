package com.mv.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.zxing.Result;

import java.util.List;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_attendance2);

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
                                reference = database.getReference("Students");

                                reference.child(QRProperties.Teacher1).setValue(QRProperties.Title1);
                                reference.child(QRProperties.Title1).setValue(nameOfStudent);
                                reference.child(QRProperties.Title1).setValue(RollNoOfStudent);
                                reference.child(QRProperties.Title1).setValue(divOfStudent);




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
