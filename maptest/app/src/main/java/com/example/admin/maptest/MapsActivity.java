package com.example.admin.maptest;


import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity {

    /*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */

    double lat, lng;
    Marker mMarker;
    LocationManager lm;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

    }

    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            LatLng coordinate = new LatLng(location.getLatitude()
                    , location.getLongitude());
            lat = location.getLatitude();
            lng = location.getLongitude();

            if (mMarker != null)
                mMarker.remove();
            mMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("I'am here"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 10));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void onResume(){
        super.onResume();
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isNetwork = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isGPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isNetwork){
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER
                    , 5000, 10, listener);
            Location loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (loc != null){
                lat = loc.getLatitude();
                lng = loc.getLongitude();
            }
        }
        if (isGPS){
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,10,listener);
            Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(loc != null){
                lat = loc.getLatitude();
                lng = loc.getLongitude();
            }
        }

    }
    public void onPause(){
        super.onPause();
        lm.removeUpdates(listener);
    }
    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
       // mMap.addMarker(new MarkerOptions().position(new LatLng(15.850000, 104.633333)).title("Marker"));
    }

}