package com.example.hitesh054.fragment.ServerConnection.DataParser;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.hitesh054.fragment.ServerConnection.ApiClient;
import com.example.hitesh054.fragment.ServerConnection.ClientDataInterfaces.PostDataInterface;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by wolfox on 18/4/2018.
 */

public class LatLongDataParser
{

    Context context;
    String lati,longi;

    public LatLongDataParser(Context context, String lati, String longi) {
        this.context=context;
        this.lati=lati;
        this.longi=longi;
    }


    public void start()
    {


        PostDataInterface getService= ApiClient.getClient().create(PostDataInterface.class);
        Call<JsonObject> call=getService.sendLatLong("1","1",lati,longi);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                parseResult(response.body().toString());
                Log.d("data ",response.body().toString());
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("LatLongDataParser ", t.toString());
            }
        });
    }


    void parseResult(String result) {


        if (result != null) {
            Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();

        }
    }

}
