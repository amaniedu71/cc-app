package org.communitycookerfoundation.communitycookerfoundation.ui.prompt;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

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

    private MutableLiveData<List<BasicReportEntity>> mReports = new MutableLiveData<>();
    private DataRepo mRepo;
    private List<BasicReportEntity> insertReport = new ArrayList<>();



    private LiveData<List<ReportPrompt>> mReportPrompts;
    private MutableLiveData<List<ReportListEntity>> mReportsList = new MutableLiveData<>();

    public PromptViewModel(@NonNull Application application) {
        super(application);
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mRepo = new DataRepo(application);

        mReports.setValue(new ArrayList<Integer>());

    }



    public LiveData<List<ReportPrompt>> getReportPrompts( ) {



        return Transformations.switchMap(mRepo.getUserRoles(), new Function<List<String>, LiveData<List<ReportPrompt>>>() {
            @Override
            public LiveData<List<ReportPrompt>> apply(List<String> input) {
                return mRepo.getAllPrompts(input);
            }
        });
    }
    public void addReport(BasicReportEntity report, int ind) {
        //TODO:fix ind here doesn't work
        mReports.getValue().put(ind, report);

    }



    public LiveData<Map<Integer, BasicReportEntity>> getReports() {
        return mReports;

    }
    public void insertReports() {

        mRepo.insertReport(mReports.getValue());

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        insertReports();
    }

    public void removeReport(int ind) {
        mReports.getValue().remove(ind);

    }
    public void removeReportsRange(int fst, int lst){
        if(lst<mReports.getValue().size())
            mReports.getValue().subList(fst, lst).clear();
    }
}
