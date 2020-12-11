package org.communitycookerfoundation.communitycookerfoundation.ui.admin;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.communitycookerfoundation.communitycookerfoundation.R;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserInfoFragment  extends Fragment implements DatePickerDialog.OnDateSetListener {

    private TextInputEditText mEmailAddress;
    private TextInputLayout mLayoutEmail;
    private TextView mCalendarText;
    private EditText mNameOfSite;
    private EditText mNameOfReporting;
    private EditText mNameOfManager;
    private UserInfoViewModel mViewModel;
    private EditText mNamePerson;
    private Button mEditButton;
    private String currentUser;
    private String mCurrentUserUID;
    private Boolean mEditedField = false;
    private HashMap<String, Object> mUserDetails;

    @Override
    public View onCreateView(

            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View fragmentFirst = inflater.inflate(R.layout.user_info_fragment, container, false);
        //showCount = fragmentFirst.findViewById(R.id.textview_first);
        return fragmentFirst;


    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        mUserDetails = (HashMap<String, Object>) UserInfoFragmentArgs.fromBundle(getArguments()).getUserDetails();
        mNamePerson = view.findViewById(R.id.name_of_reporting_edit);
        Button dateBtn = view.findViewById(R.id.open_cal);
        mNameOfSite = view.findViewById(R.id.name_of_site_edit);
        mNameOfManager = view.findViewById(R.id.name_of_manager_edit);
        mEditButton = view.findViewById(R.id.edit_btn);
        mNameOfSite.setEnabled(false);
        mNameOfManager.setEnabled(false);
        mNamePerson.setEnabled(false);
        mEmailAddress = view.findViewById(R.id.input_email);
        mCalendarText = getView().findViewById(R.id.textView);
        Button nextBtn = view.findViewById(R.id.next_btn);
        mViewModel = new ViewModelProvider(this).get(UserInfoViewModel.class);

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);
            }
        });
        mCalendarText.setText("No date chosen yet");

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
       String nameSiteManager = (String) mUserDetails.get("site_manager");
       String namePersonReporting = (String) mUserDetails.get("name");
       String nameOfSite = (String) mUserDetails.get("site");
       final String mUserId = (String) mUserDetails.get("user_id");

       if(!TextUtils.isEmpty(nameSiteManager)) mNameOfManager.setText(nameSiteManager);
       if(!TextUtils.isEmpty(namePersonReporting)) mNamePerson.setText(namePersonReporting);
       if(!TextUtils.isEmpty(nameOfSite)) mNameOfSite.setText(nameOfSite);
       mEditButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mEditedField = true;
               mNameOfSite.setEnabled(true);
               mNameOfManager.setEnabled(true);
               mNamePerson.setEnabled(true);
           }
       });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEditedField && !TextUtils.isEmpty(mNamePerson.getText().toString())){
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("site_manager",mNameOfManager.getText().toString());
                    userInfo.put("name", mNamePerson.getText().toString());
                    userInfo.put("site", mNameOfSite.getText().toString());
                    mViewModel.addCookerInfo(mUserId,userInfo);


                }
                else if(mEditedField) {
                    Toast myToast = Toast.makeText(getContext(), "Add an email address", Toast.LENGTH_SHORT);
                    myToast.show();

                }
                NavHostFragment.findNavController(UserInfoFragment.this).popBackStack();


            }
        });


    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment(this);
        newFragment.show(getParentFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        ++month; //month starts from 0 for some reason
        String text = dayOfMonth+"/"+month+"/"+year;
        mCalendarText.setText(text);

    }
}
