package org.communitycookerfoundation.communitycookerfoundation.ui.success;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.fragment.NavHostFragment;

import org.communitycookerfoundation.communitycookerfoundation.R;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.ReportEntity;
import org.communitycookerfoundation.communitycookerfoundation.ui.prompt.PromptViewModel;

public class SuccessFragment extends Fragment {

    private PromptViewModel mViewModel;
    private Button btnBackHome;
    private ReportEntity mCurrentReport;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_success, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnBackHome = view.findViewById(R.id.btnback_home);
        NavBackStackEntry navBackStackEntry  = NavHostFragment.findNavController(SuccessFragment.this).getBackStackEntry(R.id.nav_add_report);
        mViewModel = new ViewModelProvider(navBackStackEntry).get(PromptViewModel.class);
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SuccessFragment.this).navigate(R.id.action_SuccessFragment_to_HomeFragment);
            }
        });
        mViewModel.getReports().observe(getViewLifecycleOwner(), new Observer<ReportEntity>() {
            @Override
            public void onChanged(ReportEntity reportEntity) {
                mViewModel.insertReport(reportEntity);

            }
        });

    }
}
