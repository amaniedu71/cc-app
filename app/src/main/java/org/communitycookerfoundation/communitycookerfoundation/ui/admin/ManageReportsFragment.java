package org.communitycookerfoundation.communitycookerfoundation.ui.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.communitycookerfoundation.communitycookerfoundation.R;
import org.communitycookerfoundation.communitycookerfoundation.adapters.AdminHomeChoiceAdapter;
import org.communitycookerfoundation.communitycookerfoundation.adapters.AdminUserAdapter;

import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageReportsFragment extends Fragment implements AdminUserAdapter.OnUserListener {

    private static final String TAG = /*this.getTag()*/ "ManageReportsFragment" ;
    private RecyclerView mRecyclerView;
    private AdminUserAdapter mAdminUserAdapter;
    //private LiveData<List<ReportEntity>> mReports;
    private ManageReportsViewModel mViewModel;
    private Button mRefreshButton;
    private int count;
    private TextView mWelcomeMessage;

    public ManageReportsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.admin_reports_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.admin_userlist_recycler);
        mRefreshButton = view.findViewById(R.id.refresh_button);
        mAdminUserAdapter = new AdminUserAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mAdminUserAdapter);
        mViewModel = new ViewModelProvider(this).get(ManageReportsViewModel.class);
        mRecyclerView.setNestedScrollingEnabled(false);
        mViewModel.getAllUsers().observe(getViewLifecycleOwner(), new Observer<List<Map<String, Object>>>() {
            @Override
            public void onChanged(List<Map<String, Object>> users) {
                Log.d(TAG, "Observed!");
                mAdminUserAdapter.setUsers(users);
                //mReportAdapter.setReports(users);
//                if(users.size()>0)
//                    mViewModel.insertFb(reportEntities.get(reportEntities.size()-1));
            }
        });


        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NavHostFragment.findNavController(AdminFragment.this).navigate(R.id.action_HomeFragment_to_Prompt1Fragment);
                mViewModel.refreshAllUsers();
            }
        });





    }


    @Override
    public void onUserClick(int position) {
        Log.d(TAG, "CLICKED!");
        String userUID = mViewModel.getUserUID(position);
        ManageReportsFragmentDirections.ActionManageReportsFragment2ToUserAccount action = ManageReportsFragmentDirections.actionManageReportsFragment2ToUserAccount(userUID);
        NavHostFragment.findNavController(ManageReportsFragment.this).navigate(action);

    }
}
