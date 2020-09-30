package org.communitycookerfoundation.communitycookerfoundation.util;

import com.google.firebase.firestore.DocumentId;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReportPromptOptional extends ReportPrompt{

    //member variables

    private List<String> options;

    public ReportPromptOptional(){

    }

    public ReportPromptOptional(String question, String input_type, List<String> options, int question_id){

        this.question = question;
        this.input_type = input_type;
        this.options = options;
        this.question_id  = question_id;



    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public String getInput_type() {
        return input_type;
    }

    @Override
    public int getQuestion_id() {
        return question_id;
    }

    public List<String> getOptions() {
        return options;
    }
}
