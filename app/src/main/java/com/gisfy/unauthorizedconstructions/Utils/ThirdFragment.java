package com.gisfy.unauthorizedconstructions.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.gisfy.unauthorizedconstructions.Login.loginpage;
import com.gisfy.unauthorizedconstructions.R;


public class ThirdFragment extends Fragment implements View.OnTouchListener {
ConstraintLayout constraintLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = null;
        try {
            view= inflater.inflate(R.layout.fragment_third, container, false);
            constraintLayout=view.findViewById(R.id.third);
        } catch (InflateException e) {
            e.printStackTrace();
        }
        constraintLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), loginpage.class);
                startActivity(intent);
            }


        });

        return view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {


        return false;
    }
}
