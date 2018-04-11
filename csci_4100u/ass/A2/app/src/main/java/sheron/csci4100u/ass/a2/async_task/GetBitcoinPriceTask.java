package sheron.csci4100u.ass.a2.async_task;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetBitcoinPriceTask extends AsyncTask<String, Void, String> {

    private BitcoinPriceObserver bitcoinPriceObserver;

    public void setBitcoinPriceObserver(BitcoinPriceObserver bitcoinPriceObserver) {
        this.bitcoinPriceObserver = bitcoinPriceObserver;
    }

    @Override
    protected String doInBackground(String... urls) {
        String bitcoinValue = "";

        try {
            // connect to the website
            URL url = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // download the data
            InputStream inputRaw = conn.getInputStream();
            BufferedReader input = new BufferedReader(new InputStreamReader(inputRaw));

            // Store data in String licenseData
            bitcoinValue = input.readLine();

            // Close connection
            inputRaw.close();
            conn.disconnect();
        } catch (IOException io) {
            io.printStackTrace();
        }

        return bitcoinValue;
    }

    @Override
    protected void onPostExecute(String bitcoinValue) {

        // send back new data
        bitcoinPriceObserver.bitcoinValueReceived(bitcoinValue);
    }
}