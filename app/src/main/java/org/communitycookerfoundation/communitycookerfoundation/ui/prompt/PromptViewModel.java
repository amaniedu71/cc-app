package org.communitycookerfoundation.communitycookerfoundation.ui.prompt;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.communitycookerfoundation.communitycookerfoundation.db.DataRepo;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.ReportEntity;
import org.communitycookerfoundation.communitycookerfoundation.util.ReportPrompt;

import java.util.ArrayList;
import java.util.List;

public class PromptViewModel extends AndroidViewModel {
    FirebaseUser mCurrentUser;
    private String Prompt1;
    private String resp1;

    private DataRepo mRepo;
    private MutableLiveData<List<ReportEntity>> mReports = new MutableLiveData<>();
    private LiveData<List<ReportPrompt>> mReportPrompts;

    public PromptViewModel(@NonNull Application application) {
        super(application);
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mRepo = new DataRepo(application, mCurrentUser);
        mReportPrompts = mRepo.getAllPrompts();
        mReports.setValue(new ArrayList<ReportEntity>());

    }
    public LiveData<List<ReportPrompt>> getReportPrompts() {
        return mReportPrompts;
    }
    public void addReport(ReportEntity report, int ind) { mReports.getValue().set(ind, report); }
    public LiveData<List<ReportEntity>> getReports() {return mReports;}
    public void insertReports() {
        mRepo.insertReport(mReports.getValue());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        insertReports();
    }
}
