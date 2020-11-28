package org.communitycookerfoundation.communitycookerfoundation.ui.admin;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.communitycookerfoundation.communitycookerfoundation.R;
import org.communitycookerfoundation.communitycookerfoundation.adapters.ReportListAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAccount extends Fragment implements ReportListAdapter.OnReportClickListener {
    public final static String TAG = "UserAccount";
    private UserViewModel mViewModel;
    private String mCurrentUserUID;
    private ReportListAdapter mReportListAdapter;
    private RecyclerView mRecycler;

    public static UserAccount newInstance() {
        return new UserAccount();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.single_recyclerview_layout, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(UserAccount.this).get(UserViewModel.class);

        mCurrentUserUID = UserAccountArgs.fromBundle(getArguments()).getUserPosition();
        mRecycler = view.findViewById(R.id.userRecycler);
        mReportListAdapter = new ReportListAdapter(this);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mReportListAdapter);
        mViewModel.getUserReports(mCurrentUserUID).observe(this.getViewLifecycleOwner(), new Observer<List<Map<String, Object>>>() {
            @Override
            public void onChanged(List<Map<String, Object>> reports) {
                if(reports.size()>0){
                    mReportListAdapter.setReports(reports);
                    for(Map<String, Object> tempMap: reports){

                        for(String keys:tempMap.keySet()){
                            Log.d(TAG, "We got something: "+ keys);
                        }
                    }
                }
            }
        });


    }


    @Override
    public void onReportClick(Map<String, Object> chosenReport) {
        /*
        ManageReportsFragmentDirections.ActionManageReportsFragmentToUserAccount action = ManageReportsFragmentDirections.actionManageReportsFragmentToUserAccount(userUID);
        NavHostFragment.findNavController(ManageReportsFragment.this).navigate(action);
         */
        UserAccountDirections.ActionUserAccountToUserReportListFragment action = UserAccountDirections.actionUserAccountToUserReportListFragment((HashMap<String,Object>)chosenReport);
        NavHostFragment.findNavController(UserAccount.this).navigate(action);
    }
}

