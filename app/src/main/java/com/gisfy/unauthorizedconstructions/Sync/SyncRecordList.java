package com.gisfy.unauthorizedconstructions.Sync;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.gisfy.unauthorizedconstructions.R;
import com.gisfy.unauthorizedconstructions.SQLite.MainActivity;
import com.gisfy.unauthorizedconstructions.SQLite.Model;
import com.gisfy.unauthorizedconstructions.SQLite.SQLiteHelper;
import com.gisfy.unauthorizedconstructions.dashboard;
import com.google.android.material.snackbar.Snackbar;

import net.gotev.uploadservice.MultipartUploadRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.sentry.Sentry;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SyncRecordList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<SyncModel> employees = new ArrayList<>();
    private ArrayList<Integer> eids;
    private SyncAdapter adapter;
    private TextView btnGetSelected;
    SQLiteHelper mSQLiteHelper;
    StringBuilder stringBuilder;
    String urlString="http://ucimsapdtcp.ap.gov.in/UMSAPI/api/UMS/insertConstruction";
    String data_response="Failed", image_response,poly_response="Failed";
    LinearLayout relative_snack;
    int xid;
    SimpleDateFormat df;
    String currentDateandTime;
    String empid;
     String videoresponse="false";
    int id;
    String Draftsman;
    String ownername;
    String fathername;
    String address;
    String district;
    String municipality;
    String surveyno;
    String village;
    String doorno;
    String locality;
    String streetname;
    String sitearea;
    String natureofconst;
    String approvedplan;
    String parking1;
    String parking2;
    String floor1;
    String floor2;
    String front;
    String front2;
    String right;
    String right2;
    String left;
    String left2;
    String back;
    String back2;
    String landuse1;
    String landuse2;
    String slocation;
    String[] latlong;
    double longitude;
    double latitude;
    String imgpath;
    String Data_URL;
    String Polygon_URL;
    String imgpath2;
    String imgpath3;
    String imgpath4;
    String uploadid;
    String uploadid2;
    String uploadid3;
    String uploadid4;
    String phoneno;
    String ConstType;
    String ConstFloors;
    String FloorsonGround;
    String Nooffloors;
    String Existingfloors;
    String ConvertingFloors;
    String sroadwidening1;
    String sroadwidening2;
    String sarea1;
    String sarea2;
    String polygonpoints;
    String videoname,totaluc;
    String polgonURL;
    String videopath;
    String spinner23;
    String date1;
    String stiltpark;
    String age,plintharea,builtIt;
    ProgressDialog progressDialog;


    AlertDialog dialog;
    private Async_Thread mTask;

    ConstraintLayout scrollView;
    String video_response="false";
    ArrayList<Model> mList ;

    private JSONObject jsonObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_record_list);
        this.btnGetSelected = (TextView) findViewById(R.id.btnGetSelected);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        relative_snack = (LinearLayout) findViewById(R.id.relative_snack);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressDialog=new ProgressDialog(SyncRecordList.this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new SyncAdapter(this, employees);
        recyclerView.setAdapter(adapter);
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        currentDateandTime = df.format(new Date());
        SharedPreferences sh
                = getSharedPreferences("UserDetails",
                MODE_PRIVATE);
        empid = String.valueOf(sh.getString("EmpId", null));

        createList();

        if (employees.size() == 0) {
            Snackbar snackbar = Snackbar
                    .make(relative_snack, "No Data Found..?", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Add Now", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(SyncRecordList.this, MainActivity.class));
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }


        btnGetSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                eids = new ArrayList<>();
                eids.clear();


                if (adapter.getSelected().size() > 0) {


                    for (int i = 0; i < adapter.getSelected().size(); i++) {

                        eids.add(adapter.getSelected().get(i).getId());
                    }
                    Log.i("idsize",String.valueOf(eids.size()));
                    if(eids.size()==1){
                        final ProgressDialog progressDialog1=new ProgressDialog(SyncRecordList.this);
                        progressDialog1.setMessage("Please Wait...");
                        progressDialog1.setCancelable(false);
                        progressDialog1.show();
                        for(int k=0;k<eids.size();k++){
                            Log.i("Syncing id", String.valueOf(eids.get(k)));
                            xid=eids.get(k);

                            mSQLiteHelper = new SQLiteHelper(SyncRecordList.this, "Construction.sqlite", null, 2);
                            Cursor cursor =mSQLiteHelper.getData("SELECT * FROM Construction WHERE id="+xid);

                            while (cursor.moveToNext()) {
                                id          = cursor.getInt(0);
                                Draftsman      = cursor.getString(1);
                                ownername      = cursor.getString(2);
                                fathername     = cursor.getString(3);
                                address        = cursor.getString(4);
                                district       = cursor.getString(5);
                                municipality   = cursor.getString(6);
                                surveyno      = cursor.getString(7);
                                village       = cursor.getString(8);
                                doorno         = cursor.getString(9);
                                locality           = cursor.getString(10);
                                streetname         = cursor.getString(11);
                                sitearea           = cursor.getString(12);
                                natureofconst           = cursor.getString(13);
                                approvedplan        = cursor.getString(14);
                                parking1     = cursor.getString(15);
                                parking2               = cursor.getString(16);
                                floor1             = cursor.getString(17);
                                floor2            = cursor.getString(18);
                                front             = cursor.getString(19);
                                front2            = cursor.getString(20);
                                right             = cursor.getString(21);
                                right2             = cursor.getString(22);
                                left               = cursor.getString(23);
                                left2                  = cursor.getString(24);
                                back                  = cursor.getString(25);
                                back2             = cursor.getString(26);
                                landuse1          = cursor.getString(27);
                                landuse2          = cursor.getString(28);
                                slocation          = cursor.getString(29);
                                latlong             = slocation.split(",");
                                Log.i("loc",slocation);
                                longitude             = Double.parseDouble(latlong[0]);
                                latitude                    = Double.parseDouble(latlong[1]);
                                imgpath                  = cursor.getString(30);
                                imgpath2                 = cursor.getString(32);
                                imgpath3             = cursor.getString(34);
                                imgpath4              = cursor.getString(36);
                                uploadid             = cursor.getString(31);
                                uploadid2            = cursor.getString(33);
                                uploadid3             = cursor.getString(35);
                                uploadid4            = cursor.getString(37);
                                phoneno                            = cursor.getString(38);
                                ConstType              = cursor.getString(39);
                                ConstFloors           = cursor.getString(40);
                                FloorsonGround     = cursor.getString(41);
                                Nooffloors            = cursor.getString(42);
                                Existingfloors         = cursor.getString(43);
                                ConvertingFloors       = cursor.getString(44);
                                sroadwidening1         = cursor.getString(45);
                                sroadwidening2        = cursor.getString(46);
                                sarea1                = cursor.getString(47);
                                sarea2                = cursor.getString(48);
                                polygonpoints      = cursor.getString(49);
                                polygonpoints=polygonpoints.replaceAll("--"," ");
                                Log.i("polgygon points",polygonpoints);
                                videoname             =cursor.getString(51);
                                totaluc             =cursor.getString(52);

                                videopath         =cursor.getString(50);
                                spinner23             =cursor.getString(53);
                                date1                 =cursor.getString(54);
                                stiltpark         =cursor.getString(55);
                                plintharea=cursor.getString(56);
                                builtIt = cursor.getString(68);
                                age                   =cursor.getString(57);

                                String mandal = cursor.getString(58);
                                String deviations="";
                                byte[] checkBoxes = new byte[8];

                                checkBoxes[0] = (byte) cursor.getInt(59);
                                checkBoxes[1] = (byte) cursor.getInt(60);
                                checkBoxes[2] = (byte) cursor.getInt(61);
                                checkBoxes[3] = (byte) cursor.getInt(62);
                                checkBoxes[4] = (byte) cursor.getInt(63);
                                checkBoxes[5] = (byte) cursor.getInt(64);
                                checkBoxes[6] = (byte) cursor.getInt(65);
                                checkBoxes[7] = (byte) cursor.getInt(66);

                                String constStage = cursor.getString(67);

                                if(checkBoxes[0]==1)
                                    deviations+=" NoDeviation ";
                                if(checkBoxes[1]==1)
                                    deviations+=" AllDeviation ";
                                if(checkBoxes[2]==1)
                                    deviations+=" Setbacks ";
                                if(checkBoxes[3]==1)
                                    deviations+=" AdditionalFloors ";
                                if(checkBoxes[4]==1)
                                    deviations+=" RoadWinding ";
                                if(checkBoxes[5]==1)
                                    deviations+=" Buildup ";
                                if(checkBoxes[6]==1)
                                    deviations+=" Parking ";
                                if(checkBoxes[7]==1)
                                    deviations+=" LandArea ";

                                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                int empID = sh.getInt("employeeid", 0);
                                String userid = sh.getString("userid", null);
                                String curent_timestamp = "current_timestamp";

                                Log.i("natureofconst",natureofconst);

                                jsonObject = new JSONObject();
                                try {

                                    jsonObject.put("latitude",longitude);
                                    jsonObject.put("longitude",latitude);
                                    jsonObject.put("WPRSBISAD",Draftsman);
                                    jsonObject.put("Name",ownername);
                                    jsonObject.put("FHName",fathername );
                                    jsonObject.put("Address",address);
                                    jsonObject.put("viltown",village );
                                    jsonObject.put("dno",doorno);
                                    jsonObject.put("locality",locality);
                                    jsonObject.put("streetname",streetname );
                                    jsonObject.put("ULB",municipality );
                                    jsonObject.put("district",district);
                                    jsonObject.put("natureofconstruction",natureofconst );
                                    jsonObject.put("sitearea",sitearea);
                                    jsonObject.put("imagepath",uploadid + ".jpg");
                                    jsonObject.put("SurveyNo",surveyno);
                                    jsonObject.put("employeeid",empID);
                                    jsonObject.put("StartedDate",curent_timestamp);
                                    jsonObject.put("PhoneNo",phoneno);
                                    jsonObject.put("ConstructionType",ConstType);
                                    jsonObject.put("ConstructionFloors",ConstFloors);
                                    jsonObject.put("FloorsAsOnGround",FloorsonGround);
                                    jsonObject.put("NoOfFloors",Nooffloors);
                                    jsonObject.put("ExistingFloors",Existingfloors);
                                    jsonObject.put("ConvertingFloors",ConvertingFloors);
                                    jsonObject.put("imagepath1",uploadid2 + ".jpg");
                                    jsonObject.put("ConstructionVideo",videoname+".mp4");
                                    jsonObject.put("BuldingUsage",spinner23);
                                    jsonObject.put("Age",age);
                                    jsonObject.put("plintharea",plintharea);
                                    jsonObject.put("BuiltUparea",builtIt);
                                    jsonObject.put("TotalUCType",totaluc);
                                    jsonObject.put("violationparkingAsPerConst",parking2);
                                    jsonObject.put("violationlanduseAsPerPlan",landuse1);
                                    jsonObject.put("violationlanduseAsPerConst",landuse2);
                                    jsonObject.put("violationFloorsAsPerPlan",floor1);
                                    jsonObject.put("violationparkingAsPerPlan",parking1);
                                    jsonObject.put("violationFrontAsperplan",front);
                                    jsonObject.put("violationFloorsAsPerConst",floor2);
                                    jsonObject.put("violationBackAsPerPlan",back);
                                    jsonObject.put("violationRightAsPerPlan",right);
                                    jsonObject.put("violationsetbackLeft",left);
                                    jsonObject.put("ViolationFrontAsConst",front2);
                                    jsonObject.put("ViolationBackAsConst",back2);
                                    jsonObject.put("ViolationLeftAsConst",left2);
                                    jsonObject.put("ViolationRightAsConst",right2);
                                    jsonObject.put("RoadWideningAsPerPlan",sroadwidening1 );
                                    jsonObject.put("RoadWideningAsOnGround",sroadwidening2);
                                    jsonObject.put("AreaAsOnGround",sarea2);
                                    jsonObject.put("AreaAsPerPlan",sarea1);
                                    jsonObject.put("NoDeviation",(checkBoxes[0] == 1 ? "true":"false"));
                                    jsonObject.put("AllDeviation",(checkBoxes[1] == 1 ? "true": "false"));
                                    jsonObject.put("SetBack",(checkBoxes[2] == 1 ? "true":"false"));
                                    jsonObject.put("Floor",(checkBoxes[3] == 1 ? "true":"false"));
                                    jsonObject.put("RoadWidening",(checkBoxes[4] == 1 ? "true": "false"));
                                    jsonObject.put("BuiltUpArea",(checkBoxes[5] == 1 ? "true": "false"));
                                    jsonObject.put("Parking",(checkBoxes[6] == 1 ? "true":"false"));
                                    jsonObject.put("LandUse",(checkBoxes[7] == 1 ? "true":"false"));
                                    jsonObject.put("Mandal",mandal);
                                    jsonObject.put("Deviation",deviations);
                                    jsonObject.put("StageofConst",constStage);
                                    jsonObject.put("ParkingMisuse",stiltpark);
                                    jsonObject.put("approvedplan",approvedplan);
                                    jsonObject.put("ApprovedPlanDate",date1);

                                    Log.d("REquestBody397",jsonObject.toString());
//
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if (checkPermission()) {
                                    requestPermissionAndContinue();
                                }else {
                                    Log.d("DEBUGGING","POLY:::-"+polygonpoints);
                                    if(polygonpoints.equals("null")||polygonpoints==null){
                                        Log.d("DEBUGGING","Polygon null");
                                        polgonURL="null";
                                        Log.i("polgygon entered null",polgonURL);
                                    }
                                    else{
                                        Log.d("DEBUGGING","Polygon::Yes");
                                        polgonURL  = "http://ucimsapdtcp.ap.gov.in/UMSAPI/api/UMS?query=INSERT INTO public.\"tblUCPolygon\"(geom, \"OwnerName\",\"employeeid\",\"Date\") VALUES (\n" +
                                                "\tST_GeometryFromText('MULTIPOLYGON(((" + polygonpoints + ")))',4326)" +
                                                ",'" + ownername +
                                                "','" + userid +"',current_timestamp)";
                                    }

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog1.dismiss();
                                            mTask=new Async_Thread();
                                            mTask.execute(videoname, videopath,uploadid, imgpath, uploadid2, imgpath2, uploadid3, imgpath3, uploadid4, imgpath4, urlString, polgonURL,String.valueOf(xid));
                                        }
                                    }, 3000);
                                }
                            }
                        }
                    }
                    else{
                        showToast("Multiple entries are not allowed");
                    }
                }
                else {
                    showToast("select atleast one entry to sync");
                }
            }
        });
    }

    private class  Async_Thread extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            progressDialog.setMessage("Saving Details...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {

            String videoname= strings[0];
            String videopath= strings[1];
            String imgname= strings[2];
            String imgpath= strings[3];
            String imgname2= strings[4];
            String imgpath2= strings[5];
            String imgname3= strings[6];
            String imgpath3= strings[7];
            String imgname4= strings[8];
            String imgpath4= strings[9];
              Data_URL= strings[10];
              Polygon_URL= strings[11];
            String xid= strings[12];



            if(uploadvideo(videoname,videopath).equals("true")){
                Log.i("gotresponse","true");
                Multipart_Image(imgname,imgpath);
                Multipart_Image(imgname2,imgpath2);
                Log.i("imgpath3",imgpath);
                Log.i("imgpath3",imgpath2);
                Log.i("imgpath3",imgpath3);
                Log.i("imgpath3",imgpath4);
                if((imgpath3.equals("NotSelected"))){
                    Log.i("imgpath3","entered3");

                }
                else{
                    Multipart_Image(imgname3,imgpath3);
                }
                if(imgpath4.equals("NotSelected")){
                    Log.i("imgpath4","entered4");

                }
                else{
                    Multipart_Image(imgname4,imgpath4);
                }

                Log.d("DEBUGGING","DOIN BACK DONE returning xid");
                return xid;
            }
            else{
                Log.i("gotresponse","nottrue");

                //mTask.cancel(true);
                //progressDialog.dismiss();
                //Toast.makeText(SyncRecordList.this,"Some error occured\tTry after sometime",Toast.LENGTH_SHORT).show();
                Log.d("DEBUGGING","DOIN BACK  returning null");
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("DEBUGGING","onPostExec");

            if(result!=null){
                int xid=Integer.parseInt(result);
                Log.d("DEBUGGING","xid::"+xid);
                Log.d("DEBUGGING","Polygon URL-"+Polygon_URL);
                if(!(Polygon_URL.equals("null"))){
                    Log.i("Polygon_URL",Polygon_URL);
                    URL_Exec1(Polygon_URL,"Polygon",xid,Data_URL,"Data");
                }
                else{
                    Log.d("DEBUGGING","DURL::"+Data_URL);
                    URL_Exec(Data_URL,"Data",xid);
                }


               //progressDialog.dismiss();

            }
            else{

                Log.i("onpostexecute","caught here");
                mTask.cancel(true);
                progressDialog.dismiss();
                Toast.makeText(SyncRecordList.this,"Some error occured\tTry after sometime",Toast.LENGTH_SHORT).show();

            }


            super.onPostExecute(result);

        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }
    }

    public  void Multipart_Image(String image_name,String Path)
    {
    try {
        new MultipartUploadRequest(SyncRecordList.this, image_name, "http://ucimsapdtcp.ap.gov.in/UMSAPI/api/UMS/UploadImage?imgname="+image_name)
                .addFileToUpload(Path, "image")
                .addParameter("name", Path)
//                        .setNotificationConfig(new UploadNotificationConfig())
                .setMaxRetries(2)
                .startUpload();
        image_response="OK";
        Log.i("image_response",image_response);
    } catch (Exception exc) {
        Log.i("Multipart_Upload", exc.getMessage());
        image_response="Failed";
    }
}





    public String uploadvideo(final String videoname, final String path){


                Log.i("enteredrun","entered run ");
                okhttp3.Response response = null;
                OkHttpClient client=new OkHttpClient();
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                        .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                        .readTimeout(5, TimeUnit.MINUTES); // read timeout
                client = builder.build();

                MediaType mediaType = MediaType.parse("text/plain");
                RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("qwerty",videoname,
                                RequestBody.create(MediaType.parse("application/octet-stream"),
                                        new File(path)))
                        .build();
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url("http://ucimsapdtcp.ap.gov.in/UMSAPI/api/UMS/UploadVideo?VideoName=" +videoname)
                        .method("POST", body)
                        .build();

                try {
                    response = client.newCall(request).execute();
                    Log.i("response", String.valueOf(response));
                    assert response.body() != null;
                    videoresponse = response.body().string();
                    Log.i("Video_Response", videoresponse);

                } catch (IOException e) {
                    e.printStackTrace();
                    response = null;
                    video_response = "false";
                }

        return videoresponse;
    }




    public String URL_Exec(String url, final String type, final int xid)
    {
        RequestQueue queue = Volley.newRequestQueue(SyncRecordList.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("URL_Exec", response);
                        progressDialog.dismiss();
                        if (response.equals("\"inserted\"")) {
                            deleteentry(xid);
                            data_response=response;
                        }else{
                            Toast.makeText(SyncRecordList.this,"Something went wrong",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("onpostexecutedata","caught here");
                mTask.cancel(true);
                progressDialog.dismiss();
                Toast.makeText(SyncRecordList.this,"Some error occured\tTry after sometime",Toast.LENGTH_SHORT).show();

                if (type.equals("Data")) {
                    Log.i("URL_Exec_Data", String.valueOf(error));
                }

                Log.i("URL_Exec_Data", String.valueOf(error));
                data_response="Failed";
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String requestBody = jsonObject.toString();
                Log.d("REquestBody",jsonObject.toString());
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
        };
        queue.add(stringRequest);
        return data_response;
    }

    public String URL_Exec1(String url, final String type, final int xid, final String data_url, final String data_URL)
    {
        RequestQueue queue = Volley.newRequestQueue(SyncRecordList.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("\"inserted\"")) {
                            URL_Exec(data_url,"Data",xid);

                            poly_response=response;
                            Log.i("poly_Exec", poly_response);
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                progressDialog.dismiss();

                Log.i("onpostexecute","caught here");
                mTask.cancel(true);
                progressDialog.dismiss();
                Toast.makeText(SyncRecordList.this,"Some error occured\tTry after sometime",Toast.LENGTH_SHORT).show();

                poly_response=String.valueOf(error);
                if (type.equals("Data")) {
                    Log.i("URL_Exec_Data/Polygon", String.valueOf(error));
                }

                Log.i("URL_Exec_Data/Polygon", String.valueOf(error));

            }
        }
        );
        queue.add(stringRequest);
        Log.i("poly_response",poly_response);
        return poly_response;
    }




    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissionAndContinue() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Permissions");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(SyncRecordList.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE}, 1);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(SyncRecordList.this, new String[]{WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE}, 1);
            }
        } else {

        }
    }



    private void updateRecordList() {
        for(int k=0;k<eids.size();k++){

            Log.i("deletedID",String.valueOf(eids.get(k)));
            mSQLiteHelper.deleteData(eids.get(k));

        }
        eids.clear();
        this.recreate();
        createList();
    }

    private void deleteentry(int id){
        Toast.makeText(SyncRecordList.this, "Successfully Synced", Toast.LENGTH_SHORT).show();
                Snackbar snackbar = Snackbar
                        .make(relative_snack, "Sync Success", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
                Log.i("deleteid", String.valueOf(id));
        mSQLiteHelper.deleteData(id);
        createList();
    }





    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }







    public boolean Check_Data(ArrayList<Model> check)
    {
        boolean state=false;
        if (check.size()==0){
            Snackbar snackbar = Snackbar
                    .make(scrollView, "No Data Found..?", Snackbar.LENGTH_LONG)
                    .setAction("Add Now", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(SyncRecordList.this, MainActivity.class));
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }
        else
        {
            state=true;
        }
        return state;
    }




    private void createList() {


        mSQLiteHelper = new SQLiteHelper(this, "Construction.sqlite", null, 2);
        Cursor cursor = mSQLiteHelper.getData("SELECT * FROM Construction");
        employees.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String Draftsman = cursor.getString(1);
            String ownername = cursor.getString(2);
            String fathername = cursor.getString(3);
            String phoneno = cursor.getString(38);
            //add to list
            employees.add(new SyncModel(id, Draftsman, ownername, fathername,phoneno));

        }
        adapter.notifyDataSetChanged();

    }

    public void back(View view) {
        Intent intent = new Intent(SyncRecordList.this, dashboard.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
            Intent intent = new Intent(SyncRecordList.this, dashboard.class);
            startActivity(intent);
    }
}
