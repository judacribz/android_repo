package sheron.csci4100u.labs.lab7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;


public class GradesDBHelper extends SQLiteOpenHelper {
    private static final int    DATABASE_VERSION = 1;
    private static final String TABLE            = "grades";
    private static final String DROP_STATEMENT   = "DROP TABLE " + TABLE;
    private static final String CREATE_STATEMENT =
            "CREATE TABLE grades (\n" +
                    "      studentId integer primary key,\n" +
                    "      courseComponent varchar(100) not null,\n" +
                    "      mark decimal not null\n" +
            ")\n";


    GradesDBHelper(Context context) {
        super(context, TABLE, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // create the database, using CREATE SQL statement
        db.execSQL(CREATE_STATEMENT);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersionNum,
                          int newVersionNum) {
        // delete the old database
        db.execSQL(DROP_STATEMENT);

        // re-create the database
        db.execSQL(CREATE_STATEMENT);
    }

    // CRUD functions
    // CREATE
    Grade createGrade(int studentId,
                      String courseComponent,
                      float mark) {

        // create a new entity object (Grade)
        Grade grade = new Grade(studentId, courseComponent, mark);

        // put that data into the database
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put("studentId", studentId);
        newValues.put("courseComponent", courseComponent);
        newValues.put("mark", mark);

        db.insert(TABLE, null, newValues);

        return grade;
    }


    // READ
    public Grade getGrade(int id) {
        Grade grade = null;

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[] {"studentId", "courseComponent", "mark"};
        String where = "studentId = ?";
        String[] whereArgs = new String[] { "" + id };
        Cursor cursor = db.query(TABLE, columns, where, whereArgs, "", "", "");

        if (cursor.getCount() >= 1) {
            cursor.moveToFirst();

            String courseComponent = cursor.getString(1);
            float mark = cursor.getFloat(2);

            grade = new Grade(id, courseComponent, mark);
        }

        cursor.close();

        Log.i("SQLite", "createGrade(): " + grade);

        return grade;
    }


    // GET all grades
    List<Grade> getAllGrades() {
        List<Grade> grades = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[] {"studentId", "courseComponent", "mark"};
        String where = "";
        String[] whereArgs = new String[] {  };
        Cursor cursor = db.query(TABLE, columns, where, whereArgs, "", "", "mark");

        cursor.moveToFirst();
        do {
            if (!cursor.isAfterLast()) {
                int studentId = cursor.getInt(0);
                String courseComponent = cursor.getString(1);
                float mark = cursor.getFloat(2);

                Grade grade = new Grade(studentId, courseComponent, mark);
                grades.add(grade);
            }

            cursor.moveToNext();
        } while (!cursor.isAfterLast());
        cursor.close();

        Log.i("SQLite", "getAllGrades(): num = " + grades.size());


        return grades;
    }


    // UPDATE
    boolean updateGrade(Grade grade) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("courseComponent", grade.getCourseComponent());
        values.put("mark", grade.getMark());

        int numRows = db.update(TABLE,
                                values,
                                "studentId = ?",
                                new String[] {
                                    String.valueOf(grade.getStudentId())
                                });

        return (numRows == 1);
    }


    // DELETE
    boolean deleteGrade(int studentId) {
        SQLiteDatabase db = this.getWritableDatabase();

        int numRows = db.delete(TABLE, "studentId = ?", new String[] { "" + studentId });

        return (numRows == 1);
    }


    // DELETE all grades
    void deleteAllGrades() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE, "", new String[] { });
    }
}
