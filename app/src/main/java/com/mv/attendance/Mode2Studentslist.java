package com.mv.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Mode2Studentslist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode2_studentslist);

        String division = getIntent().getExtras().getString("DivisionSelected");
        String nameOfSession = getIntent().getExtras().getString("NameOfClass");
        String subject = getIntent().getExtras().getString("Subject");
        Log.d("QWERT", nameOfSession);

        ListView listViewOfStudents=findViewById(R.id.listViewPast);

        List<String> listOfStudents = new ArrayList<>();


        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Division");

        Log.d("QWERT", "Div, Sub, name = " + division + subject + nameOfSession);

        reference.child(division).child(subject).child(sh.getString("Name","")).child(nameOfSession).child("Student").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();
                    while (i.hasNext()) {
                        //Log.d("QWERT", "ChildrenValues:  " + i.next());
                        //listOfStudents.add(i.next().getKey());
                        String PRNOfStudent = i.next().getKey();
                        String nameOfStudent  = dataSnapshot.child(PRNOfStudent).child("name").getValue().toString();
                        String divOfStudent  = dataSnapshot.child(PRNOfStudent).child("div").getValue().toString();
                        String rollNoOfStudent  = dataSnapshot.child(PRNOfStudent).child("rollNo").getValue().toString();
                        long timeOfStudent  = Long.parseLong(dataSnapshot.child(PRNOfStudent).child("time").getValue().toString());
                        String stringToAdd = formatToSpecificLength(nameOfStudent, 10, 2) + " " + divOfStudent + " " + formatToSpecificLength(rollNoOfStudent, 2, 0);// + " " + timeOfStudent;
                        listOfStudents.add(stringToAdd);
                    }
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(Mode2Studentslist.this, android.R.layout.simple_list_item_1, listOfStudents);
                    listViewOfStudents.setAdapter(adapter);

                    /*listViewOfStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        }
                    });*/
                }
            }
        });
    }

    private String formatToSpecificLength(String inputString, int length, int numberOfDotsIfExceeded){
        Log.d("QWERT", "StrLengthOrig -> " + inputString.length());
        if(inputString.length() > length-numberOfDotsIfExceeded){
            Log.d("QWERT", "StrLengthNew -> " + inputString.substring(0, length-numberOfDotsIfExceeded-1) + "..");
            return inputString.substring(0, length-numberOfDotsIfExceeded-1) + "..";
        }
        StringBuilder inputStringBuilder = new StringBuilder(inputString);
        while (inputStringBuilder.length() < length-numberOfDotsIfExceeded+1){
            inputStringBuilder.append(" ");
        }
        inputString = inputStringBuilder.toString();
        Log.d("QWERT", "StrLengthNew -> " + inputString.length() + inputString + "|");
        return inputString;
    }
}