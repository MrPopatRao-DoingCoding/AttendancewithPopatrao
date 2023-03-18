package com.mv.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HomeActivity1 extends AppCompatActivity {

    ImageView setting_image;
    CardView Mode1_card;
    CardView Mode2_card;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);

        setting_image = findViewById(R.id.setting_image);
        Mode1_card = findViewById(R.id.idMode1_card);
        Mode2_card = findViewById(R.id.idMode2_card);

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String type = sh.getString("TypeOfPerson", "Not Set");

        setting_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity1.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        Mode1_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("Teacher")) {
                    Intent intent = new Intent(HomeActivity1.this, TakeAttendanceList.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(HomeActivity1.this, GiveAttendance.class);
                    startActivity(intent);
                }
            }
        });

        Mode2_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("Teacher")) {
                    Intent intent = new Intent(HomeActivity1.this, AttendanceListViewMode2.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(HomeActivity1.this, GiveAttendance2.class);
                    startActivity(intent);
                }
            }
        });
    }
}