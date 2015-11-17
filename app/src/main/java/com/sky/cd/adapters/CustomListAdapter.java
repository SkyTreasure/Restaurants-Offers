package com.sky.cd.adapters;

/**
 * Created by skytreasure on 16/11/15.
 */
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.sky.cd.R;
import com.sky.cd.app.AppController;
import com.sky.cd.model.Restaurants;

public class CustomListAdapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<Restaurants> restaurantItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Context context, List<Restaurants> restaurantItems) {
        inflater = LayoutInflater.from(context);
        this.restaurantItems = restaurantItems;
    }

    @Override
    public int getCount() {
        int i=0;
        try{
            i=restaurantItems.size();
        }catch(Exception e){

        }
        return i;
    }

    @Override
    public Object getItem(int location) {
        return restaurantItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView outlet_name = (TextView) convertView.findViewById(R.id.outletname);
        TextView distance = (TextView) convertView.findViewById(R.id.distance);
        TextView categories = (TextView) convertView.findViewById(R.id.categories);
        TextView neighbourhood = (TextView) convertView.findViewById(R.id.neighbour);
        TextView coupons = (TextView) convertView.findViewById(R.id.coupons);

        Restaurants m = restaurantItems.get(position);

        thumbNail.setImageUrl(m.getLogoURL(), imageLoader);

        outlet_name.setText(m.getOutletName());

        neighbourhood.setText( String.valueOf(m.getNeighbourhoodName()));

        String categories_temp = "";
        for (Restaurants.Categories category : m.getCategories()) {
            categories_temp += category.getName() + ", ";
        }

        categories_temp = categories_temp.length() > 0 ? categories_temp.substring(0,
                categories_temp.length() - 2) : categories_temp;
        categories.setText(categories_temp);

        distance.setText( String.format("%.2f", m.getDistance())+" km");

        coupons.setText(String.valueOf(m.getNumCoupons()+" offers"));

        return convertView;
    }

}
