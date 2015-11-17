package com.sky.cd.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sky.cd.R;
import com.sky.cd.activity.MainActivity;
import com.sky.cd.adapters.CustomListAdapter;
import com.sky.cd.model.Restaurants;
import com.sky.cd.utils.DBHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InYourCity extends Fragment {


    public InYourCity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_in_your_city, container, false);
        ListView listview = (ListView) rootview.findViewById(R.id.listView_inyourcity);

        SharedPreferences sp = getActivity().getSharedPreferences("details", getActivity().MODE_PRIVATE);
        String city = sp.getString("city", "Mumbai");
        SharedPreferences.Editor edit = sp.edit();
        edit.commit();

        List<Restaurants> sorted_res = new ArrayList<Restaurants>();

        DBHandler dbh = new DBHandler(getActivity());
        sorted_res = dbh.GetRestaurantDetailsByCity(sp.getString("city", "Mumbai"));
        if (sorted_res.size() == 0) {
            TextView tv = (TextView) rootview.findViewById(R.id.norestaurantsfound);
            tv.setVisibility(View.VISIBLE);
            tv.setText("No restaurants found in " + sp.getString("city", "Mumbai"));

        } else {
            CustomListAdapter adapter = new CustomListAdapter(getActivity(), sorted_res);
            listview.setAdapter(adapter);
        }


        return rootview;
    }


}
