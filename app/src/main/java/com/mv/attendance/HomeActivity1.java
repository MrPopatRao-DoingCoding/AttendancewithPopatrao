package com.mv.attendance;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.Executor;

public class HomeActivity1 extends AppCompatActivity {

    ImageView setting_image, mode2Img, mode1Img;
    CardView Mode1_card;
    CardView Mode2_card;
    CardView Stats_card;
    RelativeLayout backgroundRelativeLayout;
    TextView heading_text,TextViewCardView1,TextViewCardView2;
    BiometricPrompt biometricPrompt;

    //private AnimatedVectorDrawable animationOfSettings;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);

        // Linking variables with views in XML
        setting_image = findViewById(R.id.setting_image);
        mode2Img = findViewById(R.id.imgOfMode2);
        mode1Img = findViewById(R.id.imgOfMode1);
        Mode1_card = findViewById(R.id.idMode1_card);
        Mode2_card = findViewById(R.id.idMode2_card);
        Stats_card = findViewById(R.id.idStats_card);
        TextViewCardView1 = findViewById(R.id.textView_CardView1);
        TextViewCardView2 = findViewById(R.id.textView_CardView2);
        backgroundRelativeLayout = findViewById(R.id.activity_home1_background);
        heading_text = findViewById(R.id.idTVHeading);

        // Shared Preferences
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String typeOfPerson = sh.getString("TypeOfPerson", "Not Set");

        if(typeOfPerson.equals("Teacher")){  // If user is teacher, switch type to teacher
            switchToTeacher();
        }

        setting_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeOfPerson.equals("Teacher")) {
                    // If teacher, start teacher settings activity
                    Intent intent = new Intent(HomeActivity1.this, Settings_teacher.class);
                    startActivity(intent);
                }
                else{
                    // If student, start student settings activity
                    Intent intent = new Intent(HomeActivity1.this, SettingsActivity.class);
                    startActivity(intent);
                }
            }
        });

        Mode1_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If teacher, go to activity for selecting mode of attendance
                if (typeOfPerson.equals("Teacher")) {
                    Intent intent = new Intent(HomeActivity1.this, SelectModeForTakingAttendance.class);
                    startActivity(intent);
                } else {
                    // If student, go to activity for giving Mode1 attendance
                    Intent intent = new Intent(HomeActivity1.this, GiveAttendance.class);
                    startActivity(intent);
                }
            }
        });

        Stats_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start stats activity for students
                Intent intent = new Intent(HomeActivity1.this, StatsActivity.class);
                startActivity(intent);
            }
        });



        final BiometricPrompt.PromptInfo promptInfo = createBiometricSecurityPrompt();  // Set up fingerprint biometric prompt

        Mode2_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeOfPerson.equals("Teacher")) {
                    // If teacher, open Past Attendances Activity
                    Intent intent = new Intent(HomeActivity1.this, AttendanceListViewMode2.class);
                    startActivity(intent);
                } else {
                    // Authenticate biometric info and then start the next activity of giving attendance through mode 2
                    biometricPrompt.authenticate(promptInfo);
                }
            }
        });
    }

    private BiometricPrompt.PromptInfo createBiometricSecurityPrompt() {
        // creating a variable for our Executor
        Executor executor = ContextCompat.getMainExecutor(this);
        // this will give us result of AUTHENTICATION
        biometricPrompt = new BiometricPrompt(HomeActivity1.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            // THIS METHOD IS CALLED WHEN AUTHENTICATION IS SUCCESS
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Intent intent = new Intent(HomeActivity1.this, GiveAttendance2.class);
                startActivity(intent);
            }
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });
        // creating a variable for our promptInfo
        // BIOMETRIC DIALOG
        return new BiometricPrompt.PromptInfo.Builder().setTitle("Security! 😁")
                .setDescription("Use your fingerprint to access the next screen!").setNegativeButtonText("Cancel").build();
    }

    private void switchToTeacher() {
        // Change views so that they match to the theme of teacher
        backgroundRelativeLayout.setBackgroundResource(R.drawable.background_home_gradient_teacher);
        setting_image.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.dark_light_blue), android.graphics.PorterDuff.Mode.SRC_IN);
        heading_text.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.dark_light_blue));
        Mode1_card.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.dark_light_blue));
        Mode2_card.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.dark_light_blue));
        Stats_card.setVisibility(View.GONE);
        TextViewCardView1.setText("Take\nAttendance");
        TextViewCardView2.setText("Previous\nAttendances");
    }


    @Override
    protected void onStart() {
        super.onStart();
        startAnimationsOfViews();
    }

    private void startAnimationsOfViews() {
        AnimatedVectorDrawable d = (AnimatedVectorDrawable) getDrawable(R.drawable.projector_mode2_animation);  // Forward Animation
        AnimatedVectorDrawable d_rev = (AnimatedVectorDrawable) getDrawable(R.drawable.projector_mode2_animation_reverese);  // Reverse Animation

        mode2Img.setImageDrawable(d);  // Set animation to view

        d.registerAnimationCallback(new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mode2Img.setImageDrawable(d_rev);
                        d_rev.start();
                    }
                }, 800);  // Start animation, repeating after 800 milliseconds

            }
        });

        d_rev.registerAnimationCallback(new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mode2Img.setImageDrawable(d);
                        d.start();
                    }
                }, 800);  // After animation ends, reverse the animation

            }
        });

        d.start();  // Start Animation




        AnimatedVectorDrawable d2 = (AnimatedVectorDrawable) getDrawable(R.drawable.phone_qr_mode1_animation);  // Forward Animation
        AnimatedVectorDrawable d2_rev = (AnimatedVectorDrawable) getDrawable(R.drawable.phone_qr_mode1_animation_reverse);  // Reverse Animation

        mode1Img.setImageDrawable(d2);  // Set animation to view

        d.registerAnimationCallback(new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mode1Img.setImageDrawable(d2_rev);
                        d2_rev.start();
                    }
                }, 1200);  // Start animation, repeating after 1200 milliseconds

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
                }, 1200);  // After animation ends, reverse the animation

            }
        });

        d.start();
    }

}