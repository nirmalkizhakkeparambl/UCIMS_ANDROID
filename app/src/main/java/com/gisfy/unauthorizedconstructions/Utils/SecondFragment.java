package com.gisfy.unauthorizedconstructions.Utils;

import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.gisfy.unauthorizedconstructions.R;


public class SecondFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = null;
        try {
            view= inflater.inflate(R.layout.fragment_second, container, false);
        } catch (InflateException e) {
            e.printStackTrace();
        }
        return view;
    }
    }
