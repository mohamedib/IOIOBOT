package com.iti.ioiobot;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class gpsinitread extends Thread implements LocationListener {

	private LocationManager locationManager;
	private String provider;
	double lat;
	double lng;

	public void GpsInit(Context c) {

		// Get the location manager
		locationManager = (LocationManager) c
				.getSystemService(Context.LOCATION_SERVICE);

		// Define the criteria how to select the locatioin provider -> use
		// default
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);

		// Initialize the location fields
		if (location != null) {
			// System.out.println("Provider " + provider +
			// " has been selected.");
			onLocationChanged(location);
		} else {
			// latituteField.setText("Location not available");
			// longitudeField.setText("Location not available");
		}

	}

	@Override
	public void onLocationChanged(Location location) {
		lat = (double) (location.getLatitude());
		lng = (double) (location.getLongitude());
		Log.d("youssef : ", Double.toString(lng));
		DataSocket.SendMsg("$G:"+Double.toString(lat) + ":" + Double.toString(lng));
		//DataSocket.SendMsg("$G:29.96712262:30.90541701");
	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

}
