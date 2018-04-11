package sheron.csci4100u.labs.lab7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.util.List;


public class AddGrade extends AppCompatActivity implements View.OnClickListener{
    static final String EXTRA_STUDENT_ID;
    static final String EXTRA_COURSE_COMPONENT;
    static final String EXTRA_MARK;

    static {
        EXTRA_STUDENT_ID       = "sheron.csci4100u.labs.lab6.EXTRA_STUDENT_ID";
        EXTRA_COURSE_COMPONENT =
                "sheron.csci4100u.labs.lab6.EXTRA_COURSE_COMPONENT";
        EXTRA_MARK             = "sheron.csci4100u.labs.lab6.EXTRA_MARK";
    }

    static final int BTN_ADD_GRADE = R.id.btnAddGrade;

    EditText       etStudentId,
                   etCourseComponent,
                   etMark;
    List<Grade>    grades;
    GradesDBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grade);

        dbHelper = new GradesDBHelper(this);

        grades = dbHelper.getAllGrades();

        etStudentId = findViewById(R.id.etStudentId);
        etCourseComponent = findViewById(R.id.etCourseComponent);
        etMark = findViewById(R.id.etMark);

        (findViewById(BTN_ADD_GRADE)).setOnClickListener(this);
    }


    // Add button handler
    @Override
    public void onClick(View v) {

        // Get text field data
        String studentId = getFieldText(etStudentId);
        String courseComponent = getFieldText(etCourseComponent);
        String mark = getFieldText(etMark);

        if (   !studentId.isEmpty()
                && !courseComponent.isEmpty()
                && !mark.isEmpty()) {


            dbHelper.createGrade(Integer.valueOf(studentId), courseComponent, Float.valueOf(mark));

            Intent newGradeIntent = getIntent();
            setResult(RESULT_OK, newGradeIntent);
            finish();
        }
    }


    // Helper function to validate text field and return text contents
    private String getFieldText(EditText field) {
        String fieldText = field.getText().toString();

        if (fieldText.isEmpty()) {
            field.setError(getString(R.string.required));
        }

        return fieldText;
    }
}
