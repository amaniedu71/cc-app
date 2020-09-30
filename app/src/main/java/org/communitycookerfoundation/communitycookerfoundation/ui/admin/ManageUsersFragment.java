package org.communitycookerfoundation.communitycookerfoundation.ui.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
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
import org.communitycookerfoundation.communitycookerfoundation.adapters.AdminUserAdapter;
import org.communitycookerfoundation.communitycookerfoundation.ui.prompt.PromptFragment;

import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageUsersFragment extends Fragment implements AdminUserAdapter.OnUserListener {

    private static final String TAG = /*this.getTag()*/ "ManageUsersFragment" ;
    private RecyclerView mRecyclerView;
    private AdminUserAdapter mAdminUserAdapter;
    //private LiveData<List<ReportEntity>> mReports;
    private ManageUsersViewModel mViewModel;
    private Button mAddButton;
    private int count;
    private TextView mWelcomeMessage;

    public ManageUsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.manage_users_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.admin_add_userlist_recycler);
        mAddButton = view.findViewById(R.id.add_user_button);
        mAdminUserAdapter = new AdminUserAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mAdminUserAdapter);
        mViewModel = new ViewModelProvider(this).get(ManageUsersViewModel.class);
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


        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ManageUsersFragment.this).navigate(R.id.action_manageUsersFragment_to_addCookerFragment);


            }
        });





    }


    @Override
    public void onUserClick(int position) {
        Log.d(TAG, "CLICKED!");
//        String userUID = mViewModel.getUserUID(position);
//        ManageReportsFragmentDirections.ActionManageReportsFragmentToUserAccount action = ManageReportsFragmentDirections.actionManageReportsFragmentToUserAccount(userUID);
//        NavHostFragment.findNavController(ManageUsersFragment.this).navigate(action);

    }


}
