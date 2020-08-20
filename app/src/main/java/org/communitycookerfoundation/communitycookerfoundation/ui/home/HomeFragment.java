package org.communitycookerfoundation.communitycookerfoundation.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavHost;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.communitycookerfoundation.communitycookerfoundation.R;
import org.communitycookerfoundation.communitycookerfoundation.adapters.ReportAdapter;
import org.communitycookerfoundation.communitycookerfoundation.adapters.UserReportAdapter;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.ReportEntity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private UserReportAdapter mReportAdapter;
    //private LiveData<List<ReportEntity>> mReports;
    private HomeViewModel mViewModel;
    private Button mAddButton;
    private int count;
    private TextView mWelcomeMessage;

    public HomeFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        mRecyclerView = view.findViewById(R.id.recycler_report1);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        mViewModel.isAdmin().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isAdmin) {
                if (isAdmin) {
                    NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_nav_home_to_adminFragment);
                }

            }
        });


        mAddButton = view.findViewById(R.id.add_button);
        mReportAdapter = new UserReportAdapter(getContext());
        mRecyclerView.setAdapter(mReportAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setNestedScrollingEnabled(false);
        mViewModel.getAllReports().observe(getViewLifecycleOwner(), new Observer<List<ReportEntity>>() {
            @Override
            public void onChanged(List<ReportEntity> reportEntities) {

                mReportAdapter.setReports(reportEntities);
                if(reportEntities.size()>0)
                    mViewModel.insertFb(reportEntities.get(reportEntities.size()-1));
            }
        });


        count = 0;
        /*mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int ind = count ; ind < count+4; ind++){
                    String addReport = "report"+ ind;
                    ReportEntity reportEntity = new ReportEntity(addReport);
                    mViewModel.insertF(reportEntity);
                }
                count = count+4;
            }
        });*/

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_HomeFragment_to_Prompt1Fragment);
            }
        });


        mWelcomeMessage = view.findViewById(R.id.welcomeText);
        String welcome = getString(R.string.welcome_message) + mViewModel.getDisplayName();
        mWelcomeMessage.setText(welcome);

    }
}

