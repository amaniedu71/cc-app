package org.communitycookerfoundation.communitycookerfoundation.ui.prompt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.communitycookerfoundation.communitycookerfoundation.R;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.ReportEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PromptNumFragment#createInstance} factory method to
 * create an instance of this fragment.
 */
public class PromptNumFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String QUESTION = "question";
    private static final String MAX_VAL = "max_val";
    private static final String MIN_VAL = "min_val";
    private static final String HINT = "hint";
    private static final String CUR_POS = "cur_pos";
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


    public PromptNumFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param question Parameter 1.
     * @param hint Parameter 2.
     * @return A new instance of fragment PromptTextFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PromptNumFragment createInstance(String question, String hint, int max_value, int min_value, int  position) {
        PromptNumFragment fragment = new PromptNumFragment();
        Bundle args = new Bundle();
        args.putString(QUESTION, question);
        args.putString(HINT, hint);
        args.putInt(MAX_VAL, max_value);
        args.putInt(MIN_VAL, min_value);
        args.putInt(CUR_POS, position);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuestionText = getArguments().getString(QUESTION);
            mHint = getArguments().getString(HINT);
            mMaxValue = getArguments().getInt(MAX_VAL);
            mMinValue = getArguments().getInt(MIN_VAL);
            mCurPos = getArguments().getInt(CUR_POS);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prompt_num, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mQuestionView = view.findViewById(R.id.textPrompt);
        mEditText = view.findViewById(R.id.input_answer);
        mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        mEditText.setHint(mHint);
        String questionPrompt = (mCurPos + ") " +mQuestionText);
        mQuestionView.setText(questionPrompt);
        mInputEditLayout = view.findViewById(R.id.prompt_Layout1);
        TextView currentNum = view.findViewById(R.id.currentPromptNum1);
        currentNum.setText(""+mCurPos);

        mEditText.addTextChangedListener(new TextWatcher() {

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
        });


        view.findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(mEditText.getText().toString())){
                    if(validateText(mEditText.getText().toString())) {



                    }
                }
                else {
                    Toast myToast = Toast.makeText(getContext(), R.string.toast_empty_string, Toast.LENGTH_SHORT);
                    myToast.show();

                }

            }
        });

        view.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }


        });

    }

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

}