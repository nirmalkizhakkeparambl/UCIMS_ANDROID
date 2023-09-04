package com.gisfy.unauthorizedconstructions;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Test extends AppCompatActivity {

    EditText fileno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        fileno=findViewById(R.id.fileno);

    }

    public void qwerty(View view) {
//       new workingthread().execute(fileno.getText().toString());
    }
    public class workingthread extends AsyncTask<String,String,String> {

        ProgressDialog progressDialog;
        @Override
        protected String doInBackground(String... strings) {
            Response response = null;
            String fileno=strings[0];
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            Log.i("",fileno);
            RequestBody body = RequestBody.create(mediaType, "{\n" +
                    "\t\"AuthenticationKey\":\"UCIM\",\n" +
                    "\t\"FileNo\":\""+fileno+"\"\n" +
                    "}");
            Request request = new Request.Builder()
                    .url("http://45.114.245.70:8082/AutoDCR.APServices/PermitDetails/PermitInfo.svc/GetDetailsByFileNo")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                JSONObject Jobject = new JSONObject(jsonData);
                JSONObject details = new JSONObject(Jobject.getString("Details"));
                JSONArray Jarray = details.getJSONArray("FileDetails");
                for (int i = 0; i < Jarray.length(); i++) {
                    JSONObject object = Jarray.getJSONObject(i);
                    Log.i("1",object.getString("SurveyNo"));
                    JSONObject ownerdetails = new JSONObject(object.getString("OwnerDetails"));
                    Log.i("OwnerDetails", ownerdetails.getString("Name"));
                    Log.i("2",ownerdetails.getString("Name"));
//                    owneraddress=ownerdetails.getString("Address");
//                    ownerphoneno=ownerdetails.getString("Mobile");
//                    landuse=object.getString("ProposedUse");
//                    siteareas=object.getString("SiteArea");
//                    areastring=object.getString("BuiltupArea");
//                    parkingarea=object.getString("ParkingArea");
                    Log.i("ProposedUse", object.getString("ProposedUse"));
                    JSONArray setbacksArray = object.getJSONArray("BuildingDetails");
                    for (int j = 0; j < Jarray.length(); j++) {
                        JSONObject setbacksObj = setbacksArray.getJSONObject(j);
                        Log.i("3",setbacksObj.getString("FrontMargin"));
//                        rear1=setbacksObj.getString("RearMargin");
//                        right1=setbacksObj.getString("Side1Margin");
//                        left1=setbacksObj.getString("Side2Margin");
                        JSONArray building_use = setbacksObj.getJSONArray("BuildingUse");
                        for (int f = 0; f < building_use.length(); f++) {
                            JSONObject buildingobj = building_use.getJSONObject(f);
                            JSONArray floorarray = buildingobj.getJSONArray("FloorDetails");
                            for (int k = 0; k < floorarray.length(); k++) {
                                Log.i("4",String.valueOf(floorarray.length()));
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response.message();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(Test.this);
            progressDialog.setTitle("Wait");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Test.this, ""+s, Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }
}
