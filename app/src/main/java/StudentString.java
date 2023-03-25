import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

public class StudentString {
    String nameOfStudent;
    int RollNoOfStudent;
    String divOfStudent;
    String PRN;



    String getString(){
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        this.nameOfStudent = sh.getString("Name", "");
        this.RollNoOfStudent = Integer.parseInt(sh.getString("Roll No", ""));
        this.divOfStudent = sh.getString("Div", "");
        this.PRN = sh.getString("PRN","");

        return divOfStudent + "_" + RollNoOfStudent + "_" + divOfStudent;
    }




}
