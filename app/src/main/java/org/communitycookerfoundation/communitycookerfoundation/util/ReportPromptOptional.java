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

    public ReportPromptOptional(String question, String input_type, List<String> options){

        this.question = question;
        this.input_type = input_type;
        this.options = options;



    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public String getInput_type() {
        return input_type;
    }

    public List<String> getOptions() {
        return options;
    }
}
