

package org.communitycookerfoundation.communitycookerfoundation.ui.admin;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.communitycookerfoundation.communitycookerfoundation.R;
import org.communitycookerfoundation.communitycookerfoundation.adapters.AdminHomeChoiceAdapter;
import org.communitycookerfoundation.communitycookerfoundation.adapters.AdminUserAdapter;

import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminFragment extends Fragment implements AdminHomeChoiceAdapter.OnUserListener {
    private static final String TAG = /*this.getTag()*/ "AdminFragment.class" ;
    private RecyclerView mRecyclerView;
    private AdminHomeChoiceAdapter mAdminUserAdapter;
    //private LiveData<List<ReportEntity>> mReports;
    private AdminViewModel mViewModel;
    private Button mRefreshButton;
    private int count;
    private TextView mWelcomeMessage;

    public AdminFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.admin_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.home_recycler);
        mViewModel = new ViewModelProvider(this).get(AdminViewModel.class);
       // mReportAdapter = new ReportAdapter(getActivity());
      //  mRecyclerView.setAdapter(mReportAdapter);
        mAdminUserAdapter = new AdminHomeChoiceAdapter(this);
        mRecyclerView.setAdapter(mAdminUserAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setNestedScrollingEnabled(false);



        //count = 0;
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



        mWelcomeMessage = view.findViewById(R.id.admWelcomeText);
        String welcome = getString(R.string.welcome_message) + " "+ mViewModel.getDisplayName();
        mWelcomeMessage.setText(welcome);

    }

    @Override
    public void onUserClick(int position) {
        if(position == 0){
            Log.d(TAG, "CLICKED MANAGE USERS");
            NavHostFragment.findNavController(AdminFragment.this).navigate(R.id.action_adminFragment_to_manageUsersFragment);

        }
        else if(position == 1){
            Log.d(TAG, "CLICKED MANAGE REPORTS");
            NavHostFragment.findNavController(AdminFragment.this).navigate(R.id.action_adminFragment_to_manageReportsFragment2);


        }


    }
}

