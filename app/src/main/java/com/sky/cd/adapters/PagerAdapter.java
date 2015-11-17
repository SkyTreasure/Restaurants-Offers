package com.sky.cd.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sky.cd.fragments.BestOffers;
import com.sky.cd.fragments.InYourCity;
import com.sky.cd.fragments.NearBy;

/**
 * Created by skytreasure on 16/11/15.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                InYourCity in=new InYourCity();
                return in;
            case 1:
                NearBy nb=new NearBy();
                return nb;
            case 2:
                BestOffers bo=new BestOffers();
                return bo;
            default :
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
