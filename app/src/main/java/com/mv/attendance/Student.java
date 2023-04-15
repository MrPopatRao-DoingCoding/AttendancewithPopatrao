package com.mv.attendance;

import android.util.Log;

import java.util.Arrays;

public class Student {

    int RollNo;
    String roll;
    String div;
    String name;

    long time;
    int PRN_number;






    public String getName() {
        return name;
    }

    public String getDiv() {
        return div;
    }

    public int getRollNo() {
        return RollNo;
    }

    public long getTime(){return time;}



    public Student(int RollNo, String div, String name, String textFromQRCode) {
        this.RollNo = RollNo;
        this.div = div;
        this.name = name;
        String[] strsplit = textFromQRCode.split("\\|");
        this.time = Long.parseLong(strsplit[5]);
    }

    public Student(int RollNo, String div, String name, long time, int PRN) {
        this.RollNo = RollNo;
        this.div = div;
        this.name = name;
        //String[] strsplit = textFromQRCode.split("\\|");
        //this.time = Long.parseLong(strsplit[5]);
        this.time = time;
        this.PRN_number = PRN;
    }

    public Student(String name, String roll, String div, long time){
        this.roll = roll;
        this.name = name;
        this.div = div;
        this.time = time;
    }


    public Student(String result, long time) {
        String[] splitted = result.split("\\|");
        Log.d("QWERTY", Arrays.toString(splitted));
        RollNo = Integer.parseInt(splitted[2]);
        div = splitted[3];
        name = splitted[4];
        this.time = time;
        PRN_number = Integer.parseInt(splitted[5]);
        Log.d("QWERTY", "name = " + name + "\n" + "PRN = " + PRN_number);
    }

    public static boolean checkIfFormatCorrect(String result) {
        if(result==null||result.isEmpty()){ Log.d("QWERT", "Error 1"); return false;}
        if(!result.startsWith("{}")){ Log.d("QWERT", "Error 2"); return false; }
        String[] splitted = result.split("\\|");
        Log.d("QWERTY", Arrays.toString(splitted));
        Log.d("QWERTY", "Length of Splitted" + splitted.length);
        if(splitted.length != 6){ Log.d("QWERT", "Error 3"); return false; }
        if(splitted[0].length() != 2){ Log.d("QWERT", "Error 4");; return false; }
        if(splitted[2].length() > 2){ Log.d("QWERT", "Error 5"); return false; }
        if(splitted[3].length() > 1){ Log.d("QWERT", "Error 6"); return false; }
        return true;
    }




}
