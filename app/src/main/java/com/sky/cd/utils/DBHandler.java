package com.sky.cd.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.sky.cd.model.Constants;
import com.sky.cd.model.Restaurants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by skytreasure on 13/11/15.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RestaurantsDB";
    private static final String TABLE_RESTAURANTS = "RESTAURANTS";

   public DBHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RESTAURANTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_RESTAURANTS + "("
                + Constants.OUTLET_ID + " INTEGER PRIMARY KEY,"
                + Constants.OUTLET_NAME + " TEXT,"
                + Constants.BRAND_ID + " TEXT,"
                + Constants.ADDRESS + " TEXT,"
                + Constants.SUB_FRANCHISE_ID + " TEXT,"
                + Constants.NEIGHBOURHOOD_ID + " TEXT,"
                + Constants.CITY_ID + " TEXT,"
                + Constants.EMAIL + " TEXT,"
                + Constants.TIMINGS + " TEXT,"
                + Constants.CITY_RANK + " TEXT,"
                + Constants.LATITUDE + " TEXT,"
                + Constants.LONGITUDE + " TEXT,"
                + Constants.PINCODE + " TEXT,"
                + Constants.LANDMARK + " TEXT,"
                + Constants.STREETNAME + " TEXT,"
                + Constants.OUTLET_URL + " TEXT,"
                + Constants.NUM_COUPONS + " TEXT,"
                + Constants.NEIGHBOURHOOD_NAME + " TEXT,"
                + Constants.PHONE_NUMBER + " TEXT,"
                + Constants.CITY_NAME + " TEXT,"
                + Constants.DISTANCE + " TEXT,"
                + Constants.CATEGORY_TYPE + " TEXT,"
                + Constants.CATEGORY_NAME + " TEXT,"
                + Constants.LOGO_URL + " TEXT,"
                + Constants.COVER_URL + " TEXT" + ")";
        db.execSQL(CREATE_RESTAURANTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);
        onCreate(db);
    }


    /**
     * Insert Restaurants into table
     *
     * @param res
     * @return boolean
     */
    public Boolean insertRestaurants(Restaurants res) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues requestDlsValues = new ContentValues();
            requestDlsValues.put(Constants.OUTLET_ID, res.getOutletID());
            requestDlsValues.put(Constants.OUTLET_NAME, res.getOutletName());
            requestDlsValues.put(Constants.BRAND_ID, res.getBrandID());
            requestDlsValues.put(Constants.ADDRESS, res.getAddress());
            requestDlsValues.put(Constants.SUB_FRANCHISE_ID, res.getSubFranchiseID());
            requestDlsValues.put(Constants.NEIGHBOURHOOD_ID, res.getNeighbourhoodID());
            requestDlsValues.put(Constants.CITY_ID, res.getCityID());
            requestDlsValues.put(Constants.EMAIL, res.getEmail());
            requestDlsValues.put(Constants.TIMINGS, res.getTimings());
            requestDlsValues.put(Constants.CITY_RANK, res.getCityRank());
            requestDlsValues.put(Constants.LATITUDE, res.getLatitude());
            requestDlsValues.put(Constants.LONGITUDE, res.getLongitude());
            requestDlsValues.put(Constants.PINCODE, res.getPincode());
            requestDlsValues.put(Constants.LANDMARK, res.getLandmark());
            requestDlsValues.put(Constants.STREETNAME, res.getStreetname());
            requestDlsValues.put(Constants.OUTLET_URL, res.getOutletURL());
            requestDlsValues.put(Constants.NUM_COUPONS, res.getNumCoupons());
            requestDlsValues.put(Constants.NEIGHBOURHOOD_NAME, res.getNeighbourhoodName());
            requestDlsValues.put(Constants.PHONE_NUMBER, res.getPhoneNumber());
            requestDlsValues.put(Constants.CITY_NAME, res.getCityName());
            requestDlsValues.put(Constants.DISTANCE, res.getDistance());
            List<Restaurants.Categories> cats = res.getCategories();
            String cat_type = "";
            String cat_name = "";
            for (Restaurants.Categories cat : cats) {
                cat_type += cat.getCategoryType() + ",";
                cat_name += cat.getName() + ",";
            }
            requestDlsValues.put(Constants.CATEGORY_NAME, cat_name);
            requestDlsValues.put(Constants.CATEGORY_TYPE, cat_type);
            requestDlsValues.put(Constants.LOGO_URL, res.getLogoURL());
            requestDlsValues.put(Constants.COVER_URL, res.getCoverURL());

            db.insertWithOnConflict(TABLE_RESTAURANTS, null, requestDlsValues, SQLiteDatabase.CONFLICT_IGNORE);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Returns all restaurants without sorting
     * @return
     */
    public List<Restaurants> GetRestaurantDetails() {
        try {
            List<Restaurants> restaurants = new ArrayList<Restaurants>();
            SQLiteDatabase db = this.getWritableDatabase();

            String selectQuery = "SELECT * " +
                    " FROM " + TABLE_RESTAURANTS;


            Cursor cursor = db.rawQuery(selectQuery, null);

            return getRestaurantsByCursor(cursor);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  Returns the number of rows present in Restaurant table
     *
     * @return long
     */
    public long checkRowExistOrNot() {
        long count;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT COUNT(*) FROM " + TABLE_RESTAURANTS;
            SQLiteStatement statement = db.compileStatement(selectQuery);
            count = statement.simpleQueryForLong();

            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Gets the restaurants present in that city if available in database
     * @param city
     * @return List<Restaurants>
     */
    public List<Restaurants> GetRestaurantDetailsByCity(String city) {
        try {
            List<Restaurants> restaurants = new ArrayList<Restaurants>();
            SQLiteDatabase db = this.getWritableDatabase();

            String selectQuery = "SELECT * " +
                    " FROM RESTAURANTS WHERE "+Constants.CITY_NAME+" ='" +city +"'";


            Cursor cursor = db.rawQuery(selectQuery, null);

            return getRestaurantsByCursor(cursor);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sorts the Restaurants based on number of coupons
     * @return List<Restaurants>
     */
    public List<Restaurants> GetRestaurantsByOffer() {
        try {
            List<Restaurants> restaurants = new ArrayList<Restaurants>();
            SQLiteDatabase db = this.getWritableDatabase();

            String selectQuery = "SELECT * " +
                    " FROM RESTAURANTS ORDER BY "+Constants.NUM_COUPONS+" desc ";


            Cursor cursor = db.rawQuery(selectQuery, null);

            return getRestaurantsByCursor(cursor);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gives list of restaurants sorted in ascending order by distance
     * @return List<Restaurants>
     */
    public List<Restaurants> GetRestaurantsByDistance() {
        try {
            List<Restaurants> restaurants = new ArrayList<Restaurants>();
            SQLiteDatabase db = this.getWritableDatabase();

            String selectQuery = "SELECT * " +
                    " FROM RESTAURANTS ORDER BY "+Constants.DISTANCE +" asc ";


            Cursor cursor = db.rawQuery(selectQuery, null);

            return getRestaurantsByCursor(cursor);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the restaurants from cursor
     * @param cursor
     * @return List<Restaurants>
     */
    private List<Restaurants> getRestaurantsByCursor(Cursor cursor){
        List<Restaurants> restaurants = new ArrayList<Restaurants>();
        if (cursor.moveToFirst()) {
            do {
                Restaurants res = new Restaurants();
                res.setOutletName(cursor.getString(cursor.getColumnIndex(Constants.OUTLET_NAME)));
                res.setNeighbourhoodName(cursor.getString(cursor.getColumnIndex(Constants.NEIGHBOURHOOD_NAME)));
                res.setDistance(cursor.getDouble(cursor.getColumnIndex(Constants.DISTANCE)));
                res.setNumCoupons(cursor.getInt(cursor.getColumnIndex(Constants.NUM_COUPONS)));
                res.setLogoURL(cursor.getString(cursor.getColumnIndex(Constants.LOGO_URL)));
                String categories=cursor.getString(cursor.getColumnIndex(Constants.CATEGORY_NAME));
                String[] cat_list=categories.split(",");
                List<Restaurants.Categories> categories_list=new ArrayList<Restaurants.Categories>();
                Arrays.asList(cat_list);
                for(String c: cat_list){
                    Restaurants.Categories cat_temp=new Restaurants().new Categories();
                    cat_temp.setName(c);
                    categories_list.add(cat_temp);
                }
                res.setCategories(categories_list);


                restaurants.add(res);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return restaurants;
    }

}
