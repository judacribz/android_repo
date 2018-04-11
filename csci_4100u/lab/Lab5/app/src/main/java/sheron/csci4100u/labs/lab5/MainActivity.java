package sheron.csci4100u.labs.lab5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity implements LicenseObserver {

    public static final String EXTRA_LICENSE = "sheron.csci4100u.labs.lab5.EXTRA_LICENSE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void showLicense(View source) {
        // Get url of the online file
        String url = getResources().getString(R.string.gnu_license_file);

        // Initiate the download (AsyncTask)
        GetLicenseTask task = new GetLicenseTask();
        task.setLicenseObserver(this);
        task.execute(url);
    }

    public void licenseDataUpdated(String licenseData) {
        // Sends data downloaded from GetLicenseTask and sends it to ShowLicense Activity
        Intent showLicenseIntent = new Intent(this, ShowLicense.class);
        showLicenseIntent.putExtra(EXTRA_LICENSE, licenseData);
        startActivity(showLicenseIntent);
    }
}
