package com.example.hitesh054.fragment.ServerConnection.DataParser;

/**
 * Created by hitesh054 on 15-06-2018.
 */

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.hitesh054.fragment.Listeners.ResultResponse;
import com.example.hitesh054.fragment.ServerConnection.ApiClient;
import com.example.hitesh054.fragment.ServerConnection.ClientDataInterfaces.PostDataInterface;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by hitesh on 13/6/2018.
 */

public class DriverDataParser
{
    public ResultResponse result;
    Context context;
    String Name,PhoneNo,AdharNo;

    public DriverDataParser( ){}

    public DriverDataParser(Context context, String Name,String PhoneNo,String AdharNo,ResultResponse result ) {
        this.context=context;
        this.Name=Name;
        this.PhoneNo=PhoneNo;
        this.AdharNo=AdharNo;
        this.result=result;
    }


    public void start()
    {


        PostDataInterface getService= ApiClient.getClient().create(PostDataInterface.class);
        Call<JsonObject> call=getService. sendDriverData(Name,PhoneNo,AdharNo);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                //    parseResult(response.body().toString());
//                Toast.makeText(context, response.body().toString(), Toast.LENGTH_SHORT).show();
                result.returnResult(response.body().toString());
                Log.d("data ",response.body().toString());
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(" DriverDataParser ", t.toString());
            }
        });
    }


    void parseResult(String result) {


        if (result != null) {
            Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();

        }
    }

}
