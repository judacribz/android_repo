package sheron.csci4100u.assignments.a1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static sheron.csci4100u.assignments.a1.MainMenu.EXTRA_QUESTION;


public class AskQuestion extends AppCompatActivity implements
                                                   View.OnClickListener {

    final static boolean YES_CHOICE   = true;
    final static boolean NO_CHOICE    = false;
    final static String  EXTRA_CHOICE =
            "sheron.csci4100u.assignments.a1.EXTRA_CHOICE";

    TextView tvQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        String question = getIntent().getStringExtra(EXTRA_QUESTION);

        tvQuestion = (TextView) findViewById(R.id.tv_question);
        tvQuestion.setText(question);
    }


    // Helper function to return true or false depending on answer choice
    private void setExtraChoice(boolean choice) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_CHOICE, choice);
        setResult(RESULT_OK, resultIntent);
        finish();
    }


    // OnClick handler
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_yes:
                setExtraChoice(YES_CHOICE);
                break;
            case R.id.btn_no:
                setExtraChoice(NO_CHOICE);
                break;
        }
    }
}
