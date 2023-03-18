package com.mv.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewUser extends AppCompatActivity {

    CardView studentCardView, teacherCardView;
    EditText teacherPhoneNo;
    Button teacherSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        studentCardView = findViewById(R.id.cardViewStudent);
        teacherCardView = findViewById(R.id.cardViewTeacher);
        teacherPhoneNo = findViewById(R.id.teacherInputPhoneNo);
        teacherSubmitButton = findViewById(R.id.teacherInputSubmitButton);

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();

        studentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myEdit.putString("TypeOfPerson", "Student");
                myEdit.apply();
                Intent intent = new Intent(NewUser.this, HomeActivity1.class);
                startActivity(intent);
                intent = new Intent(NewUser.this, SettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        teacherCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myEdit.putString("TypeOfPerson", "Teacher");
                myEdit.apply();
                studentCardView.setVisibility(View.GONE);
                teacherCardView.setVisibility(View.GONE);
                teacherPhoneNo.setVisibility(View.VISIBLE);
                teacherSubmitButton.setVisibility(View.VISIBLE);
            }
        });

        teacherSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Upload number on Firebase
                Intent intent = new Intent(NewUser.this, HomeActivity1.class);
                startActivity(intent);
                finish();
            }
        });

    }
}