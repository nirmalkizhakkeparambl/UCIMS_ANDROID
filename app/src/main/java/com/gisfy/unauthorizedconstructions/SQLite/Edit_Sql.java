package com.gisfy.unauthorizedconstructions.SQLite;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.gisfy.unauthorizedconstructions.R;
import com.gisfy.unauthorizedconstructions.Utils.WorkaroundMapFragment;
import com.gisfy.unauthorizedconstructions.WMS_Layer.TileProviderFactory;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.watermark.androidwm_light.WatermarkBuilder;
import com.watermark.androidwm_light.bean.WatermarkText;
import com.zolad.videoslimmer.VideoSlimmer;
import net.cachapa.expandablelayout.ExpandableLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class Edit_Sql extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    ExtendedEditText district, municipality, roadwidening1, roadwidening2, are1, area2, ownername, fathername, address,
            surveyno, village, doorno, locality, streetname, sitearea,etplintharea,etplintharea_1,
            approvedplan,date,age,
            parking1, parking2, floor1, floor2, front, front2, left, left2, right, right2, back, back2, landuse1, landuse2, note, phoneno;
    TextFieldBoxes roadwidening1tf, roadwidening2tf, are1tf, area2tf, ownernametf, fathernametf, addresstf,
            surveynotf, villagetf, doornotf, localitytf, streetnametf, siteareatf, approvedplantf,datetf,plintharea,plintharea_1,
            parkingt1f, parking2f, floor1f, floor2f, fronttf, front2tf, lefttf, userage,left2tf, righttf, right2tf, backtf, back2tf, landusetf, landuse2tf, notetf, phonenotf;
    TextView draftsman,asperTV,asperTV2,violationDeviation;
    ImageView edit_latlng,done_latlng;
    EditText locationtext;
    Spinner mandalSpinner, villageSpinner ,natureofconst, Constype,constructionStage, Constfloors
            , Floorsasonground,
            NoofFloors,
            NoofFloors1,
            Existingfloors, Convertingfloors, totaluc,stiltparking;
    LinearLayout checkboxLayout;
    ImageView locationimage, PreviewImage, PreviewImage2, PreviewImage3, PreviewImage4;
    ExpandableLayout expandLayoutupdate, expandableLayout_map, cardview;
   // FusedLocationProviderClient client;
    ArrayList<Model> mList;
    RecordListAdapter mAdapter = null;
    String picturePath, picturePath2, picturePath3, picturePath4;
    double latit;
    double lngit;
    Button showvideo;
    LinearLayout villageLayout,total_ucexpand1, total_ucexpand2, total_ucexpand3,total_ucexpand4;
    double longitude;
    double latitude;
    boolean spinnerflag = false;
    boolean spinnerflag2 = false;
    boolean spinnerflag3 = false;
    boolean spinnerflag4 = false;
    boolean spinnerflag5 = false;
    boolean spinnerflag6 = false;
    boolean spinnerflag7 = false;
    SQLiteHelper mSQLiteHelper;
    int position;
    SupportMapFragment mapFragment;
    MarkerOptions manualmarker;
    ScrollView mScrollView;
    Location location;
    GoogleMap mMap;
    GoogleApiClient googleApiClient;
    Marker mCurrLocationMarker;
    LatLng latLng;
    String smunicipality,newdate,date45;
    String sdistrict,datenew;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
    String soption11="",soption12="", soption21="", soption22="", soption31="", soption32="", soption23="",soption41="";
    String savedpath;
    String formattedDate;
    boolean islatlngEditable=false;
    String uploadId, uploadId2, uploadId3, uploadId4;
    String videopath, videoname;
    VideoView videoView;
    boolean option11 = false;
    boolean option12 = false;
    boolean option21 = false;
    boolean option41 = false;
    String mandals[]=null;
    boolean flag=false,flag1,flag2;
    private String polygonFromSQL;
    private Polygon polygon = null;
    private List<LatLng> listLatLngs = new ArrayList<>();
    private List<Marker> listMarkers = new ArrayList<>();
    private List<String> listLatLngsapi = new ArrayList<>();
    boolean polyflag=false;
    private int mandalIds[]=null;
    boolean isUDA = false;
    byte[] checkBoxes = new byte[8];
    private CheckBox noDeviation, allDeviation, setbacks, additionalFloors, roadWinding, buildup,
            parking, landMisuse;

    private String villageFromSQL="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__sql);
        findViewByIds();

        mSQLiteHelper = new SQLiteHelper(this, "Construction.sqlite", null, 2);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        String grade = sh.getString("grade",null);
        final Cursor cursor3 = mSQLiteHelper.getMandals();
        int count = cursor3.getCount();

        if(count!=0){
            mandals = new String[count];
            mandalIds = new int[count];
            for(int i=0;i<count;i++){
                cursor3.moveToNext();
                mandalIds[i]=cursor3.getInt(0);
                mandals[i]=cursor3.getString(1);
            }
            ArrayAdapter mandalAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_dropdown_item_1line
                    ,mandals);
            mandalSpinner.setAdapter(mandalAdapter);
        }else{

        }


        isUDA = grade.contains("UDA");

        if(isUDA){
            villagetf.setVisibility(View.GONE);
            villageLayout.setVisibility(View.VISIBLE);
        }

        villageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Edit_Sql.this,"Please select Mandal first",Toast.LENGTH_SHORT).show();
            }
        });

        mandalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor1 = mSQLiteHelper.getVillages(mandalIds[position]);
                String villages[];
                int count = cursor1.getCount();
                if(count!=0){
                    if(cursor1.moveToNext()){
                        villages = new String[count];
                        for(int i=0;i<count;i++){
                            villages[i]=cursor1.getString(1);
                            cursor1.moveToNext();
                        }

                        ArrayAdapter villagesAdapter = new ArrayAdapter(Edit_Sql.this,
                                android.R.layout.simple_dropdown_item_1line,villages);
                        villageSpinner.setAdapter(villagesAdapter);
                        villageLayout.setOnClickListener(null);

                        for(int i=0;i<villages.length;i++){
                            if(villages[i].equalsIgnoreCase(villageFromSQL)){
                                villageSpinner.setSelection(i);
                                break;
                            }
                        }

                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        uploadId = UUID.randomUUID().toString();
        uploadId2 = UUID.randomUUID().toString();
        uploadId3 = UUID.randomUUID().toString();
        uploadId4 = UUID.randomUUID().toString();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c);
        mList = new ArrayList<>();
        mAdapter = new RecordListAdapter(this, R.layout.row, mList);
        ArrayAdapter<String> typespinner = new ArrayAdapter<String>(Edit_Sql.this,
                R.layout.spinner_layout, getApplicationContext().getResources().getStringArray(R.array.Type));

        ArrayAdapter<String> typespinner1 = new ArrayAdapter<String>(Edit_Sql.this,
                R.layout.spinner_layout, getApplicationContext().getResources().getStringArray(R.array.Type1));

        ArrayAdapter<String> typespinner2 = new ArrayAdapter<String>(Edit_Sql.this,
                R.layout.spinner_layout, getApplicationContext().getResources().getStringArray(R.array.Type2));
        ArrayAdapter<String> stiltspinner = new ArrayAdapter<String>(Edit_Sql.this,
                R.layout.spinner_layout, getApplicationContext().getResources().getStringArray(R.array.StiltParking));

        ArrayAdapter<String> option2 = new ArrayAdapter<String>(Edit_Sql.this,
                R.layout.spinner_layout, getApplicationContext().getResources().getStringArray(R.array.Option2));

        ArrayAdapter<String> floor = new ArrayAdapter<String>(Edit_Sql.this,
                R.layout.spinner_layout, getApplicationContext().getResources().getStringArray(R.array.floor));

        ArrayAdapter<String> floor12 = new ArrayAdapter<String>(Edit_Sql.this,
                R.layout.spinner_layout, getApplicationContext().getResources().getStringArray(R.array.floor1));

        Floorsasonground.setAdapter(typespinner);
        NoofFloors.setAdapter(option2);
        NoofFloors1.setAdapter(floor);
        Existingfloors.setAdapter(typespinner1);
        Convertingfloors.setAdapter(typespinner2);
        Constype.setAdapter(typespinner);
        Constfloors.setAdapter(floor12);
        stiltparking.setAdapter(stiltspinner);

        ArrayAdapter adapter2 = new ArrayAdapter(this, R.layout.spinner_layout,
                getResources().getStringArray(R.array.constructionStages));

        constructionStage.setAdapter(adapter2);

        noDeviation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    allDeviation.setChecked(false);
                    setbacks.setChecked(false);
                    additionalFloors.setChecked(false);
                    roadWinding.setChecked(false);
                    buildup.setChecked(false);
                    parking.setChecked(false);
                    landMisuse.setChecked(false);
                }
            }
        });


        allDeviation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    noDeviation.setChecked(false);
                    setbacks.setChecked(true);
                    additionalFloors.setChecked(true);
                    roadWinding.setChecked(true);
                    buildup.setChecked(true);
                    parking.setChecked(true);
                    landMisuse.setChecked(true);
                }else{
                    setbacks.setChecked(false);
                    additionalFloors.setChecked(false);
                    roadWinding.setChecked(false);
                    buildup.setChecked(false);
                    parking.setChecked(false);
                    landMisuse.setChecked(false);
                }
            }
        });

        totaluc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    total_ucexpand1.setVisibility(View.GONE);
                    total_ucexpand2.setVisibility(View.GONE);
                    total_ucexpand3.setVisibility(View.GONE);
                    total_ucexpand4.setVisibility(View.GONE);

                    removeTotalUCSelections();



                    soption31="";
                    soption32="";

                    soption21="";
                    soption22="";
                    soption23="";

                    soption11="";
                    soption12="";


                }

                else if(position==1) {

                    option11=true;
                    option12=false;
                    option21=false;
                    option41=false;

                    removeTotalUCSelections();
                    total_ucexpand1.setVisibility(View.VISIBLE);
                    total_ucexpand2.setVisibility(View.GONE);
                    total_ucexpand3.setVisibility(View.GONE);
                    total_ucexpand4.setVisibility(View.GONE);

                    soption21="";
                    soption22="";
                    soption23="";
                    soption31="";
                    soption32="";


                    Constype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position!=0) {

                                soption11 = Constype.getSelectedItem().toString();
                            }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                    Constfloors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position!=0){

                                soption12 = Constfloors.getSelectedItem().toString();
                            }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                else if(position==2){
                    option12=true;
                    option11=false;
                    option21=false;
                    option41=false;

                    removeTotalUCSelections();
                    total_ucexpand2.setVisibility(View.VISIBLE);
                    total_ucexpand1.setVisibility(View.GONE);
                    total_ucexpand3.setVisibility(View.GONE);
                    total_ucexpand4.setVisibility(View.GONE);

                    soption11="";
                    soption12="";

                    soption31="";
                    soption32="";

                    Floorsasonground.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position!=0){
                                soption21 = Floorsasonground.getSelectedItem().toString();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                    NoofFloors1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position!=0){
                                soption22 = NoofFloors1.getSelectedItem().toString();
                                soption23="";

                                if(position==1){
                                    NoofFloors.setSelection(1);
                                   // NoofFloors.setEnabled(false);
                                    soption23=NoofFloors.getSelectedItem().toString();
                                    Log.i("sp23",soption23);

                                }
                                else if(position==2){
                                    NoofFloors.setSelection(2);
                                  //  NoofFloors.setEnabled(false);
                                    soption23=NoofFloors.getSelectedItem().toString();
                                    Log.i("sp23",soption23);
                                }
                                else if(position==3){
                                    NoofFloors.setSelection(3);
                                  //  NoofFloors.setEnabled(false);
                                    soption23=NoofFloors.getSelectedItem().toString();

                                }
                                else if(position==4){
                                    NoofFloors.setSelection(4);
                                   // NoofFloors.setEnabled(false);
                                    soption23=NoofFloors.getSelectedItem().toString();

                                }
                                else if(position==5){
                                    NoofFloors.setSelection(5);
                                   // NoofFloors.setEnabled(false);
                                    soption23=NoofFloors.getSelectedItem().toString();

                                }
                                else if(position==6){
                                    NoofFloors.setSelection(6);
                                   // NoofFloors.setEnabled(false);
                                    soption23=NoofFloors.getSelectedItem().toString();

                                }
                                else if(position==7){
                                    NoofFloors.setSelection(7);
                                    //NoofFloors.setEnabled(false);
                                    soption23=NoofFloors.getSelectedItem().toString();

                                }
                                else if(position==8){
                                    NoofFloors.setSelection(8);
                                  //  NoofFloors.setEnabled(false);
                                    soption23=NoofFloors.getSelectedItem().toString();

                                }

                            }
                            else{
                                NoofFloors.setSelection(0);
                                soption23="";
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

//                    NoofFloors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                            if(position!=0){
//
//
//                                soption23 = NoofFloors.getSelectedItem().toString();
//                            }
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//
//                        }
//                    });

                }

                else if(position ==3 ){

                    option21=true;
                    option11=false;
                    option12=false;
                    option41=false;
                    removeTotalUCSelections();
                    total_ucexpand3.setVisibility(View.VISIBLE);
                    total_ucexpand1.setVisibility(View.GONE);
                    total_ucexpand2.setVisibility(View.GONE);
                    total_ucexpand4.setVisibility(View.GONE);

                    soption21="";
                    soption22="";
                    soption23="";

                    soption11="";
                    soption12="";

                    Existingfloors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position!=0){


                                soption31 = Existingfloors.getSelectedItem().toString();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                    Convertingfloors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position!=0){


                                soption32 = Convertingfloors.getSelectedItem().toString();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });



                }

                else if (position == 4) {
                    total_ucexpand4.setVisibility(View.VISIBLE);
                    total_ucexpand1.setVisibility(View.GONE);
                    total_ucexpand2.setVisibility(View.GONE);
                    total_ucexpand3.setVisibility(View.GONE);

                    removeTotalUCSelections();
                    soption21 = "";
                    soption22 = "";
                    soption23 = "";

                    soption11 = "";
                    soption12 = "";

                    soption31 = "";
                    soption32 = "";

                    option21 = false;
                    option11 = false;
                    option12 = false;
                    option41=true;
                    Log.i("entered 4", (String.valueOf(option21)));

                    stiltparking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                soption41 = stiltparking.getSelectedItem().toString();
                            }
                            else{
                                soption41="";
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        position = getIntent().getIntExtra("position", 0);
  //      client = LocationServices.getFusedLocationProviderClient(this);
        mList.clear();


        ArrayAdapter<String> districtadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.District));
        districtadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        district.setAdapter(districtadapter);
        mapFragment = (WorkaroundMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.emap);
        mapFragment.getMapAsync(this);
        mScrollView = (ScrollView) findViewById(R.id.escrollview);
        ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.emap)).setListener(new WorkaroundMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                mScrollView.requestDisallowInterceptTouchEvent(true);
            }
        });
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
           // configureCameraIdle();
        } else {
            Toast.makeText(this, "Network is Required to use Map", Toast.LENGTH_SHORT).show();
        }



        Cursor cursor = mSQLiteHelper.getData("SELECT * FROM Construction WHERE id=" + position);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String sDraftsman = cursor.getString(1);
            draftsman.setText(sDraftsman);
            String sownername = cursor.getString(2);
            ownername.setText(sownername);
            String sfathername = cursor.getString(3);
            fathername.setText(sfathername);
            String saddress = cursor.getString(4);
            address.setText(saddress);
            sdistrict = cursor.getString(5);
            district.setText(sdistrict);
            smunicipality = cursor.getString(6);
            municipality.setText(smunicipality);

            String ssurveyno = cursor.getString(7);
            Log.d("surveynum",ssurveyno);

            String svillage = cursor.getString(8);
            village.setText(svillage);

            surveyno.setText(ssurveyno);

            String sdoorno = cursor.getString(9);
            doorno.setText(sdoorno);
            String slocality = cursor.getString(10);
            locality.setText(slocality);
            String sstreetname = cursor.getString(11);
            streetname.setText(sstreetname);
            String ssitearea = cursor.getString(12);
            sitearea.setText(ssitearea);
            String snatureofconst = cursor.getString(13);
            SpinnerSelection2(natureofconst, snatureofconst);
            String sapprovedplan = cursor.getString(14);
            approvedplan.setText(sapprovedplan);
            String sparking = cursor.getString(15);
            parking1.setText(sparking);
            String sparking2 = cursor.getString(16);
            parking2.setText(sparking2);
            String sfloor1 = cursor.getString(17);
            floor1.setText(sfloor1);
            String sfloor2 = cursor.getString(18);
            floor2.setText(sfloor2);
            String sfront = cursor.getString(19);
            front.setText(sfront);
            String sfront2 = cursor.getString(20);
            front2.setText(sfront2);
            String sleft = cursor.getString(21);
            left.setText(sleft);
            String sleft2 = cursor.getString(22);
            left2.setText(sleft2);
            String sright = cursor.getString(23);
            right.setText(sright);
            String sright2 = cursor.getString(24);
            right2.setText(sright2);
            String sback = cursor.getString(25);
            back.setText(sback);
            String sback2 = cursor.getString(26);
            back2.setText(sback2);

            String date1 = cursor.getString(54);
            date.setText(date1);

            String slanduse1 = cursor.getString(27);
            landuse1.setText(slanduse1);
            String slanduse2 = cursor.getString(28);
            landuse2.setText(slanduse2);
            String slocation = cursor.getString(29);
            String[] latlong = slocation.split(",");
            latitude = Double.parseDouble(latlong[0]);
            longitude = Double.parseDouble(latlong[1]);
            locationtext.setText(slocation);
            String phonenumber = cursor.getString(38);
            phoneno.setText(phonenumber);
            videoname = cursor.getString(51);
            videopath = cursor.getString(50);

            String totaluci = cursor.getString(52);
            getspinposition(totaluci);

            polygonFromSQL = cursor.getString(49);


            villageFromSQL = cursor.getString(8);

            String mandal = cursor.getString(58);
            Log.d("DEBUGGING man",mandal);
            if(mandals!=null){
                for(int i=0;i<mandals.length;i++){
                    if(mandals[i].equalsIgnoreCase(mandal)){
                        mandalSpinner.setSelection(i);
                        break;
                    }
                }
            }
            if(!isUDA)
                village.setText(villageFromSQL);

            checkBoxes[0] = (byte) cursor.getInt(59);
            if(checkBoxes[0]==1)
                noDeviation.setChecked(true);

            checkBoxes[1] = (byte) cursor.getInt(60);
            if(checkBoxes[1]==1)
                allDeviation.setChecked(true);

            checkBoxes[2] = (byte) cursor.getInt(61);
            if(checkBoxes[2]==1)
                setbacks.setChecked(true);

            checkBoxes[3] = (byte) cursor.getInt(62);
            if(checkBoxes[3]==1)
                additionalFloors.setChecked(true);

            checkBoxes[4] = (byte) cursor.getInt(63);
            if(checkBoxes[4]==1)
                roadWinding.setChecked(true);

            checkBoxes[5] = (byte) cursor.getInt(64);
            if(checkBoxes[5]==1)
                buildup.setChecked(true);

            checkBoxes[6] = (byte) cursor.getInt(65);
            if(checkBoxes[6]==1)
                parking.setChecked(true);

            checkBoxes[7] = (byte) cursor.getInt(66);
            if(checkBoxes[7]==1)
                landMisuse.setChecked(true);


            String constStage = cursor.getString(67);
            String stages[] = getResources().getStringArray(R.array.constructionStages);
            for(int i=0;i<stages.length;i++){
                if(stages[i].equalsIgnoreCase(constStage)){
                    constructionStage.setSelection(i);
                    break;
                }
            }

            Log.i("videopath", videopath);

            showvideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoView.setVisibility(View.VISIBLE);
                    final MediaController mediacontroller = new MediaController(Edit_Sql.this);
                    mediacontroller.setAnchorView(videoView);
                    videoView.setMediaController(mediacontroller);
                    videoView.setVideoPath(videopath);
                }
            });


            try {
                picturePath = cursor.getString(30);
                Bitmap selectedImage = BitmapFactory.decodeFile(picturePath);
//                File f = new File(picturePath);
//                Picasso.get().load(f).into(PreviewImage);
                PreviewImage.setImageBitmap(selectedImage);

                picturePath2 = cursor.getString(32);
                Bitmap selectedImage2 = BitmapFactory.decodeFile(picturePath2);
                File f2 = new File(picturePath2);
                Picasso.get().load(f2).into(PreviewImage2);
                //  PreviewImage2.setImageBitmap(selectedImage2);

                picturePath3 = cursor.getString(34);
                Bitmap selectedImage3 = BitmapFactory.decodeFile(picturePath3);
                File f3 = new File(picturePath3);
                Picasso.get().load(f3).into(PreviewImage3);
                //  PreviewImage3.setImageBitmap(selectedImage3);

                picturePath4 = cursor.getString(36);
                Bitmap selectedImage4 = BitmapFactory.decodeFile(picturePath4);
                File f4 = new File(picturePath4);
                Picasso.get().load(f4).into(PreviewImage4);
                //PreviewImage4.setImageBitmap(selectedImage4);
            } catch (OutOfMemoryError e) {
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            Log.i("NoOfFloors",cursor.getString(42));
            String ConstructionType = cursor.getString(39);
            SpinnerSelectiontype(Constype, ConstructionType);
            soption11=ConstructionType;

            String ConstructionFloors = cursor.getString(40);
            SpinnerSelectionfloor(Constfloors, ConstructionFloors);
            soption12=ConstructionFloors;

            String stiltpark = cursor.getString(55);
            SpinnerSelectionStiltPark(stiltparking,stiltpark);
            soption41=stiltpark;
            Log.i("soption41",stiltpark);

            String m = cursor.getString(41);
            SpinnerSelectiontype123(Floorsasonground, m);
            soption21=m;

            Log.i("spin21",m);

            String x = cursor.getString(42);
            SpinnerSelectionfloor123(NoofFloors1, x);
            soption22=x;


            Log.i("spin22",x);

            String p = cursor.getString(53);
            SpinnerSelectionOptions2(NoofFloors, p);
            soption23=p;

            Log.i("spin23",p);




            String ExistingFloors = cursor.getString(43);
            SpinnerSelectiontype1(Existingfloors, ExistingFloors);
            soption31=ExistingFloors;


            String ConvertingFloors = cursor.getString(44);
            SpinnerSelectiontype2(Convertingfloors, ConvertingFloors);
            soption32=ConvertingFloors;


            String sroadwidening1 = cursor.getString(45);
            roadwidening1.setText(sroadwidening1);
            String sroadwidening2 = cursor.getString(46);
            roadwidening2.setText(sroadwidening2);
            String sarea1 = cursor.getString(47);
            are1.setText(sarea1);
            String sarea2 = cursor.getString(48);
            area2.setText(sarea2);
            String totaluc = cursor.getString(52);

            Log.i("totaluc", totaluc);
            String age1 = cursor.getString(57);
            age.setText(age1);

            String plinthi = cursor.getString(56);
            etplintharea.setText(plinthi);

            String builtIt = cursor.getString(68);
            etplintharea_1.setText(builtIt);
            Log.d("DEBUGGING","SQL VAR LT LNG:"+latitude+"-"+longitude);


        }

        edit_latlng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationtext.setEnabled(true);
                islatlngEditable=true;
                edit_latlng.setVisibility(View.GONE);
                done_latlng.setVisibility(View.VISIBLE);
            }
        });
        done_latlng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationtext.setEnabled(false);
                islatlngEditable=false;
                if (!isValidLatLng(locationtext.getText().toString())) {
                    Toast.makeText(Edit_Sql.this, "Not Valid LatLng", Toast.LENGTH_SHORT).show();
                } else {
                    String[] latlngstr = locationtext.getText().toString().split(",");
                    double lat = Double.parseDouble(latlngstr[0]);
                    double lng = Double.parseDouble(latlngstr[1]);
                    LatLng coordinate = new LatLng(lat, lng);


                    manualmarker=new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .title("Manually Marked")
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    mMap.addMarker(manualmarker);

                    CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                            coordinate, 15);
                    mMap.animateCamera(location);


                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            marker.remove();
                            return false;
                        }
                    });
                }
                done_latlng.setVisibility(View.GONE);
                edit_latlng.setVisibility(View.VISIBLE);
            }
        });


        natureofconst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    cardview.collapse();
                    plintharea.setVisibility(View.GONE);
                    plintharea_1.setVisibility(View.GONE);
                    expandLayoutupdate.collapse(true);
                    findViewById(R.id.eapprovedplanTexfield).setVisibility(View.GONE);
                    findViewById(R.id.date_tf).setVisibility(View.GONE);
                    findViewById(R.id.total_ucexpand1).setVisibility(View.GONE);
                    findViewById(R.id.total_ucexpand2).setVisibility(View.GONE);
                    findViewById(R.id.total_ucexpand3).setVisibility(View.GONE);
                    findViewById(R.id.total_ucexpand4).setVisibility(View.GONE);
                }
                if (position == 1) {
                    cardview.collapse();
                    plintharea.setVisibility(View.GONE);
                    plintharea_1.setVisibility(View.GONE);
                    totaluc.setVisibility(View.GONE);
                    expandLayoutupdate.expand();
                    asperTV.setText("As Per Plan");
                    asperTV2.setText("As on Ground");
                    violationDeviation.setText("Deviation (in meters)");
                    findViewById(R.id.eapprovedplanTexfield).setVisibility(View.VISIBLE);
                    findViewById(R.id.date_tf).setVisibility(View.VISIBLE);

                    findViewById(R.id.total_ucexpand1).setVisibility(View.GONE);
                    findViewById(R.id.total_ucexpand2).setVisibility(View.GONE);
                    findViewById(R.id.total_ucexpand3).setVisibility(View.GONE);
                    findViewById(R.id.total_ucexpand4).setVisibility(View.GONE);
                }
                if (position == 2) {
                    cardview.collapse();
                    plintharea.setVisibility(View.VISIBLE);
                    plintharea_1.setVisibility(View.VISIBLE);
                    expandLayoutupdate.expand(true);
                    approvedplantf.setVisibility(View.GONE);
                    datetf.setVisibility(View.GONE);
                    asperTV.setText("As Per Rule");
                    asperTV2.setText("As per Construction");
                    violationDeviation.setText("Violation (in meters)");
                    findViewById(R.id.eapprovedplanTexfield).setVisibility(View.GONE);
                    findViewById(R.id.date_tf).setVisibility(View.GONE);

                    findViewById(R.id.total_ucexpand1).setVisibility(View.VISIBLE);
                    findViewById(R.id.total_ucexpand2).setVisibility(View.VISIBLE);
                    findViewById(R.id.total_ucexpand3).setVisibility(View.VISIBLE);
                    findViewById(R.id.total_ucexpand4).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        locationimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        if (location != null) {
//                            latit = location.getLatitude();
//                            lngit = location.getLongitude();
//                            locationtext.setText(latit + " " + latit);
//                        }
//                    }
//                });
            }
        });


    }

    private void removeTotalUCSelections(){
//        Constype.setSelection(0);
//        Constfloors.setSelection(0);
//        Floorsasonground.setSelection(0);
//        NoofFloors.setSelection(0);
//        NoofFloors1.setSelection(0);
//        Existingfloors.setSelection(0);
//        Convertingfloors.setSelection(0);
//        stiltparking.setSelection(0);
    }

    @Override
    protected void onStart() {
        super.onStart();


        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener calender = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                int month=monthOfYear+1;
                datenew=year+"-"+month+"-"+dayOfMonth;
                date.setText(dayOfMonth+"-"+month+"-"+year);
            }
        };



        datetf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Edit_Sql.this, calender, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public static boolean isValidLatLng(String str)
    {
        String regex = "^(-?\\d+(\\.\\d+)?),\\s*(-?\\d+(\\.\\d+)?)$";
        Pattern p = Pattern.compile(regex);
        if (str == null) {
            return false;
        }
        Matcher m = p.matcher(str);
        return m.matches();
    }



