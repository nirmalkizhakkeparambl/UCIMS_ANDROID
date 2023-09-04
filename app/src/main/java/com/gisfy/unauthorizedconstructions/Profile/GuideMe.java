package com.gisfy.unauthorizedconstructions.Profile;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.gisfy.unauthorizedconstructions.R;
import com.gisfy.unauthorizedconstructions.Utils.CustomAlertDailog;
import com.gisfy.unauthorizedconstructions.dashboard;


public class GuideMe extends AppCompatActivity {
CardView cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_me);


    }

    public void help(View view) {
        CustomAlertDailog c=new CustomAlertDailog(GuideMe.this);
        c.show();
    }
}
