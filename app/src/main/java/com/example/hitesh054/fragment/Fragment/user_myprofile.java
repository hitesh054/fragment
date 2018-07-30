package com.example.hitesh054.fragment.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.hitesh054.fragment.Activity.user_activity;
import com.example.hitesh054.fragment.R;

/**
 * Created by hitesh054 on 08-06-2018.
 */

public class user_myprofile extends Fragment implements View.OnClickListener {
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "User_profile";


    ImageButton b1;



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.user_myprofile, container, false);
        b1= (ImageButton) myView.findViewById(R.id.go_to_edit_u);
        b1.setOnClickListener(this);
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
            case R.id.go_to_edit_u:
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame,new edit_myprofile_fragment(), user_activity.edit_myprofile_fragment);
                ft.commit();

        }
    }
}
