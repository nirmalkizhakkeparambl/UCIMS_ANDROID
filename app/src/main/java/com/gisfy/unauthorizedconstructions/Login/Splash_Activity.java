package com.gisfy.unauthorizedconstructions.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.gisfy.unauthorizedconstructions.R;
import com.gisfy.unauthorizedconstructions.dashboard;

public class Splash_Activity extends AppCompatActivity {
    ImageView img;
    Animation animfade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);
        img=findViewById(R.id.imageView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Splash_Activity.this, dashboard.class);
                startActivity(intent);
                finish();
            }

        }, 2000);
    }
}
