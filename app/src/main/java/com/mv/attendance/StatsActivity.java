package com.mv.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class StatsActivity extends AppCompatActivity {

    TextView tvR, tvPython;
    PieChart pieChart;

    int totalClasses = 0;
    int presentClasses = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);


        tvR = findViewById(R.id.tvR);
        tvPython = findViewById(R.id.tvPython);
        pieChart = findViewById(R.id.piechart);

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
                    tvR.setText(Integer.toString(presentClasses));
                    tvPython.setText(Integer.toString(totalClasses-presentClasses));

                    // Set the data and color to the pie chart
                    pieChart.addPieSlice(
                            new PieModel(
                                    "R",
                                    Integer.parseInt(tvR.getText().toString()),
                                    Color.parseColor("#FFA726")));
                    pieChart.addPieSlice(
                            new PieModel(
                                    "Python",
                                    Integer.parseInt(tvPython.getText().toString()),
                                    Color.parseColor("#66BB6A")));
                    // To animate the pie chart
                    pieChart.startAnimation();

                }
            }
        });


    }

}