package org.communitycookerfoundation.communitycookerfoundation.ui.prompt;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.communitycookerfoundation.communitycookerfoundation.db.DataRepo;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.BasicReportEntity;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.ReportListEntity;
import org.communitycookerfoundation.communitycookerfoundation.util.ReportPrompt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromptViewModel extends AndroidViewModel {
    FirebaseUser mCurrentUser;
    private String Prompt1;
    private String resp1;
    private MutableLiveData<Map<Integer, BasicReportEntity>> mReports = new MutableLiveData<>();
    private DataRepo mRepo;
    private List<BasicReportEntity> insertReport = new ArrayList<>();
    private LiveData<List<ReportPrompt>> mReportPrompts;
    private MutableLiveData<List<ReportListEntity>> mReportsList = new MutableLiveData<>();

    public PromptViewModel(@NonNull Application application) {
        super(application);
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mRepo = new DataRepo(application, mCurrentUser);
        mReportPrompts = mRepo.getAllPrompts();
        mReports.setValue(new HashMap<Integer, BasicReportEntity>());

    }
    public LiveData<List<ReportPrompt>> getReportPrompts() {
        return mReportPrompts;
    }
    public void addReport(BasicReportEntity report, int ind) {
        //TODO:fix ind here doesn't work
        mReports.getValue().put(ind, report);
    }



    public MutableLiveData<Map<Integer, BasicReportEntity>> getReports() {return mReports;}
    public void insertReports() {
        List<Integer> keys = new ArrayList<>(mReports.getValue().keySet());
        Collections.sort(keys);
        List<BasicReportEntity> finalTmp = new ArrayList<>();
        for (int k : keys) {
            finalTmp.add(mReports.getValue().get(k));
        }
        mRepo.insertReport(finalTmp);

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        insertReports();
    }
}
