package com.mv.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class HomeActivity1 extends AppCompatActivity {

    ImageView setting_image, mode2Img, mode1Img;
    CardView Mode1_card;
    CardView Mode2_card;

    //private AnimatedVectorDrawable animationOfSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);

        setting_image = findViewById(R.id.setting_image);
        mode2Img = findViewById(R.id.imgOfMode2);
        mode1Img = findViewById(R.id.imgOfMode1);
        Mode1_card = findViewById(R.id.idMode1_card);
        Mode2_card = findViewById(R.id.idMode2_card);

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String type = sh.getString("TypeOfPerson", "Not Set");

        Animation rotation = AnimationUtils.loadAnimation(HomeActivity1.this, R.anim.rotate_settings_logo);
        rotation.setFillAfter(true);
        setting_image.startAnimation(rotation);

        setting_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity1.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        Mode1_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("Teacher")) {
                    Intent intent = new Intent(HomeActivity1.this, TakeAttendanceList.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(HomeActivity1.this, GiveAttendance.class);
                    startActivity(intent);
                }
            }
        });

        Mode2_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("Teacher")) {
                    Intent intent = new Intent(HomeActivity1.this, AttendanceListViewMode2.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(HomeActivity1.this, GiveAttendance2.class);
                    startActivity(intent);
                }
            }
        });
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
                        Log.d("QWERT", "unfdnvvfvfvffvvvvvvvvvvvvvvvvvvv");
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
                        Log.d("QWERT", "unfdnvvfvfvffvvvvvvvvvvvvvvvvvvv");
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