//    private void configureCameraIdle() {
//        onCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
//            @Override
//            public void onCameraIdle() {
//                LatLng latLng = mMap.getCameraPosition().target;
//                float lati= (float) latLng.latitude;
//                float longi=(float)latLng.longitude;
//                locationtext.setText(longi + "," + lati);
//               // locationtext.setText(latLng.longitude + "," + latLng.latitude);
//            }
//        };
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if(latitude==0||longitude==0){
            Log.d("DEBUGGING","NUll LATLNG");
        }else{
            Log.d("DEBUGGING","Latitude: "+latitude+" LOngitude: "+longitude);
            LatLng latLng = new LatLng(latitude, longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));

        }

        setGoogleMapClickListener();
        setGoogleMapLongClickListener();
        setPolygonClickListener();
        TileProvider wmsTileProvider = TileProviderFactory.getOsgeoWmsTileProvider();
        googleMap.addTileOverlay(new TileOverlayOptions().tileProvider(wmsTileProvider));

        if(polygonFromSQL!=null&&!polygonFromSQL.equals("null")){
            Log.d("DEBUGGING","PolyGon::"+polygonFromSQL);

            String points[] = polygonFromSQL.split(",");

            for(String point: points){
                String[] latlng = point.split("--");

                latlng[0]=latlng[0].trim();
                latlng[1]=latlng[1].trim();
                Log.d("DEBUGGING","Lat-"+latlng[0]+"Lng-"+latlng[1]);

                LatLng e = new LatLng(Double.parseDouble(latlng[0]),Double.parseDouble(latlng[1]));
                MarkerOptions markerOptions = new MarkerOptions().position(e);
                Marker marker = mMap.addMarker(markerOptions);
                listMarkers.add(marker);
                listLatLngs.add(e);
                String lats = e.latitude + "--" + e.longitude;
                listLatLngsapi.add(lats);
                polyflag=true;
            }

            PolygonOptions polygonOptions = new PolygonOptions().addAll(listLatLngs).clickable(true);
            polygon = mMap.addPolygon(polygonOptions);

        }
    }

    private void setPolygonClickListener() {
        mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
            @Override
            public void onPolygonClick(Polygon e) {
                Snackbar.make(mScrollView, "Polygon clicked!" + e, Snackbar.LENGTH_SHORT).show();

            }
        });
    }

    private void setGoogleMapClickListener() {
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng e) {
                MarkerOptions markerOptions = new MarkerOptions().position(e);
                Marker marker = mMap.addMarker(markerOptions);
                listMarkers.add(marker);
                listLatLngs.add(e);
                String lats = e.latitude + "--" + e.longitude;
                listLatLngsapi.add(lats);
                Log.i("listLatLngsapi", String.valueOf(listLatLngsapi));
            }
        });
    }

    private void setGoogleMapLongClickListener() {
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng e) {
                if (polygon != null) polygon.remove();
                for (Marker marker : listMarkers) marker.remove();

                listLatLngs.clear();
                polyflag=false;
                listMarkers.clear();
                listLatLngsapi.clear();
                Log.i("listmarkercleared", String.valueOf(listMarkers.size()));
            }
        });
    }

