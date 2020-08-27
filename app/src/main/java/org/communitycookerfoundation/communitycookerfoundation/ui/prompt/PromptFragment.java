package org.communitycookerfoundation.communitycookerfoundation.ui.prompt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.communitycookerfoundation.communitycookerfoundation.R;
import org.communitycookerfoundation.communitycookerfoundation.util.ReportPrompt;
import org.communitycookerfoundation.communitycookerfoundation.util.ReportPromptNum;

import java.util.ArrayList;
import java.util.List;


public class PromptFragment extends Fragment {
    TextView showCount;
    private TextInputEditText mEditText;
    private TextInputLayout mInputEditLayout;
    public static final int CURRENT_PROMPT = 1;
    private PromptViewModel mViewModel;
    private ViewPager2 mPager;
    private List<ReportPrompt>  mAllPrompts = new ArrayList<>();


    @Override
    public View onCreateView(

            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View fragmentFirst =  inflater.inflate(R.layout.fragment_prompt_viewpager, container, false);
        //showCount = fragmentFirst.findViewById(R.id.textview_first);
        return fragmentFirst;


    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mPager = view.findViewById(R.id.prompt_viewpager);
        /*mEditText = view.findViewById(R.id.input_answer);
        mInputEditLayout = view.findViewById(R.id.prompt_Layout1);
        TextView currentNum = view.findViewById(R.id.currentPromptNum1);*/
        NavBackStackEntry navBackStackEntry  = NavHostFragment.findNavController(PromptFragment.this).getBackStackEntry(R.id.nav_add_report);
        mViewModel = new ViewModelProvider(navBackStackEntry).get(PromptViewModel.class);
        //currentNum.setText(CURRENT_PROMPT + "/" + TOTAL_PROMPTS);
        mPager.setAdapter(getPromptAdapter());
        mViewModel.getReportPrompts().observe(this.getViewLifecycleOwner(), new Observer<List<ReportPrompt>>() {
            @Override
            public void onChanged(List<ReportPrompt> reportPrompts) {

                mAllPrompts = reportPrompts;
            }
        });


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


        /*
        view.findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(mEditText.getText().toString())){
                    if(validateText(mEditText.getText().toString())) {
                        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                        mViewModel.setReport(new ReportEntity("litres",mEditText.getText().toString(),date));
//                        FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(mEditText.getText().toString(), CURRENT_PROMPT);
//                        NavHostFragment.findNavController(FirstFragment.this).navigate(action);
                        NavHostFragment.findNavController(PromptFragment.this).navigate(R.id.action_Prompt1Fragment_to_SuccessFragment);
                    }
                }
                else {
                    Toast myToast = Toast.makeText(getContext(), R.string.toast_empty_string, Toast.LENGTH_SHORT);
                    myToast.show();

                }

            }
        });*/

        /*view.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }


        });*/
    }

    public FragmentStateAdapter getPromptAdapter() {

        return new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                if(mAllPrompts.get(position) instanceof ReportPromptNum){
                    ReportPromptNum prompt = (ReportPromptNum) mAllPrompts.get(position);
                    return PromptNumFragment.createInstance(prompt.getQuestion(),prompt.getHint(), prompt.getMax_value(), prompt.getMin_value(), position);
                }
                else return PromptNumFragment.createInstance("error", "", 100, 0, position);
            }

            @Override
            public int getItemCount() {
                return mAllPrompts.size();
            }
        };
    }

    /*private boolean validateText(String inputString) {

        if (!inputString.isEmpty() && Integer.parseInt(inputString) > 100 ) {
            mInputEditLayout.setErrorEnabled(true);
            mInputEditLayout.setError("Please add an amount less than " + 100);
            return false;
        }
        else {
            mInputEditLayout.setErrorEnabled(false);
            return true;
        }
    }*/
/*
    private void count(View view) {
        String number = showCount.getText().toString();
        Integer count = Integer.parseInt(number);
        count++;
        showCount.setText(count.toString());

    }*/


}
