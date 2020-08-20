package org.communitycookerfoundation.communitycookerfoundation.ui.admin;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.communitycookerfoundation.communitycookerfoundation.db.DataRepo;

import java.util.List;
import java.util.Map;

public class UserAccountViewModel extends AndroidViewModel {

    private final FirebaseUser mCurrentUser;
    private final DataRepo mRepo;

    public UserAccountViewModel(@NonNull Application application) {
        super(application);
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mRepo = new DataRepo(application, mCurrentUser);

    }


    public LiveData<List<Map<String,Object>>> getUserReports(String userUID){
        return mRepo.getUserReports(userUID);
    }



}
