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
import com.sky.cd.adapters.CustomListAdapter;
import com.sky.cd.model.Restaurants;
import com.sky.cd.utils.DBHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment class to
 */
public class BestOffers extends Fragment {


    public BestOffers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_best_offers, container, false);
        ListView listview = (ListView) rootview.findViewById(R.id.listView_bestoffers);

        List<Restaurants> sorted_res = new ArrayList<Restaurants>();

        DBHandler dbh = new DBHandler(getActivity());
        sorted_res = dbh.GetRestaurantsByOffer();
        if (sorted_res.size() > 0) {
            CustomListAdapter adapter = new CustomListAdapter(getActivity(), sorted_res);
            listview.setAdapter(adapter);
        }
        return rootview;
    }


}
