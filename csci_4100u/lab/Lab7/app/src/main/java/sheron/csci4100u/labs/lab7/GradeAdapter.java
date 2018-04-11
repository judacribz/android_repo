package sheron.csci4100u.labs.lab7;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class GradeAdapter extends ArrayAdapter<Grade> {

    private List<Grade> grades;
    private Context     context;


    GradeAdapter(Context context, List<Grade> grades) {
        super(context, R.layout.grade_list_item, grades);
        this.context = context;
        this.grades  = grades;
    }


    @Override
    @NonNull
    public View getView(int position,
                        View reusableView,
                        @NonNull ViewGroup parent) {

        Grade grade = grades.get(position);

        if (reusableView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            reusableView = inflater.inflate(R.layout.grade_list_item,
                    parent,
                    false);
        }

        TextView studentId = reusableView.findViewById(R.id.tvId);
        studentId.setText(String.valueOf(grade.getStudentId()));

        TextView courseComponent = reusableView.findViewById(R.id.tvName);
        courseComponent.setText(grade.getCourseComponent());
        TextView mark = reusableView.findViewById(R.id.tvMark);
        mark.setText(String.valueOf(grade.getMark()));

        return reusableView;
    }

    @Override
    public View getDropDownView(int position,
                                View reusableView,
                                @NonNull ViewGroup parent) {

        Grade grade = grades.get(position);

        if (reusableView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            reusableView = inflater.inflate(
                    R.layout.support_simple_spinner_dropdown_item,
                    parent,
                    false);
        }

        TextView name = reusableView.findViewById(android.R.id.text1);
        name.setGravity(Gravity.CENTER);
        name.setBackgroundColor(Color.BLACK);
        name.setText(String.valueOf(grade.getStudentId()));

        return reusableView;
    }
}
