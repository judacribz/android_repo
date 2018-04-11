package sheron.csci4100u.assignments.a1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static sheron.csci4100u.assignments.a1.MainMenu.EXTRA_NO_COUNT;
import static sheron.csci4100u.assignments.a1.MainMenu.EXTRA_YES_COUNT;


public class Summary extends AppCompatActivity {

    TextView tvYes, tvNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        tvYes = (TextView) findViewById(R.id.tv_yes_count);
        tvNo  = (TextView) findViewById(R.id.tv_no_count);

        Intent countIntent = getIntent();
        int yesCount = countIntent.getIntExtra(EXTRA_YES_COUNT, -1);
        int noCount  = countIntent.getIntExtra(EXTRA_NO_COUNT, -1);

        // Displays results of answers
        tvYes.setText(String.valueOf(yesCount));
        tvNo.setText(String.valueOf(noCount));
    }
}
