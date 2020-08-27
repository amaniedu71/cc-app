package org.communitycookerfoundation.communitycookerfoundation.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.communitycookerfoundation.communitycookerfoundation.R;

public class ReportPromptInflater {

    private View promptView;

    public ReportPromptInflater(LayoutInflater inflater, ViewGroup viewGroup){

        promptView = inflater.inflate(R.layout.admin_home_layout, viewGroup, false);

    }

    public View getPromptView() {
        return promptView;
    }

    /*private class ReportPrompt{


    }*/

}
