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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AttendanceListViewMode2 extends AppCompatActivity {

    ListView listViewPast;
    ImageButton new_attendance_session_button, button_sort_alphabetically;

    FirebaseDatabase database;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list_view_mode2);

        listViewPast=findViewById(R.id.listViewPast);
        new_attendance_session_button=findViewById(R.id.buttonNewAttendanceMode2ThroughQRCode);

        List<String> ListElementsArrayList = new ArrayList<>();
        //ListElementsArrayList.add("Hello");
        List<Lecture> ListOfAttendanceSessions = new ArrayList<>();
        /*if(!sh.getString("ListAttendanceSession", "").equals("")){
            //Gson gson = new Gson();
            //String jsonFromPreviousActivityAttendanceSession = sh.getString("ListAttendanceSession", "");
            //ListElementsArrayList = gson.fromJson(jsonFromPreviousActivityAttendanceSession, String.class);
            ListOfAttendanceSessions = getList(sh.getString("ListAttendanceSession", ""), AttendanceSession.class);

        }*/

        if(ListOfAttendanceSessions.size()>0){
            for(int i=0;i<ListOfAttendanceSessions.size();i++){
                ListElementsArrayList.add(ListOfAttendanceSessions.get(i).Title);
            }
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<> (AttendanceListViewMode2.this, android.R.layout.simple_list_item_1, ListElementsArrayList);
        listViewPast.setAdapter(adapter);

        listViewPast.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                    ListElementsArrayList.remove(i);
                    adapter.notifyDataSetChanged();

                    return true;
                }
            }
        );

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        new_attendance_session_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AttendanceListViewMode2.this);
                LinearLayout layout = new LinearLayout(AttendanceListViewMode2.this);
                layout.setBackgroundColor(Color.parseColor("#EEEEEE"));
                Drawable drawable = ContextCompat.getDrawable(AttendanceListViewMode2.this,  R.drawable.white_alert_dialogue_background);
                layout.setBackground(drawable);
                layout.setPadding(20,20,20,0);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

                final EditText inputEditTextTitle = new EditText(getApplicationContext());
                final EditText inputEditTextNameOfTeacher = new EditText(getApplicationContext());
                //final EditText inputEditTextRollNoOfStudent = new EditText(getApplicationContext());
                inputEditTextTitle.setHint("Title");
                inputEditTextNameOfTeacher.setHint("Teacher Name");
                inputEditTextNameOfTeacher.setText(sh.getString("Name", ""));
                //inputEditTextRollNoOfStudent.setHint("Roll No.");
                //inputEditTextRollNoOfStudent.setInputType(InputType.TYPE_CLASS_NUMBER);
                layout.addView(inputEditTextTitle);
                layout.addView(inputEditTextNameOfTeacher);
                //layout.addView(inputEditTextRollNoOfStudent);
                builder.setMessage("Add a new lecture")
                        .setCancelable(false)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                /*Student student = new Student(Integer.parseInt(inputEditTextRollNoOfStudent.getText().toString()), inputEditTextDivOfStudent.getText().toString(), inputEditTextNameOfStudent.getText().toString());
                                finalListAttendanceSession[0].get(countInListAttendance).addStudent(student);
                                Gson gson = new Gson();
                                String jsonToShareAttendanceSession = gson.toJson(attendanceSession);


                                String jsonToListElementsAdapterList= gson.toJson(finalListAttendanceSession[0]);
                                myEdit.putString("ListAttendanceSession", jsonToListElementsAdapterList);
                                myEdit.apply();
                                mainDisplayAttendanceTextView.setText(finalListAttendanceSession[0].get(countInListAttendance).generateStringNonRepeatative());*/
                                Intent intent = new Intent(AttendanceListViewMode2.this, Mode2TakeAttendanceShowQRCode.class);
                                intent.putExtra("Title", inputEditTextTitle.getText().toString());
                                intent.putExtra("TeacherName", inputEditTextNameOfTeacher.getText().toString());
                                startActivity(intent);
                                dialog.dismiss();
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
                alert.setTitle("New Attendance Session");
                alert.setView(layout);
                drawable = ContextCompat.getDrawable(AttendanceListViewMode2.this,  R.drawable.grey_alert_dialogue_background);
                alert.getWindow().setBackgroundDrawable(drawable);
                alert.show();
                WindowManager.LayoutParams lp = alert.getWindow().getAttributes();
                lp.dimAmount = 0.75f;
                alert.getWindow().setAttributes(lp);
            }
        });

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Lectures");

        reference.child(sh.getString("Name", "")).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){

                    DataSnapshot dataSnapshot = task.getResult();
                    String name = String.valueOf(dataSnapshot.getValue());
                    //System.out.println(name);
                    Log.d("QWERT", "Data:  " + name);
                    Log.d("QWERT", "Children:  " + dataSnapshot.getChildren());
                    Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();
                    List<String> childrenList = new ArrayList<>();
                    while(i.hasNext()){
                        //Log.d("QWERT", "ChildrenValues:  " + i.next());
                        childrenList.add(i.next().getKey());
                    }

                    Log.d("QWERT", "Titles   - " + String.valueOf(childrenList));

                    


                }

            }
        });




    }
}