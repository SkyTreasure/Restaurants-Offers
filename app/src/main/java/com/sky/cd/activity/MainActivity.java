package com.sky.cd.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sky.cd.R;
import com.sky.cd.adapters.PagerAdapter;
import com.sky.cd.app.AppController;
import com.sky.cd.model.Constants;
import com.sky.cd.model.Restaurants;
import com.sky.cd.utils.DBHandler;
import com.sky.cd.utils.GPSTracker;
import com.sky.cd.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
/**
 * Created by skytreasure on 15/11/15.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RequestQueue requestQueue;
    public static String jsonurl = "http://staging.couponapitest.com/task_data.txt";
    public static List<Restaurants> restaurants;

    public static Double myLatitude;
    public static Double myLongitude;
    public static String myCity;

    DBHandler db;
    GPSTracker gpstracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHandler(this);


        gpstracker = new GPSTracker(this, this);


        if (!Util.isNetworkAvailable(getApplicationContext())) {
            Toast.makeText(this, "Please switch on Internet for Updated data",
                    Toast.LENGTH_LONG).show();

        } else {
            downloadRestaurantsData();
        }
        SharedPreferences sp = getSharedPreferences("details", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        try {
            if (gpstracker.canGetLocation()) {
                myLatitude = gpstracker.getLatitude();
                myLongitude = gpstracker.getLongitude();
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());

                addresses = geocoder.getFromLocation(myLatitude, myLongitude, 1);

                if (myLongitude != 0.0)
                    myCity = addresses.get(0).getLocality();
            } else {
                gpstracker.showSettingsAlert();

            }


        } catch (IOException e) {
            Log.e("", "");
        }

        editor.putString("lat", String.valueOf(myLatitude));
        editor.putString("lng", String.valueOf(myLongitude));
        editor.putString("city", myCity);
        editor.commit();

        //Toast.makeText(this, "Latitude:" + myLatitude + " Longitude:" + myLongitude + "City:" + myCity, Toast.LENGTH_LONG).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        TabLayout tablayout = (TabLayout) findViewById(R.id.tab_layout);
        tablayout.addTab(tablayout.newTab().setText(Constants.TAB_IN_YOUR_CITY));
        tablayout.addTab(tablayout.newTab().setText(Constants.TAB_NEAR_BY));
        tablayout.addTab(tablayout.newTab().setText(Constants.TAB_BEST_OFFERS));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewpager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter pageadapter = new PagerAdapter(getSupportFragmentManager(), tablayout.getTabCount());
        viewpager.setAdapter(pageadapter);
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onResume() {

        super.onResume();

    }

    private void downloadRestaurantsData() {
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, jsonurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            restaurants = new ArrayList<Restaurants>();
                            int[] skip = new int[]{4, 5, 18, 19, 23, 25, 31};
                            JSONObject dataobject = response.getJSONObject("data");
                            for (int i = 0; i < 36; i++) {
                                if (!(Arrays.binarySearch(skip, i) >= 0)) {

                                    JSONObject res = dataobject.getJSONObject(String.valueOf(i));

                                    restaurants.add(jsonToRestaurant(res));
                                    db.insertRestaurants(jsonToRestaurant(res));
                                }


                            }
                            Log.e("", "" + restaurants);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }


                }
        );
        requestQueue.add(jor);
        AppController.getInstance().addToRequestQueue(jor);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Action Not Implemented", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.action_search) {
            Toast.makeText(this, "Action Not Implemented", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.action_filter) {
            Toast.makeText(this, "Action Not Implemented", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.action_refresh) {
            finish();
            Intent intent = getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.twitter) {
            String url = "http://www.twiiter.com/SkyTreasure";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } else if (id == R.id.instagram) {
            String url = "http://www.instagram.com/SkyTreasure";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        } else if (id == R.id.github) {
            String url = "http://www.github.com/SkyTreasure";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        } else if (id == R.id.linkedin) {
            String url = "https://in.linkedin.com/in/akash-nidhi-p-s-8743143a";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * This method saves the JSon object to Pojo class
     * @param res
     * @return
     * @throws JSONException
     */
    private Restaurants jsonToRestaurant(JSONObject res) throws JSONException {
        Restaurants rest = new Restaurants();

        rest.setSubFranchiseID(res.getString(Constants.SUB_FRANCHISE_ID));
        rest.setOutletID(res.getString(Constants.OUTLET_ID));
        rest.setOutletName(res.getString(Constants.OUTLET_NAME));
        rest.setBrandID(res.getString(Constants.BRAND_ID));
        rest.setAddress(res.getString(Constants.ADDRESS));
        rest.setNeighbourhoodID(res.getString(Constants.NEIGHBOURHOOD_ID));
        rest.setCityID(res.getString(Constants.CITY_ID));
        rest.setEmail(res.getString(Constants.EMAIL));
        rest.setTimings(res.getString(Constants.TIMINGS));
        rest.setCityRank(res.getString(Constants.CITY_RANK));
        rest.setLatitude(res.getString(Constants.LATITUDE));
        rest.setLongitude(res.getString(Constants.LONGITUDE));
        rest.setPincode(res.getString(Constants.PINCODE));
        rest.setLandmark(res.getString(Constants.LANDMARK));
        rest.setStreetname(res.getString(Constants.STREETNAME));
        rest.setBrandName(res.getString(Constants.BRANDNAME));
        rest.setOutletURL(res.getString(Constants.OUTLET_URL));
        rest.setNumCoupons(res.getInt(Constants.NUM_COUPONS));
        rest.setNeighbourhoodName(res.getString(Constants.NEIGHBOURHOOD_NAME));
        rest.setPhoneNumber(res.getString(Constants.PHONE_NUMBER));
        rest.setCityName(res.getString(Constants.CITY_NAME));
        rest.setDistance(res.getDouble(Constants.DISTANCE));
        rest.setLogoURL(res.getString(Constants.LOGO_URL));
        rest.setCoverURL(res.getString(Constants.COVER_URL));

        List<Restaurants.Categories> categories = new ArrayList<Restaurants.Categories>();
        JSONArray cat_array = res.getJSONArray(Constants.CATEGORY);
        for (int i = 0; i < cat_array.length(); i++) {
            JSONObject jobj = cat_array.getJSONObject(i);
            Restaurants.Categories cat = new Restaurants().new Categories();
            cat.setCategoryType(jobj.getString(Constants.CATEGORY_TYPE));
            cat.setName(jobj.getString(Constants.CATEGORY_NAME));
            cat.setOfflineCategoryID(jobj.getString(Constants.OFFLINE_CATEGORY_ID));
            cat.setParentCategoryID(jobj.getString(Constants.PARENT_CATEGORY_ID));
            categories.add(cat);
        }
        rest.setCategories(categories);
        setLocationDistance(rest);

        return rest;
    }


    /**
     *  Method to update the distance between current location and restaurant's location
     * @param res
     */
    private void setLocationDistance(Restaurants res) {
        try {
            Location l1 = new Location("MyCurrentLocation");
            l1.setLatitude(myLatitude);
            l1.setLongitude(myLongitude);

            Location l2 = new Location("Restaurant's Location");
            l2.setLatitude(Double.parseDouble(res.getLatitude()));
            l2.setLongitude(Double.parseDouble(res.getLongitude()));

            float distance_bw_one_and_two = l1.distanceTo(l2);
            double distance = distance_bw_one_and_two / 1000;

            res.setDistance(distance);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * This is used only for Marshmellow to handle permission granted event
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 01: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    finish();
                    startActivity(getIntent());

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }


        }
    }




}
