package sheron.csci4100u.labs.lab9;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ShowLocation extends AppCompatActivity
        implements LocationListener,
        OnMapReadyCallback {

    private static final int REQUEST_GEOLOCATION_PERMS = 1;

    LocationManager locationManager;
    String recommendedProvider = "";
    SupportMapFragment mapFragment;
    double latitude, longitude;
    Address address;
    GoogleMap map;
    LatLng position;
    MarkerOptions options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        verifyGeolocationPermission();

        mapFragment.getMapAsync(this);
    }


    private void verifyGeolocationPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            String[] perms = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(perms, REQUEST_GEOLOCATION_PERMS);
        } else {
            // geolocation permission granted, so request location updates
            verifyGeolocationEnabled();
        }
    }


    private void verifyGeolocationEnabled() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager != null) {
            // check if geolocation is enabled in settings
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                requestLocationUpdates();
            } else {
                // show the settings app to let the user enable it
                String locationSettings =
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS;
                Intent enableGeoloc = new Intent(locationSettings);
                startActivity(enableGeoloc);
            }
        }
    }


    private void requestLocationUpdates() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(true);
        criteria.setSpeedRequired(true);
        criteria.setCostAllowed(false);

        recommendedProvider = locationManager.getBestProvider(criteria,
                true);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(recommendedProvider,
                    5000,
                    10,
                    this);
        }
    }


    private Address geocode(double latitude, double longitude) {
        Address address = null;
        if (Geocoder.isPresent()) {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            try {
                List<Address> results = geocoder.getFromLocation(latitude,
                        longitude,
                        1);

                if (results.size() > 0) {
                    address = results.get(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return address;
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        position = new LatLng(latitude, longitude);
        address = geocode(latitude, longitude);

        // add a marker at the specified location
        String title = "";
        String snippet = "";
        if (address != null) {
            ArrayList<String> locationInfo = new ArrayList<>();
            title = address.getAddressLine(0);
            locationInfo.add(address.getAddressLine(1));
            locationInfo.add(address.getLocality());
            locationInfo.add(address.getAdminArea());
            locationInfo.add(address.getCountryName());
            locationInfo.add(address.getPostalCode());
            locationInfo.add(address.getPhone());
            locationInfo.add(address.getUrl());

            StringBuilder stringBuilder = new StringBuilder();
            for (String s : locationInfo) {
                if (s != null) {
                    stringBuilder.append(s);
                    stringBuilder.append(", ");
                }
            }
            snippet = stringBuilder.toString();
        }
        map.clear();
        map.addMarker(options.position(position).title(title).snippet(snippet));

        // centre the map around the specified location
        map.animateCamera(CameraUpdateFactory.newLatLng(position));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        options = new MarkerOptions();

        // configure the map settings
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        // enable the zoom controls
        map.setMinZoomPreference(15.0f);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setZoomGesturesEnabled(true);
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
}