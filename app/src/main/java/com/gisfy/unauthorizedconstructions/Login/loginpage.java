package com.gisfy.unauthorizedconstructions.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gisfy.unauthorizedconstructions.R;
import com.gisfy.unauthorizedconstructions.SQLite.SQLiteHelper;
import com.gisfy.unauthorizedconstructions.Utils.SessionManagement;
import com.gisfy.unauthorizedconstructions.dashboard;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

public class loginpage extends AppCompatActivity {
    ImageView img;
    Animation animtop,animbottom;
    ConnectivityManager connectivityManager;
    EditText email, pass;
    CardView cardView;
    ConstraintLayout relativeLayout;
    SessionManagement sessionManagement;
    String empname;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        requestPermissions();

        img=findViewById(R.id.imageView);
        cardView=findViewById(R.id.cardView);
        animtop= AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fromtop);
        img.setVisibility(View.VISIBLE);
        img.startAnimation(animtop);
        animbottom= AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.frombottom);
        cardView.setVisibility(View.VISIBLE);
        cardView.startAnimation(animbottom);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        relativeLayout=findViewById(R.id.parentlayout_login);

        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);


        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        sessionManagement=new SessionManagement(loginpage.this);
        if (sessionManagement.isLoggedIn())
        {
            startActivity(new Intent(loginpage.this, dashboard.class));
        }
    }

    public void login(View view) {

        if ( connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ) {
            if (email.getText().length() == 0) {
                email.setError("Be patience and enter Username");
                email.requestFocus();
            } else if (pass.getText().length() == 0) {
                pass.setError("Be patience and enter password");
                pass.requestFocus();
            } else {
                progressDialog.setMessage("Logging In...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                if (email.getText().length() > 0 && pass.getText().length() > 0) {
                    //Toast.makeText(LoginActivity.this, email.getText() + " " + password.getText(), Toast.LENGTH_LONG).show();
                        final String credentials = "http://ucimsapdtcp.ap.gov.in/UMSAPI/api/UMS/LoginCheck?userid=" + email.getText().toString() + "&password=" + pass.getText().toString();
                    Log.d("DEBUGGING","Cred: "+credentials);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, credentials,
                            new Response.Listener<String>() {
                                String empid;

                                @Override
                                public void onResponse(String response) {
                                    Log.i("RESPONSE: ",response);
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        for (int i = 0; i < 1; i++) {
                                            JSONObject jsonobject = jsonArray.getJSONObject(i);
                                            empid = String.valueOf(jsonobject.getInt("employeeid"));
                                            profiledata(empid);
                                        }
                                        if (empid==null) {

                                            Snackbar snackbar = Snackbar
                                                    .make(relativeLayout, "Something went Wrong",
                                                            Snackbar.LENGTH_LONG);
                                            snackbar.setActionTextColor(Color.RED);
                                            snackbar.show();
                                        }

                                    } catch (JSONException ex) {
                                        ex.printStackTrace();
                                        progressDialog.dismiss();
                                        Snackbar snackbar = Snackbar
                                                .make(relativeLayout, "Invalid details",
                                                        Snackbar.LENGTH_LONG);
                                        snackbar.setActionTextColor(Color.RED);
                                        snackbar.show();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.dismiss();
                                    //displaying the error in toast if occurrs
                                    Toast.makeText(getApplicationContext(), "No Network or Maintenance problem", Toast.LENGTH_SHORT).show();
                                }
                            });


                    requestQueue.add(stringRequest);

                }
            }
        }
            else if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                    || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

                Snackbar snackbar = Snackbar
                        .make(relativeLayout, "No Network Connection", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();

        }
    }

    private void profiledata(String empid) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://ucimsapdtcp.ap.gov.in/UMSAPI/api/UMS/ProfileDetails?employeeid="+empid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String name = null;
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < 1; i++) {
                                JSONObject empobj = jsonArray.getJSONObject(i);
                                if (empobj==null)
                                {
                                    progressDialog.dismiss();
                                    Toast.makeText(loginpage.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                                }
                                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.putString("employeeid", empobj.getString("employeeid"));
                                myEdit.putString("employeename", empobj.getString("employeename"));
                                empname=empobj.getString("employeename");
                                myEdit.putString("designationname", empobj.getString("designationname"));
                                myEdit.putString("emailid", empobj.getString("emailid"));
                                myEdit.putString("ulbname", empobj.getString("ulbname"));
                                name =  empobj.getString("name");
                                myEdit.putString("name",name);
                                myEdit.putString("district", empobj.getString("district"));
                                myEdit.putInt("employeeid", empobj.getInt("employeeid"));
                                myEdit.putString("phoneno", empobj.getString("phoneno"));
                                myEdit.putInt("ulbcode", empobj.getInt("ulbcode"));
                                myEdit.putInt("designationid", empobj.getInt("designationid"));
                                myEdit.putString("userid", empobj.getString("userid"));
                                myEdit.putString("grade",empobj.getString("grade"));
                                Log.i("DEBUGGING","json values");

//                                myEdit.commit();
                                myEdit.apply();
                            }

                            getMandalsAndVillages(name);

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                            Log.d("DEBUGGING","JsonEx profiledetails");
                            Snackbar snackbar = Snackbar
                                    .make(relativeLayout, "Some thing Wrong", Snackbar.LENGTH_LONG);
                            snackbar.setActionTextColor(Color.RED);
                            snackbar.show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(stringRequest);
    }


    private void getMandalsAndVillages(String name){
        final String URL ="http://ucimsapdtcp.ap.gov.in/UMSAPI/api/UMS/Mandal?ULB="+name;
        Log.d("DEBUGGING Mandal url",URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                SQLiteHelper database = new SQLiteHelper(loginpage.this,"Construction.sqlite",
                        null, 2);
                database.createtable();
                Log.d("DEBUGGING res",response);
                response=response.trim();
                response=response.replace("\\","");
                response=response.replaceFirst("\"","");
                response = response.substring(0,response.length()-1);
                Log.d("DEBUGGING re",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("DEBUGGING CreateJson",jsonObject.toString());
                    String grade = loginpage.this.getSharedPreferences("MySharedPref",
                            MODE_PRIVATE).getString("grade",null);
                    Iterator<String> iterator = jsonObject.keys();

                    while (iterator.hasNext()){
                        String mandal = iterator.next();
                        long rowId = database.insertMandal(mandal);
                        if(grade.contains("UDA")){
                            JSONArray jsonArray = jsonObject.getJSONArray(mandal);
                            for(int villageIndex=0; villageIndex<jsonArray.length(); villageIndex++){
                                database.insertVillage(rowId,jsonArray.getString(villageIndex));
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("DEBUGGING CreateJson","Exception");
                }
                progressDialog.dismiss();
                sessionManagement.createLoginSession(email.getText().toString(), pass.getText().toString());
                Intent intent = new Intent(getBaseContext(), Splash_Activity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar
                        .make(relativeLayout, "Some thing Wrong", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
                progressDialog.dismiss();
            }
        });

        requestQueue.add(stringRequest);

    }

    private void requestPermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.ACCESS_NETWORK_STATE
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(loginpage.this, "All permissions are granted!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            showSettingsDialog();
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }


    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(loginpage.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
}
