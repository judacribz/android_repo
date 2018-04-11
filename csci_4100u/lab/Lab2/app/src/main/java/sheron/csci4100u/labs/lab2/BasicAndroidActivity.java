package sheron.csci4100u.labs.lab2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BasicAndroidActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
    }
}