package org.communitycookerfoundation.communitycookerfoundation.ui.admin;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.communitycookerfoundation.communitycookerfoundation.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
    private ArrayList<String> mTextTypes;
    private boolean mTypesAdded = false;
    private Button mNextBtn;
    private String mUserId;
    private ArrayList<String> mOriginalroles=new ArrayList<>();

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
        mNextBtn = view.findViewById(R.id.next_btn);
        setNxtBtnClickListener(mNextBtn);
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



    }

    @Override
    public void onStart() {
        super.onStart();
        mOriginalroles = (ArrayList<String>) mUserDetails.get("roles");
        mViewModel.getCookerTypes().observe(this.getViewLifecycleOwner(), new Observer<ArrayList<String>>() {

            @Override
            public void onChanged(ArrayList<String> strings) {
                mTextTypes = strings;
                final LinearLayout layout = getView().findViewById(R.id.LinearLayout);
                if(!mTypesAdded){
                    for(int ind= 0; ind <=strings.size(); ind++ ){

                        if(ind != strings.size()){
                            String option = strings.get(ind);
                            CheckBox checkBox = new CheckBox(UserInfoFragment.this.getContext());
                            checkBox.setText(option);
                            checkBox.setId(ind);
                            checkBox.setEnabled(false);
                            if(mOriginalroles.contains(option))  checkBox.setChecked(true);
                            layout.addView(checkBox);
                        }
                        else{

//                            String option = "Basic Set";
//                            CheckBox checkBox = new CheckBox(UserInfoFragment.this.getContext());
//                            checkBox.setText(option);
//                            checkBox.setId(ind);
//                            checkBox.setEnabled(false);
//                            checkBox.setChecked(true);
//                            layout.addView(checkBox);

                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.setMargins(64, 16,64,0);
                            Button nxtBtn = new Button(UserInfoFragment.this.getContext());
                            nxtBtn.setId(ind+1);
                            nxtBtn.setLayoutParams(params);
                            nxtBtn.setText("Finish");
                            layout.addView(nxtBtn);
                            mTypesAdded = true;
                            mNextBtn.setVisibility(View.GONE);
                            mNextBtn = getView().findViewById(mTextTypes.size()+1);
                            setNxtBtnClickListener(mNextBtn);


                        }

                    }

                }
            }

        });

        String nameSiteManager = (String) mUserDetails.get("site_manager");
        String namePersonReporting = (String) mUserDetails.get("name");
        String nameOfSite = (String) mUserDetails.get("site");
        mUserId = (String) mUserDetails.get("user_id");


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
                if(mTypesAdded) {
                    for (int i = 0; i < mTextTypes.size(); i++) {
                        CheckBox checkBox = getView().findViewById(i);
                        checkBox.setEnabled(true);
                    }
                }

            }
        });



    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment(this);
        newFragment.show(getParentFragmentManager(), "datePicker");
    }

    public void setNxtBtnClickListener(Button nextBtn) {
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEditedField && !TextUtils.isEmpty(mNamePerson.getText().toString())){
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("site_manager",mNameOfManager.getText().toString());
                    userInfo.put("name", mNamePerson.getText().toString());
                    userInfo.put("site", mNameOfSite.getText().toString());
                    //checkbox
                    ArrayList<String> checked = new ArrayList<>();
                    for(int ind = 0; ind<mTextTypes.size(); ind++)
                    {
                        CheckBox checkBox = getView().findViewById(ind);
                        if (checkBox.isChecked()) {
                            String optCh = mTextTypes.get(ind);
                            checked.add(optCh);

                        }

                    }
                    userInfo.put("roles", checked);


                    mViewModel.addCookerInfo(mUserId,userInfo);
                    mViewModel.refreshAllUsers();

                    NavHostFragment.findNavController(UserInfoFragment.this).popBackStack();

                }
                else if(mEditedField) {
                    Toast myToast = Toast.makeText(getContext(), "Add an email address", Toast.LENGTH_SHORT);
                    myToast.show();

                }
                else NavHostFragment.findNavController(UserInfoFragment.this).popBackStack();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        ++month; //month starts from 0 for some reason
        String text = dayOfMonth+"/"+month+"/"+year;
        mCalendarText.setText(text);

    }
}
