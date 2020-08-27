package org.communitycookerfoundation.communitycookerfoundation.ui.admin;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.communitycookerfoundation.communitycookerfoundation.R;
import org.communitycookerfoundation.communitycookerfoundation.adapters.AdminReportAdapter;

import java.util.List;
import java.util.Map;

public class UserAccount extends Fragment {
    public final static String TAG = "UserAccount";
    private UserAccountViewModel mViewModel;
    private String mCurrentUserUID;
    private AdminReportAdapter mAdminReportAdapter;
    private RecyclerView mRecycler;

    public static UserAccount newInstance() {
        return new UserAccount();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_fragment, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel =  new ViewModelProvider(this).get(UserAccountViewModel.class);
        mCurrentUserUID = UserAccountArgs.fromBundle(getArguments()).getUserPosition();
        mRecycler = view.findViewById(R.id.userRecycler);
        mAdminReportAdapter = new AdminReportAdapter(getContext());
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdminReportAdapter);
        mViewModel.getUserReports(mCurrentUserUID).observe(this.getViewLifecycleOwner(), new Observer<List<Map<String, Object>>>() {
            @Override
            public void onChanged(List<Map<String, Object>> reports) {
                if(reports.size()>0){
                    mAdminReportAdapter.setReports(reports);
                    /*
                    for(Map<String, Object> tempMap: reports){
                        for(String keys:tempMap.keySet()){
                            Log.d(TAG, "We got something: "+ keys);
                        }
                    }*/


                }
            }
        });


    }



}
