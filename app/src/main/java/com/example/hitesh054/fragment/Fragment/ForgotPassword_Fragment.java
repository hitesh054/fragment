package com.example.hitesh054.fragment.Fragment;

/**
 * Created by hitesh054 on 22-05-2018.
 */



        import java.util.regex.Matcher;
        import java.util.regex.Pattern;


        import android.os.Bundle;
        import android.support.design.widget.BottomNavigationView;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.hitesh054.fragment.Utils.CustomToast;
        import com.example.hitesh054.fragment.R;
        import com.example.hitesh054.fragment.Utils.Utils;

public class ForgotPassword_Fragment extends Fragment implements
       View.OnClickListener {


    BottomNavigationView navigation;

    private static View view;
    private static Animation shakeAnimation;
    private static LinearLayout loginLayout;

    private static EditText emailId;
    private static TextView submit;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forgotpassword_layout, container,
                false);
        emailId = (EditText) view.findViewById(R.id.registered_emailid);
        submit = (TextView) view.findViewById(R.id.submit);
        loginLayout = (LinearLayout) view.findViewById(R.id.layout);
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);

        setListeners();
        return view;
    }

    // Initialize the views




        // Setting text selector over textviews

//        try {
//            ColorStateList csl = ColorStateList.createFromXml(getResources(),
//                    xrp);
//
//            back.setTextColor(csl);
//            submit.setTextColor(csl);
//
//        } catch (Exception e) {
//        }



    // Set Listeners over buttons
    private void setListeners() {

        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:

                // Call Submit button task
                submitButtonTask();
                break;

        }

    }


    private void submitButtonTask() {
        String getEmailId = emailId.getText().toString().trim();

        // Pattern for email id validation
        Pattern p = Pattern.compile(Utils.regEx);

        // Match the pattern
        Matcher m = p.matcher(getEmailId);

        // First check if email id is not null else show error toast
        if (getEmailId.equals("") || getEmailId.length() == 0) {

            new CustomToast().Show_Toast(getActivity(), view,
                    "Please enter your Email Id.");
            loginLayout.startAnimation(shakeAnimation);
        }
//       Toast.makeText(getActivity(), "Your toast message.", Toast.LENGTH_SHORT).show();

            // Check if email id is valid or not
        else if (!m.find()) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");
            loginLayout.startAnimation(shakeAnimation);
        }
            // Else submit email id and fetch passwod or do your stuff
        else
            Toast.makeText(getActivity(), "Get Forgot Password.",
                    Toast.LENGTH_SHORT).show();
    }

}
