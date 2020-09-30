package org.communitycookerfoundation.communitycookerfoundation.util;

import com.google.firebase.firestore.DocumentId;

import java.util.List;

public class ReportPromptNum extends ReportPrompt{
    //member variables

    private String hint;
    private int max_value;
    private int min_value;


    public ReportPromptNum(){

    }

    public ReportPromptNum(String question, String input_type, String hint, int max_value, int min_value, int question_id){

        this.question = question;
        this.input_type = input_type;
        this.hint = hint;
        this.max_value = max_value;
        this.min_value = min_value;
        this.question_id = question_id;

    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public String getInput_type() {
        return input_type;
    }

    public String getHint() {
        return hint;
    }

    public int getMax_value() {
        return max_value;
    }

    public int getMin_value() {
        return min_value;
    }

    @Override
    public int getQuestion_id() {
        return question_id;
    }

}
