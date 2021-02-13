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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserInfoViewModel extends AndroidViewModel {

    FirebaseUser mCurrentUser;
    private DataRepo mRepo;
    private LiveData<List<ReportEntity>> mAllReports;
    private MutableLiveData<List<Map<String, Object>>> mAllUsers;

    public UserInfoViewModel(@NonNull Application application) {
        super(application);
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mRepo = new DataRepo(application);
        mAllReports = mRepo.getAllReports();
        mAllUsers = mRepo.getAllUsers();
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
        return (String) mAllUsers.getValue().get(position).get("user_uid");
    }
    public LiveData<ArrayList<String>> getCookerTypes(){
        return mRepo.getCookerTypes();
    }

    public void addCookerInfo(String userId,Map<String, Object> userInfo ) {

        mRepo.addUserInfo(userId, userInfo);

    }
}


