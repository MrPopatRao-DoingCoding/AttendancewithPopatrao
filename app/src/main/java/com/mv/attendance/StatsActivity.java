package com.mv.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
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

    List<String> subjTitle;
    List<Integer> absent;
    List<Integer> present;


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

        present = new ArrayList<>();
        //present.add(2);
        //present.add(12);
        absent = new ArrayList<>();
        //absent.add(3);
        //absent.add(9);
        subjTitle = new ArrayList<>();
        //subjTitle.add("Maths");
        //subjTitle.add("Science");


        //final ArrayAdapter<String> adapter = new ArrayAdapter<>(StatsActivity.this, android.R.layout.simple_list_item_1, listOfSubjects);
        //subjectListView.setAdapter(adapter);


        setData();

        subjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StatsActivity.this);
                LinearLayout layout = new LinearLayout(StatsActivity.this);
                layout.setBackgroundColor(Color.parseColor("#EEEEEE"));
                Drawable drawable = ContextCompat.getDrawable(StatsActivity.this,  R.drawable.white_alert_dialogue_background);
                layout.setBackground(drawable);
                layout.setPadding(20,20,20,0);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

                final EditText inputEditTextNameOfStudent = new EditText(getApplicationContext());
                final EditText inputEditTextDivOfStudent = new EditText(getApplicationContext());
                final EditText inputEditTextRollNoOfStudent = new EditText(getApplicationContext());
                inputEditTextNameOfStudent.setHint("Name");
                inputEditTextDivOfStudent.setHint("Div");
                inputEditTextRollNoOfStudent.setHint("Roll No.");
                inputEditTextRollNoOfStudent.setInputType(InputType.TYPE_CLASS_NUMBER);
                layout.addView(inputEditTextNameOfStudent);
                layout.addView(inputEditTextDivOfStudent);
                layout.addView(inputEditTextRollNoOfStudent);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("New Student");
                alert.setView(layout);
                drawable = ContextCompat.getDrawable(StatsActivity.this,  R.drawable.grey_alert_dialogue_background);
                alert.getWindow().setBackgroundDrawable(drawable);
                alert.show();
                WindowManager.LayoutParams lp = alert.getWindow().getAttributes();
                lp.dimAmount = 0.75f;
                alert.getWindow().setAttributes(lp);
            }
        });





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
                    Iterator<DataSnapshot> subjectsIterator = dataSnapshot.getChildren().iterator();
                    List<String> listOfSubjects = new ArrayList<>();
                    while (subjectsIterator.hasNext()) {
                        //Log.d("QWERT", "ChildrenValues:  " + i.next());
                        listOfSubjects.add(subjectsIterator.next().getKey());
                    }
                    for(int iiiii=0;iiiii<listOfSubjects.size(); iiiii++){
                        Iterator<DataSnapshot> i = dataSnapshot.child(listOfSubjects.get(iiiii)).getChildren().iterator();
                        List<String> listOfTeachers = new ArrayList<>();
                        while (i.hasNext()) {
                            //Log.d("QWERT", "ChildrenValues:  " + i.next());
                            listOfTeachers.add(i.next().getKey());
                        }
                        subjTitle.add(listOfSubjects.get(iiiii));
                        int numberClassesPresentInSubject = 0;
                        int totalNumberClassesInSubject = 0;
                        Log.d("QWERT", "ListOfTeachers   ->   " + listOfTeachers);
                        Log.d("QWERT", "ListOfTeachersSnapShot   ->   " + dataSnapshot);
                        Log.d("QWERT", "ListOfTeachersSnapShotValue   ->   " + dataSnapshot.getValue());
                        for(int ii=0;ii<listOfTeachers.size(); ii++){
                            Log.d("QWERT", "TeacherInfo   ->   " + listOfTeachers.get(ii) + " - " + dataSnapshot.child(listOfTeachers.get(ii)).getValue());
                            Iterator <DataSnapshot> lectureNames = dataSnapshot.child(listOfSubjects.get(iiiii)).child(listOfTeachers.get(ii)).getChildren().iterator();
                            totalClasses += dataSnapshot.child(listOfSubjects.get(iiiii)).child(listOfTeachers.get(ii)).getChildrenCount();
                            totalNumberClassesInSubject = (int) dataSnapshot.child(listOfSubjects.get(iiiii)).child(listOfTeachers.get(ii)).getChildrenCount();
                            List<String> listOfClasses = new ArrayList<>();
                            while (lectureNames.hasNext()) {
                                //Log.d("QWERT", "ChildrenValues:  " + i.next());
                                listOfClasses.add(lectureNames.next().getKey());
                            }
                            for(int iii=0;iii<listOfClasses.size(); iii++){
                                Log.d("QWERT", "Children Present    -> " + dataSnapshot.child(listOfSubjects.get(iiiii)).child(listOfTeachers.get(ii)).child(listOfClasses.get(iii)).child("Student").getValue());
                                Iterator <DataSnapshot> childrenPresentNames = dataSnapshot.child(listOfSubjects.get(iiiii)).child(listOfTeachers.get(ii)).child(listOfClasses.get(iii)).child("Student").getChildren().iterator();
                                while (childrenPresentNames.hasNext()){
                                    if (Objects.equals(childrenPresentNames.next().getKey(), sh.getString("PRN", " "))){
                                        presentClasses += 1;
                                        numberClassesPresentInSubject += 1;
                                        Log.d("QWERT", "Present!!");
                                    }
                                }
                            }
                        }
                        present.add(numberClassesPresentInSubject);
                        absent.add(totalNumberClassesInSubject-numberClassesPresentInSubject);
                    }
                    //Log.d("QWERT", "ListOfTeachersSnapShotValue   ->   " + dataSnapshot.child(Objects.requireNonNull(dataSnapshot.getKey())));
                    Log.d("QWERT", "Total     -> " + totalClasses);
                    Log.d("QWERT", "Present   -> " + presentClasses);

                    setDataToListView();


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

    private void setDataToListView() {
        CustomListViewAdapter customAdapter = new CustomListViewAdapter(StatsActivity.this, present, absent, subjTitle);
        //CustomListViewAdapter customAdapter = new CustomListViewAdapter(getApplicationContext(), subjTitle);
        subjectListView.setAdapter(customAdapter);
    }

}