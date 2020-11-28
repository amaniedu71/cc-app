package org.communitycookerfoundation.communitycookerfoundation.ui.admin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public  class DatePickerFragment extends DialogFragment
         {

    private final DatePickerDialog.OnDateSetListener onDateSetListener;

    public DatePickerFragment(DatePickerDialog.OnDateSetListener addCookerFragment) {
        onDateSetListener = addCookerFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), onDateSetListener, year, month, day);
    }

}