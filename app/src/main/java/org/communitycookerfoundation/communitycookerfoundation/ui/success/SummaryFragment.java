package org.communitycookerfoundation.communitycookerfoundation.ui.success;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.communitycookerfoundation.communitycookerfoundation.R;
import org.communitycookerfoundation.communitycookerfoundation.adapters.SummaryAdapter;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.ReportEntity;
import org.communitycookerfoundation.communitycookerfoundation.ui.prompt.PromptViewModel1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class SummaryFragment extends Fragment {
    TextView showCount;
    private TextInputEditText mEditText;
    private TextInputLayout mInputEditLayout;
    public static final int CURRENT_PROMPT = 1;
    private PromptViewModel1 mViewModel;
    private SummaryAdapter mRecyclerAdapter;


    @Override
    public View onCreateView(

            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View fragmentFirst =  inflater.inflate(R.layout.fragment_prompt1, container, false);
        //showCount = fragmentFirst.findViewById(R.id.textview_first);
        return fragmentFirst;


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mEditText = view.findViewById(R.id.input_answer);
        mInputEditLayout = view.findViewById(R.id.prompt_Layout1);
        TextView currentNum = view.findViewById(R.id.currentPromptNum1);
        NavBackStackEntry navBackStackEntry  = NavHostFragment.findNavController(SummaryFragment.this).getBackStackEntry(R.id.nav_add_report);
        mViewModel = new ViewModelProvider(navBackStackEntry).get(PromptViewModel1.class);
        //currentNum.setText(CURRENT_PROMPT + "/" + TOTAL_PROMPTS);
        mRecyclerAdapter = new SummaryAdapter(getContext());
        mViewModel.getReports().observe(getViewLifecycleOwner(), new Observer<ReportEntity>() {
            @Override
            public void onChanged(ReportEntity reportEntity) {
                mRecyclerAdapter.setFields(reportEntity);

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




}
