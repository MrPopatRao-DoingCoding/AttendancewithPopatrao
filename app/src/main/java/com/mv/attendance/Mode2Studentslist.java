package com.mv.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

        String nameOfSession = getIntent().getExtras().getString("NameOfClass");
        Log.d("QWERT", nameOfSession);

        ListView listViewOfStudents=findViewById(R.id.listViewPast);

        List<String> listOfStudents = new ArrayList<>();


        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Lectures");

        reference.child(sh.getString("Name", "")).child(nameOfSession).child("Student").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();
                    while (i.hasNext()) {
                        //Log.d("QWERT", "ChildrenValues:  " + i.next());
                        listOfStudents.add(i.next().getKey());
                    }
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(Mode2Studentslist.this, android.R.layout.simple_list_item_1, listOfStudents);
                    listViewOfStudents.setAdapter(adapter);
                }
            }
        });
    }
}