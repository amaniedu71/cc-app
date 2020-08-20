package org.communitycookerfoundation.communitycookerfoundation.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.DocumentReference;

import org.communitycookerfoundation.communitycookerfoundation.db.DataRepo;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.ReportEntity;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    FirebaseUser mCurrentUser;
    private LiveData<Boolean> mIsAdmin;
    private DataRepo mRepo;
    private LiveData<List<ReportEntity>> mAllReports;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mRepo = new DataRepo(application, mCurrentUser);
        mAllReports = mRepo.getAllReports();
        mIsAdmin = mRepo.getIsAdmin();

    }

    public LiveData<List<ReportEntity>> getAllReports() {return mAllReports; }
    public void insertFb(ReportEntity reportEntity) {mRepo.insertReportFb(reportEntity);}

    public LiveData<Boolean> isAdmin() {
        return mIsAdmin;
    }

    public String getDisplayName() {
        return mCurrentUser.getDisplayName();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mRepo.clearObservedData();
    }
}
