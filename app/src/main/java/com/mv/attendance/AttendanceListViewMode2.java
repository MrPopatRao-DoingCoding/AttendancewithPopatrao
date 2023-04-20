package com.mv.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
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
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AttendanceListViewMode2 extends AppCompatActivity {

    ListView listViewPast;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list_view_mode2);

        // Linking corresponding XML views to variables
        listViewPast=findViewById(R.id.listViewPast);

        // Shared Preferences
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        // Getting FireBase Realtime Database instance
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Division");

        List<String> divisionListOfTeacher = new ArrayList<>();  // List of Divisions where teacher has taught (taken attendance)
        ArrayList<ListElement_class> element_ArrayList = new ArrayList<ListElement_class>();  // Arraylist of elements to be shown in listview

        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {  // Retrieve data from database
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {  // If data retrieval is successful
                    DataSnapshot dataSnapshot = task.getResult();
                    Iterator<DataSnapshot> divisionsIterator = dataSnapshot.getChildren().iterator();  // Iterate through all divisions
                    while (divisionsIterator.hasNext()) {
                        String division = divisionsIterator.next().getKey().toString();  // current division selected
                        Log.d("QWERT", "DivisionChecking -> " + division);
                        Iterator<DataSnapshot> subjectInDivisionsIterator = dataSnapshot.child(division).getChildren().iterator();  // Iterate through all subjects from given Division
                        while (subjectInDivisionsIterator.hasNext()){
                            boolean inDivision = false;
                            String currentSubjects = subjectInDivisionsIterator.next().getKey().toString();  // current subject selected
                            Log.d("QWERT", "Subject -> " + currentSubjects);
                            Iterator<DataSnapshot> teacherInSubjectInDivisionsIterator = dataSnapshot.child(division).child(currentSubjects).getChildren().iterator();  // Iterate through all teachers from given subject
                            while (teacherInSubjectInDivisionsIterator.hasNext()){
                                String teacherName = teacherInSubjectInDivisionsIterator.next().getKey().toString();
                                Log.d("QWERT", "Name -> " + teacherName);
                                if(teacherName.equals(sh.getString("PRN", ""))){  // Check if teacherName PRN is name of user of the app
                                    divisionListOfTeacher.add(division);  // Add division to the list divisionListOfTeacher
                                    ListElement_class element = new ListElement_class(division, R.drawable.training);  // Assign drawable to the specific subject
                                    element_ArrayList.add(element);  // Add element to arraylist to be shown in ListView
                                    inDivision = true;  // The teacher teaches the selected division
                                    break;
                                }
                            }
                            if(inDivision){  // If the teacher teaching the division is already true, then exit checking the current division and move to the next division
                                break;
                            }
                        }
                    }


                    ListAdapter_cardview listAdapter = new ListAdapter_cardview(AttendanceListViewMode2.this,element_ArrayList);
                    listViewPast.setAdapter(listAdapter);  // Attach listViewAdapter to the ListView in XML

                    listViewPast.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            // Start next activity after a division is selected
                            Intent intent = new Intent(AttendanceListViewMode2.this, Mode2Teacher_DivisonList.class);
                            intent.putExtra("DivisionSelected", divisionListOfTeacher.get(i));
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }

    private void positiveButtonClickedAlertDialog(EditText inputEditTextTitle, EditText inputEditTextNameOfTeacher, EditText divisionOfClass) {
        Intent intent = new Intent(AttendanceListViewMode2.this, Mode2TakeAttendanceShowQRCode.class);
        intent.putExtra("Title", inputEditTextTitle.getText().toString());
        intent.putExtra("Teacher_PRN", inputEditTextNameOfTeacher.getText().toString());
        intent.putExtra("Division", divisionOfClass.getText().toString());
        startActivity(intent);
    }

    private LinearLayout createAlertDialogLayout(SharedPreferences sh, Drawable drawable, EditText inputEditTextTitle, EditText inputEditTextNameOfTeacher, EditText divisionOfClass) {
        // Setting different parameters for the linearlayout
        LinearLayout layout = new LinearLayout(AttendanceListViewMode2.this);
        layout.setBackgroundColor(Color.parseColor("#EEEEEE"));
        layout.setBackground(drawable);
        layout.setPadding(20,20,20,0);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

        // Setting parameters to editTexts
        inputEditTextTitle.setHint("Title");
        inputEditTextNameOfTeacher.setHint("Teacher PRN");
        inputEditTextNameOfTeacher.setText(sh.getString("PRN", ""));
        divisionOfClass.setHint("Division");
        // Add editTexts to linearlayout
        layout.addView(inputEditTextTitle);
        layout.addView(inputEditTextNameOfTeacher);
        layout.addView(divisionOfClass);
        return layout;
    }
}