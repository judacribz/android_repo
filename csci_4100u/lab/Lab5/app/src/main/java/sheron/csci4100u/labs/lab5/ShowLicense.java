package sheron.csci4100u.labs.lab5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import static sheron.csci4100u.labs.lab5.MainActivity.EXTRA_LICENSE;

public class ShowLicense extends AppCompatActivity {

    TextView tvLicenseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_license);

        tvLicenseData = (TextView) findViewById(R.id.tvLicenseData);

        // Sets textview to string received by mainActivity
        Intent licenseDataIntent = getIntent();
        tvLicenseData.setText(licenseDataIntent.getStringExtra(EXTRA_LICENSE));
    }

    public void closeActivity(View view) {
        finish();
    }
}
