package org.communitycookerfoundation.communitycookerfoundation.ui.admin;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.communitycookerfoundation.communitycookerfoundation.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCookerFragment  extends Fragment {

    private TextInputEditText mEmailAddress;
    private TextInputLayout mLayoutEmail;
    private TextView mCalendarText;
    private AddCookerViewModel mViewModel;
    private Spinner mSpinner;
    private ArrayList<String> mCookerTypes;

    @Override
    public View onCreateView(

            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View fragmentFirst = inflater.inflate(R.layout.dialog_add_cooker, container, false);
        //showCount = fragmentFirst.findViewById(R.id.textview_first);
        return fragmentFirst;


    }

    @Override
    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        mEmailAddress = view.findViewById(R.id.input_email);
        mCalendarText = getView().findViewById(R.id.textView);
        final Button nextBtn = view.findViewById(R.id.next_btn);
        mViewModel = new ViewModelProvider(this).get(AddCookerViewModel.class);
        //Attempt to create spinner

        //final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item);
        //spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);




//        mSpinner.setAdapter(spinnerAdapter);

       /* mEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = mEditText.getText().toString().trim();
                validateText(text);
            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });*/
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(mEmailAddress.getText().toString())){
                    mViewModel.addCookerUser( mEmailAddress.getText().toString());
                    NavHostFragment.findNavController(AddCookerFragment.this).popBackStack();

                }
                else {
                    Toast myToast = Toast.makeText(getContext(), "Add an email address", Toast.LENGTH_SHORT);
                    myToast.show();

                }

            }
        });
    }


}
