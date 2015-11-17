package com.sky.cd.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Util {
	public static boolean isNetworkAvailable(Context mContext) {

		ConnectivityManager cm = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			//Log.e("Network Testing", "***Available***");
			return true;
		}
		//Log.e("Network Testing", "***Not Available***");
		return false;
	}
}
