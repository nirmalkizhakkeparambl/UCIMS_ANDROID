package com.gisfy.unauthorizedconstructions.SQLite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gisfy.unauthorizedconstructions.R;
import com.gisfy.unauthorizedconstructions.dashboard;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class RecordListActivity extends AppCompatActivity {

    ListView mListView;
    ArrayList<Model> mList;
    RecordListAdapter mAdapter = null;
    SQLiteHelper mSQLiteHelper;
    LinearLayout relativeLayout;

    FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);
        mListView = findViewById(R.id.listView);
        ImageView iv=findViewById(R.id.back);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecordListActivity.this, dashboard.class));
            }
        });
        relativeLayout=findViewById(R.id.relative_snack);
        mList = new ArrayList<>();
        mAdapter = new RecordListAdapter(this, R.layout.row, mList);
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        client= LocationServices.getFusedLocationProviderClient(this);


        mSQLiteHelper = new SQLiteHelper(this, "Construction.sqlite", null, 2);
        Cursor cursor =mSQLiteHelper.getData("SELECT * FROM Construction");
        mList.clear();
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String Draftsman = cursor.getString(1);
            String ownername = cursor.getString(2);
            String fathername = cursor.getString(3);
            String phoneno = cursor.getString(38);
            //add to list
            mList.add(new Model(id, Draftsman, ownername, fathername,phoneno));

        }
        mAdapter.notifyDataSetChanged();
        if (mList.size()==0){
            Snackbar snackbar = Snackbar
                    .make(relativeLayout, "No Data Found..?", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Add Now", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(RecordListActivity.this, MainActivity.class));
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                //alert dialog to display options of update and delete
                final CharSequence[] items = {"Update", "Delete","Cancel"};
                final AlertDialog.Builder dialog = new AlertDialog.Builder(RecordListActivity.this);
                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0){
                            //update
                            Cursor c =mSQLiteHelper.getData("SELECT id FROM Construction");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            Intent intent = new Intent(getBaseContext(), Edit_Sql.class);
                          intent.putExtra("position", arrID.get(position));
                            startActivity(intent);



                        }
                        if (i==1){
                            //delete
                            Cursor c = mSQLiteHelper.getData("SELECT id FROM Construction");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));
                        }
                        if (i==2)
                        {
                            dialogInterface.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });


    }
public void Refresh()
{
    mAdapter.notifyDataSetChanged();
}
    private void showDialogDelete(final int idRecord)
    {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(RecordListActivity.this);
        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure to delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    mSQLiteHelper.deleteData(idRecord);
                    Toast.makeText(RecordListActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateRecordList();
            }
        });
        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogDelete.show();
    }


    private void updateRecordList() {
        //get all data from sqlite
        Cursor cursor = mSQLiteHelper.getData("SELECT * FROM Construction");
        mList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String Draftsman = cursor.getString(1);
            String ownername = cursor.getString(2);
            String fathername = cursor.getString(3);
            String phoneno = cursor.getString(38);
            mList.add(new Model(id, Draftsman, ownername, fathername,phoneno));
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RecordListActivity.this,dashboard.class));
    }

    public void back(View view) {
        startActivity(new Intent(RecordListActivity.this,dashboard.class));
    }
}