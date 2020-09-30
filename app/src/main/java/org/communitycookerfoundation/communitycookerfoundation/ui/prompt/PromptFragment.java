package org.communitycookerfoundation.communitycookerfoundation.ui.prompt;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
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
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.ReportEntity;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.ReportListEntity;
import org.communitycookerfoundation.communitycookerfoundation.ui.home.UserHomeFragment;
import org.communitycookerfoundation.communitycookerfoundation.util.ReportPrompt;
import org.communitycookerfoundation.communitycookerfoundation.util.ReportPromptCond;
import org.communitycookerfoundation.communitycookerfoundation.util.ReportPromptNum;
import org.communitycookerfoundation.communitycookerfoundation.util.ReportPromptOptional;
import org.communitycookerfoundation.communitycookerfoundation.util.ReportPromptTextChoices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PromptFragment extends Fragment implements PromptNumFragment.OnPromptBtnClicked, PromptCondFragment.OnPromptCondBtnClicked, PromptTextChoicesFragment.OnPromptTextChoicesBtnClicked, PromptOptionalFragment.OnPromptOptionalBtnClicked {
    private static final String TAG = "PromptFragment";
    TextView showCount;
    private TextInputEditText mEditText;
    private TextInputLayout mInputEditLayout;
    public static final int CURRENT_PROMPT = 1;
    private PromptViewModel mViewModel;
    private ViewPager2 mPager;
    private List<ReportPrompt>  mAllPrompts = new ArrayList<>();
    private FragmentStateAdapter mPromptAdapter;
    private int mPrevPos;
    Map<Integer, Integer> backDestinationMap = new HashMap<Integer, Integer>();

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
        mPrevPos = -1;
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(mPager.getCurrentItem() > 0) {
                    if(backDestinationMap.get(mPager.getCurrentItem()) != null){
                        int prev = backDestinationMap.get(mPager.getCurrentItem());
                        Log.d(TAG, "Back is"+prev);
                        mPager.setCurrentItem(prev, false);
                    }
                    else{
                        mPager.setCurrentItem(mPager.getCurrentItem()-1, false);
                    }

                }
            }
        };


        getActivity().getOnBackPressedDispatcher().addCallback(this.getViewLifecycleOwner(), callback);
        mPager = view.findViewById(R.id.prompt_viewpager);
        mPager.setUserInputEnabled(false);
        /*mEditText = view.findViewById(R.id.input_answer);
        mInputEditLayout = view.findViewById(R.id.prompt_Layout1);
        TextView currentNum = view.findViewById(R.id.currentPromptNum1);*/
        NavBackStackEntry navBackStackEntry  = NavHostFragment.findNavController(PromptFragment.this).getBackStackEntry(R.id.nav_add_report);
        mViewModel = new ViewModelProvider(navBackStackEntry).get(PromptViewModel.class);
        //currentNum.setText(CURRENT_PROMPT + "/" + TOTAL_PROMPTS);
        mPromptAdapter = getPromptAdapter();
        mPager.setAdapter(mPromptAdapter);
        mViewModel.getReportPrompts().observe(this.getViewLifecycleOwner(), new Observer<List<ReportPrompt>>() {
            @Override
            public void onChanged(List<ReportPrompt> reportPrompts) {

                mAllPrompts = reportPrompts;
                mPromptAdapter.notifyDataSetChanged();

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
                    return PromptNumFragment.createInstance(prompt.getQuestion(),prompt.getHint(), prompt.getMax_value(), prompt.getMin_value(), position, PromptFragment.this, mAllPrompts.size(), mPrevPos);
                }
                else if(mAllPrompts.get(position) instanceof ReportPromptCond){
                    ReportPromptCond promptCond = (ReportPromptCond) mAllPrompts.get(position);
                    return PromptCondFragment.createInstance(promptCond.getQuestion(), promptCond.getIf_false(), promptCond.getIf_true(), position, PromptFragment.this, mAllPrompts.size(), mPrevPos);
                }

                else if(mAllPrompts.get(position) instanceof ReportPromptTextChoices){
                    ReportPromptTextChoices promptTextChoices = (ReportPromptTextChoices) mAllPrompts.get(position);
                    return PromptTextChoicesFragment.createInstance(promptTextChoices.getQuestion(), promptTextChoices.getOptions(), position, PromptFragment.this,mAllPrompts.size(), mPrevPos);
                }
                else if(mAllPrompts.get(position) instanceof ReportPromptOptional){
                    ReportPromptOptional promptOptional = (ReportPromptOptional) mAllPrompts.get(position);
                    return PromptOptionalFragment.createInstance(promptOptional.getQuestion(), promptOptional.getOptions(), position, PromptFragment.this, mAllPrompts.size(), mPrevPos);
                }

                else return PromptNumFragment.createInstance("error", "", 100, 0, position, PromptFragment.this,  mAllPrompts.size(), mPrevPos);
            }

            @Override
            public int getItemCount() {
                return mAllPrompts.size();
            }
        };
    }



    @Override
    public void onNextClick( String response) {
        //curPos += 1; 
        ReportPrompt reportPrompt = mAllPrompts.get(mPager.getCurrentItem());
        validateSave(mPager.getCurrentItem(), response, reportPrompt.getInput_type());
        if(mPager.getCurrentItem()<mAllPrompts.size()-1){
           // mPrevPos = mPager.getCurrentItem() -1;
            mPager.setCurrentItem(mPager.getCurrentItem()+1,true );

        }
        else
            navToSummary();


    }

    @Override
    public void onNextClick(String response, int destination) {
        validateSave(mPager.getCurrentItem(),response, "text_choices");
        if(destination<mAllPrompts.size()){
            ReportPromptCond tempPrompt = (ReportPromptCond) mAllPrompts.get(mPager.getCurrentItem());
            boolean isTrue= tempPrompt.getIf_true() == destination;
            if(backDestinationMap.get(tempPrompt.getIf_false()) == null && backDestinationMap.get(tempPrompt.getIf_true()) == null){
                backDestinationMap.put(destination, mPager.getCurrentItem());
                mPager.setCurrentItem(destination, false);
            }
            else if(backDestinationMap.get(tempPrompt.getIf_true()) != null){
                if(isTrue){
                    mPager.setCurrentItem(destination, false);
                }
                else{
                    backDestinationMap.remove(tempPrompt.getIf_true());
                    backDestinationMap.put(destination, mPager.getCurrentItem());
                    mPager.setCurrentItem(destination, false);
                }
            }
            else if(backDestinationMap.get(tempPrompt.getIf_false())!= null){
                if(!isTrue){
                    mPager.setCurrentItem(destination, false);
                }
                else{
                    backDestinationMap.remove(tempPrompt.getIf_true());
                    backDestinationMap.put(destination, mPager.getCurrentItem());
                    mPager.setCurrentItem(destination, false);
                }
            }

        }
        else if(mPager.getCurrentItem()<mAllPrompts.size()-1) {
            mPager.setCurrentItem(mPager.getCurrentItem() + 1);


        }
        else
            navToSummary();

    }


    @Override
    public void onNextClick(List<String> response) {
        ReportPrompt reportPrompt = mAllPrompts.get(mPager.getCurrentItem());
        validateSaveList(mPager.getCurrentItem(), response, reportPrompt.getInput_type());
        if(mPager.getCurrentItem()<mAllPrompts.size()-1){
            mPager.setCurrentItem(mPager.getCurrentItem()+1,false );

        }
        else
            navToSummary();
    }

    private void validateSaveList(int currentItem, List<String> response, String input_type) {

        if(mAllPrompts.get(currentItem) instanceof ReportPromptOptional){

            ReportPromptOptional prompt;
            prompt = (ReportPromptOptional) mAllPrompts.get(currentItem);

            mViewModel.addReport(new ReportListEntity(prompt.getQuestion(),response), currentItem);
        }
    }

    @Override
    public void onBackClick(int prevPos) {
        if(mPager.getCurrentItem() > 0) {
            if(backDestinationMap.get(mPager.getCurrentItem()) != null){
                int prev = backDestinationMap.get(mPager.getCurrentItem());
                Log.d(TAG, "Back is"+prev);
                mPager.setCurrentItem(prev, false);
            }
            else{
                Log.d(TAG, String.valueOf(prevPos));
                mPager.setCurrentItem(mPager.getCurrentItem()-1, false);
            }

        }
        else {
            getActivity().onBackPressed();
            NavHostFragment.findNavController(PromptFragment.this).popBackStack();
            //getParentFragmentManager().popBackStack();
        }
    }



    private void validateSave(int pos, String response, String input_type) {

        if(mAllPrompts.get(pos) instanceof ReportPromptNum){
            ReportPromptNum prompt;
            prompt = (ReportPromptNum) mAllPrompts.get(pos);
            mViewModel.addReport(new ReportEntity(prompt.getQuestion(),response), pos);
        }
        if(mAllPrompts.get(pos) instanceof ReportPromptCond){
            ReportPromptCond prompt;
            prompt = (ReportPromptCond) mAllPrompts.get(pos);
            mViewModel.addReport(new ReportEntity(prompt.getQuestion(),response), pos);
        }
        if(mAllPrompts.get(pos) instanceof ReportPromptTextChoices){
            ReportPromptTextChoices prompt;
            prompt = (ReportPromptTextChoices) mAllPrompts.get(pos);
            mViewModel.addReport(new ReportEntity(prompt.getQuestion(),response), pos);
        }
        else if(mAllPrompts.get(pos) instanceof ReportPromptOptional){
            ReportPromptOptional prompt;
            prompt = (ReportPromptOptional) mAllPrompts.get(pos);
            mViewModel.addReport(new ReportEntity(prompt.getQuestion(),response), pos);

        }
        else
            Log.e(TAG, "error in adding report to viewmodel");
        //String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        /*if(input_type.contains("number")){
            mViewModel.setReport(new ReportEntity(prompt.getQuestion(),response,date));
        }*/
    }

    private void navToSummary(){
        NavHostFragment.findNavController(PromptFragment.this).navigate(R.id.action_nav_prompt_to_summaryFragment);

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
