package org.communitycookerfoundation.communitycookerfoundation.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.DocumentReference;

import org.communitycookerfoundation.communitycookerfoundation.db.DataRepo;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.ReportEntity;

import java.util.List;

public class UserHomeViewModel extends AndroidViewModel {

    FirebaseUser mCurrentUser;
    private MutableLiveData<Boolean> mIsAdmin = new MutableLiveData<>();
    private DataRepo mRepo;
    private LiveData<List<ReportEntity>> mAllReports;
    private MutableLiveData<Boolean> mIsAuthdUser = new MutableLiveData<>();

    public UserHomeViewModel(@NonNull Application application) {
        super(application);
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mRepo = new DataRepo(application, mCurrentUser);
        mAllReports = mRepo.getAllReports();
        mIsAdmin.setValue(Boolean.FALSE);
        mIsAuthdUser.setValue(Boolean.FALSE);
        isAdmin();
        IsAuthdUser();

    }

    private void IsAuthdUser() {

        mCurrentUser.getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() { // 1
            @Override
            public void onSuccess(GetTokenResult result) {
                try{
                    boolean isAuthdUser = (boolean) result.getClaims().get("authd_user"); // 2
                    if (isAuthdUser) {
                        mIsAuthdUser.setValue(Boolean.TRUE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }

            }
        });

    }

    private void isAdmin() {
        mCurrentUser.getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() { // 1
            @Override
            public void onSuccess(GetTokenResult result) {

                try{
                    boolean isAdmin = (boolean) result.getClaims().get("admin"); // 2
                    if (isAdmin) {
                        mIsAdmin.setValue(Boolean.TRUE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public LiveData<List<ReportEntity>> getAllReports() {return mAllReports; }
    //public void insertFb(ReportEntity reportEntity) {mRepo.insertReportFb(reportEntity);}

    public LiveData<Boolean> getIsAdmin() {
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

    public LiveData<Boolean> getIsAuthdUser() {
        
        return mIsAuthdUser; 
    }
}
