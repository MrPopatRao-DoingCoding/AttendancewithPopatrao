package com.mv.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SelectModeForTakingAttendance extends AppCompatActivity {

    ImageView setting_image, mode2Img, mode1Img;
    CardView Mode1_card;
    CardView Mode2_card;
    RelativeLayout backgroundRelativeLayout;
    TextView heading_text,TextViewCardView1,TextViewCardView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mode_for_taking_attendance);

        setting_image = findViewById(R.id.setting_image);
        mode2Img = findViewById(R.id.imgOfMode2);
        mode1Img = findViewById(R.id.imgOfMode1);
        Mode1_card = findViewById(R.id.idMode1_card);
        Mode2_card = findViewById(R.id.idMode2_card);
        TextViewCardView1 = findViewById(R.id.textView_CardView1);
        TextViewCardView2 = findViewById(R.id.textView_CardView2);
        backgroundRelativeLayout = findViewById(R.id.activity_home1_background);
        heading_text = findViewById(R.id.idTVHeading);

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        //String type = sh.getString("TypeOfPerson", "Not Set");

        setting_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectModeForTakingAttendance.this, Settings_teacher.class);
                startActivity(intent);
            }
        });

        Mode1_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectModeForTakingAttendance.this, TakeAttendanceList.class);
                startActivity(intent);



                /*SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
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
                    }
                }



                AlertDialog.Builder builder = new AlertDialog.Builder(SelectModeForTakingAttendance.this);
                List<AttendanceSession> finalListAttendanceSession = ListAttendanceSession;
                List<AttendanceSession> finalListAttendanceSession2 = ListAttendanceSession;

                final EditText inputEditTextDialogue = new EditText(getApplicationContext());
                inputEditTextDialogue.setHint("Title");
                LinearLayout layout = new LinearLayout(SelectModeForTakingAttendance.this);
                layout.setBackground(ContextCompat.getDrawable(SelectModeForTakingAttendance.this,  R.drawable.white_alert_dialogue_background));
                layout.setPadding(20,20,20,0);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
                layout.addView(inputEditTextDialogue);

                //Setting message manually and performing action on button click
                builder.setMessage("Set a new name for the Attendance Session")
                        .setCancelable(false)
                        .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AttendanceSession attendanceSession = new AttendanceSession();
                                attendanceSession.title = inputEditTextDialogue.getText().toString();

                                Gson gson = new Gson();
                                String jsonToShareAttendanceSession = gson.toJson(attendanceSession);

                                Intent intent = new Intent(SelectModeForTakingAttendance.this, TakeAttendance.class);
                                intent.putExtra("Attendance Session", jsonToShareAttendanceSession);
                                intent.putExtra("CountInListAttendance", finalListAttendanceSession2.size());
                                startActivity(intent);

                                //ListElementsArrayList.add(attendanceSession.title);
                                //adapter.notifyDataSetChanged();
                                finalListAttendanceSession.add(attendanceSession);
                                String jsonToListElementsAdapterList= gson.toJson(finalListAttendanceSession);
                                myEdit.putString("ListAttendanceSession", jsonToListElementsAdapterList);
                                myEdit.apply();
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
                Drawable drawable = ContextCompat.getDrawable(SelectModeForTakingAttendance.this,  R.drawable.grey_alert_dialogue_background);
                alert.getWindow().setBackgroundDrawable(drawable);
                alert.show();
                WindowManager.LayoutParams lp = alert.getWindow().getAttributes();
                lp.dimAmount = 0.75f;
                alert.getWindow().setAttributes(lp);*/
            }
        });

        Mode2_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(SelectModeForTakingAttendance.this, AttendanceListViewMode2.class);
                startActivity(intent);*/

                AlertDialog.Builder builder = new AlertDialog.Builder(SelectModeForTakingAttendance.this);
                LinearLayout layout = new LinearLayout(SelectModeForTakingAttendance.this);
                layout.setBackgroundColor(Color.parseColor("#EEEEEE"));
                Drawable drawable = ContextCompat.getDrawable(SelectModeForTakingAttendance.this,  R.drawable.white_alert_dialogue_background);
                layout.setBackground(drawable);
                layout.setPadding(20,20,20,0);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

                final EditText inputEditTextTitle = new EditText(getApplicationContext());
                final EditText inputEditTextNameOfTeacher = new EditText(getApplicationContext());
                final EditText divisionOfClass = new EditText(getApplicationContext());
                //final EditText inputEditTextRollNoOfStudent = new EditText(getApplicationContext());
                inputEditTextTitle.setHint("Title");
                inputEditTextNameOfTeacher.setHint("Teacher PRN");
                inputEditTextNameOfTeacher.setText(sh.getString("PRN", ""));
                divisionOfClass.setHint("Division");
                //inputEditTextRollNoOfStudent.setHint("Roll No.");
                //inputEditTextRollNoOfStudent.setInputType(InputType.TYPE_CLASS_NUMBER);
                layout.addView(inputEditTextTitle);
                layout.addView(inputEditTextNameOfTeacher);
                layout.addView(divisionOfClass);
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
                                Intent intent = new Intent(SelectModeForTakingAttendance.this, Mode2TakeAttendanceShowQRCode.class);
                                intent.putExtra("Title", inputEditTextTitle.getText().toString());
                                intent.putExtra("Teacher_PRN", inputEditTextNameOfTeacher.getText().toString());
                                intent.putExtra("Division", divisionOfClass.getText().toString());
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
                drawable = ContextCompat.getDrawable(SelectModeForTakingAttendance.this,  R.drawable.grey_alert_dialogue_background);
                alert.getWindow().setBackgroundDrawable(drawable);
                alert.show();
                WindowManager.LayoutParams lp = alert.getWindow().getAttributes();
                lp.dimAmount = 0.75f;
                alert.getWindow().setAttributes(lp);

            }
        });
    }


    public <T> List<T> getList(String jsonArray, Class<T> clazz) {
        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
        return new Gson().fromJson(jsonArray, typeOfT);
    }



    @Override
    protected void onStart() {
        super.onStart();
        AnimatedVectorDrawable d = (AnimatedVectorDrawable) getDrawable(R.drawable.projector_mode2_animation);
        AnimatedVectorDrawable d_rev = (AnimatedVectorDrawable) getDrawable(R.drawable.projector_mode2_animation_reverese);

        mode2Img.setImageDrawable(d);
        //d.start();

        d.registerAnimationCallback(new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        mode2Img.setImageDrawable(d_rev);
                        d_rev.start();
                        //Log.d("QWERT", "unfdnvvfvfvffvvvvvvvvvvvvvvvvvvv");
                    }
                }, 800);

            }
        });

        d_rev.registerAnimationCallback(new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        mode2Img.setImageDrawable(d);
                        d.start();
                    }
                }, 800);

            }
        });

        d.start();




        AnimatedVectorDrawable d2 = (AnimatedVectorDrawable) getDrawable(R.drawable.phone_qr_mode1_animation);
        AnimatedVectorDrawable d2_rev = (AnimatedVectorDrawable) getDrawable(R.drawable.phone_qr_mode1_animation_reverse);

        mode1Img.setImageDrawable(d2);
        //d.start();

        d.registerAnimationCallback(new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        mode1Img.setImageDrawable(d2_rev);
                        d2_rev.start();
                        //Log.d("QWERT", "unfdnvvfvfvffvvvvvvvvvvvvvvvvvvv");
                    }
                }, 1200);

            }
        });

        d_rev.registerAnimationCallback(new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        mode1Img.setImageDrawable(d2);
                        d2.start();
                    }
                }, 1200);

            }
        });

        d.start();
        /*Drawable d = setting_image.getDrawable();
        if (d instanceof AnimatedVectorDrawable) {
            Log.d("QWERT", "onCreate: instancefound" + d.toString());
            animationOfSettings = (AnimatedVectorDrawable) d;
            animationOfSettings.start();
        }*/
    }
}