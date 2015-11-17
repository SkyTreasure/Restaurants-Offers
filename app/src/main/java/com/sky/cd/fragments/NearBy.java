package com.sky.cd.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.sky.cd.R;
import com.sky.cd.adapters.CustomListAdapter;
import com.sky.cd.model.Restaurants;
import com.sky.cd.utils.DBHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearBy extends Fragment {


    public NearBy() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_near_by, container, false);
        ListView listview= (ListView) rootview.findViewById(R.id.listView_nearby);

        List<Restaurants> sorted_res=new ArrayList<Restaurants>();

        DBHandler dbh=new DBHandler(getActivity());
        sorted_res= dbh.GetRestaurantsByDistance();
        if(sorted_res.size()>0) {
            CustomListAdapter adapter = new CustomListAdapter(getActivity(), sorted_res);
            listview.setAdapter(adapter);
        }else{
            TextView tv = (TextView) rootview.findViewById(R.id.switchonlocation);
            tv.setVisibility(View.VISIBLE);

            tv.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    getActivity().startActivity(intent);
                }
            });

        }
        return rootview;
    }





}
