package com.mv.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.util.List;

public class GiveAttendance2 extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    private TextView scannerTV;

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

        CodeScannerView scannerView = findViewById(R.id.camView_Mode2);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        long unixTimeStamp = System.currentTimeMillis() / 1000L;
                        String textFromQRCode = result.getText();
                        //TODO: create a boolean function 'check_textFromQRCode_isCorrect(textFromQRCode, unixTimeStamp)' to check whether the qrcode is in correct format in class Mode2QRCodeProperties
                        if(Mode2QRCodeProperties.check_textFromQRCode_isCorrect(textFromQRCode, unixTimeStamp) == true){
                            //QR Code is in correct format. Now we need to up-load it
                            //TODO: create a constructor in Mode2QRCodeProperties which takes string of QRCode and assigns specific values inside the object.
                            Mode2QRCodeProperties QRProperties = new Mode2QRCodeProperties(textFromQRCode, unixTimeStamp);

                            if(QRProperties.checkIfTimeInBuffer(2)){
                                //TODO: Upload details to firebase realtime database.
                            }


                        }


                        }
                    });
                }
                ;
            });
        }

    }

    //After scanning successfully ://











}