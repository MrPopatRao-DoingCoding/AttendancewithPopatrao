package com.mv.attendance;

import android.util.Log;

import java.util.Arrays;

public class Student {

    int RollNo;
    String div;
    String name;

    public Student(int RollNo, String div, String name) {
        this.RollNo = RollNo;
        this.div = div;
        this.name = name;
    }

    public Student(String result) {
        String[] splitted = result.split("\\|");
        Log.d("QWERTY", Arrays.toString(splitted));
        RollNo = Integer.parseInt(splitted[2]);
        div = splitted[3];
        name = splitted[4];
        Log.d("QWERTY", "name = " + name);
    }

    public static boolean checkIfFormatCorrect(String result) {
        if(result==null||result.isEmpty()){ return false; }
        if(!result.startsWith("{}")){ return false; }
        String[] splitted = result.split("\\|");
        Log.d("QWERTY", Arrays.toString(splitted));
        if(splitted.length != 5){ return false; }
        if(splitted[0].length() != 2){ return false; }
        if(splitted[2].length() > 2){ return false; }
        if(splitted[3].length() > 1){ return false; }
        return true;
    }

    public Student convertToStudent(String result){
        Student student = new Student(0, "", "");
        String[] splitted = result.split("\\|");
        student.RollNo = Integer.parseInt(splitted[2]);
        student.div = splitted[3];
        student.name = splitted[4];
        return student;
    }


}
