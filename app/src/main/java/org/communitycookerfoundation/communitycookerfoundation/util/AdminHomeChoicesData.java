package org.communitycookerfoundation.communitycookerfoundation.util;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeChoicesData {
    private String firstOption;
    private String secondOption;
    private List<String> options = new ArrayList<>();

    public AdminHomeChoicesData(){
        options.add(0, "Manage users");
        options.add(1, "Manage Reports");
    }

    public List<String> getOptions() {
        return options;
    }







}
