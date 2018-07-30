package com.example.hitesh054.fragment.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hitesh054.fragment.FCM.Config;
import com.example.hitesh054.fragment.Listeners.ResultResponse;
import com.example.hitesh054.fragment.Models.Route;
import com.example.hitesh054.fragment.R;
import com.example.hitesh054.fragment.ServerConnection.DataParser.DriverDisplayParser;
import com.example.hitesh054.fragment.Utils.DirectionsJSONParser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class userMapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener, ResultResponse {

    private GoogleMap mMap;

    Location mLastLocation;
    Marker mCurrLocationMarker;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    JSONObject jsonObject;
    ArrayList markerPoints= new ArrayList();
    Marker marker1,marker2;
    List<MarkerOptions> markers = new ArrayList<>();
    DriverDisplayParser driverDisplayParser = new DriverDisplayParser();
    TextView tv_Name;
    TextView call;
    String name,phone_no;
    JSONObject json_data;
    SpannableString content;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_maps);
        tv_Name=(TextView)findViewById(R.id.tv_name);
        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
         content = new SpannableString("Content");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        Bitmap mbitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.driver1)).getBitmap();
        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 80, 80, mpaint);// Round Image Corner 100 100 100 100
        imageView.setImageBitmap(imageRounded);
        driverDisplayParser.result=this;
        new DriverDisplayParser(getApplicationContext(), "56",driverDisplayParser.result).start();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
//                    try {
//
////                        setMarker(jsonObject.getString("latitude"), jsonObject.getString("longitude"));
//                    }
//                    catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                    String message = intent.getStringExtra("latlong");
                    try {
                         jsonObject = new JSONObject(message);
                        Toast.makeText(getApplicationContext(), "lat : " + jsonObject.getString
                                ("latitude") + " Long :" + jsonObject.getString("longitude"), Toast.LENGTH_LONG).show();
                        drawLine(jsonObject.getString("latitude"),jsonObject.getString
                                ("longitude"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    txtMessage.setText(message);
                }
            }
        };

         call = (TextView) findViewById(R.id.caller);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNo = call.getText().toString();
                String dial = "tel:" + phoneNo;
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void returnResult(String output) {
//        Toast.makeText(getApplicationContext(), "" +  json_data, Toast.LENGTH_SHORT).show();

        JSONObject jObj = null;
        try {
            jObj = new JSONObject(output);
            JSONArray jsonArray = jObj.getJSONArray("Driver");
           json_data = jsonArray.getJSONObject(0);
            name=json_data.getString("Name");
            phone_no=json_data.getString("PhoneNo");
            tv_Name.setText(name);
            tv_Name.setPaintFlags(tv_Name.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            call.setText(phone_no);
            call.setPaintFlags(call.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }





    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));
    }

    void drawLine(String latitude,String longitude)
    {
        LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        Marker tempMarker=null;
        LatLng firstLatLong;//=new LatLng(232,656);
        //LatLng secondLatLong=new LatLng(232,656);

        if (markerPoints.size()==2) {
            firstLatLong=(LatLng) markerPoints.get(1);
            markerPoints.clear();
            markerPoints.add(firstLatLong);
            markerPoints.add(latLng);

            //mMap.clear();
        }
        else
        {
            markerPoints.add(latLng);
        }



        // Creating MarkerOptions
        MarkerOptions options = new MarkerOptions();

        // Setting the position of the marker
        options.position(latLng);

        if (markerPoints.size() == 1) {
            markers.add(options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            // Add new marker to the Google Map Android API V2
           marker1= mMap.addMarker(markers.get(0));
        } else if (markerPoints.size() == 2) {
            if(markers.size()==2)
            {
                markers.remove(1);
                marker2.remove();
            }
            markers.add(options.icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));
            // Add new marker to the Google Map Android API V2

            marker2=mMap.addMarker(markers.get(1));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((LatLng) markerPoints.get(1), 16));
        }
        
        // Checks, whether start and end locations are captured
        if (markerPoints.size() == 2) {
            LatLng origin = (LatLng) markerPoints.get(0);
            LatLng dest = (LatLng) markerPoints.get(1);

            // Getting URL to the Google Directions API
            String url = getDirectionsUrl(origin, dest);

            DownloadTask downloadTask = new DownloadTask();

            // Start downloading json data from Google Directions API
            downloadTask.execute(url);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng start = new LatLng(16.696675, 74.242263);

        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.json_maps));

            if (!success) {

            }
        } catch (Resources.NotFoundException e) {

        }

        MarkerOptions markerOptions = new MarkerOptions();
        LatLng school = new LatLng(16.7044, 74.2487);
        markerOptions.position(school);
        markerOptions.title("Seventh Day Adventist School");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.school));
        googleMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(school, 16));


    }


    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }


    }


    public class DownloadTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);

        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.BLACK);
                lineOptions.geodesic(true);

            }

// Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
    public void rotateMarker(final Marker marker, final float toRotation, final float st) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final float startRotation = st;
        final long duration = 1555;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed / duration);

                float rot = t * toRotation + (1 - t) * startRotation;

                marker.setRotation(-rot > 180 ? rot / 2 : rot);
                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });
    }


    public void animateMarker(final LatLng toPosition,final boolean hideMarke) {

        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = mMap.getProjection();
        Point startPoint = proj.toScreenLocation( mCurrLocationMarker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 5000;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                mCurrLocationMarker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarke) {
                        mCurrLocationMarker.setVisible(false);
                    } else {
                        mCurrLocationMarker.setVisible(true);
                    }
                }
            }
        });
    }

}
