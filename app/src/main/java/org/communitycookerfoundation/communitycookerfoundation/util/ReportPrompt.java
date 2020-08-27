package org.communitycookerfoundation.communitycookerfoundation.util;

abstract public class ReportPrompt {

    protected String question;

    protected String input_type;

    abstract String getQuestion();

    abstract String getInput_type();


}
