package sheron.csci4100u.labs.lab8;

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
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ShowLocation extends AppCompatActivity implements LocationListener {

    private static final int REQUEST_GEOLOCATION_PERMS = 1;
    private LocationManager locationManager;

    EditText address1,
             address2,
             locality,
             adminArea,
             countryName,
             postalCode,
             phoneNumber,
             url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity);

        address1    = (EditText) findViewById(R.id.etAddress1);
        address2    = (EditText) findViewById(R.id.etAddress2);
        locality    = (EditText) findViewById(R.id.etLocality);
        adminArea   = (EditText) findViewById(R.id.etAdminArea);
        countryName = (EditText) findViewById(R.id.etCountry);
        postalCode  = (EditText) findViewById(R.id.etPostalCode);
        phoneNumber = (EditText) findViewById(R.id.etPhoneNum);
        url         = (EditText) findViewById(R.id.etUrl);

        verifyGeolocationPermission();
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

                // Note:  startActivityForResult() may be better here
            }
        }
    }


    private void requestLocationUpdates() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(false);

        String recommendedProvider = locationManager.getBestProvider(criteria,
                                                                     true);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(recommendedProvider,
                                                   3000,
                                                   1,
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
        Address address;

        // geocode the result - get the location name
        address = geocode(location.getLatitude(), location.getLongitude());
        updateUI(address);
    }


    private void updateUI(Address address) {
        if (address != null) {
            Toast.makeText(this, address.getAddressLine(0), Toast.LENGTH_SHORT).show();
            address1.setText(address.getAddressLine(0));
            address2.setText(address.getAddressLine(1));
            locality.setText(address.getLocality());
            adminArea.setText(address.getAdminArea());
            countryName.setText(address.getCountryName());
            postalCode.setText(address.getPostalCode());
            phoneNumber.setText(address.getPhone());
            url.setText(address.getUrl());
        }
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
