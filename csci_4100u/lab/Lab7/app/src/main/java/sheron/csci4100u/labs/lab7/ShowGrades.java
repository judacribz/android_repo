package sheron.csci4100u.labs.lab7;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;


public class ShowGrades extends AppCompatActivity implements View.OnClickListener{

    // Constants
    private static final int REQ_ADD_CONTACT    = 101;
    private static final int REQ_DELETE_CONTACT = 102;

    static final String EXTRA_GRADES =
            "sheron.csci4100u.labs.lab6.EXTRA_GRADES";

    boolean        returnFromActivity = false;
    ListView       listView;
    List<Grade>    grades;
    GradesDBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_grades);

        (findViewById(R.id.btnAdd)).setOnClickListener(this);
        (findViewById(R.id.btnDelete)).setOnClickListener(this);
        listView = findViewById(R.id.lvGrades);

        dbHelper = new GradesDBHelper(this);
    }


    // Btn click handler
    @Override
    public void onClick(View v) {
        Intent intent = null;
        int requestCode = -1;

        switch (v.getId()) {
            // Start AddGrade activity
            case R.id.btnAdd:
                intent = new Intent(this, AddGrade.class);
                requestCode = REQ_ADD_CONTACT;
                break;

            // Start DeleteGrade activity
            case R.id.btnDelete:
                intent = new Intent(this, DeleteGrade.class);
                requestCode = REQ_DELETE_CONTACT;
                break;
        }

        if (requestCode != -1) {
            startActivityForResult(intent, requestCode);
        }
    }


    // Read from file in onStart if grades list is empty and set the list
    // adapter
    @Override
    protected void onStart() {
        super.onStart();

        grades = dbHelper.getAllGrades();
        listView.setAdapter(new GradeAdapter(this, grades));
    }


    // Return from sub-activities handler
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent gradeInfo) {
        if (resultCode == RESULT_OK) {
            returnFromActivity = true;

            switch (requestCode) {

                // Return from AddGrade Activity
                case REQ_ADD_CONTACT:
                    break;

                // Return from DeleteGrade Activity
                case REQ_DELETE_CONTACT:
                    break;
            }
        } else {
            Toast.makeText(this, "No grades added or deleted", Toast.LENGTH_SHORT).show();
            returnFromActivity = false;
        }
    }
}

