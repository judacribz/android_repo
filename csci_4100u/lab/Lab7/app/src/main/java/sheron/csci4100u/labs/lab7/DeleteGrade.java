package sheron.csci4100u.labs.lab7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import java.util.List;


public class DeleteGrade extends AppCompatActivity implements View.OnClickListener {

    static final String EXTRA_CONTACT_ID =
            "sheron.csci4100u.labs.lab6.EXTRA_CONTACT_ID";

    List<Grade>    grades;
    GradesDBHelper dbHelper;
    Spinner        spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_grade);

        (findViewById(R.id.btnDeleteGrade)).setOnClickListener(this);
        spinner = findViewById(R.id.spnrGrades);

        dbHelper = new GradesDBHelper(this);

        // Set adapter for spinner
        grades = dbHelper.getAllGrades();
        spinner.setAdapter(new GradeAdapter(this, grades));
    }


    // Delete button handler
    @Override
    public void onClick(View v) {
        if (!grades.isEmpty()) {
            Intent deleteContactIntent = getIntent();
            int gradeId = (spinner.getSelectedItemPosition());

            Grade grade = grades.get(gradeId);

            dbHelper.deleteGrade(grade.getStudentId());

            setResult(RESULT_OK, deleteContactIntent);
        }
        finish();
    }
}
