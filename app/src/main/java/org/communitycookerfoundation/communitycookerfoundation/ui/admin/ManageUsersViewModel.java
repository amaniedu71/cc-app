package org.communitycookerfoundation.communitycookerfoundation.ui.admin;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.communitycookerfoundation.communitycookerfoundation.db.DataRepo;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.ReportEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageUsersViewModel extends AndroidViewModel {

    FirebaseUser mCurrentUser;
    private DataRepo mRepo;
    private LiveData<List<ReportEntity>> mAllReports;
    private MutableLiveData<List<Map<String, Object>>> mAllUsers;

    public ManageUsersViewModel(@NonNull Application application) {
        super(application);
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mRepo = new DataRepo(application, mCurrentUser);
        mAllReports = mRepo.getAllReports();
        mAllUsers = mRepo.getAllUsers();
    }

    public LiveData<List<ReportEntity>> getAllReports() {return mAllReports; }
    public void insertFb(ReportEntity reportEntity) {mRepo.insertReportFb(reportEntity);}

    public String getDisplayName() {
        return mCurrentUser.getDisplayName();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mRepo.clearObservedData();
    }

    public MutableLiveData<List<Map<String, Object>>> getAllUsers() {
        return mAllUsers;
    }
    public void refreshAllUsers(){
        mRepo.refreshUserList();
    }

    public String getUserUID(int position) {
        return mRepo.getUserUID(position);
    }

    public void addCookerUser(String userTitle, String userEmail) {
        Map<String, String> cookerUser = new HashMap<>();
        cookerUser.put("name", userTitle);
        cookerUser.put("user_email", userEmail);
        mRepo.addUser(cookerUser);

    }
}


