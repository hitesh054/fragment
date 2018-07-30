package com.example.hitesh054.fragment.ServerConnection.ClientDataInterfaces;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by wolfox on 17/5/2018.
 */

public interface PostDataInterface
{
    @FormUrlEncoded
    @POST("store_lat_long.php")
    Call<JsonObject> sendLatLong(@Field("driverid") String driverid,
                                 @Field("routeid") String routeid,
                                 @Field("latitude") String latitude,
                                 @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("welcome/checkLoginAndroid")
    Call<JsonObject> getLatestMatchData(@Field("username") String Email_id,
                                        @Field("password") String Password);

    @FormUrlEncoded
    @POST("welcome/Driver_Insert")
    Call<JsonObject> sendDriverData(@Field("Name") String Name ,
                                 @Field("PhoneNo") String PhoneNo,
                                 @Field("AdharNo") String AdharNo);

    @FormUrlEncoded
    @POST("welcome/User_Insert")
    Call<JsonObject> sendUserData(@Field("Name") String Name ,
                                    @Field("PhoneNo") String PhoneNo,
                                    @Field("Email_id") String Email_id);
    @FormUrlEncoded
    @POST("welcome/Display_Route")
    Call<JsonObject> DisplayData(@Field("Name") String UserId);

    @FormUrlEncoded
    @POST("welcome/Display_driver")
    Call<JsonObject> DisplayDriver(@Field("SerialNo") String SerialNo);

//
//    @FormUrlEncoded
//    @POST("login/loadLatestMatches")
//    Call<JsonObject> loadMoreData(@Field("userId") String userId, @Field("username") String username, @Field("password") String password);
//
//    @FormUrlEncoded
//    @POST("login/viewLatestUpdatedProfileData")
//    Call<JsonObject> getLatestUpgradedData(@Field("userId") String userId, @Field("username") String username, @Field("password") String password);

}
