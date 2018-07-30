package com.example.hitesh054.ifsc;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText ed1;
    Button b1;
    ArrayList<data> d;
    String url2="https://github.com/razorpay/ifsc/wiki/API/:ifsc";
    String strbank;
    String strifsc;
    String strBranch;
    String strAddress;
    String strContact;
    String strCity;
    String strRtgs;
    String strDistrict;
    String strState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FetchTask fetchTask = new FetchTask();
        fetchTask.execute(url2);
    }

    public void addListenerOnButton() {
        ed1 = (EditText) findViewById(R.id.edittext1);
        b1 = (Button) findViewById(R.id.button1);

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String s1 = ed1.getText().toString().trim();

                if(!s1.isEmpty()) {

                    Intent i = new Intent(MainActivity.this, Final.class);
                    i.putExtra("items", d);
                    startActivity(i);
                }else {
                    Toast.makeText(MainActivity.this, "Please enter the IFSC code", Toast.LENGTH_LONG).show();
                }



            }

        });
    }

    private final class FetchTask extends AsyncTask<String, Void, ArrayList<data>> {
        String url;

        @Override
        protected ArrayList<data> doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }
            url = params[0];
            try {
                URL url1 = new URL(url);
                String response = NetworkUtils.makeHttpRequest(url1);
                ArrayList<data> json = extractFromJson(response);
                return json;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("-->", "Error with creating URL ", e);
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("", "Error with creating connection ", e);
                return null;

            }

        }

        @Override
        protected void onPostExecute(ArrayList<data> data) {
            super.onPostExecute(data);
            addListenerOnButton();
        }

        public ArrayList<data> extractFromJson(String data) {
            ArrayList<data> list = new ArrayList<>();
            try {
                JSONObject root = new JSONObject(data);
                String bank = root.getString("BANK");
                String ifsc = root.getString("IFSC");
                String branch = root.getString("BRANCH");
                String address = root.getString("ADDRESS");
                String contact = root.getString("CONTACT");
                String city = root.getString("CITY");
                String rtgs = root.getString("RTGS");
                String district = root.getString("DISTRICT");
                String state = root.getString("STATE");
                data d1 = new data(bank, branch, ifsc, address, contact, city, rtgs, district, state);
                list.add(d1);
                return list;

            } catch (JSONException e) {
                e.printStackTrace();


                Log.e("", "problem", e);

            }
            return null;
        }
    }
}



