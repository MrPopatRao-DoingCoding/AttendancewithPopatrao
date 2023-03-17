package com.mv.attendance;

import java.util.Objects;

public class Mode2QRCodeProperties {

    static String  first;
    Lecture lecture;
    Student this_student;
    long time_when_QRCode_Scanned;
    static String Title;
    static String  Teacher_name;

    String QR_CODE_Encoded_Title;
    String QR_CODE_Encoded_trName;
    long QR_CODE_Encoded_time;

    public Mode2QRCodeProperties(String textFromQRCode, long unixTimeStamp) {

        //{}|MAS|Popatrao|1679044524|

        String[] strsplit = textFromQRCode.split("\\|");
        this.first = strsplit[0];
        this.Title = strsplit[1];
        this.Teacher_name = strsplit[2];
        this.QR_CODE_Encoded_time = Integer.parseInt(strsplit[3]);




    }

    public static boolean check_textFromQRCode_isCorrect(String textFromQRCode, long unixTimeStamp){
        int count = 0;
        for(int i=0; i<=textFromQRCode.length();i++){
            if((textFromQRCode.charAt(i) == '|')){count++;}

        }

        if(!first.equals("{}")){return false;}
        if(textFromQRCode==null||textFromQRCode.isEmpty()){return false;}
        if(count!=4){return false;}

        return true;


    }

    public boolean checkIfTimeInBuffer(int seconds) {

        return true;
    }
}
