package com.example.hitesh054.fragment.Fragment;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hitesh054.fragment.Activity.MainActivity;
import com.example.hitesh054.fragment.Activity.driver_Activity;
import com.example.hitesh054.fragment.R;



/**
 * Created by hitesh054 on 08-06-2018.
 */

public class fragment_driver_profile extends Fragment implements View.OnClickListener {
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "driverprofile";

    ImageView imageview;
    Button submit;
TextView tv,tv_2,tv_3;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.driver_profile, container, false);
        imageview= (ImageView) myView.findViewById(R.id.go_to_edit_d);
        tv= (TextView)myView.findViewById(R.id.tv1);
        tv.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_2= (TextView)myView.findViewById(R.id.tv2);
        tv_2.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_3= (TextView)myView.findViewById(R.id.tv3);
        tv_3.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        imageview.setOnClickListener(this);
        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_to_edit_d:
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame,new edit_fragment_driver_profile(), driver_Activity.edit_DriverMyprofile);
                ft.commit();

        }
    }
}

