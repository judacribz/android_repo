package sheron.csci4100u.labs.lab5;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


class GetLicenseTask extends AsyncTask<String, Void, String> {

    private LicenseObserver licenseObserver;

    void setLicenseObserver(LicenseObserver licenseObserver) {

        this.licenseObserver = licenseObserver;
    }

    @Override
    protected String doInBackground(String... urls) {
        String licenseData = "";

        try {
            // connect to the website
            URL url = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            // download the data
            InputStream inputRaw = conn.getInputStream();
            BufferedReader input = new BufferedReader(new InputStreamReader(inputRaw));

            // Store data in String licenseData
            String line;
            while ((line = input.readLine()) != null) {
                if (line.equals("")) {
                    licenseData += "\n\n";
                }

                licenseData += line + " ";
            }

            // Close connection
            inputRaw.close();
            conn.disconnect();
        } catch (IOException io) {
            io.printStackTrace();
        }

        return licenseData;
    }

    @Override
    protected void onPostExecute(String licenseData) {

        // send back new data
        licenseObserver.licenseDataUpdated(licenseData);
    }
}
