package com.mv.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AttendanceListViewMode2 extends AppCompatActivity {

    ListView listViewPast;
    ImageButton new_attendance_session_button, button_sort_alphabetically;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list_view_mode2);

        listViewPast=findViewById(R.id.listViewPast);
        //new_attendance_session_button=findViewById(R.id.buttonNewAttendance);

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

        final ArrayAdapter<String> adapter = new ArrayAdapter<> (AttendanceListViewMode2.this, android.R.layout.simple_list_item_1, ListElementsArrayList);
        listViewPast.setAdapter(adapter);

        listViewPast.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                    return true;
                }
            }
        );

    }
}