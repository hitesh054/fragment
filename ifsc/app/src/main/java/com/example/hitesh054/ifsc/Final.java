package com.example.hitesh054.ifsc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Final extends AppCompatActivity {
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9;
    data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        tv1= (TextView)findViewById(R.id.TextViewdetails);
        tv2= (TextView)findViewById(R.id.TextViewIfcsdetails);
        tv3= (TextView)findViewById(R.id.TextViewBranchdetails);
        tv4= (TextView)findViewById(R.id.TextViewAdressDetails);
        tv5= (TextView)findViewById(R.id.TextViewcontactDetails);
        tv6= (TextView)findViewById(R.id.TextViewcityDetails);
        tv7= (TextView)findViewById(R.id.TextViewRTGSDetails);
        tv8= (TextView)findViewById(R.id.TextViewdisDetails);
        tv9= (TextView)findViewById(R.id.TextViewstateDetails);
        Intent intent = getIntent();
        data = intent.getParcelableExtra("items");
        tv1.setText(data.getStrbank());
        tv2.setText(data.getStrifsc());
        tv3.setText(data.getStrBranch());
        tv4.setText(data.getStrAddress());
        tv5.setText(data.getStrContact());
        tv6.setText(data.getStrCity());
        tv7.setText(data.getStrRtgs());
        tv8.setText(data.getStrDistrict());
        tv9.setText(data.getStrState());


    }
}
