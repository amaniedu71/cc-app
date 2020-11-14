package org.communitycookerfoundation.communitycookerfoundation.ui.prompt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.communitycookerfoundation.communitycookerfoundation.R;
import org.communitycookerfoundation.communitycookerfoundation.util.ReportPromptTextChoices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PromptNumFragment#createInstance} factory method to
 * create an instance of this fragment.
 */
public class PromptOptionalFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String QUESTION = "question";
    //    private static final String IF_FALSE = "if_false";
//    private static final String IF_TRUE = "if_true";
    // private static final String HINT = "hint";
    private static final String CUR_POS = "cur_pos";
    private static final String PREV_POS = "prev_pos";
    private static final String SIZE = "size";
    private final OnPromptOptionalBtnClicked mBtnClicked;
    public static final int BACK_CLICKED = 0 ;
    public static final int NEXT_CLICKED = 1;
    private final List<String> mOptions;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView showCount;
    private TextInputEditText mEditText;
    private TextInputLayout mInputEditLayout;
    public static final int CURRENT_PROMPT = 1;
    private PromptViewModel mViewModel;
    private ViewPager2 mPager;
    private int mMaxValue;
    private int mMinValue;
    private String mQuestionText;
    private String mHint;
    private int mPosition;
    private TextView mQuestionView;
    private int mCurPos;
    //    private boolean mIsLast;
    private Button mNextBtn;
    private Button mCancelBtn;
    private int mPromptSize;
    private CheckBox mCheckBox;
    private int mPrevPos;
    private CheckBox checkBox;


    public PromptOptionalFragment(List<String> options, OnPromptOptionalBtnClicked nextClicked) {
        mBtnClicked = nextClicked;
        mOptions = options;

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param question Parameter 1.
     * @return A new instance of fragment PromptTextFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PromptOptionalFragment createInstance(String question, List<String> options, int position, OnPromptOptionalBtnClicked nextClicked, int isLast, int prev) {
        PromptOptionalFragment fragment = new PromptOptionalFragment(options, nextClicked);
        Bundle args = new Bundle();
       // args.putParcelableArrayList("Hello", options); TODO: for rotation purpose impliment Parsable wrapper
        args.putString(QUESTION, question);
        //  args.putString(HINT, hint);
        args.putInt(CUR_POS, position);
        args.putInt(SIZE, isLast);
        args.putInt(PREV_POS, prev);


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuestionText = getArguments().getString(QUESTION);
            //mHint = getArguments().getString(HINT);

            mCurPos = getArguments().getInt(CUR_POS);
            mPromptSize = getArguments().getInt(SIZE);
            mPrevPos = getArguments().getInt(PREV_POS);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prompt_optional, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout layout = view.findViewById(R.id.linearLayout);

        for(int ind= 0; ind <=mOptions.size(); ind++ ){

            if(ind != mOptions.size()){
                String option = mOptions.get(ind);
                CheckBox checkBox = new CheckBox(this.getContext());
                checkBox.setText(option);
                checkBox.setId(ind);
                layout.addView(checkBox);
            }
            else{

                String option = "None of the above";
                CheckBox checkBox = new CheckBox(this.getContext());
                checkBox.setText(option);
                checkBox.setId(ind);
                layout.addView(checkBox);
            }

        }

        mQuestionView = view.findViewById(R.id.textPromptOptional);
        String questionPrompt = (/*mCurPos + ") " +*/mQuestionText);
        mQuestionView.setText(questionPrompt);
        TextView currentNum = view.findViewById(R.id.currentPromptOptional);
        mCurPos+=1;
        currentNum.setText(""+mCurPos);
        setProgressBar();
     /*   mEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = mEditText.getText().toString().trim();
                validateText(text);
                //TODO: Update cache of responses after text changes
                //TODO: sort out persistence

            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });*/


        mNextBtn = view.findViewById(R.id.next_btn);
        if(mCurPos != mPromptSize-1){
            mNextBtn.setText(R.string.next_btn);
        }
        else
            mNextBtn.setText(R.string.finish);
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> checked = new ArrayList<>();
                for(int ind = 0; ind<mOptions.size(); ind++)
                {
                    CheckBox checkBox = getView().findViewById(ind);
                    if (checkBox.isChecked()) {
                        String optCh = mOptions.get(ind);
                        checked.add(optCh);

                    }

                }
                if (checked.size() != 0){
                    mBtnClicked.onNextClick(checked);
                }
                else{
                    Toast myToast = Toast.makeText(getContext(), R.string.toast_empty_string, Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }
        });
        mCancelBtn = view.findViewById(R.id.cancel_btn);
        if(mCurPos == 0) mCancelBtn.setText(R.string.cancel);
        else mCancelBtn.setText(R.string.back_btn);
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBtnClicked.onBackClick(mPrevPos);
            }


        });

    }

    private void setProgressBar() {
        ProgressBar propmtProgBar = getView().findViewById(R.id.promptOptionalProgBar);
        int percentage = (int) (mCurPos+1/mPromptSize) * 100;
        propmtProgBar.setProgress(percentage);

    }


/*

    private boolean validateText(String inputString) {

        if (!inputString.isEmpty()) {
            if (Integer.parseInt(inputString) > mMaxValue) {
                mInputEditLayout.setErrorEnabled(true);
                mInputEditLayout.setError("Please add an amount less than " + mMaxValue);
                return false;
            }

            else if (Integer.parseInt(inputString) < mMinValue) {
                mInputEditLayout.setErrorEnabled(true);
                mInputEditLayout.setError("Please add an amount greater than " + mMinValue);
                return false;
            }

            else {
                mInputEditLayout.setErrorEnabled(false);
                return true;
            }
        }
        else {
            Toast myToast = Toast.makeText(getContext(), R.string.toast_empty_string, Toast.LENGTH_SHORT);
            myToast.show();
            return false;

        }
    }
*/

    public  interface OnPromptOptionalBtnClicked {
        void onNextClick( List<String> response);
        void onBackClick(int prevPos);

    }

}