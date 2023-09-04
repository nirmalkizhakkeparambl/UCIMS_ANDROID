package com.gisfy.unauthorizedconstructions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gisfy.unauthorizedconstructions.Profile.GuideMe;
import com.gisfy.unauthorizedconstructions.Profile.Profile;
import com.gisfy.unauthorizedconstructions.SQLite.MainActivity;
import com.gisfy.unauthorizedconstructions.SQLite.Model;
import com.gisfy.unauthorizedconstructions.SQLite.RecordListActivity;
import com.gisfy.unauthorizedconstructions.SQLite.SQLiteHelper;
import com.gisfy.unauthorizedconstructions.Sync.SyncRecordList;
import com.gisfy.unauthorizedconstructions.Utils.SessionManagement;
import com.gisfy.unauthorizedconstructions.Utils.Upload;
import com.google.android.material.snackbar.Snackbar;

import net.gotev.uploadservice.MultipartUploadRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class dashboard extends AppCompatActivity  {

    SQLiteHelper mSQLiteHelper;
    AlertDialog dialog;
    String urlString;
    ConstraintLayout scrollView;
    String data_response,image_response,poly_response,video_response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mSQLiteHelper = new SQLiteHelper(this, "Construction.sqlite", null, 2);

        scrollView=findViewById(R.id.parent_scroll);
        scrollView=findViewById(R.id.parent_scroll);
        TextView tv=findViewById(R.id.employeephn);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String empName = sh.getString("employeename", "");
        tv.setText(empName);
    }

    public void addform(View view) {
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void syncform(View view)
    {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            if ( connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                    || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ) {

                try {

                    Intent intent=new Intent(getApplicationContext(), SyncRecordList.class);
                    startActivity(intent);

                } catch (IllegalStateException e) {
                    e.printStackTrace();
//                    Log.i("sync dash",e.getMessage());
                }
                catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Check LatLng in Edit", Toast.LENGTH_SHORT).show();
                }
            }
            else if ( connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                    || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {
                Snackbar snackbar = Snackbar
                        .make(scrollView, "No Network Connection", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
        }
    }


    public void userprofile(View view) {
        startActivity(new Intent(dashboard.this, Profile.class));
    }

    public void logout(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(dashboard.this);
        builder.setTitle("Alert");
        builder.setMessage("Do you Want to Logout?");
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mSQLiteHelper.deleteMandalsAndVillages();
                SessionManagement sessionManagement=new SessionManagement(dashboard.this);

                sessionManagement.logoutUser();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog=builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(dashboard.this);
        builder.setTitle("Alert");
        builder.setMessage("Do you Want to Exit?");

        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog=builder.create();
        dialog.show();

    }

    public void Locateme(View view) {
        startActivity(new Intent(getApplicationContext(), GuideMe.class));
    }

    public void edit(View view) {
        startActivity(new Intent(getApplicationContext(), RecordListActivity.class));
    }









//    public void Alert_Result()
//    {
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//        final AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialogBuilder.setTitle("Result:");
//        alertDialogBuilder.setMessage("Data:"+data_response+"\nImages:"+image_response+"\npolygon:"+poly_response+"\nVideo:"+video_response);
//        alertDialogBuilder.setPositiveButton("OK",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        alertDialog.dismiss();
//                    }
//                });
//        alertDialog.show();
//    }

}