package com.mv.attendance;

public class Mode2QRCodeProperties {

    Lecture lecture;
    Student this_student;
    long time_when_QRCode_Scanned;

    String QR_CODE_Encoded_Title;
    String QR_CODE_Encoded_trName;
    long QR_CODE_Encoded_time;

    public Mode2QRCodeProperties(String textFromQRCode, long unixTimeStamp) {
    }

    public static boolean check_textFromQRCode_isCorrect(String textFromQRCode, long unixTimeStamp){
        return true;
    }

    public boolean checkIfTimeInBuffer(int seconds) {
        return true;
    }
}
