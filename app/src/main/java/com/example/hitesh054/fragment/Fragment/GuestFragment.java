package com.example.hitesh054.fragment.Fragment;

/**
 * Created by hitesh054 on 21-05-2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hitesh054.fragment.Activity.GuestActivity;
import com.example.hitesh054.fragment.Activity.MainActivity;
import com.example.hitesh054.fragment.Listeners.ResultResponse;
import com.example.hitesh054.fragment.ServerConnection.DataParser.LatLongDataParser;
import com.example.hitesh054.fragment.ServerConnection.DataParser.LoginDataParser;
import com.example.hitesh054.fragment.Utils.CustomToast;
import com.example.hitesh054.fragment.R;
import com.example.hitesh054.fragment.Session.SessionManagement;

import org.json.JSONException;
import org.json.JSONObject;


public class GuestFragment extends Fragment implements View.OnClickListener , ResultResponse {
    private EditText Email_id, Password;
    private Button login;
    private CheckBox ch;
    private TextView alreadyuser, forgotPassword;
    //    AlertDialogManager alert = new AlertDialogManager();
    SessionManagement session;
    String value1;
    String value2;
    private static Animation shakeAnimation;
    private static LinearLayout loginLayout;
    FragmentManager manager;
    LoginDataParser loginDataParser = new LoginDataParser();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_guest, container, false);
        // Session Manager
        session = new SessionManagement(getActivity());
        loginLayout = (LinearLayout) myView.findViewById(R.id.login_layout);
        Email_id = (EditText) myView.findViewById(R.id.login_emailid);
        manager = getActivity().getSupportFragmentManager();
        Password = (EditText) myView.findViewById(R.id.login_password);
        forgotPassword = (TextView) myView.findViewById(R.id.forgot_password);
        ch = (CheckBox) myView.findViewById(R.id.show_hide_password);
        login = (Button) myView.findViewById(R.id.loginBtn);
        alreadyuser = (TextView) myView.findViewById(R.id.createAccount);
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);
        login.setOnClickListener(this);
        alreadyuser.setOnClickListener(this);
        ch.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        loginDataParser.result = this;

        ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton button,
                                         boolean isChecked) {

                // If it is checkec then show password else hide
                // password
                if (isChecked) {

                    ch.setText(R.string.hide_pwd);// change
                    // checkbox
                    // text

                    Password.setInputType(InputType.TYPE_CLASS_TEXT);
                    Password.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());// show password
                } else {
                    ch.setText(R.string.show_pwd);// change
                    // checkbox
                    // text

                    Password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    Password.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());// hide password

                }

            }
        });


        return myView;


    }

    public void checkLogin(String Email_id, String Password) {
        new LoginDataParser(getActivity(), Email_id, Password, loginDataParser.result).start();
    }

    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.loginBtn:
                value1 = Email_id.getText().toString();
                value2 = Password.getText().toString();


                if (value1.trim().length() > 0 && value2.trim().length() > 0) {
                    checkLogin(Email_id.getText().toString(), Password.getText().toString());
//                    // For testing puspose username, password is checked with sample data
//                    // username = test
//                    // password = test
//                    if(value1.equals("test") && value2.equals("test")){
//
//                        // Creating user login session
//                        // For testing i am stroing name, email as follow
//                        // Use user real data
//                        checkLogin(Email_id.getText().toString(), Password.getText().toString());
                        session.createLoginSession("Android Hive", "anroidhive@gmail.com","guest");
//
//                        // Staring MainActivity
//                        Intent i = new Intent(getActivity(), GuestActivity.class);
//                        startActivity(i);
//                        getActivity().finish();
//
//                    }else{
//                    // username / password doesn't match.
//                    FragmentManager manager = getActivity().getSupportFragmentManager();
//
////                        alert.showAlertDialog(getActivity(), "Login failed..", "Username/Password is incorrect", false);
//                }
                } else {
                    // user didn't entered username or password
                    // Show alert asking him to enter the details
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    loginLayout.startAnimation(shakeAnimation);
                    new CustomToast().Show_Toast(getActivity(), v,
                            "Enter details.");
//                    alert.showAlertDialog(getActivity(), "Login failed..", "Please enter username and password", false);
                }


                break;

            case R.id.createAccount:
//                fragment = new SignUp_Fragment();
//                FragmentManager manager = getActivity().getSupportFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.replace(R.id.container, fragment);
                fragment = new SignUp_Fragment();

                manager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.fragment_container, fragment, MainActivity.SignUpFragment).commit();
                break;

            case R.id.forgot_password:

                // Replace forgot password fragment with animation
                manager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.fragment_container,
                                new ForgotPassword_Fragment(),
                                MainActivity.forgotpasswordfragment).commit();
                break;


            default:
                break;
        }


    }


    @Override
    public void returnResult(String output) {


        try {
            JSONObject json = new JSONObject(output);

            String status=json.getString("status");

            if(status.equals("success")){
                Intent i = new Intent(getActivity(), GuestActivity.class);
                startActivity(i);
                getActivity().finish();
            }
            else
            {

                    new CustomToast().Show_Toast(getActivity(),this.getView(),
                            "credentials dont match.");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



            }


    }











