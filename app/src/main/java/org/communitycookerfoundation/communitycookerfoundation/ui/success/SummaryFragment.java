package org.communitycookerfoundation.communitycookerfoundation.ui.success;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.communitycookerfoundation.communitycookerfoundation.R;
import org.communitycookerfoundation.communitycookerfoundation.adapters.SummaryAdapter;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.BasicReportEntity;
import org.communitycookerfoundation.communitycookerfoundation.ui.prompt.PromptFragmentDirections;
import org.communitycookerfoundation.communitycookerfoundation.ui.prompt.PromptViewModel;

import java.util.List;
import java.util.Map;


public class SummaryFragment extends Fragment implements SummaryAdapter.OnSummaryListener {
    TextView showCount;
    private TextInputEditText mEditText;
    private TextInputLayout mInputEditLayout;
    public static final int CURRENT_PROMPT = 1;
    private PromptViewModel mViewModel;
    private SummaryAdapter mRecyclerAdapter;
    private RecyclerView mRecyclerSummary;


    @Override
    public View onCreateView(

            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View fragmentFirst =  inflater.inflate(R.layout.fragment_summary_list, container, false);
        //showCount = fragmentFirst.findViewById(R.id.textview_first);
        return fragmentFirst;


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mRecyclerSummary = view.findViewById(R.id.summary_list);
        mEditText = view.findViewById(R.id.input_answer);
        mInputEditLayout = view.findViewById(R.id.prompt_Layout);
        TextView currentNum = view.findViewById(R.id.currentPromptNum1);
        NavBackStackEntry navBackStackEntry  = NavHostFragment.findNavController(SummaryFragment.this).getBackStackEntry(R.id.nav_add_report);
        mViewModel = new ViewModelProvider(navBackStackEntry).get(PromptViewModel.class);
        //currentNum.setText(CURRENT_PROMPT + "/" + TOTAL_PROMPTS);
        mRecyclerAdapter = new SummaryAdapter(this);
        mRecyclerSummary.setAdapter(mRecyclerAdapter);
        mRecyclerSummary.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mViewModel.getReports().observe(getViewLifecycleOwner(), new Observer<Map<Integer, BasicReportEntity>>() {
                    @Override
                    public void onChanged(Map<Integer, BasicReportEntity> integerBasicReportEntityMap) {
                        mRecyclerAdapter.setFields(integerBasicReportEntityMap);
                    }
                });
                mRecyclerSummary.setNestedScrollingEnabled(false);
        Button onFinish = view.findViewById(R.id.finish_btn);
        onFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SummaryFragment.this).navigate(R.id.action_summaryFragment_to_nav_slideshow);
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




    }


    @Override
    public void onReportListClick(int reportId) {
        SummaryFragmentDirections.ActionSummaryFragmentToNavPrompt action = SummaryFragmentDirections.actionSummaryFragmentToNavPrompt();
        action.setRetArgument(reportId);
        NavHostFragment.findNavController(SummaryFragment.this).navigate(action);
    }
}
