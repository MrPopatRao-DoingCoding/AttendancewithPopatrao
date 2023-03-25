package com.mv.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class StatsActivity extends AppCompatActivity {

    TextView tvPresent, tvAbsent;
    PieChart pieChart;

    ListView subjectListView;

    int totalClasses = 0;
    int presentClasses = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);


        tvPresent = findViewById(R.id.textView_Present);
        tvAbsent = findViewById(R.id.textView_Absent);
        pieChart = findViewById(R.id.piechart);
        subjectListView = findViewById(R.id.subjectListView);

        List<String> listOfSubjects = new ArrayList<>();
        listOfSubjects.add("123");
        listOfSubjects.add("1234");
        listOfSubjects.add("12345");
        listOfSubjects.add("123456");

        List<Integer> present = new ArrayList<>();
        present.add(2);
        present.add(12);
        List<Integer> absent = new ArrayList<>();
        absent.add(3);
        absent.add(9);
        List<String> subjTitle = new ArrayList<>();
        subjTitle.add("Maths");
        subjTitle.add("Science");


        //final ArrayAdapter<String> adapter = new ArrayAdapter<>(StatsActivity.this, android.R.layout.simple_list_item_1, listOfSubjects);
        //subjectListView.setAdapter(adapter);

        CustomListViewAdapter customAdapter = new CustomListViewAdapter(getApplicationContext(), present, absent, subjTitle);
        //CustomListViewAdapter customAdapter = new CustomListViewAdapter(getApplicationContext(), subjTitle);
        subjectListView.setAdapter(customAdapter);



        setData();



    }

    private void setData()
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Division");

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        reference.child(sh.getString("Div", "")).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();
                    List<String> listOfTeachers = new ArrayList<>();
                    while (i.hasNext()) {
                        //Log.d("QWERT", "ChildrenValues:  " + i.next());
                        listOfTeachers.add(i.next().getKey());
                    }
                    Log.d("QWERT", "ListOfTeachers   ->   " + listOfTeachers);
                    Log.d("QWERT", "ListOfTeachersSnapShot   ->   " + dataSnapshot);
                    Log.d("QWERT", "ListOfTeachersSnapShotValue   ->   " + dataSnapshot.getValue());
                    for(int ii=0;ii<listOfTeachers.size(); ii++){
                        Log.d("QWERT", "TeacherInfo   ->   " + listOfTeachers.get(ii) + " - " + dataSnapshot.child(listOfTeachers.get(ii)).getValue());
                        Iterator <DataSnapshot> lectureNames = dataSnapshot.child(listOfTeachers.get(ii)).getChildren().iterator();
                        totalClasses += dataSnapshot.child(listOfTeachers.get(ii)).getChildrenCount();
                        List<String> listOfClasses = new ArrayList<>();
                        while (lectureNames.hasNext()) {
                            //Log.d("QWERT", "ChildrenValues:  " + i.next());
                            listOfClasses.add(lectureNames.next().getKey());
                        }
                        for(int iii=0;iii<listOfClasses.size(); iii++){
                            Log.d("QWERT", "Children Present    -> " + dataSnapshot.child(listOfTeachers.get(ii)).child(listOfClasses.get(iii)).child("Student").getValue());
                            Iterator <DataSnapshot> childrenPresentNames = dataSnapshot.child(listOfTeachers.get(ii)).child(listOfClasses.get(iii)).child("Student").getChildren().iterator();
                            while (childrenPresentNames.hasNext()){
                                if (Objects.equals(childrenPresentNames.next().getKey(), sh.getString("PRN", " "))){
                                    presentClasses += 1;
                                    Log.d("QWERT", "Present!!");
                                }
                            }
                        }
                    }
                    //Log.d("QWERT", "ListOfTeachersSnapShotValue   ->   " + dataSnapshot.child(Objects.requireNonNull(dataSnapshot.getKey())));
                    Log.d("QWERT", "Total     -> " + totalClasses);
                    Log.d("QWERT", "Present   -> " + presentClasses);


                    // Set the percentage of language used
                    tvPresent.setText(Integer.toString(presentClasses));
                    tvAbsent.setText(Integer.toString(totalClasses-presentClasses));

                    // Set the data and color to the pie chart
                    pieChart.addPieSlice(
                            new PieModel(
                                    "Present",
                                    Integer.parseInt(tvPresent.getText().toString()),
                                    Color.parseColor("#00FB54")));
                    pieChart.addPieSlice(
                            new PieModel(
                                    "Absent",
                                    Integer.parseInt(tvAbsent.getText().toString()),
                                    Color.parseColor("#FF0000")));
                    // To animate the pie chart
                    pieChart.startAnimation();

                }
            }
        });


    }

}