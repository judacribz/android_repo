package sheron.csci4100u.assignments.a1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import static sheron.csci4100u.assignments.a1.AskQuestion.EXTRA_CHOICE;


public class MainMenu extends AppCompatActivity {

    final static String EXTRA_QUESTION  =
            "sheron.csci4100u.assignments.a1.EXTRA_QUESTION";
    final static String EXTRA_YES_COUNT =
            "sheron.csci4100u.assignments.a1.EXTRA_YES_COUNT";
    final static String EXTRA_NO_COUNT  =
            "sheron.csci4100u.assignments.a1.EXTRA_NO_COUNT";

    final static int REQ_ASK_QUESTION = 100;

    Questions questions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Set the questions in an array list
        setQuestions();
    }


    // Create questions object and pass questions array
    public void setQuestions() {
        questions = new Questions(getResources().getStringArray(R.array.questions_array));
    }


    // OnClick handler
    public void startQuestions(View v) {
        askQuestion(questions.getQuestion());
    }


    // Function to start AskQuestion activity
    public void askQuestion(String question) {
        Intent askQuestionIntent =
                new Intent(getApplicationContext(), AskQuestion.class);
        askQuestionIntent.putExtra(EXTRA_QUESTION, question);
        startActivityForResult(askQuestionIntent, REQ_ASK_QUESTION);
    }


    // On Activity Return
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        // Returns from AskQuestion to increment the yes or no count
        if (requestCode == REQ_ASK_QUESTION) {
            if (resultCode == RESULT_OK) {

                // Increases the yes count or no count depending on users
                // choice
                boolean choice = data.getBooleanExtra(EXTRA_CHOICE, true);
                if (choice) {
                    questions.incYesCount();
                } else {
                    questions.incNoCount();
                }

                // Goes to Summary activity when all questions are answered
                if (questions.atEndOfQuestions()) {
                    Intent summaryIntent =
                            new Intent(getApplicationContext(), Summary.class);
                    summaryIntent
                            .putExtra(EXTRA_YES_COUNT, questions.getYesCount())
                            .putExtra(EXTRA_NO_COUNT, questions.getNoCount());
                    startActivity(summaryIntent);

                    // resets questions by re-adding them and show toast
                    setQuestions();
                    Toast.makeText(getApplicationContext(),
                                   getResources().getString(R.string.question_index_reset),
                                   Toast.LENGTH_SHORT).show();

                // If not end of questions, opens AskQuestion with the next
                // question
                } else {
                    askQuestion(questions.getQuestion());
                }
            }
        }
    }
}
