package org.communitycookerfoundation.communitycookerfoundation.ui.admin;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.communitycookerfoundation.communitycookerfoundation.db.DataRepo;

import java.util.List;
import java.util.Map;

public class UserViewModel extends AndroidViewModel {

    private final FirebaseUser mCurrentUser;
    private final DataRepo mRepo;
    LiveData<List<Map<String,Object>>> mUserReportsTemp = new MutableLiveData<>();

    public UserViewModel(@NonNull Application application) {
        super(application);
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mRepo = new DataRepo(application);
    }


    public LiveData<List<Map<String,Object>>> getUserReports(String userUID){

        mUserReportsTemp = (mRepo.getUserReports(userUID));
        return getCacheUserReports();
    }

    public LiveData<List<Map<String, Object>>> getCacheUserReports(){
        return mUserReportsTemp;
    }


    public void exportReports(String currentUserUID) {
        mRepo.exportReport(currentUserUID);
    }
}
