package com.example.hitesh054.fragment.Fragment;

/**
 * Created by hitesh054 on 06-06-2018.
 */
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hitesh054.fragment.Activity.userMapsActivity;
import com.example.hitesh054.fragment.Adapter.Adapter;
import com.example.hitesh054.fragment.Listeners.ResultResponse;
import com.example.hitesh054.fragment.R;
import com.example.hitesh054.fragment.Listeners.RecyclerTouchListener;
import com.example.hitesh054.fragment.Models.Route;
import com.example.hitesh054.fragment.ServerConnection.DataParser.RouteDataParser;
import com.example.hitesh054.fragment.Session.SessionManagement;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class fragment_route extends Fragment implements  ResultResponse {
    private List<Route> RouteList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Adapter mAdapter;
    SessionManagement session;
    RouteDataParser routeDataParser = new RouteDataParser();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View myView = inflater.inflate(R.layout.fragment_route, container, false);
        routeDataParser.result = this;
        new RouteDataParser(getActivity(), routeDataParser.result).start();

        recyclerView = (RecyclerView) myView.findViewById(R.id.recycler_view);

        mAdapter = new Adapter(RouteList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        // adding inbuilt divider line
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Route route = RouteList.get(position);
                Toast.makeText(getActivity(), route.getName() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(getActivity(), userMapsActivity.class);
                getActivity().startActivity(myIntent);

//                String topic=session.getTopic();
                for (Route r : RouteList) {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(r.getName());
                }

                FirebaseMessaging.getInstance().subscribeToTopic(route.getName());
//                    session.setTopic(route.getName());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        return myView;
    }


    /**
     * Prepares sample data to provide data set to adapter
     */


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void returnResult(String output) {
//        Toast.makeText(getContext(), "" + output, Toast.LENGTH_SHORT).show();


        JSONObject jObj = null;
        try {
            jObj = new JSONObject(output);

            JSONArray jsonArray = jObj.getJSONArray("Routes");


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json_data = jsonArray.getJSONObject(i);
                Route route = new Route();
                route = new Route(json_data.getString("Route_id"),
                        json_data.getString("RouteName"),
                        json_data.getString("Driver_id"));

                RouteList.add(route);
                mAdapter.notifyDataSetChanged();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}



