package com.example.hitesh054.fragment.Adapter;

/**
 * Created by hitesh054 on 28-05-2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hitesh054.fragment.R;
import com.example.hitesh054.fragment.Models.Route;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Route> RouteList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,Rid,Did;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            Rid = (TextView) view.findViewById(R.id.routeid);
            Did = (TextView) view.findViewById(R.id.driverid);


        }
    }


    public Adapter(List<Route> RouteList) {
        this.RouteList = RouteList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.route_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Route route = RouteList.get(position);
        holder.name.setText(route.getName());
        holder.Rid.setText(route.getRId());
        holder.Did.setText(route.getDId());
    }

    @Override
    public int getItemCount()
    {
        return RouteList.size();
    }
}
