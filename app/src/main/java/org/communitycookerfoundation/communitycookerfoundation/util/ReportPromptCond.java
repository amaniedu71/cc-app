package org.communitycookerfoundation.communitycookerfoundation.util;

import com.google.firebase.firestore.DocumentId;

import java.util.List;

public class ReportPromptCond extends ReportPrompt{
    //member variables

    //private String hint;
    private int if_false;
    private int if_true;


    public ReportPromptCond(){

    }

    public ReportPromptCond(String question, String input_type, /*String hint,*/ int if_false, int if_true, int question_id){

        this.question = question;
        this.input_type = input_type;
//        this.hint = hint;
        this.if_false = if_false;
        this.if_true = if_true;
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

    @Override
    public int getQuestion_id() {
        return question_id;
    }

    /*public String getHint() {
        return hint;
    }*/

    public int getIf_false() {
        return if_false;
    }

    public int getIf_true() {
        return if_true;
    }

}
