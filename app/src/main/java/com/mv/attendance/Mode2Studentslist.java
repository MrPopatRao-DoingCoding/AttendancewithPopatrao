package com.mv.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

        List<String> name_list = new ArrayList<>();

        List<String> Division_list = new ArrayList<>();

        List<String> Roll_list = new ArrayList<>();

        List<String> PRN_list = new ArrayList<>();


        ArrayList<ListElement_class> student_list = new ArrayList<>();


        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Division");

        Log.d("QWERT", "Div, Sub, name = " + division + subject + nameOfSession);

        reference.child(division).child(subject).child(sh.getString("PRN","")).child(nameOfSession).child("Student").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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
                        PRN_list.add(PRNOfStudent);
                        name_list.add(nameOfStudent);
                        Division_list.add(divOfStudent);
                        Roll_list.add(rollNoOfStudent);
                        ListElement_class element = new ListElement_class(stringToAdd,R.drawable.student);
                        student_list.add(element);
                    }
                    //final ArrayAdapter<String> adapter = new ArrayAdapter<>(Mode2Studentslist.this, android.R.layout.simple_list_item_1, listOfStudents);
                    //listViewOfStudents.setAdapter(adapter);

                    studentlist_adapter listAdapter = new studentlist_adapter(Mode2Studentslist.this, student_list );
                    listViewOfStudents.setAdapter(listAdapter);

                    listViewOfStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(Mode2Studentslist.this);
                            LinearLayout layout = new LinearLayout(Mode2Studentslist.this);
                            layout.setBackgroundColor(Color.parseColor("#EEEEEE"));
                            Drawable drawable = ContextCompat.getDrawable(Mode2Studentslist.this,  R.drawable.white_alert_dialogue_background);
                            layout.setBackground(drawable);
                            layout.setPadding(20,20,20,0);
                            layout.setOrientation(LinearLayout.VERTICAL);
                            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

                            final TextView Name = new TextView(getApplicationContext());
                            final TextView Roll = new TextView(getApplicationContext());
                            final TextView Div = new TextView(getApplicationContext());
                            final TextView PRN = new TextView(getApplicationContext());
                            /*
                            inputEditTextTitle.setHint("Title");
                            inputEditTextNameOfTeacher.setHint("Teacher PRN");
                            inputEditTextNameOfTeacher.setText(sh.getString("PRN", ""));
                            divisionOfClass.setHint("Division");

                            */
                            layout.addView(Name);
                            layout.addView(Roll);
                            layout.addView(Div);
                            layout.addView(PRN);
                            Name.setText(name_list.get(i));
                            Roll.setText(Roll_list.get(i));
                            Div.setText(division);
                            PRN.setText(PRN_list.get(i));
                            builder.setMessage(nameOfSession)
                                    .setCancelable(false)
                                    .setPositiveButton("REMOVE", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            DatabaseReference reference = database.getReference("Division");

                                            reference.child(division).child(subject).child(sh.getString("PRN","")).child(nameOfSession).child("Student").child(PRN_list.get(i)).removeValue();
                                            student_list.remove(i);

                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //  Action for 'NO' Button
                                            dialog.cancel();
                                        }
                                    });
                            //Creating dialog box
                            AlertDialog alert = builder.create();
                            //Setting the title manually
                            alert.setTitle("Selected Student");
                            alert.setView(layout);
                            drawable = ContextCompat.getDrawable(Mode2Studentslist.this,  R.drawable.grey_alert_dialogue_background);
                            alert.getWindow().setBackgroundDrawable(drawable);
                            alert.show();
                            WindowManager.LayoutParams lp = alert.getWindow().getAttributes();
                            lp.dimAmount = 0.75f;
                            alert.getWindow().setAttributes(lp);
                        }



                    });
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