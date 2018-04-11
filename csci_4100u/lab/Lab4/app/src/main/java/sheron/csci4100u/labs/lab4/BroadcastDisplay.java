package sheron.csci4100u.labs.lab4;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.widget.Button;

import static sheron.csci4100u.labs.lab4.UtilFunctions.*;


public class BroadcastDisplay extends AppCompatActivity {

    Button bShowStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_display);

        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        BatteryReceiver receiver = new BatteryReceiver();

        registerReceiver(receiver, filter);

        // Shows last recorded status
        bShowStatus = (Button) findViewById(R.id.show_status_b);
        bShowStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyStatus(getApplicationContext(), status);
            }
        });



    }
}
