package com.gisfy.unauthorizedconstructions.SQLite;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import com.gisfy.unauthorizedconstructions.R;
import com.gisfy.unauthorizedconstructions.Utils.WorkaroundMapFragment;
import com.gisfy.unauthorizedconstructions.WMS_Layer.TileProviderFactory;
import com.gisfy.unauthorizedconstructions.dashboard;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.watermark.androidwm_light.WatermarkBuilder;
import com.watermark.androidwm_light.bean.WatermarkText;
import com.zolad.videoslimmer.VideoSlimmer;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

import static java.lang.Thread.sleep;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, TimePickerDialog.OnTimeSetListener {
    private GoogleApiClient googleApiClient;
    private GoogleMap mMap;
    Marker mCurrLocationMarker;
    TextView draftsman, asPerTV, asPerTV2;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
    public static SQLiteHelper mSQLiteHelper;

    String picturePath, picturePath2, picturePath3 = "NotSelected", picturePath4 = "NotSelected";

    Location location;
    LatLng latLng;
    SupportMapFragment mapFragment;
    EditText locationtext;
    ImageView locationimage;
    Location loc;
    double latit;
    double lngit;
    int flag, flag1, flag2;
    ImageView edit_latlng, done_latlng;
    FusedLocationProviderClient client;
    LinearLayout villageLayout, layoutviolation, expandspin1, expandspin2, expandspin3, expandspin4, voilationLayout;
    Spinner natureofconst, Constype, constructionStage,
            mandalSpinner, villageSpinner, Constfloors, Floorsasonground, NoOfFloors, ExistingFloors,
            ConvertingFloors,
            totaluc,
            NoOfFloors12, stiltparking;
    LinearLayout checkboxLayout;

    ExtendedEditText roadwidening1, roadwidening2, are1, area2, ownername, fathername, address,
            surveyno, village, doorno, locality, streetname, sitearea,
            approvedplan, district, municipality, date, age,
            parking1, parking2, floor1, floor2, front, front2, left, left2, right, right2, back, back2, landuse1, landuse2, phoneno, etplintharea,etplintharea_1;
    ImageView previewimage, previewimage2, previewimage3, previewimage4;
    TextFieldBoxes roadwidening1tf, roadwidening2tf, are1tf, area2tf, ownernametf, fathernametf, addresstf, area2Texfield,
            surveynotf, villagetf, doornotf, localitytf, streetnametf, siteareatf, approvedplantf, datetf, plintharea,plintharea_1,
            parkingt1f, parking2f, floor1f, floor2f, fronttf, front2tf, lefttf, left2tf, righttf, right2tf, backtf, back2tf, landusetf, landuse2tf, phonenotf, userage;

    boolean spinnerflag = false;
    ExpandableLayout el;
    ScrollView mScrollView;
    ExpandableLayout map;
    ExpandableLayout cardview;
    SimpleDateFormat df, dfin, dfout;
    final String TAG = "DEBUGGING";
    DateFormat df1;
    private Polygon polygon = null;
    private List<LatLng> listLatLngs = new ArrayList<>();
    private List<Marker> listMarkers = new ArrayList<>();
    private List<String> listLatLngsapi = new ArrayList<>();
    boolean option11 = false;
    boolean option12 = false;
    boolean islatlngEditable = false;
    MarkerOptions manualmarker;
    boolean option21 = false;
    boolean option22 = false;
    boolean polyflag = false;
    boolean option23 = false;
    boolean option31 = true;
    boolean option32 = true;
    String soption11 = "", soption12 = "", soption21 = "", soption22 = "", soption23, soption31 = "", soption32 = "", soption41 = "";
    String formattedDate, changedateformat;
    String uploadId, uploadId2, uploadId3, uploadId4;
    String savedpath, datenew;
    String videoname, Videopath;
    VideoView videoView;
    Date dateformat;
    private static final int UPDATE_INTERVAL = 1000 * 5000;
    LocationRequest locationRequest;
    FusedLocationProviderClient locationProviderClient;
    LocationCallback locationCallback;
    private Location currentLocation;
    private int mandalIds[] = null;
    private boolean isUDA = false;
    private CheckBox noDeviation, allDeviation, setbacks, additionalFloors, roadWinding, buildup,
            parking, landMisuse;
    private TextView violationDeviation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Videopath = "";
        findViewByIds();

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        cardview.collapse();

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        mSQLiteHelper = new SQLiteHelper(this, "Construction.sqlite", null, 2);

        String grade = sh.getString("grade", null);
        final Cursor cursor = mSQLiteHelper.getMandals();
        int count = cursor.getCount();
        String mandals[] = null;
        if (count != 0) {
            mandals = new String[count];
            mandalIds = new int[count];
            for (int i = 0; i < count; i++) {
                cursor.moveToNext();
                mandalIds[i] = cursor.getInt(0);
                mandals[i] = cursor.getString(1);
            }
            ArrayAdapter mandalAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_dropdown_item_1line
                    , mandals);
            mandalSpinner.setAdapter(mandalAdapter);
        } else {

        }
        isUDA = grade.contains("UDA");

        if (isUDA) {
            villagetf.setVisibility(View.GONE);
            villageLayout.setVisibility(View.VISIBLE);
        }

        villageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Please select Mandal first", Toast.LENGTH_SHORT).show();
            }
        });

        mandalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor1 = mSQLiteHelper.getVillages(mandalIds[position]);
                String villages[];
                int count = cursor1.getCount();
                if (count != 0) {
                    if (cursor1.moveToNext()) {
                        villages = new String[count];
                        for (int i = 0; i < count; i++) {
                            villages[i] = cursor1.getString(1);
                            cursor1.moveToNext();
                        }

                        ArrayAdapter villagesAdapter = new ArrayAdapter(MainActivity.this,
                                android.R.layout.simple_dropdown_item_1line, villages);
                        villageSpinner.setAdapter(villagesAdapter);
                        villageLayout.setOnClickListener(null);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        File myDirectory = new File(Environment.getExternalStorageDirectory(), "UCIMSUCVideos");
        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }
        flag = 0;
        flag1 = 0;
        flag2 = 0;
        videoname = UUID.randomUUID().toString();
        uploadId = UUID.randomUUID().toString();
        uploadId2 = UUID.randomUUID().toString();
        uploadId3 = UUID.randomUUID().toString();
        uploadId4 = UUID.randomUUID().toString();
        Date c = Calendar.getInstance().getTime();
        df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c);

        dfin = new SimpleDateFormat("yyyy-MM-dd");
        dfout = new SimpleDateFormat("dd-MM-yyyy");

        df1 = new SimpleDateFormat("dd-MMM-yyyy");

        String empName = sh.getString("employeename", "");
        district.setText(sh.getString("district", ""));
        municipality.setText(sh.getString("name", ""));
        draftsman.setText(empName);
        mapFragment = (WorkaroundMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mScrollView = (ScrollView) findViewById(R.id.scrillview);

        ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).setListener(new WorkaroundMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                mScrollView.requestDisallowInterceptTouchEvent(true);
            }
        });
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            configureCameraIdle();
        } else {
            Snackbar snackbar = Snackbar.make(mScrollView, "Network is Required to use Map. still you can get LatLng by clicking on Location icon", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }

        client = LocationServices.getFusedLocationProviderClient(this);
        locationimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocation();
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                R.layout.spinner_layout, getApplicationContext().getResources().getStringArray(R.array.Option));

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(MainActivity.this,
                R.layout.spinner_layout, getApplicationContext().getResources().getStringArray(R.array.total_uc));

        ArrayAdapter adapter2 = new ArrayAdapter(this, R.layout.spinner_layout,
                getResources().getStringArray(R.array.constructionStages));

        natureofconst.setAdapter(adapter);

        totaluc.setAdapter(adapter1);

        constructionStage.setAdapter(adapter2);

        natureofconst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1, int arg2, long arg3) {
                if (arg2 == 0) {
                    el.collapse();
                    cardview.collapse();
                    option11 = false;
                    option12 = false;
                    option21 = false;
                    plintharea.setVisibility(View.GONE);
                    plintharea_1.setVisibility(View.GONE);
                    option22 = false;
                    soption23 = "";
                    totaluc.setVisibility(View.GONE);
                    expandspin1.setVisibility(View.GONE);
                    expandspin2.setVisibility(View.GONE);
                    expandspin3.setVisibility(View.GONE);
                    expandspin4.setVisibility(View.GONE);
                    findViewById(R.id.approvedplanTexfield).setVisibility(View.GONE);
                    findViewById(R.id.date_tf).setVisibility(View.GONE);
                }
                if (arg2 == 1 || arg2 == 3) {
                  setDeviationViews();
                    findViewById(R.id.approvedplanTexfield).setVisibility(View.VISIBLE);
                    findViewById(R.id.date_tf).setVisibility(View.VISIBLE);
                }
                if (arg2 == 2) {
                   setTotalUcViews();
                    findViewById(R.id.approvedplanTexfield).setVisibility(View.GONE);
                    findViewById(R.id.date_tf).setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        noDeviation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    allDeviation.setChecked(false);
                    setbacks.setChecked(false);
                    roadWinding.setChecked(false);
                    buildup.setChecked(false);
                    parking.setChecked(false);
                    landMisuse.setChecked(false);

                    allDeviation.setEnabled(false);
                    setbacks.setEnabled(false);
                    roadWinding.setEnabled(false);
                    buildup.setEnabled(false);
                    parking.setEnabled(false);
                    landMisuse.setEnabled(false);
                }else {
                    allDeviation.setEnabled(true);
                    setbacks.setEnabled(true);
                    roadWinding.setEnabled(true);
                    buildup.setEnabled(true);
                    parking.setEnabled(true);
                    landMisuse.setEnabled(true);
                }
            }
        });


        allDeviation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    noDeviation.setChecked(false);
                    setbacks.setChecked(true);
                    additionalFloors.setChecked(true);
                    roadWinding.setChecked(true);
                    buildup.setChecked(true);
                    parking.setChecked(true);
                    landMisuse.setChecked(true);
                } else {
                    setbacks.setChecked(false);
                    additionalFloors.setChecked(false);
                    roadWinding.setChecked(false);
                    buildup.setChecked(false);
                    parking.setChecked(false);
                    landMisuse.setChecked(false);
                }
            }
        });

        ArrayAdapter<String> typespinner = new ArrayAdapter<String>(MainActivity.this,
                R.layout.spinner_layout, getApplicationContext().getResources().getStringArray(R.array.Type));

        ArrayAdapter<String> typespinner1 = new ArrayAdapter<String>(MainActivity.this,
                R.layout.spinner_layout, getApplicationContext().getResources().getStringArray(R.array.Type1));

        ArrayAdapter<String> typespinner2 = new ArrayAdapter<String>(MainActivity.this,
                R.layout.spinner_layout, getApplicationContext().getResources().getStringArray(R.array.Type2));

        ArrayAdapter<String> stiltspinner = new ArrayAdapter<String>(MainActivity.this,
                R.layout.spinner_layout, getApplicationContext().getResources().getStringArray(R.array.StiltParking));

        ArrayAdapter<String> option1 = new ArrayAdapter<String>(MainActivity.this,
                R.layout.spinner_layout, getApplicationContext().getResources().getStringArray(R.array.Option1));
        ArrayAdapter<String> option2 = new ArrayAdapter<String>(MainActivity.this,
                R.layout.spinner_layout, getApplicationContext().getResources().getStringArray(R.array.Option2));
        ArrayAdapter<String> floor = new ArrayAdapter<String>(MainActivity.this,
                R.layout.spinner_layout, getApplicationContext().getResources().getStringArray(R.array.floor));

        ArrayAdapter<String> floor1 = new ArrayAdapter<String>(MainActivity.this,
                R.layout.spinner_layout, getApplicationContext().getResources().getStringArray(R.array.floor1));


        Constype.setAdapter(typespinner);
        Constfloors.setAdapter(floor1);
        Floorsasonground.setAdapter(typespinner);
        NoOfFloors12.setAdapter(floor);
        NoOfFloors.setAdapter(option2);

        ExistingFloors.setAdapter(typespinner1);
        ConvertingFloors.setAdapter(typespinner2);
        stiltparking.setAdapter(stiltspinner);


        totaluc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    expandspin1.setVisibility(View.GONE);
                    expandspin2.setVisibility(View.GONE);
                    expandspin3.setVisibility(View.GONE);
                    expandspin4.setVisibility(View.GONE);

                    removeTotalUCSelections();

                    option11 = false;
                    option12 = false;
                    option21 = false;
                    option23 = false;

                    soption21 = "";
                    soption22 = "";
                    soption23 = "";

                    soption11 = "";
                    soption12 = "";

                    soption31 = "";
                    soption32 = "";

                    soption41 = "";


                } else if (position == 1) {
                    expandspin1.setVisibility(View.VISIBLE);
                    expandspin2.setVisibility(View.GONE);
                    expandspin3.setVisibility(View.GONE);
                    expandspin4.setVisibility(View.GONE);
                    removeTotalUCSelections();
                    soption31 = "";
                    soption32 = "";

                    soption21 = "";
                    soption22 = "";
                    soption23 = "";

                    soption41 = "";

                    option11 = true;
                    option12 = false;
                    option21 = false;
                    option23 = false;

                    Log.i("entered 1", (String.valueOf(option11)));


                    Constype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
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
                            if (position != 0) {

                                soption12 = Constfloors.getSelectedItem().toString();
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                } else if (position == 2) {
                    expandspin2.setVisibility(View.VISIBLE);
                    expandspin1.setVisibility(View.GONE);
                    expandspin3.setVisibility(View.GONE);
                    expandspin4.setVisibility(View.GONE);
                    removeTotalUCSelections();
                    soption11 = "";
                    soption12 = "";

                    soption31 = "";
                    soption32 = "";

                    soption41 = "";

                    option12 = true;
                    option11 = false;
                    option21 = false;
                    option23 = false;
                    Log.i("entered 2", (String.valueOf(option12)));


                    Floorsasonground.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {


                                soption21 = Floorsasonground.getSelectedItem().toString();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                    NoOfFloors12.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                soption22 = NoOfFloors12.getSelectedItem().toString();
                                soption23 = "";

                                if (position == 1) {
                                    // NoOfFloors.setSelection(1);
                                    //  NoOfFloors.setEnabled(false);
                                    soption23 = NoOfFloors.getSelectedItem().toString();
                                    Log.i("sp23", soption23);

                                } else if (position == 2) {
                                    //  NoOfFloors.setSelection(2);
                                    //   NoOfFloors.setEnabled(false);
                                    soption23 = NoOfFloors.getSelectedItem().toString();
                                    Log.i("sp23", soption23);
                                } else if (position == 3) {
                                    //   NoOfFloors.setSelection(3);
                                    //  NoOfFloors.setEnabled(false);
                                    soption23 = NoOfFloors.getSelectedItem().toString();

                                } else if (position == 4) {
                                    //  NoOfFloors.setSelection(4);
                                    // NoOfFloors.setEnabled(false);
                                    soption23 = NoOfFloors.getSelectedItem().toString();

                                } else if (position == 5) {
                                    //  NoOfFloors.setSelection(5);
                                    //NoOfFloors.setEnabled(false);
                                    soption23 = NoOfFloors.getSelectedItem().toString();

                                } else if (position == 6) {
                                    // NoOfFloors.setSelection(6);
                                    //NoOfFloors.setEnabled(false);
                                    soption23 = NoOfFloors.getSelectedItem().toString();

                                } else if (position == 7) {
                                    //   NoOfFloors.setSelection(7);
                                    // NoOfFloors.setEnabled(false);
                                    soption23 = NoOfFloors.getSelectedItem().toString();

                                } else if (position == 8) {
                                    // NoOfFloors.setSelection(8);
                                    //  NoOfFloors.setEnabled(false);
                                    soption23 = NoOfFloors.getSelectedItem().toString();

                                }

                            } else {
                                //NoOfFloors.setSelection(0);
                                soption23 = "";
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

//                    NoOfFloors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                            if(position!=0){
//
//
//                                soption23 = NoOfFloors.getSelectedItem().toString();
//                            }
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//
//                        }
//                    });

                } else if (position == 3) {
                    expandspin3.setVisibility(View.VISIBLE);
                    expandspin1.setVisibility(View.GONE);
                    expandspin2.setVisibility(View.GONE);
                    expandspin4.setVisibility(View.GONE);
                    removeTotalUCSelections();
                    soption21 = "";
                    soption22 = "";
                    soption23 = "";

                    soption11 = "";
                    soption12 = "";

                    soption41 = "";

                    option21 = true;
                    option11 = false;
                    option12 = false;
                    option23 = false;
                    Log.i("entered 3", (String.valueOf(option21)));

                    ExistingFloors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {


                                soption31 = ExistingFloors.getSelectedItem().toString();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                    ConvertingFloors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {


                                soption32 = ConvertingFloors.getSelectedItem().toString();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                } else if (position == 4) {
                    expandspin4.setVisibility(View.VISIBLE);
                    expandspin1.setVisibility(View.GONE);
                    expandspin2.setVisibility(View.GONE);
                    expandspin3.setVisibility(View.GONE);
                    removeTotalUCSelections();
                    soption21 = "";
                    soption22 = "";
                    soption23 = "";

                    soption11 = "";
                    soption12 = "";

                    soption31 = "";
                    soption32 = "";

                    option23 = true;
                    option21 = false;
                    option11 = false;
                    option12 = false;

                    Log.i("entered 4", (String.valueOf(option21)));

                    stiltparking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                soption41 = stiltparking.getSelectedItem().toString();
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


        setLocation();


        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener calender = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                int month = monthOfYear + 1;
                date.setText(dayOfMonth + "-" + month + "-" + year);

            }
        };


        datetf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity.this, calender, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        edit_latlng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationtext.setEnabled(true);
                islatlngEditable = true;
                edit_latlng.setVisibility(View.GONE);
                done_latlng.setVisibility(View.VISIBLE);
            }
        });
        done_latlng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationtext.setEnabled(false);
                islatlngEditable = false;
                if (!isValidLatLng(locationtext.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Not Valid LatLng", Toast.LENGTH_SHORT).show();
                } else {

                    String[] latlngstr = locationtext.getText().toString().split(",");
                    double lat = Double.parseDouble(latlngstr[0]);
                    double lng = Double.parseDouble(latlngstr[1]);
                    Log.i("latlongiapi", lat + "" + lng);

                    LatLng coordinate = new LatLng(lat, lng);


                    manualmarker = new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .title("Manually Marked")
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    mMap.addMarker(manualmarker);

                    CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                            coordinate, 20);
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

    }

    private void removeTotalUCSelections(){
        Constype.setSelection(0);
        Constfloors.setSelection(0);
        Floorsasonground.setSelection(0);
        NoOfFloors.setSelection(0);
        NoOfFloors12.setSelection(0);
        ExistingFloors.setSelection(0);
        ConvertingFloors.setSelection(0);
        stiltparking.setSelection(0);
    }

    @Override
    protected void onStart() {
        super.onStart();


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
                if (locationAvailability.isLocationAvailable()) {
                    Log.i(TAG, "Location is available");
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    currentLocation = location;
                                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                    Log.i("DEBUGGING", currentLocation.getLatitude() + " " + currentLocation.getLongitude());
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 20));
                                }
                                }
                        });
                        return;
                    }


                }else {
                    Log.i(TAG,"Location is unavailable");
                }
            }

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                if(currentLocation!=null) {
                    Log.i(TAG, "Location result is available: " + currentLocation.getLatitude() + " " + currentLocation.getLongitude());
                    LatLng midLatLng = mMap.getCameraPosition().target;
//                    if(Math.abs(midLatLng.latitude - currentLocation.getLatitude())>=0.001 || Math.abs(midLatLng.longitude - currentLocation.getLongitude())>=0.001)
//                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 18f));
                }else{
                    Log.i("DEBUGGING","Loc NULL");
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationProviderClient.requestLocationUpdates(locationRequest,
                    locationCallback, MainActivity.this.getMainLooper());
            locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location!=null) {
                        currentLocation = location;
                        latLng = new LatLng(location.getLatitude(),location.getLongitude());
                        Log.i("DEBUGGING", currentLocation.getLatitude() + " " + currentLocation.getLongitude());
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 20));
                    }
                }
            });

            locationProviderClient.getLastLocation().addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i(TAG, "Exception while getting the location: " + e.getMessage());

                }
            });

        }

    }

    public static boolean isValidLatLng(String str) {
        String regex = "^(-?\\d+(\\.\\d+)?),\\s*(-?\\d+(\\.\\d+)?)$";
        Pattern p = Pattern.compile(regex);
        if (str == null) {
            return false;
        }
        Matcher m = p.matcher(str);
        return m.matches();
    }


    private void findViewByIds() {
        edit_latlng=findViewById(R.id.edit_latlng);
        done_latlng=findViewById(R.id.done_latlng);
        videoView = findViewById(R.id.video_view);
        Constype = findViewById(R.id.ConstructionType);
        totaluc = findViewById(R.id.total_uc);
        constructionStage = findViewById(R.id.constructionStage);
        checkboxLayout = findViewById(R.id.checkboxLayout);

        noDeviation = findViewById(R.id.noDeviationCB);
        allDeviation = findViewById(R.id.allDeviationCB);
        setbacks = findViewById(R.id.setbacksCB);
        additionalFloors = findViewById(R.id.additionalFloorsCB);
        roadWinding = findViewById(R.id.roadWindingCB);
        buildup = findViewById(R.id.buildupAreaCB);
        parking = findViewById(R.id.parkingCB);
        landMisuse = findViewById(R.id.landMisuseCB);

        violationDeviation = findViewById(R.id.violationDeviation);

        age=findViewById(R.id.age);
        etplintharea=findViewById(R.id.etplintharea);
        plintharea=findViewById(R.id.plintharea);
        plintharea_1 = findViewById(R.id.plintharea_i);
        etplintharea_1 = findViewById(R.id.etplintharea_i);
        userage=findViewById(R.id.userage);
        Constfloors = findViewById(R.id.ConstructionFloors);
        expandspin1 = findViewById(R.id.total_ucexpand1);
        expandspin2 = findViewById(R.id.total_ucexpand2);
        expandspin3 = findViewById(R.id.total_ucexpand3);
        expandspin4 = findViewById(R.id.total_ucexpand4);
        Floorsasonground = findViewById(R.id.FloorsAsOnGround);
        NoOfFloors = findViewById(R.id.NoOfFloors);
        NoOfFloors12 = findViewById(R.id.NoOfFloors1);
        stiltparking=findViewById(R.id.misusingofstiltparking);
        ExistingFloors = findViewById(R.id.ExistingFloors);
        ConvertingFloors = findViewById(R.id.ConvertingFloors);
        map = findViewById(R.id.expandable_layout_map);
        cardview = findViewById(R.id.expandable_layout2);
        phoneno = findViewById(R.id.phonenumber);
        phonenotf = findViewById(R.id.phonenumbertextfield);
        draftsman = findViewById(R.id.draftsman);
        ownername = findViewById(R.id.owner);
        el = findViewById(R.id.expandable_layout);
        roadwidening1 = findViewById(R.id.roadwidening1);
        roadwidening2 = findViewById(R.id.roadwidening2);
        are1 = findViewById(R.id.area1);
        area2 = findViewById(R.id.area2);
        roadwidening1tf = findViewById(R.id.roadwidening1Textfield);
        roadwidening2tf = findViewById(R.id.roadwidening2Texfield);
        are1tf = findViewById(R.id.area1Textfield);
        area2tf = findViewById(R.id.area2Texfield);
        fathername = findViewById(R.id.fathername);
        address = findViewById(R.id.address);
        district = findViewById(R.id.district);
        district.setEnabled(false);
        municipality = findViewById(R.id.municipality);
        municipality.setEnabled(false);
        surveyno = findViewById(R.id.surveyno);
        village = findViewById(R.id.village);
        doorno = findViewById(R.id.doorno);
        locality = findViewById(R.id.locality);
        streetname = findViewById(R.id.streetname);
        approvedplan = findViewById(R.id.approvedplan);
        parking1 = findViewById(R.id.parking1);
        parking2 = findViewById(R.id.parking2);
        floor1 = findViewById(R.id.floor1);
        floor2 = findViewById(R.id.floor2);
        front = findViewById(R.id.front1);
        front2 = findViewById(R.id.front2);
        left = findViewById(R.id.left1);
        left2 = findViewById(R.id.left2);
        right = findViewById(R.id.right1);
        back = findViewById(R.id.back1);
        back2 = findViewById(R.id.back2);
        landuse1 = findViewById(R.id.landuse1);
        landuse2 = findViewById(R.id.landuse2);
        right2 = findViewById(R.id.right2);
        sitearea = findViewById(R.id.sitearea);

        date = findViewById(R.id.date_edit);
        datetf = findViewById(R.id.date_tf);


        locationimage = findViewById(R.id.locationimage);

        natureofconst = findViewById(R.id.natureofconst);
        previewimage = findViewById(R.id.imageView);
        previewimage2 = findViewById(R.id.imageView2);
        previewimage3 = findViewById(R.id.imageview3);
        previewimage4 = findViewById(R.id.imageview4);
        locationtext = findViewById(R.id.loc_text);
        ownernametf = findViewById(R.id.ownerTexfield);
        fathernametf = findViewById(R.id.fathernameTexfield);
        addresstf = findViewById(R.id.addressTexfield);
        surveynotf = findViewById(R.id.surveynoTexfield);
        area2Texfield = findViewById(R.id.area2Texfield);
        villagetf = findViewById(R.id.villageTexfield);
        doornotf = findViewById(R.id.doornoTexfield);
        localitytf = findViewById(R.id.localityTexfield);
        streetnametf = findViewById(R.id.streetnameTexfield);
        parkingt1f = findViewById(R.id.parking1Textfield);
        parking2f = findViewById(R.id.parking2Texfield);
        floor1f = findViewById(R.id.floor1Texfield);
        floor2f = findViewById(R.id.floor2Texfield);
        approvedplantf = findViewById(R.id.approvedplanTexfield);
        fronttf = findViewById(R.id.front1Texfield);
        front2tf = findViewById(R.id.front2Texfield);
        lefttf = findViewById(R.id.left1Texfield);
        left2tf = findViewById(R.id.left2Texfield);
        righttf = findViewById(R.id.right1Texfield);
        right2tf = findViewById(R.id.right2Texfield);
        backtf = findViewById(R.id.back1Texfield);
        back2tf = findViewById(R.id.back2Texfield);

        landusetf = findViewById(R.id.landuse1Texfield);
        landuse2tf = findViewById(R.id.landuse2Texfield);
        siteareatf = findViewById(R.id.siteareaTexfield);
        layoutviolation = findViewById(R.id.layout_violation);

        mandalSpinner = findViewById(R.id.mandalSpinner);
        villageLayout = findViewById(R.id.villageLayout);
        villageSpinner = findViewById(R.id.villageSpinner);

        voilationLayout = findViewById(R.id.voilationLayout);
        asPerTV = findViewById(R.id.asperTV);
        asPerTV2 = findViewById(R.id.asperTV2);
        date.setEnabled(false);

        fathername.setFilters(new InputFilter[]{new InputFilter.LengthFilter(25)});
        ownername.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        address.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        surveyno.setFilters(new InputFilter[]{new InputFilter.LengthFilter(25)});
        village.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        doorno.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        locality.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        approvedplan.setFilters(new InputFilter[]{new InputFilter.LengthFilter(35)});
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
        etplintharea.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});
        etplintharea_1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});
        back2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        landuse1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        landuse2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        sitearea.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        phoneno.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        right.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        right2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        back.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        back2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        roadwidening1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        roadwidening2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        are1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        area2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
    }

    public void submit(View view) {

        Log.i("markerssize", String.valueOf(listMarkers.size()));
        Log.i("latlangslist", String.valueOf(listLatLngs.size()));
        if (validate(ownername, ownernametf)) {
            if (validate(fathername, fathernametf)) {
                if (validateAddress(address, addresstf)) {
                    if(natureofconst.getSelectedItemPosition()==2){
                        if(totaluc.getSelectedItemPosition()!=0){
                            if(listMarkers.size()>0){
                                if(polyflag){
                                    if(age.getText().length()>0){
                                        if (!(district.getText().length() == 0)) {
                                            if (!(municipality.getText().length() == 0)) {
                                                if (surveyno.getText().length() == 0 && village.getText().length() == 0) {
                                                    if (doorno.getText().length() == 0) {
                                                        surveynotf.setError("Enter Survey no",
                                                                true);
                                                        villagetf.setError("Enter Village Name", true);
                                                        doornotf.setError("Enter Door No", true);
                                                    }
                                                    else {
                                                        if (validate(locality, localitytf)) {
                                                            if (spinnerflag) {
                                                                if (natureofconst.getSelectedItem().toString().equals("Deviation to the approved plan")) {
                                                                    if (validate(approvedplan, approvedplantf) && validate(parking1, parkingt1f) && validate(parking2, parking2f) && validate(floor1, floor1f) && validate(floor2, floor2f) && validate(front, fronttf) && validate(front2, front2tf) && validate(right, righttf) && validate(right2, right2tf) && validate(left, lefttf) && validate(left2, left2tf) && validate(back, backtf) && validate(back2, back2tf) && validate(landuse1, landusetf) && validate(landuse2, landuse2tf)) {

                                                                        if (picturePath == null) {
                                                                            Toast.makeText(this, "Select picture", Toast.LENGTH_SHORT).show();
                                                                        } else if (picturePath2 == null) {
                                                                            Toast.makeText(this, "Select 2nd picture", Toast.LENGTH_SHORT).show();
                                                                        }  else {
                                                                            Alert();
                                                                        }
                                                                    }
                                                                } else {
                                                                    if (validate(parking1, parkingt1f) && validate(parking2, parking2f) && validate(floor1, floor1f) && validate(floor2, floor2f) && validate(front, fronttf) && validate(front2, front2tf) && validate(right, righttf) && validate(right2, right2tf) && validate(left, lefttf) && validate(left2, left2tf) && validate(back, backtf) && validate(back2, back2tf) && validate(roadwidening1, roadwidening1tf) && validate(roadwidening2, roadwidening2tf) && validate(are1,are1tf) && validate(area2,area2tf) &&validate(landuse1, landusetf) && validate(landuse2, landuse2tf)){
                                                                        if (picturePath == null) {
                                                                            Toast.makeText(this, "Select picture", Toast.LENGTH_SHORT).show();
                                                                        } else if (picturePath2 == null) {
                                                                            Toast.makeText(this, "Select 2nd picture", Toast.LENGTH_SHORT).show();
                                                                        }  else {
                                                                            Alert();
                                                                        }
                                                                    }

                                                                }
                                                            } else {
                                                                Toast.makeText(this, "Please Select Nature of Construction", Toast.LENGTH_SHORT).show();
                                                                natureofconst.requestFocusFromTouch();
                                                                natureofconst.performClick();
                                                            }
                                                        }
                                                    }
                                                }
                                                else {
                                                    if (validate(locality, localitytf)) {
                                                        if (spinnerflag) {
                                                            if (natureofconst.getSelectedItem().toString().equals("Deviation to the approved plan")) {
                                                                if (validate(approvedplan, approvedplantf) && validate(parking1, parkingt1f) && validate(parking2, parking2f) && validate(floor1, floor1f) && validate(floor2, floor2f) && validate(front, fronttf) && validate(front2, front2tf) && validate(right, righttf) && validate(right2, right2tf) && validate(left, lefttf) && validate(left2, left2tf) && validate(back, backtf) && validate(back2, back2tf) && validate(landuse1, landusetf) && validate(landuse2, landuse2tf)) {

                                                                    if (picturePath == null) {
                                                                        Toast.makeText(this, "Select picture", Toast.LENGTH_SHORT).show();
                                                                    } else if (picturePath2 == null) {
                                                                        Toast.makeText(this, "Select 2nd picture", Toast.LENGTH_SHORT).show();
                                                                    }  else {
                                                                        Log.i("Picture Path", picturePath);
                                                                        Alert();
                                                                    }
                                                                }
                                                            } else {
                                                                if (validate(parking1, parkingt1f) && validate(parking2, parking2f) && validate(floor1, floor1f) && validate(floor2, floor2f) && validate(front, fronttf) && validate(front2, front2tf) && validate(right, righttf) && validate(right2, right2tf) && validate(left, lefttf) && validate(left2, left2tf) && validate(back, backtf) && validate(back2, back2tf) && validate(roadwidening1, roadwidening1tf) && validate(roadwidening2, roadwidening2tf) && validate(are1,are1tf) && validate(area2,area2tf) &&validate(landuse1, landusetf) && validate(landuse2, landuse2tf)){
                                                                    if (picturePath == null) {
                                                                        Toast.makeText(this, "Select picture", Toast.LENGTH_SHORT).show();
                                                                    } else if (picturePath2 == null) {
                                                                        Toast.makeText(this, "Select 2nd picture", Toast.LENGTH_SHORT).show();
                                                                    }  else {
                                                                        Log.i("Picture Path", picturePath);
                                                                        Alert();
                                                                    }
                                                                }

                                                            }
                                                        } else {
                                                            Toast.makeText(this, "Please Select Nature of Construction", Toast.LENGTH_SHORT).show();
                                                            natureofconst.requestFocusFromTouch();
                                                            natureofconst.performClick();
                                                        }
                                                    }
                                                }
                                            } else {
                                                Toast.makeText(this, "Try again by logging again", Toast.LENGTH_SHORT).show();
                                                municipality.requestFocusFromTouch();
                                                municipality.performClick();
                                            }
                                        }
                                        else {
                                            Toast.makeText(this, "Try again by logging again", Toast.LENGTH_SHORT).show();
                                            district.requestFocusFromTouch();
                                            district.performClick();
                                        }
                                    }
                                    else{
                                        Toast.makeText(this, "Please enter Age", Toast.LENGTH_SHORT).show();
                                        userage.setError("Enter Age", true);
                                        age.requestFocusFromTouch();
                                        age.performClick();
                                    }
                                }
                                else{
                                    Toast.makeText(this, "Please connect polygon", Toast.LENGTH_SHORT).show();
                                }

                            }
                            else{
                                if(age.getText().length()>0){
                                    if (!(district.getText().length() == 0)) {
                                        if (!(municipality.getText().length() == 0)) {
                                            if (surveyno.getText().length() == 0 && village.getText().length() == 0) {
                                                if (doorno.getText().length() == 0) {
                                                    surveynotf.setError("Enter Survey no", true);
                                                    villagetf.setError("Enter Village Name", true);
                                                    doornotf.setError("Enter Door No", true);
                                                }
                                                else {
                                                    if (validate(locality, localitytf)) {
                                                        if (spinnerflag) {
                                                            if (natureofconst.getSelectedItem().toString().equals("Deviation to the approved plan")) {
                                                                if (validate(approvedplan, approvedplantf) && validate(parking1, parkingt1f) && validate(parking2, parking2f) && validate(floor1, floor1f) && validate(floor2, floor2f) && validate(front, fronttf) && validate(front2, front2tf) && validate(right, righttf) && validate(right2, right2tf) && validate(left, lefttf) && validate(left2, left2tf) && validate(back, backtf) && validate(back2, back2tf) && validate(landuse1, landusetf) && validate(landuse2, landuse2tf)) {

                                                                    if (picturePath == null) {
                                                                        Toast.makeText(this, "Select picture", Toast.LENGTH_SHORT).show();
                                                                    } else if (picturePath2 == null) {
                                                                        Toast.makeText(this, "Select 2nd picture", Toast.LENGTH_SHORT).show();
                                                                    }  else {
                                                                        Alert();
                                                                    }
                                                                }
                                                            } else {
                                                                if (validate(parking1, parkingt1f) && validate(parking2, parking2f) && validate(floor1, floor1f) && validate(floor2, floor2f) && validate(front, fronttf) && validate(front2, front2tf) && validate(right, righttf) && validate(right2, right2tf) && validate(left, lefttf) && validate(left2, left2tf) && validate(back, backtf) && validate(back2, back2tf) && validate(roadwidening1, roadwidening1tf) && validate(roadwidening2, roadwidening2tf) && validate(are1,are1tf) && validate(area2,area2tf) &&validate(landuse1, landusetf) && validate(landuse2, landuse2tf)){
                                                                    if (picturePath == null) {
                                                                        Toast.makeText(this, "Select picture", Toast.LENGTH_SHORT).show();
                                                                    } else if (picturePath2 == null) {
                                                                        Toast.makeText(this, "Select 2nd picture", Toast.LENGTH_SHORT).show();
                                                                    }  else {
                                                                        Alert();
                                                                    }
                                                                }

                                                            }
                                                        } else {
                                                            Toast.makeText(this, "Please Select Nature of Construction", Toast.LENGTH_SHORT).show();
                                                            natureofconst.requestFocusFromTouch();
                                                            natureofconst.performClick();
                                                        }
                                                    }
                                                }
                                            }
                                            else {
                                                if (validate(locality, localitytf)) {
                                                    if (spinnerflag) {
                                                        if (natureofconst.getSelectedItem().toString().equals("Deviation to the approved plan")) {
                                                            if (validate(approvedplan, approvedplantf) && validate(parking1, parkingt1f) && validate(parking2, parking2f) && validate(floor1, floor1f) && validate(floor2, floor2f) && validate(front, fronttf) && validate(front2, front2tf) && validate(right, righttf) && validate(right2, right2tf) && validate(left, lefttf) && validate(left2, left2tf) && validate(back, backtf) && validate(back2, back2tf) && validate(landuse1, landusetf) && validate(landuse2, landuse2tf)) {

                                                                if (picturePath == null) {
                                                                    Toast.makeText(this, "Select picture", Toast.LENGTH_SHORT).show();
                                                                } else if (picturePath2 == null) {
                                                                    Toast.makeText(this, "Select 2nd picture", Toast.LENGTH_SHORT).show();
                                                                }  else {
                                                                    Log.i("Picture Path", picturePath);
                                                                    Alert();
                                                                }
                                                            }
                                                        } else {
                                                            if (validate(parking1, parkingt1f) && validate(parking2, parking2f) && validate(floor1, floor1f) && validate(floor2, floor2f) && validate(front, fronttf) && validate(front2, front2tf) && validate(right, righttf) && validate(right2, right2tf) && validate(left, lefttf) && validate(left2, left2tf) && validate(back, backtf) && validate(back2, back2tf) && validate(roadwidening1, roadwidening1tf) && validate(roadwidening2, roadwidening2tf) && validate(are1,are1tf) && validate(area2,area2tf) &&validate(landuse1, landusetf) && validate(landuse2, landuse2tf)){
                                                                if (picturePath == null) {
                                                                    Toast.makeText(this, "Select picture", Toast.LENGTH_SHORT).show();
                                                                } else if (picturePath2 == null) {
                                                                    Toast.makeText(this, "Select 2nd picture", Toast.LENGTH_SHORT).show();
                                                                }  else {
                                                                    Log.i("Picture Path", picturePath);
                                                                    Alert();
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        Toast.makeText(this, "Please Select Nature of Construction", Toast.LENGTH_SHORT).show();
                                                        natureofconst.requestFocusFromTouch();
                                                        natureofconst.performClick();
                                                    }
                                                }
                                            }
                                        } else {
                                            Toast.makeText(this, "Try again by logging again", Toast.LENGTH_SHORT).show();
                                            municipality.requestFocusFromTouch();
                                            municipality.performClick();
                                        }
                                    }
                                    else {
                                        Toast.makeText(this, "Try again by logging again", Toast.LENGTH_SHORT).show();
                                        district.requestFocusFromTouch();
                                        district.performClick();
                                    }
                                }
                                else{
                                    Toast.makeText(this, "Please enter Age", Toast.LENGTH_SHORT).show();
                                    userage.setError("Enter Age", true);
                                    age.requestFocusFromTouch();
                                    age.performClick();
                                }
                            }
                        }
                        else{
                            Toast.makeText(this, "Please select Total Unauthorized Construction Type", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else if(natureofconst.getSelectedItemPosition()==1){
                        if(listMarkers.size()>0){
                            if(polyflag){
                                if(age.getText().length()>0){
                                    if (!(district.getText().length() == 0)) {
                                        if (!(municipality.getText().length() == 0)) {
                                            if (surveyno.getText().length() == 0 && village.getText().length() == 0) {
                                                if (doorno.getText().length() == 0) {
                                                    surveynotf.setError("Enter Survey no", true);
                                                    villagetf.setError("Enter Village Name", true);
                                                    doornotf.setError("Enter Door No", true);
                                                }
                                                else {
                                                    if (validate(locality, localitytf)) {
                                                        if (spinnerflag) {
                                                            if (natureofconst.getSelectedItem().toString().equals("Deviation to the approved plan")) {
                                                                if (validate(approvedplan, approvedplantf) && validate(parking1, parkingt1f) && validate(parking2, parking2f) && validate(floor1, floor1f) && validate(floor2, floor2f) && validate(front, fronttf) && validate(front2, front2tf) && validate(right, righttf) && validate(right2, right2tf) && validate(left, lefttf) && validate(left2, left2tf) && validate(back, backtf) && validate(back2, back2tf) && validate(landuse1, landusetf) && validate(landuse2, landuse2tf)) {

                                                                    if (picturePath == null) {
                                                                        Toast.makeText(this, "Select picture", Toast.LENGTH_SHORT).show();
                                                                    } else if (picturePath2 == null) {
                                                                        Toast.makeText(this, "Select 2nd picture", Toast.LENGTH_SHORT).show();
                                                                    }  else {
                                                                        Alert();
                                                                    }
                                                                }
                                                            } else {

                                                                if (validate(parking1, parkingt1f) && validate(parking2, parking2f) && validate(floor1, floor1f) && validate(floor2, floor2f) && validate(front, fronttf) && validate(front2, front2tf) && validate(right, righttf) && validate(right2, right2tf) && validate(left, lefttf) && validate(left2, left2tf) && validate(back, backtf) && validate(back2, back2tf) && validate(roadwidening1, roadwidening1tf) && validate(roadwidening2, roadwidening2tf) && validate(are1,are1tf) && validate(area2,area2tf) &&validate(landuse1, landusetf) && validate(landuse2, landuse2tf)){
                                                                    if (picturePath == null) {
                                                                        Toast.makeText(this, "Select picture", Toast.LENGTH_SHORT).show();
                                                                    } else if (picturePath2 == null) {
                                                                        Toast.makeText(this, "Select 2nd picture", Toast.LENGTH_SHORT).show();
                                                                    }  else {
                                                                        Log.i("Picture Path", picturePath);
                                                                        Alert();
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            Toast.makeText(this, "Please Select Nature of Construction", Toast.LENGTH_SHORT).show();
                                                            natureofconst.requestFocusFromTouch();
                                                            natureofconst.performClick();
                                                        }
                                                    }
                                                }
                                            }
                                            else {
                                                if (validate(locality, localitytf)) {
                                                    if (spinnerflag) {
                                                        if (natureofconst.getSelectedItem().toString().equals("Deviation to the approved plan")) {
                                                            if (validate(approvedplan, approvedplantf) && validate(parking1, parkingt1f) && validate(parking2, parking2f) && validate(floor1, floor1f) && validate(floor2, floor2f) && validate(front, fronttf) && validate(front2, front2tf) && validate(right, righttf) && validate(right2, right2tf) && validate(left, lefttf) && validate(left2, left2tf) && validate(back, backtf) && validate(back2, back2tf) && validate(landuse1, landusetf) && validate(landuse2, landuse2tf)) {

                                                                if (picturePath == null) {
                                                                    Toast.makeText(this, "Select picture", Toast.LENGTH_SHORT).show();
                                                                } else if (picturePath2 == null) {
                                                                    Toast.makeText(this, "Select 2nd picture", Toast.LENGTH_SHORT).show();
                                                                }  else {
                                                                    Log.i("Picture Path", picturePath);
                                                                    Alert();
                                                                }
                                                            }
                                                        } else {
                                                            if (validate(parking1, parkingt1f) && validate(parking2, parking2f) && validate(floor1, floor1f) && validate(floor2, floor2f) && validate(front, fronttf) && validate(front2, front2tf) && validate(right, righttf) && validate(right2, right2tf) && validate(left, lefttf) && validate(left2, left2tf) && validate(back, backtf) && validate(back2, back2tf) && validate(roadwidening1, roadwidening1tf) && validate(roadwidening2, roadwidening2tf) && validate(are1,are1tf) && validate(area2,area2tf) &&validate(landuse1, landusetf) && validate(landuse2, landuse2tf)){
                                                                if (picturePath == null) {
                                                                    Toast.makeText(this, "Select picture", Toast.LENGTH_SHORT).show();
                                                                } else if (picturePath2 == null) {
                                                                    Toast.makeText(this, "Select 2nd picture", Toast.LENGTH_SHORT).show();
                                                                }  else {
                                                                    Log.i("Picture Path", picturePath);
                                                                    Alert();
                                                                }
                                                            }

                                                        }
                                                    } else {
                                                        Toast.makeText(this, "Please Select Nature of Construction", Toast.LENGTH_SHORT).show();
                                                        natureofconst.requestFocusFromTouch();
                                                        natureofconst.performClick();
                                                    }
                                                }
                                            }
                                        } else {
                                            Toast.makeText(this, "Try again by logging again", Toast.LENGTH_SHORT).show();
                                            municipality.requestFocusFromTouch();
                                            municipality.performClick();
                                        }
                                    }
                                    else {
                                        Toast.makeText(this, "Try again by logging again", Toast.LENGTH_SHORT).show();
                                        district.requestFocusFromTouch();
                                        district.performClick();
                                    }
                                }
                                else{
                                    Toast.makeText(this, "Please enter Age", Toast.LENGTH_SHORT).show();
                                    userage.setError("Enter Age", true);
                                    age.requestFocusFromTouch();
                                    age.performClick();
                                }
                            }
                            else{
                                Toast.makeText(this, "Please connect polygon", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else{
                            if(age.getText().length()>0){
                                if (!(district.getText().length() == 0)) {
                                    if (!(municipality.getText().length() == 0)) {
                                        if (surveyno.getText().length() == 0 && village.getText().length() == 0) {
                                            if (doorno.getText().length() == 0) {
                                                surveynotf.setError("Enter Survey no", true);
                                                villagetf.setError("Enter Village Name", true);
                                                doornotf.setError("Enter Door No", true);
                                            }
                                            else {
                                                if (validate(locality, localitytf)) {
                                                    if (spinnerflag) {
                                                        if (natureofconst.getSelectedItem().toString().equals("Deviation to the approved plan")) {
                                                            if (validate(approvedplan, approvedplantf) && validate(parking1, parkingt1f) && validate(parking2, parking2f) && validate(floor1, floor1f) && validate(floor2, floor2f) && validate(front, fronttf) && validate(front2, front2tf) && validate(right, righttf) && validate(right2, right2tf) && validate(left, lefttf) && validate(left2, left2tf) && validate(back, backtf) && validate(back2, back2tf) && validate(landuse1, landusetf) && validate(landuse2, landuse2tf)) {

                                                                if (picturePath == null) {
                                                                    Toast.makeText(this, "Select picture", Toast.LENGTH_SHORT).show();
                                                                } else if (picturePath2 == null) {
                                                                    Toast.makeText(this, "Select 2nd picture", Toast.LENGTH_SHORT).show();
                                                                }  else {
                                                                    Alert();
                                                                }
                                                            }
                                                        } else {
                                                            if (validate(parking1, parkingt1f) && validate(parking2, parking2f) && validate(floor1, floor1f) && validate(floor2, floor2f) && validate(front, fronttf) && validate(front2, front2tf) && validate(right, righttf) && validate(right2, right2tf) && validate(left, lefttf) && validate(left2, left2tf) && validate(back, backtf) && validate(back2, back2tf) && validate(roadwidening1, roadwidening1tf) && validate(roadwidening2, roadwidening2tf) && validate(are1,are1tf) && validate(area2,area2tf) &&validate(landuse1, landusetf) && validate(landuse2, landuse2tf)){
                                                                if (picturePath == null) {
                                                                    Toast.makeText(this, "Select picture", Toast.LENGTH_SHORT).show();
                                                                } else if (picturePath2 == null) {
                                                                    Toast.makeText(this, "Select 2nd picture", Toast.LENGTH_SHORT).show();
                                                                }  else {
                                                                    Log.i("Picture Path", picturePath);
                                                                    Alert();
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        Toast.makeText(this, "Please Select Nature of Construction", Toast.LENGTH_SHORT).show();
                                                        natureofconst.requestFocusFromTouch();
                                                        natureofconst.performClick();
                                                    }
                                                }
                                            }
                                        }
                                        else {
                                            if (validate(locality, localitytf)) {
                                                if (spinnerflag) {
                                                    if (natureofconst.getSelectedItem().toString().equals("Deviation to the approved plan")) {
                                                        if (validate(approvedplan, approvedplantf) && validate(parking1, parkingt1f) && validate(parking2, parking2f) && validate(floor1, floor1f) && validate(floor2, floor2f) && validate(front, fronttf) && validate(front2, front2tf) && validate(right, righttf) && validate(right2, right2tf) && validate(left, lefttf) && validate(left2, left2tf) && validate(back, backtf) && validate(back2, back2tf) && validate(landuse1, landusetf) && validate(landuse2, landuse2tf)) {

                                                            if (picturePath == null) {
                                                                Toast.makeText(this, "Select picture", Toast.LENGTH_SHORT).show();
                                                            } else if (picturePath2 == null) {
                                                                Toast.makeText(this, "Select 2nd picture", Toast.LENGTH_SHORT).show();
                                                            }  else {
                                                                Log.i("Picture Path", picturePath);
                                                                Alert();
                                                            }
                                                        }
                                                    } else {
                                                        if (validate(parking1, parkingt1f) && validate(parking2, parking2f) && validate(floor1, floor1f) && validate(floor2, floor2f) && validate(front, fronttf) && validate(front2, front2tf) && validate(right, righttf) && validate(right2, right2tf) && validate(left, lefttf) && validate(left2, left2tf) && validate(back, backtf) && validate(back2, back2tf) && validate(roadwidening1, roadwidening1tf) && validate(roadwidening2, roadwidening2tf) && validate(are1,are1tf) && validate(area2,area2tf) &&validate(landuse1, landusetf) && validate(landuse2, landuse2tf)){
                                                            if (picturePath == null) {
                                                                Toast.makeText(this, "Select picture", Toast.LENGTH_SHORT).show();
                                                            } else if (picturePath2 == null) {
                                                                Toast.makeText(this, "Select 2nd picture", Toast.LENGTH_SHORT).show();
                                                            }  else {
                                                                Log.i("Picture Path", picturePath);
                                                                Alert();
                                                            }
                                                        }

                                                    }
                                                } else {
                                                    Toast.makeText(this, "Please Select Nature of Construction", Toast.LENGTH_SHORT).show();
                                                    natureofconst.requestFocusFromTouch();
                                                    natureofconst.performClick();
                                                }
                                            }
                                        }
                                    } else {
                                        Toast.makeText(this, "Try again by logging again", Toast.LENGTH_SHORT).show();
                                        municipality.requestFocusFromTouch();
                                        municipality.performClick();
                                    }
                                }
                                else {
                                    Toast.makeText(this, "Try again by logging again", Toast.LENGTH_SHORT).show();
                                    district.requestFocusFromTouch();
                                    district.performClick();
                                }
                            }
                            else{
                                Toast.makeText(this, "Please enter Age", Toast.LENGTH_SHORT).show();
                                userage.setError("Enter Age", true);
                                age.requestFocusFromTouch();
                                age.performClick();
                            }
                        }

                    }
                    else{
                        Toast.makeText(this, "Please select Unauthorized Construction Type", Toast.LENGTH_SHORT).show();
                    }




                }
            }
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

    public void Alert() {
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

        } else {
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

    public void formsubmit() {

        if(constructionStage.getSelectedItem().toString().equals("Select Construction Stage")){
            TextView textView = (TextView) constructionStage.getSelectedView();
            textView.setError("Select Construction Stage");
            Toast.makeText(this,"Select Construction Stage",Toast.LENGTH_SHORT).show();
            mScrollView.scrollTo(0,0);
            return;
        }

        if (Videopath.equals("")) {
            Toast.makeText(getApplicationContext(), "Video is mandatory", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(mScrollView, "Video is mandatory", Snackbar.LENGTH_SHORT);
        }
        else if (surveyno.getText().length() == 0) {
            surveynotf.setError("Survery Number is mandatory", true);
        }

        else if (age.getText().length() == 0) {
            userage.setError("Please enter age", true);
        }else if (doorno.getText().length() == 0) {
            doornotf.setError("Door Number is mandatory", true);
        } else if (sitearea.getText().length() == 0) {
            siteareatf.setError("Site area is mandatory", true);
        } else if (phoneno.getText().length() == 0) {
            phonenotf.setError("Phone number is mandatory", true);
        } else if (streetname.getText().length() == 0) {
            streetnametf.setError("Street name is mandatory", true);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Do you want to save the data?")
                    .setCancelable(false)
                    .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
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
                                //String Survey = RemoveSpecialChar(surveyno.getText().toString());

                                String villageValue;
                                if(isUDA){
                                    try{
                                        villageValue = villageSpinner.getSelectedItem().toString();

                                    }catch (Exception e){
                                        villageValue = "Test";
                                    }
                                }else{
                                    villageValue = village.getText().toString();
                                }
                                byte[] checkBoxes = new byte[8];
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

                                mSQLiteHelper.insertData(
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
                                        left.getText().toString().trim(),
                                        left2.getText().toString().trim(),
                                        right.getText().toString().trim(),
                                        right2.getText().toString().trim(),
                                        back.getText().toString().trim(),
                                        back2.getText().toString().trim(),
                                        landuse1.getText().toString().trim(),
                                        landuse2.getText().toString().trim(),
                                        locationtext.getText().toString().trim(),
                                        picturePath,
                                        uploadId,
                                        picturePath2,
                                        uploadId2,
                                        picturePath3,
                                        uploadId3,
                                        picturePath4,
                                        uploadId4,
                                        phoneno.getText().toString().trim(),
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
                                        polygonpoints,
                                        Videopath,
                                        videoname,
                                        totaluc.getSelectedItem().toString().trim(),
                                        soption23,
                                        date.getText().toString().trim(),
                                        soption41,
                                        etplintharea.getText().toString().trim(),
                                        etplintharea_1.getText().toString().trim(),
                                        age.getText().toString().trim(),
                                        mandalSpinner.getSelectedItem().toString().trim(),
                                        checkBoxes,
                                        constructionStage.getSelectedItem().toString()
                                );


                                Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                                Log.i("gettingback", back.getText().toString());

                                listLatLngsapi.clear();
                                Log.i("polygnpoints", polygonpoints);
                                finish();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.i("sql exp", e.getMessage());
                                dialog.dismiss();
                                Snackbar snackbar = Snackbar.make(mScrollView, "Please plot the correct polygon", Snackbar.LENGTH_SHORT);
                                snackbar.setActionTextColor(Color.RED);
                                snackbar.show();
                            }
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
    }

    public void addForm() {


        Log.i("entered getting1", String.valueOf(option11));
        Log.i("entered getting2", String.valueOf(option12));
        Log.i("entered getting3", String.valueOf(option21));

        if (option11) {
            if (soption11.equals("") || soption12.equals("")) {
                Toast.makeText(getApplicationContext(), "enter building type and number of floors", Toast.LENGTH_SHORT).show();
            } else {
                formsubmit();
            }

        } else if (option12) {
            if (soption21.equals("") || soption22.equals("") || soption23.equals("")) {
                Toast.makeText(getApplicationContext(), "enter building type and number of floors of existing and construction", Toast.LENGTH_SHORT).show();
            } else {
                formsubmit();
            }

        } else if (option21) {
            if (soption31.equals("") || soption32.equals("")) {
                Toast.makeText(getApplicationContext(), "choose change in existing usage from and to", Toast.LENGTH_SHORT).show();
            } else {
                formsubmit();
            }

        } else if (option22) {
            if (date.getText().length() == 0) {
                Toast.makeText(getApplicationContext(), "choose approved date", Toast.LENGTH_SHORT).show();
            } else {
                formsubmit();
            }



        }

        else if (option23) {
            if (soption41.equals("")) {
                Toast.makeText(getApplicationContext(), "choose stilt parking", Toast.LENGTH_SHORT).show();
            } else {
                formsubmit();
            }
        }

        else if (roadwidening1.getText().length() == 0) {

            roadwidening1tf.setError("Road widening area is mandatory", true);

        } else if (are1.getText().length() == 0) {

            are1tf.setError("Site area is mandatory", true);

        } else if (area2.getText().length() == 0) {

            area2Texfield.setError("Site area is mandatory", true);

        } else if (roadwidening2.getText().length() == 0) {

            roadwidening2tf.setError("Road widening area is mandatory", true);

        } else {
            formsubmit();
        }

//        else {  are1 area2 roadwidening2
//            if (option22 && option21 && option23) {
//                LatLngAPI();
//            } else {
//                if (option32 && option31) {
//                    LatLngAPI();
//                } else {
//                    Snackbar snackbar = Snackbar.make(mScrollView, " please select from any of the above 3 options", Snackbar.LENGTH_SHORT);
//                    snackbar.setActionTextColor(Color.RED);
//                    snackbar.show();
//                }
//            }
//        }

    }


    public void setLocation() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d("mylog", "Not granted");
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else
                requestLocation();
        } else
            requestLocation();
    }

    public void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if (location != null) {
//                    loc = location;
//                    latit = loc.getLatitude();
//                    lngit = loc.getLongitude();
//                    String ltlg=String.format("%.5f", loc.getLatitude())+","+String.format("%.5f", loc.getLongitude());
//                    locationtext.setText(ltlg);
//                    //locationtext.setText(lngit + "," + latit);
//
//                }
//            }
//        });
    }

    private void configureCameraIdle() {
        onCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng latLng = mMap.getCameraPosition().target;
                float lati= (float) latLng.latitude;
                float longi=(float)latLng.longitude;
                String ltlg=String.format("%.5f", latLng.latitude)+","+String.format("%.5f", latLng.longitude);
                locationtext.setText(ltlg);
            }
        };
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setOnCameraIdleListener(onCameraIdleListener);
        setGoogleMapClickListener();
        setGoogleMapLongClickListener();
        setPolygonClickListener();
        TileProvider wmsTileProvider = TileProviderFactory.getOsgeoWmsTileProvider();
        googleMap.addTileOverlay(new TileOverlayOptions().tileProvider(wmsTileProvider));
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            if (location != null) {
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current Position");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                mCurrLocationMarker = mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(20));
            }
            if (googleApiClient != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, (com.google.android.gms.location.LocationListener) this);
            }
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    buildGoogleApiClient();
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }

            mMap.clear();
            try {


                mMap.setMyLocationEnabled(true);
            } catch (SecurityException e) {
                startActivity(new Intent(getApplicationContext(), dashboard.class));
                Toast.makeText(this, "You can't proceed without GPS", Toast.LENGTH_SHORT).show();
            }
        } else {
            Snackbar snackbar = Snackbar.make(mScrollView, "Network is Required to use Map. still you can get LatLng by clicking on Location icon", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
//            TextView loc_error = findViewById(R.id.loc_error);
//            loc_error.setText("*Network is Required to use Map. still you can get LatLng by clicking on Location icon");
        }
    }

    private void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();
    }

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
//            location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
//            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//            //locationtext.setText(location.getLongitude()+","+location.getLatitude());
//            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 20);
//            mMap.animateCamera(cameraUpdate);
        }
    }

    private void setDeviationViews(){
        el.expand(true);
        cardview.collapse();
        totaluc.setVisibility(View.GONE);
        expandspin1.setVisibility(View.GONE);
        expandspin2.setVisibility(View.GONE);
        expandspin3.setVisibility(View.GONE);
        expandspin4.setVisibility(View.GONE);
        plintharea.setVisibility(View.GONE);
        plintharea_1.setVisibility(View.GONE);
        spinnerflag = true;
        voilationLayout.setVisibility(View.VISIBLE);
        asPerTV.setText("As per Plan");
        asPerTV2.setText("As on Ground");
        violationDeviation.setText("Technical Details");
        option11 = false;
        option12 = false;
        option21 = false;
        soption23 = "";
        option22 = true;
        roadwidening1.setText("");
        roadwidening2.setText("");
        are1.setText("");
        area2.setText("");
        ownername.setText("");
        fathername.setText("");
        address.setText("");
        surveyno.setText("");
        village.setText("");
        doorno.setText("");
        locality.setText("");
        streetname.setText("");
        sitearea.setText("");
        approvedplan.setText("");
        parking1.setText("");
        parking2.setText("");
        floor1.setText("");
        floor2.setText("");
        front.setText("");
        front2.setText("");
        left.setText("");
        left2.setText("");
        right.setText("");
        right2.setText("");
        back.setText("");
        back2.setText("");
        landuse1.setText("");
        landuse2.setText("");
        phoneno.setText("");

        Constype.setSelection(0);
        Constfloors.setSelection(0);
        Floorsasonground.setSelection(0);
        date.setText("");
        NoOfFloors.setSelection(0);
        ExistingFloors.setSelection(0);
        ConvertingFloors.setSelection(0);
        typeofconst();
    }

    private void setTotalUcViews() {
        el.expand(true);
        datetf.setVisibility(View.GONE);
        totaluc.setVisibility(View.VISIBLE);
        expandspin1.setVisibility(View.GONE);
        expandspin2.setVisibility(View.GONE);
        expandspin3.setVisibility(View.GONE);
        plintharea.setVisibility(View.VISIBLE);
        plintharea_1.setVisibility(View.VISIBLE);
        spinnerflag = true;
        option22 = false;
        sitearea.setText("");
        landuse1.setText("");
        front.setText("");
        back.setText("");
        right.setText("");
        left.setText("");
        ownername.setText("");
        phoneno.setText("");
        address.setText("");
        floor1.setText("");
        are1.setText("");
        surveyno.setText("");
        parking1.setText("");
        approvedplan.setText("");
        date.setText("");
        asPerTV.setText("As per Rule");
        asPerTV2.setText("As per Construction");
        violationDeviation.setText("Technical Details");
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You can't proceed without GPS. want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(getApplicationContext(), dashboard.class));
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case 1001:
                    // image 1 -- handle camera image
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        Bitmap wm = Watermark(selectedImage, "lat:" + latit + " lng:" + lngit, " D:" + formattedDate);
                        picturePath = saveReceivedImage(wm, uploadId);
                        Log.i("return", saveReceivedImage(wm, uploadId));
                        previewimage.setImageBitmap(selectedImage);
                    }else
                        Toast.makeText(this,"Image not selected",Toast.LENGTH_SHORT).show();
                    break;
                case 1002:
                    // image 1 -- handle galary image
                    if (resultCode == RESULT_OK && data != null){
                        Uri imageURI =  data.getData();
                        Bitmap selectedImage = uriToBitmap(imageURI);
                        Bitmap wm = Watermark(selectedImage, "lat:" + latit + " lng:" + lngit, " D:" + formattedDate);
                        picturePath = saveReceivedImage(wm, uploadId);
                        Log.i("return", saveReceivedImage(wm, uploadId));
                        previewimage.setImageBitmap(selectedImage);
                    }else
                        Toast.makeText(this,"Image not selected",Toast.LENGTH_SHORT).show();
                    break;
                case 2001:
                    // image 2 -- handle camare image
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage2 = (Bitmap) data.getExtras().get("data");
                        Bitmap wm2 = Watermark(selectedImage2, "lat:" + latit + " lng:" + lngit, " D:" + formattedDate);
                        picturePath2 = saveReceivedImage(wm2, uploadId2);
                        previewimage2.setImageBitmap(selectedImage2);
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
                        previewimage2.setImageBitmap(selectedImage2);
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
                try{
                    if (data != null) {
                        Uri contentURI = data.getData();
                        Videopath = getRealPathFromURI(contentURI);
                        Log.d("DEBUGGING VP::",Videopath);
                        if(Videopath.contains("OLDGISFY")){
                            Log.d("DEBUGGING VP","UCIMS PATH");
                            Toast.makeText(MainActivity.this,"Video is corrupted, please select " +
                                            "other video",
                                    Toast.LENGTH_LONG).show();
                        }else{
                            Log.d("DEBUGGING VP","NOT UCIMS PATH");
                            PlayVideo(Videopath);

                        }
                    }
                }catch (Exception e){
                    Toast.makeText(MainActivity.this,"The video you selected is corrupted, please" +
                            " record video",Toast.LENGTH_LONG).show();
                }
            } else if (requestCode == 917) {
                Uri contentURI = data.getData();
                Videopath = getRealPathFromURI(contentURI);
                PlayVideo(Videopath);
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


    public void CloseMap(View view) {
        map.toggle(true);
    }

    public void image1(View view) {
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

//    public void LatLngAPI() {
//        String[] latlong = locationtext.getText().toString().split(",");
//        double longitude = Double.parseDouble(latlong[0]);
//        double latitude = Double.parseDouble(latlong[1]);
//        String urlString = "http://13.235.35.55/umsapi/api/UMS/WithInUMS?lat=" + latitude + "&lng=" + longitude + "&UMStype=UC";
//        final ProgressDialog pDialog = new ProgressDialog(MainActivity.this);
//        pDialog.setMessage("Please wait! Checking..");
//        pDialog.setCancelable(false);
//        pDialog.show();
//
//        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlString,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                        String reponse = null;
//                        try {
//                            Log.i("LatLngAPILog", response.substring(0, 4));
//                            reponse = response.substring(0, 4);
//                            Log.i("LatLngAPIString", response);
//                        } catch (IndexOutOfBoundsException e) {
//                            e.printStackTrace();
//                        }
//                        if (reponse.equals("true")) {
//                            pDialog.dismiss();
//                            formsubmit();
//                        } else {
//                            pDialog.dismiss();
//                            Snackbar snackbar = Snackbar
//                                    .make(mScrollView, "This is nearby construction information already updated in our system. Do you want to continue to submit?", Snackbar.LENGTH_LONG);
//                            snackbar.setAction("Submit", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    formsubmit();
//                                }
//                            });
//                            snackbar.setActionTextColor(Color.RED);
//                            snackbar.show();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                pDialog.dismiss();
//                Snackbar snackbar = Snackbar
//                        .make(mScrollView, "Something went wrong", Snackbar.LENGTH_LONG);
//                snackbar.setActionTextColor(Color.RED);
//                snackbar.show();
//            }
//        });
//        queue.add(stringRequest);
//    }

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
                .create(MainActivity.this, bitmap)
                .loadWatermarkText(latlng)
                .loadWatermarkText(date)// use .loadWatermarkImage(watermarkImage) to load an image.
                .getWatermark()
                .getOutputImage();
        return wmimg;
    }

    public void record(View view) {
        try {
            final File myDirectory = new File(Environment.getExternalStorageDirectory(), "UCIMSUCVideos");
            if (!myDirectory.exists()) {
                myDirectory.mkdirs();
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
                                    startActivityForResult(intent, 917);
                                    break;
                            }
                        }
                    });
            pictureDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("video error",e.getMessage());
        }
    }

    public void typeofconst() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Auto fill for deviation");
        alertDialog.setMessage("Enter File no:");

        final EditText input = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.file);
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Toast.makeText(MainActivity.this, "Fill manually", Toast.LENGTH_SHORT).show();
                    }
                });
        final AlertDialog dialog = alertDialog.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Boolean wantToCloseDialog = false;
                if(wantToCloseDialog)
                    dialog.dismiss();
                if (!(input.getText().length() == 0))
                {
                    wantToCloseDialog = true;
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    if ( connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                            || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ) {
                            approvedplan.setText(input.getText().toString());
                            new workingthread().execute(input.getText().toString());
                    }
                    else {
                        Toast.makeText(MainActivity.this, "No Network Fill Manually", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    input.setError("Enter value");
                    wantToCloseDialog = false;
                }
                if (wantToCloseDialog)
                {
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public class workingthread extends AsyncTask<String, String, Integer> {

        ProgressDialog progressDialog;
        String siteareas, landuse, front1, rear1, right1, left1,isdate, ownernamestring, ownerphoneno, owneraddress, nooffloorsstring, areastring, surveynostring,parkingarea,ProceedingIssuedDate,updatedsurvey;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Wait");
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(String... strings) {
//            okhttp3.Response response = null;
            String fileno = strings[0];
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n" +
                    "\t\"AuthenticationKey\":\"UCIM\",\n" +
                    "\t\"FileNo\":\""+fileno+"\"\n" +
                    "}");
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url("http://apdpms.ap.gov.in:8082/AutoDCR.APServices/PermitDetails/PermitInfo.svc/GetDetailsByFileNo")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();


//            OkHttpClient client = new OkHttpClient().newBuilder()
//                    .build();
//            MediaType mediaType = MediaType.parse("application/json");
//            RequestBody body = RequestBody.create(mediaType, "{\r\n        \"AuthenticationKey\":\"UCIM\",\r\n        \"FileNo\":\"1168/4983/B/AGRPLE/AIP/2018\"\r\n}\r\n");
//            Request request = new Request.Builder()
//                    .url("http://13.127.180.195:8082/AutoDCR.APServices/PermitDetails/PermitInfo.svc/GetDetailsByFileNo")
//                    .method("POST", body)
//                    .addHeader("Content-Type", "application/json")
//                    .build();



            try {
                okhttp3.Response response = client.newCall(request).execute();
                assert response.body() != null;
                String jsonData = response.body().string();
                Log.i("jsondata",jsonData);

                JSONObject Jobject = new JSONObject(jsonData);
                JSONObject details = Jobject.getJSONObject("Details");
                JSONArray Jarray = details.getJSONArray("FileDetails");


                for (int i = 0; i < Jarray.length(); i++) {
                    JSONObject object = Jarray.getJSONObject(i);
                    surveynostring=object.getString("SurveyNo");
                    Log.i("surveynostring",surveynostring);

                    if(surveynostring.contains("&")){

                        surveynostring=surveynostring.replaceAll("[&'\"]","-");
                    }
                    Log.i("updatedsurveynostring",surveynostring);

                    ProceedingIssuedDate = object.getString("ProceedingIssuedDate");
                    JSONObject ownerdetails = new JSONObject(object.getString("OwnerDetails"));
                    Log.i("OwnerDetails", ownerdetails.getString("Name"));
                    ownernamestring=ownerdetails.getString("Name");
                    ownernamestring=ownernamestring.replaceAll("[&'\"]",",");
                    owneraddress=ownerdetails.getString("Address");
                    owneraddress=owneraddress.replaceAll("[&'\"]",",");
                    ownerphoneno=ownerdetails.getString("Mobile");
                    landuse=object.getString("ProposedUse");
                    siteareas=object.getString("SiteArea");
                    areastring=object.getString("BuiltupArea");
                    parkingarea=object.getString("ParkingArea");

                    Log.i("ProposedUse", object.getString("ProposedUse"));
                    JSONArray setbacksArray = object.getJSONArray("BuildingDetails");
                    for (int j = 0; j < setbacksArray.length(); j++) {
                        JSONObject setbacksObj = setbacksArray.getJSONObject(j);
                        front1=setbacksObj.getString("FrontMargin");
                        rear1=setbacksObj.getString("RearMargin");
                        right1=setbacksObj.getString("Side1Margin");
                        left1=setbacksObj.getString("Side2Margin");
                        JSONArray building_use = setbacksObj.getJSONArray("BuildingUse");
                        for (int f = 0; f < building_use.length(); f++) {
                            JSONObject buildingobj = building_use.getJSONObject(f);
                            JSONArray floorarray = buildingobj.getJSONArray("FloorDetails");
                            ArrayList<String> floornames=new ArrayList<>();
                            for (int k = 0; k < floorarray.length(); k++) {
                                JSONObject setbacksObj12 = floorarray.getJSONObject(k);
                                String floorname=setbacksObj12.getString("FloorName");
                                floornames.add(floorname);
                                Log.i("entered",floorname);


                            }
                            for(int p=0;p<floornames.size();p++){
                                if(floornames.contains("TERRACE FLOOR")){
                                    nooffloorsstring=String.valueOf((floorarray.length())-1);
                                }
                                else {
                                    nooffloorsstring=String.valueOf((floorarray.length()));
                                }
                            }


                        }
                    }
                    JSONArray occupancydetails = object.getJSONArray("OccupancyDetails");
                    if (occupancydetails.length()!=0)
                    {
                        for (int j = 0; j < occupancydetails.length(); j++) {
                            JSONObject occupancyObj = occupancydetails.getJSONObject(j);
                            isdate=occupancyObj.getString("IssuedDate");

                        }
                    }

                }

                updateUI(siteareas,landuse,front1,rear1,right1,left1,ownernamestring,ownerphoneno,owneraddress,nooffloorsstring,areastring,surveynostring,parkingarea,isdate,ProceedingIssuedDate );
                progressDialog.dismiss();
                return response.code();

            }
            catch (IOException | JSONException e) {
                progressDialog.dismiss();

             //   startActivity(getIntent());
              //  toast(getApplicationContext(),"No Data Found. Please Try to Fill Manually");
                e.printStackTrace();

                return 404;
            }





        }
        @Override
        protected void onPostExecute(Integer s) {


            if(! (s.equals(200))){
                //startActivity(getIntent());
                Toast.makeText(MainActivity.this, "No Data Found. Please Try to Fill Manually", Toast.LENGTH_LONG).show();

                el.toggle(false);
                el.toggle(true);

            }

          //  Toast.makeText(MainActivity.this, "No Data Found. Please Try to Fill Manually", Toast.LENGTH_LONG).show();
//            super.onPostExecute(s);
//            progressDialog.dismiss();
//            if (s.equals(null))
//            {
//                approvedplan.setText("");
//                Toast.makeText(MainActivity.this, "Wrong File no. Fill manually", Toast.LENGTH_SHORT).show();
//
//            }
//            else
//            {
//                if(!(siteareas==null)) {
//                    Toast.makeText(MainActivity.this, "Data auto filled", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    approvedplan.setText("");
//                    Toast.makeText(MainActivity.this, "Data not found with the file no. Enter Manually", Toast.LENGTH_SHORT).show();
//                }
//            }
        }
    }




    public void updateUI(final String siteareas, final String landuse, final String front1, final String rear1, final String right1, final String left1, final String newownernamestring, final String ownerphoneno, final String owneraddress, final String nooffloorsstring, final String areastring, final String surveynostring, final String parkingarea1,final String idate, final  String proceedingIssuedDate) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sitearea.setText(siteareas);
                landuse1.setText(landuse);
                front.setText(front1);
                back.setText(rear1);
                right.setText(right1);
                left.setText(left1);
                String newname = newownernamestring.replaceAll("/", "");
                ownername.setText(newname);
                phoneno.setText(ownerphoneno);
                address.setText(owneraddress);
                floor1.setText(nooffloorsstring);
                are1.setText(areastring);
                 surveyno.setText(surveynostring);
                parking1.setText(parkingarea1);
                if (proceedingIssuedDate != null)
                {
                    String[] str = proceedingIssuedDate.split("-");
                    date.setText(str[2]+"-"+str[1]+"-"+str[0]);
                }

//                if (idate != null)
//                {
//                    String[] str = idate.split("-");
//                    date.setText(str[2]+"-"+str[1]+"-"+str[0]);
//                }
            }
        });
    }





    public void PlayVideo(final String input) {
        videoname+="OLDGISFY";
        File myDirectory = new File(Environment.getExternalStorageDirectory(), "UCIMSUCVideos");
        if(!myDirectory.exists()) {
            myDirectory.mkdirs();
        }

        if(Build.VERSION.SDK_INT<30){
            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Compressing Video");
            progressDialog.setCancelable(false);
            try {
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
                            Videopath = Environment.getExternalStorageDirectory() + "/UCIMSUCVideos/" + videoname + ".mp4";
                        }
                        final MediaController mediacontroller = new MediaController(MainActivity.this);
                        mediacontroller.setAnchorView(videoView);
                        videoView.setMediaController(mediacontroller);
                        videoView.setVisibility(View.VISIBLE);
                        videoView.setVideoPath(Videopath);
                        videoView.start();
                    }

                    @Override
                    public void onProgress(float percent) {
                        Log.i("Preogress", String.valueOf(percent));
                        progressDialog.setMessage("Compressing Video "+ Math.floor(percent)+"%");

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            final MediaController mediacontroller = new MediaController(MainActivity.this);
            mediacontroller.setAnchorView(videoView);
            videoView.setMediaController(mediacontroller);
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoPath(Videopath);
            videoView.start();
        }

    }
    public static String RemoveSpecialChar(String str) {
        String specialCharacters="&";
        String str2[]=str.split("");
        String modidied = "";
        for (int i=0;i<str2.length;i++)
        {
            if (specialCharacters.contains(str2[i]))
            {
             modidied=str.replace("&",",");
             System.out.println("found &");
            }
            else
             System.out.println("false");
        }
        Log.i("Remove &",modidied);
       return modidied;
    }
}