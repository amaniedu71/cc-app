package org.communitycookerfoundation.communitycookerfoundation.db;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.communitycookerfoundation.communitycookerfoundation.db.Dao.ReportDao;
import org.communitycookerfoundation.communitycookerfoundation.db.Dao.UserDao;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.UserEntity;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.ReportEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataRepo {
    private static final String TAG = "DataRepo";
    FirebaseFirestore mFirebaseDB;
    FirebaseUser mFirebaseUser;
    private String mUserId;
    private ReportDao mReportDao;
    private UserDao mUserDao;
    private LiveData<List<ReportEntity>> mAllReports;
    private LiveData<UserEntity> mUserEntity;
    private MutableLiveData<Boolean> mIsAdmin = new MutableLiveData<>();
    private Integer mUserCount;
    private Boolean misUserGen;
    private final Observer mObserveUserDB;
    private MutableLiveData<List<Map<String, Object>>> mUserList = new MutableLiveData<>();


    private MutableLiveData<List<Map<String, Object>>> mUserReports = new MutableLiveData<>();


    public LiveData<Boolean> getIsAdmin() {
        return mIsAdmin;
    }

   /* public DataRepo(boolean isAdminChecker, Application application, FirebaseUser firebaseUser){
        this(application, firebaseUser);
        if(isAdminChecker){

        }
    }
*/

    public DataRepo(Application application, FirebaseUser firebaseUser){


        ReportDB reportDB = ReportDB.getInstance(application);
        mReportDao  = reportDB.reportDao();
        mAllReports = mReportDao.getAllReports();
        UserDB userDB = UserDB.getInstance(application);
        mUserDao = userDB.userDao();
        mFirebaseDB = FirebaseFirestore.getInstance();
        mFirebaseUser = firebaseUser;
        mUserId = mFirebaseUser.getUid();


        /*UserDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mUserCount = mUserDao.getCount();
            }
        });

        assert mUserCount != null;
        if (mUserCount == 0) {
            generateUser();
        } else {
            mUserEntity = mUserDao.findByUserId(mFirebaseUser.getUid());

        }*/


        mObserveUserDB = new Observer<UserEntity>() {
            @Override
            public void onChanged(UserEntity userEntity) {
                if (userEntity != null) {
                    mIsAdmin.setValue(userEntity.getIsAdmin());

                } else {
                    checkAdminFb();
                    //generateUser();
                }
            }
        };
        mUserEntity =  mUserDao.findByUserId(firebaseUser.getUid());
        mUserEntity.observeForever(mObserveUserDB);
            /*@Override
            public void onChanged(Integer integer) {
                if (integer.equals(0)) {
                    checkAdmin();
                    generateUser();
                } else {
                    mUserEntity = mUserDao.findByUserId(mFirebaseUser.getUid());

                }
            }
        });*/

        //make sure to do this somewhere
       // mUserDao.getCount().removeObserver();


        //DocumentReference currentUser = mFirebaseDB.collection("users").document(mUserId);
        /*currentUser.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
          @Override
          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
              if (task.isSuccessful()) {
                  DocumentSnapshot document = task.getResult();
                  if (document.exists()) {
                      Log.w(TAG, "Document exists!");
                  } else {
                      Log.w(TAG, "Document does not exist!");
                      checkAdminFb();
                      generateUser();
                  }

              }
            }
        });*/

    }







    public void insertReportFb(ReportEntity reportToInsert) {
        // [START add_ada_lovelace]
        // Create a new user with a first and last name
        Map<String, Object> report = new HashMap<>();
        report.put("report_prompt", "QtyLiters");
        report.put("report_response", reportToInsert.getLiters());
        report.put("report_date", reportToInsert.getDate());
        int id = reportToInsert.getId();
        mFirebaseDB.collection(("users")).document(mUserId).collection("user_reports")
                .document("report_"+id)
                .set(report)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Report added successfully! ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding report", e);
                    }
                });
        // [END add_ada_lovelace]
    }

    private void generateUser() {



        //Firebase Logic
        String mUserName = mFirebaseUser.getDisplayName();
        Map<String, Object> user = new HashMap<>();
        user.put("name", mUserName);
        user.put("user_id", mUserId);

        assert mIsAdmin != null;
            // Add a new document with a generated ID
        mFirebaseDB.collection("users").document(mUserId)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "User Added!");
                        misUserGen = Boolean.TRUE;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        misUserGen = Boolean.FALSE;
                    }
                });

        //Database Logic
        if (mIsAdmin != null) {
            UserDB.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    mUserDao.insert(new UserEntity(mFirebaseUser.getDisplayName(), mFirebaseUser.getUid(), mIsAdmin.getValue()));
                }
            });


        }
    }



    public LiveData<List<ReportEntity>> getAllReports() {
        return mAllReports;
    }

    public void insertReportDB(final ReportEntity report) {
        ReportDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mReportDao.insert(report);
            }
        });
    }

    private void checkAdminFb() {
        /*mCurrentUser.getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() { // 1
            @Override
            public void onSuccess(GetTokenResult result) {
                mIsAdmin = result.getClaims().get("moderator"); // 2
                if (isModerator) { // 3
                    // Show moderator UI
                    showModeratorUI();
                } else {
                    // Show regular user UI.
                    showRegularUI();
                }
            }
        });*/



        DocumentReference docRef = mFirebaseDB.collection("private_data").document("access_list");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if ( document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Map<String,Object> roles = (Map<String, Object>) document.get("roles");
                        String admin = (String) roles.get(mFirebaseUser.getUid());
                        if(admin != null){
                            if(admin.contentEquals("owner")){
                                mIsAdmin.setValue( Boolean.TRUE);
                                generateUser();
                                return;
                            }
                            //other if statements

                        }
                        else
                            Log.d(TAG, "USER ADMIN CHECK IS CAN'T BE FOUND");
                            mIsAdmin.setValue(Boolean.FALSE);
                            generateUser();

                            return;

                    } else {
                        Log.d(TAG, "No such document");
                        mIsAdmin.setValue(Boolean.FALSE);
                        generateUser();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    mIsAdmin.setValue(Boolean.FALSE);
                    generateUser();

                }
            }

        });
       // mIsAdmin.setValue(Boolean.FALSE);

    }

    public void clearObservedData() {
        mUserEntity.removeObserver(mObserveUserDB);

    }

    public MutableLiveData<List<Map<String, Object>>> getAllUsers() {
        refreshUserList();
        return mUserList;
    }

    public void refreshUserList() {
        final List<Map<String,Object>> tempDocStorage = new ArrayList<Map<String, Object>>();
        mFirebaseDB.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "HERE IT IS "+ document.getId() + " => " + document.getData());
                                tempDocStorage.add(document.getData());

                            }
                            mUserList.setValue(tempDocStorage);
                            for (Map<String, Object> testMap : mUserList.getValue()){
                                for (String key: testMap.keySet()){
                                    Log.d(TAG, "KEY: "+ key + " VALUE: "+ testMap.get(key) );
                                }
                            }
                            //Log.d(TAG, "STORED: " +  tempDocStorage.get(0).get("name"));
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void refreshUserReports(String userUID) {

        mFirebaseDB.collection("users").document(userUID).collection("user_reports")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Map<String, Object>> tempDocStorage = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "HERE IT IS "+ document.getId() + " => " + document.getData());
                                tempDocStorage.add(document.getData());
                                mUserReports.setValue(tempDocStorage);

                            }
                            mUserList.setValue(tempDocStorage);
                            for (Map<String, Object> testMap : tempDocStorage){
                                for (String key: testMap.keySet()){
                                    Log.d(TAG, "KEY: "+ key + " VALUE: "+ testMap.get(key) );
                                }
                            }

                            //Log.d(TAG, "STORED: " +  tempDocStorage.get(0).get("name"));
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    public LiveData<List<Map<String, Object>>> getUserReports(String userUID) {

        refreshUserReports(userUID);
        return mUserReports;
    }

    public String getUserUID(int position) {
        if(mUserList.getValue() != null){
            return (String) mUserList.getValue().get(position).get("user_id");
        }
        return "null";
    }
    //TODO: REFACTOR THIS METHOD COMPLETELY!
    public void addUser(Map<String, String> cookerUser) {
        Map<String, Object> parentData = new HashMap<>();
        parentData.put("roles", cookerUser);
        // Add a new document with a generated ID
        mFirebaseDB.collection("private_data").document("access_list")
                .set(parentData, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "User Added!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });



    }
}