//    private void buildGoogleApiClient() {
//        googleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API).build();
//        googleApiClient.connect();
//    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        assert manager != null;
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        } else {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Please Turn on Gps", Toast.LENGTH_SHORT).show();
            }
            location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(getApplicationContext(), RecordListActivity.class));
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    private void findViewByIds() {
        asperTV = findViewById(R.id.asperTV);
        asperTV2 = findViewById(R.id.asperTV2);
        violationDeviation = findViewById(R.id.violationDeviation);
        videoView = findViewById(R.id.video_view);
        edit_latlng=findViewById(R.id.edit_latlng);
        done_latlng=findViewById(R.id.done_latlng);
        etplintharea=findViewById(R.id.etplintharea);
        plintharea=findViewById(R.id.plintharea);
        etplintharea_1=findViewById(R.id.etplintharea_i);
        plintharea_1=findViewById(R.id.plintharea_i);
        age=findViewById(R.id.age);
        userage=findViewById(R.id.userage);
        roadwidening1 = findViewById(R.id.eroadwidening1);
        roadwidening2 = findViewById(R.id.eroadwidening2);
        are1 = findViewById(R.id.earea1);
        area2 = findViewById(R.id.earea2);
        date=findViewById(R.id.date_edit);
        date.setEnabled(false);
        showvideo=findViewById(R.id.showvideo);
        datetf=findViewById(R.id.date_tf);
        roadwidening1tf = findViewById(R.id.eroadwidening1Textfield);
        roadwidening2tf = findViewById(R.id.eroadwidening2Texfield);
        are1tf = findViewById(R.id.earea1Textfield);
        area2tf = findViewById(R.id.earea2Texfield);
        totaluc = findViewById(R.id.total_uc);
        checkboxLayout = findViewById(R.id.checkboxLayout);

        noDeviation = findViewById(R.id.noDeviationCB);
        allDeviation = findViewById(R.id.allDeviationCB);
        setbacks = findViewById(R.id.setbacksCB);
        additionalFloors = findViewById(R.id.additionalFloorsCB);
        roadWinding = findViewById(R.id.roadWindingCB);
        buildup = findViewById(R.id.buildupAreaCB);
        parking = findViewById(R.id.parkingCB);
        landMisuse = findViewById(R.id.landMisuseCB);

        stiltparking = findViewById(R.id.misusingofstiltparking);
        cardview = findViewById(R.id.eexpandable_layout2);
        PreviewImage = findViewById(R.id.eprofileimage);
        PreviewImage2 = findViewById(R.id.eimageView2);
        PreviewImage3 = findViewById(R.id.eimageview3);
        PreviewImage4 = findViewById(R.id.eimageview4);
        draftsman = findViewById(R.id.edraftsman);
        ownername = findViewById(R.id.eowner);
        fathername = findViewById(R.id.efathername);
        address = findViewById(R.id.eaddress);
        district = findViewById(R.id.edistrict);
        district.setEnabled(false);
        municipality = findViewById(R.id.emunicipality);
        municipality.setEnabled(false);
        surveyno = findViewById(R.id.esurveyno);
        village = findViewById(R.id.evillage);
        doorno = findViewById(R.id.edoorno);

        total_ucexpand1 = findViewById(R.id.total_ucexpand1);
        total_ucexpand2 = findViewById(R.id.total_ucexpand2);
        total_ucexpand3 = findViewById(R.id.total_ucexpand3);
        total_ucexpand4 = findViewById(R.id.total_ucexpand4);

        locality = findViewById(R.id.elocality);
        streetname = findViewById(R.id.estreetname);
        approvedplan = findViewById(R.id.eapprovedplan);
        parking1 = findViewById(R.id.eparking1);
        parking2 = findViewById(R.id.eparking2);
        floor1 = findViewById(R.id.efloor1);
        floor2 = findViewById(R.id.efloor2);
        front = findViewById(R.id.efront1);
        front2 = findViewById(R.id.efront2);
        left = findViewById(R.id.eleft1);
        left2 = findViewById(R.id.eleft2);
        right = findViewById(R.id.eright1);
        back = findViewById(R.id.eback1);
        back2 = findViewById(R.id.eback2);
        landuse1 = findViewById(R.id.elanduse1);
        landuse2 = findViewById(R.id.elanduse2);
        right2 = findViewById(R.id.eright2);
        locationimage = findViewById(R.id.elocationimage);
        sitearea = findViewById(R.id.esitearea);
        natureofconst = findViewById(R.id.enatureofconst);
        natureofconst.setEnabled(false);
        locationtext = findViewById(R.id.eloc_text);
        phoneno = findViewById(R.id.ephonenumber);
        expandLayoutupdate = findViewById(R.id.eexpandable_layout);
        expandableLayout_map = findViewById(R.id.eexpandable_layout_map);
        //expandableLayout_map.collapse();
        expandLayoutupdate.collapse();
        ownernametf = findViewById(R.id.eownerTexfield);
        fathernametf = findViewById(R.id.efathernameTexfield);
        addresstf = findViewById(R.id.eaddressTexfield);
        surveynotf = findViewById(R.id.esurveynoTexfield);
        villagetf = findViewById(R.id.evillageTexfield);
        doornotf = findViewById(R.id.edoornoTexfield);
        localitytf = findViewById(R.id.elocalityTexfield);
        streetnametf = findViewById(R.id.estreetnameTexfield);
        approvedplantf = findViewById(R.id.eapprovedplanTexfield);
        parkingt1f = findViewById(R.id.eparking1Texfield);
        parking2f = findViewById(R.id.eparking2Texfield);
        floor1f = findViewById(R.id.efloor1Texfield);
        floor2f = findViewById(R.id.efloor2Texfield);
        fronttf = findViewById(R.id.efront1Texfield);
        front2tf = findViewById(R.id.efront2Texfield);
        lefttf = findViewById(R.id.eleft1Texfield);
        left2tf = findViewById(R.id.eleft2Texfield);
        righttf = findViewById(R.id.eright1Texfield);
        backtf = findViewById(R.id.eback1Texfield);
        back2tf = findViewById(R.id.eback2Texfield);
        right2tf = findViewById(R.id.eright2Texfield);
        siteareatf = findViewById(R.id.esiteareaTexfield);
        landusetf = findViewById(R.id.eland1Texfield);
        landuse2tf = findViewById(R.id.elanduse2Texfield);
        phonenotf = findViewById(R.id.ephonenumbertextfiled);

        Constype = findViewById(R.id.ConstructionType);
        constructionStage = findViewById(R.id.constructionStage);
        Constfloors = findViewById(R.id.ConstructionFloors);

        Floorsasonground = findViewById(R.id.FloorsAsOnGround);
        NoofFloors = findViewById(R.id.NoOfFloors);
        NoofFloors1 = findViewById(R.id.NoOfFloors1);

        Existingfloors = findViewById(R.id.ExistingFloors);
        Convertingfloors = findViewById(R.id.ConvertingFloors);

        mandalSpinner = findViewById(R.id.mandalSpinner);
        villageLayout = findViewById(R.id.villageLayout);
        villageSpinner = findViewById(R.id.villageSpinner);

        fathername.setFilters(new InputFilter[]{new InputFilter.LengthFilter(25)});
        ownername.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        address.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        surveyno.setFilters(new InputFilter[]{new InputFilter.LengthFilter(25)});
        village.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        doorno.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        locality.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        approvedplan.setFilters(new InputFilter[]{new InputFilter.LengthFilter(35)});
        etplintharea.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});
        etplintharea_1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});
        streetname.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        parking1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        parking2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        floor1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        floor2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        front.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        front2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        left.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        left2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        right.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        right2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        back.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        back2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        landuse1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        landuse2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        sitearea.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        phoneno.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        roadwidening1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        roadwidening2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        are1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        area2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
    }

    public void Update(View view) {

        if(constructionStage.getSelectedItem().toString().equals("Select Construction Stage")){
            TextView textView = (TextView) constructionStage.getSelectedView();
            textView.setError("Select Construction Stage");
            Toast.makeText(this,"Select Construction Stage",Toast.LENGTH_SHORT).show();
            mScrollView.scrollTo(0,0);
            return;
        }

        if (validate(ownername, ownernametf)) {
            if (validate(fathername, fathernametf)) {
                if (validateAddress(address, addresstf)) {

                        if(listLatLngsapi.size()>0){
                            if(polyflag){
                                validateFeilds();
                            }
                            else{
                                Toast.makeText(this, "Please connect polygon", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else{
                            validateFeilds();
                        }


                }
            }
        }
    }

    private void validateFeilds(){
        if(age.getText().length()>0){
            if (!(district.getText().length() == 0)) {
                if (!(municipality.getText().length() == 0)) {
                    if (surveyno.getText().length() == 0 && village.getText().length() == 0) {
                        if (doorno.getText().length() == 0) {
                            surveynotf.setError("Enter Survey no ", false);
                            villagetf.setError("Enter village ", false);
                            doornotf.setError("Enter Door No ", true);
                        } else {
                            if (validate(locality, localitytf)) {
                                if (spinnerflag) {
                                    if (natureofconst.getSelectedItem().toString().equals("Deviation to the approved plan")) {
                                        if (validate(approvedplan, approvedplantf) && validate(parking1, parkingt1f) && validate(parking2, parking2f) && validate(floor1, floor1f) && validate(floor2, floor2f) && validate(front, fronttf) && validate(front2, front2tf) && validate(right, righttf) && validate(right2, right2tf) && validate(left, lefttf) && validate(left2, left2tf) && validate(back, backtf) && validate(back2, back2tf) && validate(landuse1, landusetf) && validate(landuse2, landuse2tf)) {
                                            Alert(position);
                                        }
                                    } else {
                                        if (validate(parking1, parkingt1f) && validate(parking2, parking2f) && validate(floor1, floor1f) && validate(floor2, floor2f) && validate(front, fronttf) && validate(front2, front2tf) && validate(right, righttf) && validate(right2, right2tf) && validate(left, lefttf) && validate(left2, left2tf) && validate(back, backtf) && validate(back2, back2tf) && validate(roadwidening1, roadwidening1tf) && validate(roadwidening2, roadwidening2tf) && validate(are1,are1tf) && validate(area2,area2tf) &&validate(landuse1, landusetf) && validate(landuse2, landuse2tf))
                                            Alert(position);
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Please Select Nature of Construction", Toast.LENGTH_SHORT).show();
                                    natureofconst.requestFocusFromTouch();
                                    natureofconst.performClick();
                                }
                            }
                        }
                    } else {
                        if (validate(locality, localitytf)) {
                            if (spinnerflag) {
                                if (natureofconst.getSelectedItem().toString().equals("Deviation to the approved plan")) {
                                    if (validate(approvedplan, approvedplantf) && validate(parking1, parkingt1f) && validate(parking2, parking2f) && validate(floor1, floor1f) && validate(floor2, floor2f) && validate(front, fronttf) && validate(front2, front2tf) && validate(right, righttf) && validate(right2, right2tf) && validate(left, lefttf) && validate(left2, left2tf) && validate(back, backtf) && validate(back2, back2tf) && validate(landuse1, landusetf) && validate(landuse2, landuse2tf)) {
                                        Alert(position);
                                    }
                                } else {
                                    if (validate(parking1, parkingt1f) && validate(parking2, parking2f) && validate(floor1, floor1f) && validate(floor2, floor2f) && validate(front, fronttf) && validate(front2, front2tf) && validate(right, righttf) && validate(right2, right2tf) && validate(left, lefttf) && validate(left2, left2tf) && validate(back, backtf) && validate(back2, back2tf) && validate(roadwidening1, roadwidening1tf) && validate(roadwidening2, roadwidening2tf) && validate(are1,are1tf) && validate(area2,area2tf) &&validate(landuse1, landusetf) && validate(landuse2, landuse2tf))
                                        Alert(position);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Please Select Nature of Construction", Toast.LENGTH_SHORT).show();
                                natureofconst.requestFocusFromTouch();
                                natureofconst.performClick();
                            }
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "can't proceed without ULB", Toast.LENGTH_SHORT).show();
                    municipality.requestFocusFromTouch();
                    municipality.performClick();
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "can't proceed without District", Toast.LENGTH_SHORT).show();
                district.requestFocusFromTouch();
                district.performClick();
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Please enter age", Toast.LENGTH_SHORT).show();
            userage.setError("Enter Age", true);
            age.requestFocusFromTouch();
            age.performClick();
        }
    }

    public void connectPolygon(View view) {

        if (listLatLngs.isEmpty()) {
            Snackbar snackbar = Snackbar.make(mScrollView, "Polygon Should be 3 or more", Snackbar.LENGTH_SHORT);
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }
        else if(listLatLngs.size()<3){
            Snackbar snackbar = Snackbar.make(mScrollView, "Polygon Should be 3 or more", Snackbar.LENGTH_SHORT);
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }
        else {
            polyflag=true;
            if (polygon != null)
                polygon.remove();
            PolygonOptions polygonOptions = new PolygonOptions().addAll(listLatLngs).clickable(true);
            polygon = mMap.addPolygon(polygonOptions);
        }

    }

    public boolean validate(ExtendedEditText editText, TextFieldBoxes textFieldBoxes) {
        boolean validation = false;
        if (editText.getText().length() <= 0) {
            textFieldBoxes.setError("Please Fill the Field", true);

        } else {
            validation = true;
        }

        return validation;
    }

    public boolean validateAddress(ExtendedEditText editText, TextFieldBoxes textFieldBoxes) {
        boolean validation = false;
        if (editText.getText().length() <= 0) {
            textFieldBoxes.setError("Please Fill the Address", true);

        } else {
            validation = true;
        }

        return validation;
    }

    public void Alert(int position) {
        if (streetname.getText().length() != 0) {
            if (validate(streetname, streetnametf)) {
                if (sitearea.getText().length() != 0) {
                    if (sitearea.getText().length() > 6) {
                        siteareatf.setError("Site Area Should be <=6", true);
                    }
                    else {
                        if (phoneno.getText().length() != 0) {
                            if (phoneno.getText().length() == 10) {
                                addForm();
                            }
                            else {
                                phonenotf.setError("Phone Number should be 10 Digits", true);
                            }
                        }
                        else {
                            addForm();
                        }
                    }

                }
                else {
                    if (phoneno.getText().length() != 0) {
                        if (phoneno.getText().length() == 10) {
                            addForm();
                        }
                        else {
                            phonenotf.setError("Phone Number should be 10 Digits", true);
                        }
                    } else {
                        addForm();
                    }
                }
            }

        }


        else {
            if (sitearea.getText().length() != 0) {
                if (sitearea.getText().length() > 6) {
                    siteareatf.setError("Site Area Should be <=6", true);
                } else {
                    if (phoneno.getText().length() != 0) {
                        if (phoneno.getText().length() == 10) {
                            addForm();
                        } else {
                            phonenotf.setError("Phone Number should be 10 Digits", true);
                        }
                    } else {
                        addForm();
                    }
                }

            } else {
                if (phoneno.getText().length() != 0) {
                    if (phoneno.getText().length() == 10) {
                        addForm();
                    } else {
                        phonenotf.setError("Phone Number should be 10 Digits", true);
                    }
                } else {
                    addForm();
                }
            }
        }
    }


    public void checkspinner(){
        if(soption11.equals("")&&soption12.equals("")){
            Toast.makeText(getApplicationContext(),"Enter building type and number of floors",Toast.LENGTH_SHORT).show();
        }
    }

    public void addForm() {


        if (videopath.equals("")) {

            Toast.makeText(getApplicationContext(), "Video is mandatory", Toast.LENGTH_SHORT).show();

            Snackbar snackbar = Snackbar.make(mScrollView, "Video is mandatory", Snackbar.LENGTH_SHORT);


        }
        else if (surveyno.getText().length() == 0) {
            surveynotf.setError("Survery Number is mandatory", true);
        }
        else if (doorno.getText().length() == 0) {
            doornotf.setError("Door Number is mandatory", true);
        }

        else if (age.getText().length() == 0) {
            userage.setError("Please enter age", true);
        }

        else if (sitearea.getText().length() == 0) {
            siteareatf.setError("Site area is mandatory", true);
        }
        else if(phoneno.getText().length()==0){
            phonenotf.setError("Phone number is mandatory",true);
        }
        else if(option11) {
            if (soption11.equals("") || soption12.equals("")) {
                Toast.makeText(getApplicationContext(), "enter building type and number of floors", Toast.LENGTH_SHORT).show();
                Log.i("entered here11",String.valueOf(option11));
            }
            else{
                updatedialog();
            }

        }

        else if (option12) {
            if(soption21.equals("") || soption22.equals("") || soption23.equals("")){
                Toast.makeText(getApplicationContext(),"enter building type and number of floors of existing and construction",Toast.LENGTH_SHORT).show();
                Log.i("entered here12",String.valueOf(option12));
            }
            else{
                updatedialog();
            }


        }

        else if (option21) {
            if(soption31.equals("") || soption32.equals("")){
                Toast.makeText(getApplicationContext(),"choose change in existing usage from and to",Toast.LENGTH_SHORT).show();
                Log.i("entered here21",String.valueOf(option21));
            }
            else{
                updatedialog();
            }


        }

        else if (option41) {
            if(soption41.equals("") ){
                Toast.makeText(getApplicationContext(),"choose stilt parking",Toast.LENGTH_SHORT).show();
                Log.i("entered here21",String.valueOf(option21));
            }
            else{
                updatedialog();
            }


        }

        else {

            updatedialog();


        }
    }


    public void updatedialog(){

        Log.d("DEBUGGING","Floor "+floor1.getText().toString()+"-"+floor2.getText().toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to update the data?")
                .setCancelable(false)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            String polygonpoints, text, poly_edit;
                            if (listLatLngsapi.size() != 0) {
                                Log.i("listlangapi", String.valueOf(listLatLngsapi));
                                text = listLatLngsapi.toString().replace("[", "").replace("]", "");
                                polygonpoints = text + "," + listLatLngsapi.get(0);
                                Log.i("listlangapi(0)", listLatLngsapi.get(0));
                                poly_edit = listLatLngsapi.toString().replace(" ", ",").replace("[", "").replace("]", "");
                                String[] loc = poly_edit.split(",");
                            }
                            else {
                                polygonpoints = "null";
                            }

                            String villageValue;
                            if(isUDA){
                                villageValue = villageSpinner.getSelectedItem().toString();
                            }else{
                                villageValue = village.getText().toString();
                            }

                            if(checkboxLayout.getVisibility()==View.VISIBLE){
                                checkBoxes[0]= (byte) (noDeviation.isChecked()? 1:0);
                                checkBoxes[1] = (byte) (allDeviation.isChecked() ? 1:0);
                                checkBoxes[2] = (byte) (setbacks.isChecked() ? 1:0);
                                checkBoxes[3] = (byte) (additionalFloors.isChecked() ? 1:0);
                                checkBoxes[4] = (byte) (roadWinding.isChecked() ? 1:0);
                                checkBoxes[5] = (byte) (buildup.isChecked() ? 1:0);
                                checkBoxes[6] = (byte) (parking.isChecked() ? 1:0);
                                checkBoxes[7] = (byte) (landMisuse.isChecked() ? 1:0);

                            }else{
                                for(int i=0;i<checkBoxes.length;i++){
                                    checkBoxes[i]=0;
                                }
                            }
                            Log.d("DEBUGGING","Update "+picturePath);
                            mSQLiteHelper.updateData(
                                    draftsman.getText().toString().trim(),
                                    ownername.getText().toString().trim(),
                                    fathername.getText().toString().trim(),
                                    address.getText().toString().trim(),
                                    district.getText().toString().trim(),
                                    municipality.getText().toString().trim(),
                                    surveyno.getText().toString().trim(),
                                    villageValue,
                                    doorno.getText().toString().trim(),
                                    locality.getText().toString().trim(),
                                    streetname.getText().toString().trim(),
                                    sitearea.getText().toString().trim(),
                                    natureofconst.getSelectedItem().toString(),
                                    approvedplan.getText().toString().trim(),
                                    parking1.getText().toString().trim(),
                                    parking2.getText().toString().trim(),
                                    floor1.getText().toString().trim(),
                                    floor2.getText().toString().trim(),
                                    front.getText().toString().trim(),
                                    front2.getText().toString().trim(),
                                    right.getText().toString().trim(),
                                    right2.getText().toString().trim(),
                                    left.getText().toString().trim(),
                                    left2.getText().toString().trim(),
                                    back.getText().toString().trim(),
                                    back2.getText().toString().trim(),
                                    landuse1.getText().toString().trim(),
                                    landuse2.getText().toString().trim(),
                                    locationtext.getText().toString().trim(),
                                    phoneno.getText().toString().trim(),
                                    picturePath,
                                    picturePath2,
                                    picturePath3,
                                    picturePath4,
                                    soption11,
                                    soption12,
                                    soption21,
                                    soption22,
                                    soption31,
                                    soption32,
                                    roadwidening1.getText().toString().trim(),
                                    roadwidening2.getText().toString().trim(),
                                    are1.getText().toString().trim(),
                                    area2.getText().toString().trim(),
                                    videopath,
                                    totaluc.getSelectedItem().toString(),
                                    soption23,
                                    date.getText().toString(),
                                    soption41,
                                    etplintharea.getText().toString(),
                                    etplintharea_1.getText().toString(),
                                    age.getText().toString(),
                                    polygonpoints,
                                    mandalSpinner.getSelectedItem().toString(),
                                    checkBoxes,
                                    constructionStage.getSelectedItem().toString(),
                                    position
                            );

                            Toast.makeText(getApplicationContext(), "Update Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getBaseContext(), RecordListActivity.class));
                        } catch (Exception error) {
                            Log.e("Update error", error.getMessage());
                            Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.i("sql er", error.getMessage());
                        }
                        updateRecordList();
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.dismiss();
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "Please Add the Form",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void SpinnerSelection2(Spinner mSpinner, String compareValue) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Option, R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        mSpinner.setAdapter(adapter);
        int arg2;
        if (compareValue != null) {
            arg2 = adapter.getPosition(compareValue);
            mSpinner.setSelection(arg2);
            if (arg2 != 0) {
                spinnerflag = true;
            }
        }
    }

    public void SpinnerSelectionOptions(Spinner mSpinner, String compareValue) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Option1, R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        mSpinner.setAdapter(adapter);
        int arg2;
        if (compareValue != null) {
            arg2 = adapter.getPosition(compareValue);
            mSpinner.setSelection(arg2);
            if (arg2 != 0) {
                spinnerflag = true;
            }
        }
    }

    public void SpinnerSelectionOptions2(Spinner mSpinner, String compareValue) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Option2, R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        mSpinner.setAdapter(adapter);
        int arg2;
        if (compareValue != null) {
            arg2 = adapter.getPosition(compareValue);
            mSpinner.setSelection(arg2);
            if (arg2 != 0) {
                spinnerflag5 = true;


            }
        }
    }

    public void SpinnerSelectionfloor(Spinner mSpinner, String compareValue) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.floor, R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        mSpinner.setAdapter(adapter);
        int arg2;
        if (compareValue != null) {
            arg2 = adapter.getPosition(compareValue);
            mSpinner.setSelection(arg2);
            if (arg2 != 0) {
                spinnerflag2 = true;

            }

        }
    }

    public void SpinnerSelectionStiltPark(Spinner mSpinner, String compareValue) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.StiltParking, R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        mSpinner.setAdapter(adapter);
        int arg2;
        if (compareValue != null) {
            arg2 = adapter.getPosition(compareValue);
            mSpinner.setSelection(arg2);
            if (arg2 != 0) {
                spinnerflag2 = true;

            }

        }
    }

    public void SpinnerSelectionfloor123(Spinner mSpinner, String compareValue) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.floor, R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        mSpinner.setAdapter(adapter);
        int arg2;
        if (compareValue != null) {
            arg2 = adapter.getPosition(compareValue);
            mSpinner.setSelection(arg2);
            if (arg2 != 0) {
                spinnerflag4 = true;

            }

        }
    }

    public void SpinnerSelectiontype(Spinner mSpinner, String compareValue) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Type, R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        mSpinner.setAdapter(adapter);
        int arg2;
        if (compareValue != null) {
            arg2 = adapter.getPosition(compareValue);
            mSpinner.setSelection(arg2);
            if (arg2 != 0) {
                spinnerflag = true;

            }

        }
    }

    public void SpinnerSelectiontype123(Spinner mSpinner, String compareValue) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Type, R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        mSpinner.setAdapter(adapter);
        int arg2;
        if (compareValue != null) {
            arg2 = adapter.getPosition(compareValue);
            mSpinner.setSelection(arg2);
            if (arg2 != 0) {
                spinnerflag3 = true;

            }

        }
    }

    public void SpinnerSelectiontype1(Spinner mSpinner, String compareValue) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Type1, R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        mSpinner.setAdapter(adapter);
        int arg2;
        if (compareValue != null) {
            arg2 = adapter.getPosition(compareValue);
            mSpinner.setSelection(arg2);
            if (arg2 != 0) {
                spinnerflag6 = true;

            }

        }
    }

    public void SpinnerSelectiontype2(Spinner mSpinner, String compareValue) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Type2, R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        mSpinner.setAdapter(adapter);
        int arg2;
        if (compareValue != null) {
            arg2 = adapter.getPosition(compareValue);
            mSpinner.setSelection(arg2);
            if (arg2 != 0) {
                spinnerflag7 = true;
            }

        }
    }

    private void updateRecordList() {
        //get all data from sqlite
        Cursor cursor = mSQLiteHelper.getData("SELECT * FROM Construction");
        mList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String Draftsman = cursor.getString(1);
            String ownername = cursor.getString(2);
            String fathername = cursor.getString(3);
            String phoneno = cursor.getString(38);
            mList.add(new Model(id, Draftsman, ownername, fathername, phoneno));
        }
    }

    public void eCloseMap(View view) {
        expandableLayout_map.toggle();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case 1001:
                    // image 1 -- handle camera image
                    if (resultCode == RESULT_OK && data != null) {
                        Log.d("DEBUGGING upload id",uploadId);
                        PreviewImage.setImageBitmap(null);
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        Bitmap wm = Watermark(selectedImage, "lat:" + latit + " lng:" + lngit, " D:" + formattedDate);
                        picturePath = saveReceivedImage(wm, uploadId);
                        Log.i("DEBUGGING", picturePath);
                        PreviewImage.setImageBitmap(selectedImage);
                    }else
                        Toast.makeText(this,"Image not selected",Toast.LENGTH_SHORT).show();
                    break;
                case 1002:
                    // image 1 -- handle galary image
                    if (resultCode == RESULT_OK && data != null){
                        PreviewImage.setImageBitmap(null);
                        Uri imageURI =  data.getData();
                        Bitmap selectedImage = uriToBitmap(imageURI);
                        Bitmap wm = Watermark(selectedImage, "lat:" + latit + " lng:" + lngit, " D:" + formattedDate);
                        picturePath = saveReceivedImage(wm, uploadId);
                        Log.i("DEBUGGING", picturePath);
                        PreviewImage.setImageBitmap(selectedImage);
                    }else
                        Toast.makeText(this,"Image not selected",Toast.LENGTH_SHORT).show();
                    break;
                case 2001:
                    // image 2 -- handle camare image
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage2 = (Bitmap) data.getExtras().get("data");
                        Bitmap wm2 = Watermark(selectedImage2, "lat:" + latit + " lng:" + lngit, " D:" + formattedDate);
                        picturePath2 = saveReceivedImage(wm2, uploadId2);
                        PreviewImage2.setImageBitmap(selectedImage2);
                        break;
                    }else
                        Toast.makeText(this,"Image not selected",Toast.LENGTH_SHORT).show();
                    break;
                case 2002:
                    // image 3 -- handle galary image
                    if (resultCode == RESULT_OK && data != null){
                        Uri imageURI =  data.getData();
                        Bitmap selectedImage2 = uriToBitmap(imageURI);
                        Bitmap wm2 = Watermark(selectedImage2, "lat:" + latit + " lng:" + lngit, " D:" + formattedDate);
                        picturePath2 = saveReceivedImage(wm2, uploadId2);
                        PreviewImage2.setImageBitmap(selectedImage2);
                    }else
                        Toast.makeText(this,"Image not selected",Toast.LENGTH_SHORT).show();
                    break;
            }
        } catch (NullPointerException e) {
            Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show();
        } catch (OutOfMemoryError e) {
            Toast.makeText(this, "Select Again", Toast.LENGTH_SHORT).show();
        }
        try {
            if (resultCode == this.RESULT_CANCELED) {
                Log.d("what", "cancle");
                return;
            }
            if (requestCode == 970) {
                Log.d("what", "gale");
                if (data != null) {
                    Uri contentURI = data.getData();
                    videopath = getRealPathFromURI(contentURI);
                    if(videopath.contains("OLDGISFY")){
                        Log.d("DEBUGGING VP","UCIMS PATH");
                        Toast.makeText(Edit_Sql.this,"Video is corrupted, please select " +
                                        "other video",
                                Toast.LENGTH_LONG).show();
                    }else{
                        Log.d("DEBUGGING VP","NOT UCIMS PATH");
                        PlayVideo(videopath);

                    }
                }

            } else if (requestCode == 917) {
                Uri contentURI = data.getData();
                videopath = getRealPathFromURI(contentURI);
                PlayVideo(videopath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap uriToBitmap(Uri imageUri){
        Bitmap bitmap;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            int nh = (int) ( bitmap.getHeight() * (375.0 / bitmap.getWidth()) );
            bitmap = Bitmap.createScaledBitmap(bitmap, 512, nh, true);

        } catch (IOException e) {
            e.printStackTrace();
            bitmap=null;
        }


        return bitmap;
    }


    public String getRealPathFromURI(Uri uri) {
        Uri returnUri = uri;
        Cursor returnCursor = getContentResolver().query(returnUri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         *     * move to the first row in the Cursor, get the data,
         *     * and display it.
         * */
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
        File file = new File(getFilesDir(), name);
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();

            //int bufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            Log.e("File Size", "Size " + file.length());
            inputStream.close();
            outputStream.close();
            Log.e("File Path", "Path " + file.getPath());
            Log.e("File Size", "Size " + file.length());
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return file.getPath();
    }


    public void eprofile(View view) {
        selectImage(1001,1002);
    }

    public void image2(View view) {
        selectImage(2001,2002);
    }


    private void selectImage(final int takePhotoCode, final int pickPhotoCode) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pic photo from");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, takePhotoCode);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , pickPhotoCode);

                }
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to leave this page?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
    }

    public void cancel(View view) {
        finish();
    }

    private String saveReceivedImage(Bitmap bitmap, String imageName) {
        try {
            File path = new File("/storage/emulated/0/Android/data/com.gisfy.unauthorizedconstructions/files/", "UCIMSUC" + File.separator + "Images");
            if (!path.exists()) {
                path.mkdirs();
            }
            File outFile = new File(path, imageName + ".jpg");
            savedpath = path + "/" + imageName + ".jpg";
            Log.i("picture Path", String.valueOf(path));
            FileOutputStream outputStream = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        } catch (FileNotFoundException e) {
            Log.e("fileout", "Saving received message failed with", e);
        } catch (IOException e) {
            Log.e("fileout", "Saving received message failed with", e);
        }
        Log.d("DEBUGGING","Saved path "+savedpath);
        return savedpath;
    }

    public Bitmap Watermark(Bitmap bitmap, String latlng_text, String date_text) {
        WatermarkText latlng = new WatermarkText(latlng_text)
                .setPositionX(0)
                .setPositionY(0.2)
                .setTextColor(Color.BLACK)
                .setTextAlpha(150)
                .setRotation(360)
                .setTextSize(15);
        WatermarkText date = new WatermarkText(latlng_text + date_text)
                .setPositionX(0)
                .setPositionY(0.6)
                .setTextColor(Color.BLACK)
                .setTextAlpha(150)
                .setRotation(360)
                .setTextSize(15);
        Bitmap wmimg = WatermarkBuilder
                .create(Edit_Sql.this, bitmap)
                .loadWatermarkText(latlng)
                .loadWatermarkText(date)// use .loadWatermarkImage(watermarkImage) to load an image.
                .getWatermark()
                .getOutputImage();
        return wmimg;
    }

    public void record(View view) {
        try {
            File path = new File(Environment.getExternalStorageDirectory().getPath() + "UCIMSUCVideos");
            if (!path.exists()) {
                path.mkdirs();
            }
            AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
            pictureDialog.setTitle("Select Action");
            String[] pictureDialogItems = {
                    "Select video from gallery",
                    "Record video from camera"};
            pictureDialog.setItems(pictureDialogItems,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                            android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(galleryIntent, 970);
                                    break;
                                case 1:
                                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Environment.getExternalStorageDirectory().getPath() + "UCIMSUCVideos/" + videoname + ".mp4");
                                    startActivityForResult(intent, 917);
                                    break;
                            }
                        }
                    });
            pictureDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show();
        }
    }

    public void PlayVideo(final String input) {
        videoname+="OLDGISFY";
        File myDirectory = new File(Environment.getExternalStorageDirectory(), "UCIMSUCVideos");
        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }
        try {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Compressing...");
            progressDialog.setCancelable(false);


            VideoSlimmer.convertVideo(input, Environment.getExternalStorageDirectory() + "/UCIMSUCVideos/" + videoname + ".mp4", 200, 360, 200 * 360 * 30, new VideoSlimmer.ProgressListener() {
                @Override
                public void onStart() {
                    progressDialog.show();
                }

                @Override
                public void onFinish(boolean result) {
                    progressDialog.dismiss();
                    Log.i("result", String.valueOf(result));
                    if (result) {
                        videopath = Environment.getExternalStorageDirectory() + "/UCIMSUCVideos/" + videoname + ".mp4";
                    }
                    final MediaController mediacontroller = new MediaController(Edit_Sql.this);
                    mediacontroller.setAnchorView(videoView);
                    videoView.setMediaController(mediacontroller);
                    videoView.setVisibility(View.VISIBLE);
                    videoView.setVideoPath(videopath);
                    videoView.start();
                }

                @Override
                public void onProgress(float percent) {
                    Log.i("Preogress", String.valueOf(percent));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Record max of 30sec", Toast.LENGTH_SHORT).show();
        }
    }

    public void getspinposition(String compareValue) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.total_uc, R.layout.spinner_layout);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        totaluc.setAdapter(adapter);
        if (compareValue != null) {
            int spinnerPosition = adapter.getPosition(compareValue);
            totaluc.setSelection(spinnerPosition);
            if (spinnerPosition == 0) {

                total_ucexpand1.setVisibility(View.GONE);
                total_ucexpand2.setVisibility(View.GONE);
                total_ucexpand3.setVisibility(View.GONE);
                total_ucexpand4.setVisibility(View.GONE);

            }
            if (spinnerPosition == 1) {
                int arg2;
                total_ucexpand1.setVisibility(View.VISIBLE);
                total_ucexpand2.setVisibility(View.GONE);
                total_ucexpand3.setVisibility(View.GONE);
                total_ucexpand4.setVisibility(View.GONE);

                arg2 = adapter.getPosition(compareValue);
                totaluc.setSelection(arg2);


            }
            if (spinnerPosition == 2) {

                int arg2;

                total_ucexpand1.setVisibility(View.GONE);
                total_ucexpand2.setVisibility(View.VISIBLE);
                total_ucexpand3.setVisibility(View.GONE);
                total_ucexpand4.setVisibility(View.GONE);

                arg2 = adapter.getPosition(compareValue);
                totaluc.setSelection(arg2);

            }
            if (spinnerPosition == 3) {


                int arg2;

                total_ucexpand1.setVisibility(View.GONE);
                total_ucexpand2.setVisibility(View.GONE);
                total_ucexpand3.setVisibility(View.VISIBLE);
                total_ucexpand4.setVisibility(View.GONE);

                arg2 = adapter.getPosition(compareValue);
                totaluc.setSelection(arg2);

            }
            if (spinnerPosition == 4) {


                int arg2;

                total_ucexpand1.setVisibility(View.GONE);
                total_ucexpand2.setVisibility(View.GONE);
                total_ucexpand3.setVisibility(View.GONE);
                total_ucexpand4.setVisibility(View.VISIBLE);

                arg2 = adapter.getPosition(compareValue);
                totaluc.setSelection(arg2);

            }
        }
    }


    public void showvideo(View view) {
    }
}
