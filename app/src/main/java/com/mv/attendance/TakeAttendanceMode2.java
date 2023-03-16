package com.mv.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TakeAttendanceMode2 extends AppCompatActivity {


    Button idBtoGiveAttendance2;
    Button idBtoTakeAttendance2;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance_mode2);

        idBtoGiveAttendance2 = findViewById(R.id.idBtoTakeAttendance2);
        idBtoTakeAttendance2 = findViewById(R.id.idBtoTakeAttendance2);

        idBtoGiveAttendance2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TakeAttendanceMode2.this, AttendanceListViewMode2.class);



            }
        });

        idBtoTakeAttendance2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TakeAttendanceMode2.this, GiveAttendance2.class);


            }
        });

    }
}