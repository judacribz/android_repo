package sheron.csci4100u.assignments.a1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


class Questions {
    private ArrayList<String> questions;
    private int               questIndex = 0,
                              yesCount   = 0,
                              noCount    = 0;

    // Questions constructor
    Questions(String[] questions) {
        this.questions = new ArrayList<>(Arrays.asList(questions));
    }


    // Removes a question from the array and returns its value
    String getQuestion() {
        setRandomIndex();
        return this.questions.remove(this.questIndex);
    }


    // Sets questionIndex to a random value within questions array size
    private void setRandomIndex() {
        Random rand = new Random();
        this.questIndex = rand.nextInt(this.questions.size());
    }


    boolean atEndOfQuestions() {
        return this.questions.size() == 0;
    }


    int getYesCount() {
        return this.yesCount;
    }


    int getNoCount() {
        return this.noCount;
    }


    void incYesCount() {
        this.yesCount++;
    }


    void incNoCount() {
        this.noCount++;
    }
}
