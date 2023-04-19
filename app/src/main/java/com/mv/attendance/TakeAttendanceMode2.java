package com.mv.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class TakeAttendanceMode2 extends AppCompatActivity {


    ImageView setting_image;
    Button buttonToGiveAttendance2;
    Button buttonToTakeAttendance2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance_mode2);

        // Link variables with XML Views
        setting_image = findViewById(R.id.setting_image);
        buttonToGiveAttendance2 = findViewById(R.id.idBtoGiveAttendance2);
        buttonToTakeAttendance2 = findViewById(R.id.idBtoTakeAttendance2);

        // Shared Preferences
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        setting_image.setOnClickListener(new View.OnClickListener() {  // Settings Image clicked, open Settings activity
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TakeAttendanceMode2.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        buttonToGiveAttendance2.setOnClickListener(new View.OnClickListener() {  // If settings changed before delta time, then only allow to give attendance
            @Override
            public void onClick(View v) {
                if(Math.abs(sh.getLong("savedTime", 167666442) - getTime()) > 20) {  // Check delta-time condition   ////////Change time here!!
                    Intent intent = new Intent(TakeAttendanceMode2.this, GiveAttendance2.class);
                    startActivity(intent);
                }
                else{  // Settings changed in less than delta time, show error toast.
                    Toast.makeText(TakeAttendanceMode2.this, "Not enough time has passed since you changed the settings!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        buttonToTakeAttendance2.setOnClickListener(new View.OnClickListener() {  // Mode 2 Attendance List
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TakeAttendanceMode2.this, AttendanceListViewMode2.class);
                startActivity(intent);

            }
        });

    }

    private long getTime() {  // Return current UNIX Timestamp in seconds
        return System.currentTimeMillis() / 1000L;
    }
}