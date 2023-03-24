package com.mv.attendance;

import java.util.Objects;

public class Mode2QRCodeProperties {

    String PRN;
    static String first;
    Lecture lecture;
    Student this_student;
    long time_when_QRCode_Scanned;
    static String Title1;
    static String Teacher1;

    String QR_CODE_Encoded_Title;
    String QR_CODE_Encoded_trName;
    long QR_CODE_Encoded_time;

    public Mode2QRCodeProperties(String textFromQRCode, long unixTimeStamp) {

        //{}|MAS|Popatrao|1679044524|

        String[] strsplit = textFromQRCode.split("\\|");
        first = strsplit[0];
        Title1 = strsplit[1];
        Teacher1 = strsplit[2];
        this.PRN = strsplit[3];
        this.QR_CODE_Encoded_time = Long.parseLong(strsplit[4]);
    }

    public static boolean check_textFromQRCode_isCorrect(String textFromQRCode, long unixTimeStamp){
        int count = 0;
        for(int i=0; i<textFromQRCode.length();i++){
            if((textFromQRCode.charAt(i) == '|')){count++;}

        }

        String first_two_digits = textFromQRCode.substring(0, 2);

        if(!first_two_digits.equals("{}")){return false;}
        if(textFromQRCode==null||textFromQRCode.isEmpty()){return false;}
        if(count!=5){return false;}

        return true;


    }

    public boolean checkIfTimeInBuffer(long seconds) {
        long unixTime = System.currentTimeMillis() / 1000L;
        seconds = unixTime - QR_CODE_Encoded_time;

        return true;
    }
}
