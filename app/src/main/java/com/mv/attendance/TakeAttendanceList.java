package com.mv.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TakeAttendanceList extends AppCompatActivity {

    ListView listViewPast;
    ImageButton new_attendance_session_button, button_sync_to_firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance_list);

        listViewPast=findViewById(R.id.listViewPast);
        new_attendance_session_button=findViewById(R.id.buttonNewAttendance);
        button_sync_to_firebase = findViewById(R.id.buttonSyncTOFirebase);


        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();


        List<String> ListElementsArrayList = new ArrayList<>();
        List<AttendanceSession> ListAttendanceSession = new ArrayList<>();
        if(!sh.getString("ListAttendanceSession", "").equals("")){
            //Gson gson = new Gson();
            //String jsonFromPreviousActivityAttendanceSession = sh.getString("ListAttendanceSession", "");
            //ListElementsArrayList = gson.fromJson(jsonFromPreviousActivityAttendanceSession, String.class);
            ListAttendanceSession = getList(sh.getString("ListAttendanceSession", ""), AttendanceSession.class);

        }

        if(ListAttendanceSession.size()>0){
            for(int i=0;i<ListAttendanceSession.size();i++){
                ListElementsArrayList.add(ListAttendanceSession.get(i).title);
                Log.d("QWER", "AttendanceSession ->  " + ListAttendanceSession.get(i).title + " " + ListAttendanceSession.get(i).division + " " + ListAttendanceSession.get(i).subject);
            }
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<> (TakeAttendanceList.this, android.R.layout.simple_list_item_1, ListElementsArrayList);

        listViewPast.setAdapter(adapter);





        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        List<AttendanceSession> finalListAttendanceSession = ListAttendanceSession;
        List<AttendanceSession> finalListAttendanceSession2 = ListAttendanceSession;
        new_attendance_session_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText inputEditTextDialogue = new EditText(getApplicationContext());
                inputEditTextDialogue.setHint("Title");
                final EditText inputDivisionEditTextDialogue = new EditText(getApplicationContext());
                inputDivisionEditTextDialogue.setHint("Division");
                final EditText inputSubjectEditTextDialogue = new EditText(getApplicationContext());
                inputSubjectEditTextDialogue.setHint("Subject");
                LinearLayout layout = new LinearLayout(TakeAttendanceList.this);
                layout.setBackground(ContextCompat.getDrawable(TakeAttendanceList.this,  R.drawable.white_alert_dialogue_background));
                layout.setPadding(20,20,20,0);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
                layout.addView(inputEditTextDialogue);
                layout.addView(inputDivisionEditTextDialogue);
                layout.addView(inputSubjectEditTextDialogue);

                //Setting message manually and performing action on button click
                builder.setMessage("Set a new name for the Attendance Session")
                        .setCancelable(false)
                        .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AttendanceSession attendanceSession = new AttendanceSession();
                                attendanceSession.title = inputEditTextDialogue.getText().toString();
                                attendanceSession.division = inputDivisionEditTextDialogue.getText().toString();
                                attendanceSession.subject = inputSubjectEditTextDialogue.getText().toString();

                                Gson gson = new Gson();
                                String jsonToShareAttendanceSession = gson.toJson(attendanceSession);

                                Intent intent = new Intent(TakeAttendanceList.this, TakeAttendance.class);
                                intent.putExtra("Attendance Session", jsonToShareAttendanceSession);
                                intent.putExtra("CountInListAttendance", finalListAttendanceSession2.size());
                                startActivity(intent);

                                ListElementsArrayList.add(attendanceSession.title);
                                adapter.notifyDataSetChanged();
                                finalListAttendanceSession.add(attendanceSession);
                                String jsonToListElementsAdapterList= gson.toJson(finalListAttendanceSession);
                                myEdit.putString("ListAttendanceSession", jsonToListElementsAdapterList);
                                myEdit.apply();
                                Log.d("QWER", "AttendanceSession ->  " + attendanceSession.title + " " + attendanceSession.division + " " + attendanceSession.subject);
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
                alert.setTitle("Set Title");
                alert.setView(layout);
                Drawable drawable = ContextCompat.getDrawable(TakeAttendanceList.this,  R.drawable.grey_alert_dialogue_background);
                alert.getWindow().setBackgroundDrawable(drawable);
                alert.show();
                WindowManager.LayoutParams lp = alert.getWindow().getAttributes();
                lp.dimAmount = 0.75f;
                alert.getWindow().setAttributes(lp);





            }
        });


        listViewPast.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                builder.setMessage("Do you want to delete this?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Delete the selected item
                        finalListAttendanceSession.remove(i);
                        ListElementsArrayList.remove(i);
                        adapter.notifyDataSetChanged();
                        Gson gson = new Gson();
                        String jsonToListElementsAdapterList= gson.toJson(finalListAttendanceSession);
                        myEdit.putString("ListAttendanceSession", jsonToListElementsAdapterList);
                        myEdit.apply();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.setTitle("Delete");
                alert.show();
                return true;
            }
        });

        List<AttendanceSession> finalListAttendanceSession1 = ListAttendanceSession;
        listViewPast.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AttendanceSession attendanceSession = finalListAttendanceSession1.get(i);
                Gson gson = new Gson();
                String jsonToShareAttendanceSession = gson.toJson(attendanceSession);

                Intent intent = new Intent(TakeAttendanceList.this, TakeAttendanceDisplay.class);
                intent.putExtra("Attendance Session", jsonToShareAttendanceSession);
                intent.putExtra("CountInListAttendance", i);
                startActivity(intent);

            }
        });


        List<AttendanceSession> finalListAttendanceSession3 = ListAttendanceSession;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Division");
        //SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        button_sync_to_firebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable()) {
                    uploadToFirebase(finalListAttendanceSession3, reference, sh);
                    myEdit.putString("ListAttendanceSession", "");
                    myEdit.apply();
                    ListElementsArrayList.clear();
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(TakeAttendanceList.this, "Network Not Available!", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }

    public <T> List<T> getList(String jsonArray, Class<T> clazz) {
        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
        return new Gson().fromJson(jsonArray, typeOfT);
    }


    public void uploadToFirebase(List<AttendanceSession> finalListAttendanceSession3, DatabaseReference reference, SharedPreferences sh){
        if(finalListAttendanceSession3.size()>0){
            int i = 0;
            while (finalListAttendanceSession3.size()> 0){
                //ListElementsArrayList.add(finalListAttendanceSession3.get(i).title);
                uploadAttendanceSessionToFirebase(finalListAttendanceSession3.get(i), reference, sh);
                finalListAttendanceSession3.remove(i);
            }
        }
    }

    public void uploadAttendanceSessionToFirebase(AttendanceSession attendanceSession, DatabaseReference reference, SharedPreferences sh){
        Log.d("QWER", "AttendanceSession ->  " + attendanceSession.title + " " + attendanceSession.division + " " + attendanceSession.subject);
        int student_i = 0;
        if ((attendanceSession.present !=null) &&attendanceSession.present.size() > 0) {
            //reference.child(attendanceSession.division).child(attendanceSession.subject).child(sh.getString("PRN", "ERROR")).child(attendanceSession.title).child("Student").child(String.valueOf(attendanceSession.present.get(student_i).PRN_number)).setValue(attendanceSession.present.get(student_i));
            uploadStudentToFirebase(attendanceSession, reference, sh, attendanceSession.present, attendanceSession.division, attendanceSession.subject, attendanceSession.title, attendanceSession.present.get(student_i));
            //attendanceSession.present.remove(student_i);
        }
        //uploadStudentToFirebase(attendanceSession, reference, sh, attendanceSession.present, attendanceSession.division, attendanceSession.subject, attendanceSession.title, attendanceSession.present.get(student_i));
        //finalListAttendanceSession3.remove(i);
    }

    public void uploadStudentToFirebase(AttendanceSession attendanceSession, DatabaseReference reference, SharedPreferences sh, ArrayList<Student> presentlist, String division, String subject, String title, Student student){
        //reference.child(division).child(subject).child(sh.getString("PRN", "ERROR")).child(title).child("Student").child(String.valueOf(student.PRN_number)).setValue(student);
        Log.d("QWER", "Added");
        Log.d("QWER", "PresentList -> " + presentlist);
        int student_i = 0;
        if(presentlist.size() > 0) {
            Log.d("QWER", "wwwwwwww");
            reference.child(attendanceSession.division).child(attendanceSession.subject).child(sh.getString("PRN", "ERROR")).child(attendanceSession.title).child("Student").child(String.valueOf(presentlist.get(student_i).PRN_number)).setValue(presentlist.get(student_i)).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    presentlist.remove(0);
                    if(presentlist.size() > 0) {
                        uploadStudentToFirebase(attendanceSession, reference, sh, presentlist, attendanceSession.division, attendanceSession.subject, attendanceSession.title, presentlist.get(student_i));
                    }

                }
            });
            //reference.child(attendanceSession.division).child(attendanceSession.subject).child(sh.getString("PRN", "ERROR")).child(attendanceSession.title).child("Student").child(String.valueOf(presentlist.get(student_i).PRN_number)).setValue(presentlist.get(student_i));
            //presentlist.remove(0);
        }
    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}