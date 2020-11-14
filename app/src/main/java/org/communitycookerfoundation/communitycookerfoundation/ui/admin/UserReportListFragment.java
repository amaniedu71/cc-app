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
import org.communitycookerfoundation.communitycookerfoundation.adapters.UserReportListAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserReportListFragment extends Fragment  {
    public final static String TAG = "UserAccount";
//    private UserViewModel mViewModel;
    private HashMap<String, Object> mCurrentReport;
    private UserReportListAdapter mUserReportListAdapter;
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
//        mViewModel = new ViewModelProvider(navBackStackEntry).get(UserViewModel.class);
        mCurrentReport = (HashMap<String, Object>) UserReportListFragmentArgs.fromBundle(getArguments()).getReportPos();
        mRecycler = view.findViewById(R.id.userRecycler);
        mUserReportListAdapter = new UserReportListAdapter(getContext());
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mUserReportListAdapter);
        mUserReportListAdapter.setReports(mCurrentReport);


    }



}
