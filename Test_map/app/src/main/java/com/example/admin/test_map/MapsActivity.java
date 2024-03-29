package com.example.admin.test_map;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity  {
    GoogleMap mMap;
    Marker mMarker;
    LocationManager lm;
    double lat, lng;
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mMap = ((SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();
    }

    LocationListener listener = new LocationListener() {
        public void onLocationChanged(Location loc) {
            LatLng coordinate = new LatLng(loc.getLatitude()
                    , loc.getLongitude());
            lat = loc.getLatitude();
            lng = loc.getLongitude();

            if(mMarker != null)
                mMarker.remove();

            mMarker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lng)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    coordinate, 16));
        }

        public void onStatusChanged(String provider, int status
                , Bundle extras) {}
        public void onProviderEnabled(String provider) {}
        public void onProviderDisabled(String provider) {}
    };

    public void onResume() {
        super.onResume();

        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        boolean isNetwork =
                lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isGPS =
                lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(isNetwork) {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER
                    , 5000, 10, (android.location.LocationListener) listener);
            Location loc = lm.getLastKnownLocation(
                    LocationManager.NETWORK_PROVIDER);
            if(loc != null) {
                lat = loc.getLatitude();
                lng = loc.getLongitude();
            }
        }

        if(isGPS) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER
                    , 5000, 10, (android.location.LocationListener) listener);
            Location loc = lm.getLastKnownLocation(
                    LocationManager.GPS_PROVIDER);
            if(loc != null) {
                lat = loc.getLatitude();
                lng = loc.getLongitude();
            }
        }
    }

    public void onPause() {
        super.onPause();
        lm.removeUpdates((android.location.LocationListener) listener);
    }
}