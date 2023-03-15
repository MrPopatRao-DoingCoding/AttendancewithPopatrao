package com.mv.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;

public class SettingsActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextRollNo;
    EditText editTextDivision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        editTextName = findViewById(R.id.editTextName);
        editTextRollNo = findViewById(R.id.editTextRollNo);
        editTextDivision = findViewById(R.id.editTextDivision);

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        editTextName.setText(sh.getString("Name", ""));
        editTextRollNo.setText(sh.getString("Roll No", ""));
        editTextDivision.setText(sh.getString("Div", ""));

        SharedPreferences.Editor myEdit = sh.edit();


        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                myEdit.putString("Name", editTextName.getText().toString());
                try {
                    long t = getTime();
                    myEdit.putLong("savedTime", t);
                    Log.d("QWERT", "Time Saved as " + t);
                } catch (Exception e) {
                    long t = System.currentTimeMillis() / 1000L;
                    myEdit.putLong("savedTime", t);
                }
                myEdit.apply();
            }
        });

        editTextRollNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                myEdit.putString("Roll No", editTextRollNo.getText().toString());
                try {
                    long t = getTime();
                    myEdit.putLong("savedTime", t);
                    Log.d("QWERT", "Time Saved as " + t);
                } catch (Exception e) {
                    long t = System.currentTimeMillis() / 1000L;
                    myEdit.putLong("savedTime", t);
                }
                myEdit.apply();
            }
        });

        editTextDivision.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                myEdit.putString("Div", editTextDivision.getText().toString());
                try {
                    long t = getTime();
                    myEdit.putLong("savedTime", t);
                    Log.d("QWERT", "Time Saved as " + t);
                } catch (Exception e) {
                    long t = System.currentTimeMillis() / 1000L;
                    myEdit.putLong("savedTime", t);
                }
                myEdit.apply();
            }
        });




    }




    private long getTime() throws Exception {
        String url = "https://time.is/Unix_time_now";
        Document doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
        String[] tags = new String[] {
                "div[id=time_section]",
                "div[id=clock0_bg]"
        };
        Elements elements= doc.select(tags[0]);
        for (int i = 0; i <tags.length; i++) {
            elements = elements.select(tags[i]);
        }
        return Long.parseLong(elements.text());
    }


}