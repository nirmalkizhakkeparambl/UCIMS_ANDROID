package com.gisfy.unauthorizedconstructions.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.gisfy.unauthorizedconstructions.R;

public class Profile extends AppCompatActivity {
    TextView txempID,txempname,txempplace,txempworking,txempmobile,txempwhatsapp,txempmail,txempdesignationid,txempdesignationname,txempuserid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        txempname=findViewById(R.id.txt_empName);
        txempplace=findViewById(R.id.ULB);
        txempmobile=findViewById(R.id.txt_empmbno);
        txempwhatsapp=findViewById(R.id.designationname);
        txempmail=findViewById(R.id.txt_empmail);
        txempuserid=findViewById(R.id.txt_empuserid);
        SharedPreferences sh =getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String empName = sh.getString("employeename", "");
        String empULB = sh.getString("ulbname", "");
        String empEmail = sh.getString("emailid", "");
        String empDesignationname = sh.getString("designationname", "");
        String empUserID = sh.getString("userid", "");
        String empMobile = sh.getString("phoneno", "");
        int empID = sh.getInt("employeeid", 0);
        int empDesignationID = sh.getInt("designationid", 0);

        int empULBCode = sh.getInt("ulbcode", 0);

        if (!(empName==null&&empULB==null&&empDesignationname==null&&empEmail==null)) {

            txempplace.setText(empULB);
            txempmail.setText(empEmail);

            txempname.setText(empName);
            txempmobile.setText(empMobile);
            txempwhatsapp.setText((empDesignationname));
            txempuserid.setText(empUserID);
            Log.i("sharedpref", empName + empEmail);
        }
    }
}
