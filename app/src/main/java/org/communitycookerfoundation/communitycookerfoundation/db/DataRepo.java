package org.communitycookerfoundation.communitycookerfoundation.db;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.firebase.platforminfo.KotlinDetector;

import org.communitycookerfoundation.communitycookerfoundation.db.Dao.ReportDao;
import org.communitycookerfoundation.communitycookerfoundation.db.Dao.UserDao;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.BasicReportEntity;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.ReportEntity;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.ReportListEntity;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.UserEntity;
import org.communitycookerfoundation.communitycookerfoundation.util.ReportPrompt;
import org.communitycookerfoundation.communitycookerfoundation.util.ReportPromptCond;
import org.communitycookerfoundation.communitycookerfoundation.util.ReportPromptNum;
import org.communitycookerfoundation.communitycookerfoundation.util.ReportPromptOptional;
import org.communitycookerfoundation.communitycookerfoundation.util.ReportPromptTextChoices;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
   // private final Observer mObserveUserDB;
    private MutableLiveData<List<Map<String, Object>>> mUserList = new MutableLiveData<List<Map<String, Object>>>(new ArrayList<Map<String, Object>>());

    private MutableLiveData<List<String>> mUserRoles = new MutableLiveData<List<String>>(new ArrayList<String>());
    private MutableLiveData<List<Map<String, Object>>> mUserReports = new MutableLiveData<>();
    private MutableLiveData<List<ReportPrompt>> mAllPrompts = new MutableLiveData<>();
    private MutableLiveData<List<ReportPrompt>> mTestAdditionalPrompts = new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> mCookerTypes = new MutableLiveData<>();
    private final ReportDB mReportDB;


    public LiveData<Boolean> getIsAdmin() {
        return mIsAdmin;
    }

   /* public DataRepo(boolean isAdminChecker, Application application, FirebaseUser firebaseUser){
        this(application, firebaseUser);
        if(isAdminChecker){

        }
    }
*/

    public DataRepo(Application application){


        mReportDB = ReportDB.getInstance(application);
        mReportDao  = mReportDB.reportDao();
        mAllReports = mReportDao.getAllReports();
        UserDB userDB = UserDB.getInstance(application);
        mUserDao = userDB.userDao();
        mFirebaseDB = FirebaseFirestore.getInstance();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //check if different user is logged in

        mAllPrompts.setValue(new ArrayList<ReportPrompt>());
        if(mFirebaseUser != null) mUserId = mFirebaseUser.getUid();


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


       /* Observer<UserEntity> observeUserDB = new Observer<UserEntity>() {
            @Override
            public void onChanged(UserEntity userEntity) {
                if (userEntity != null) {
                    mUserRoles = userEntity.getRole();

                }
            }
        };
        mUserEntity =  mUserDao.getUserLiveData();
        mUserEntity.observeForever(observeUserDB);*/
                   /* @Override
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
        //mUserDao.getUser().removeObserver(observeUserDB);




    }

    public LiveData<List<String>> getUserRoles() {
//        UserDB.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                mUserRoles.setValue(mUserDao.getUserNormal().getRole());
//
//
//            }
//        });
        DocumentReference currentUser = mFirebaseDB.collection("users").document(mUserId);
        currentUser.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                if (doc.exists()) {
                    Log.w(TAG, "Document exists!");

                    final List<String> userRoles = (List<String>) doc.get("roles");
                    mUserRoles.setValue(userRoles);

                }
            }
        });
//        String[] fakeData = {"basic_set"};
//        mUserRoles.setValue(new ArrayList<>(Collections.singletonList("basic_set")));

        return mUserRoles;
    }


    public void newUser() {
        UserDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mReportDB.clearAllTables();

            }
        });
        /*
        DocumentReference currentUser = mFirebaseDB.collection("users").document(mUserId);
        currentUser.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                if(doc.exists()){
                    Log.w(TAG, "Document exists!");
                    final List<String> userRoles  = (List<String>)doc.get("roles");
                    UserDB.databaseWriteExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            UserEntity newUser = new UserEntity(mFirebaseUser.getDisplayName(), mFirebaseUser.getUid(), userRoles);
                            mUserDao.insert(newUser);
                            mUserRoles.setValue(userRoles);
                        }
                    });

                } else {

                    Log.w(TAG, "Document does not exist!");
                    //checkAdminFb();
                    generateUser();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "failed to fetch user!", e);
            }
        });


*/



    }


    private void generateUser() {



        //Firebase Logic
        String mUserName = mFirebaseUser.getDisplayName();
        Map<String, Object> user = new HashMap<>();
        user.put("name", mUserName);
        user.put("user_id", mUserId);

        //assert mIsAdmin != null;
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
//        if (mIsAdmin != null) {
//            UserDB.databaseWriteExecutor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    mUserDao.insert(new UserEntity(mFirebaseUser.getDisplayName(), mFirebaseUser.getUid(), mIsAdmin.getValue()));
//                }
//            });
//
//
//        }
    }


/*
 Room vs Firebase-
    - user profile Fb
    - user role Room & Firebase
    - details of other users  - listen for changes firebase
    - prompts and parameters

 */
    public LiveData<List<ReportEntity>> getAllReports() {
        return mAllReports;
    }

    public void insertReport(final List<BasicReportEntity> reportListToInsert) {
       /* ReportDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mReportDao.insert(report);
            }
        });
        //TODO: check room with firebase.
*/      List<Map<String,Object>> listToInsert = new ArrayList<>();
        String curDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        for(BasicReportEntity report:reportListToInsert) {

            Map<String, Object> reportToInsert = new HashMap<>();
            if(report instanceof ReportEntity ) {
                ReportEntity entity = (ReportEntity) report;
                //reportToInsert.put("report_prompt", entity.getPrompt());
                reportToInsert.put(entity.getPrompt(), entity.getResponse());
                // reportToInsert.put("report_date", curDate);
//                Log.d(TAG, "Inserting: "+ reportToInsert.keySet());
                listToInsert.add(reportToInsert);
            }
            else if(report instanceof ReportListEntity) {
                ReportListEntity entity = (ReportListEntity) report;
                //reportToInsert.put("report_prompt", entity.getPrompt());
                reportToInsert.put(entity.getPrompt(), entity.getResponses());
                // reportToInsert.put("report_date", curDate);
//                Log.d(TAG, "Inserting: "+ reportToInsert.keySet());
                listToInsert.add(reportToInsert);

            }
        }


        /*for (Map<String,Object> test: listToInsert){
            Log.d(TAG, "Inserting: " + test.keySet());
        }*/
        for(int ind = 0; ind<listToInsert.size(); ind++) {
            Map<String, Object> reportToInsert = listToInsert.get(ind);
            mFirebaseDB.collection(("users")).document(mUserId).collection("user_reports")
                    .document(curDate)
                    .set(reportToInsert, SetOptions.merge())
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
        }
    }

    public void clearObservedData() {
  //      mUserEntity.removeObserver(mObserveUserDB)
    }



    public MutableLiveData<List<Map<String, Object>>> getAllUsers() {
        refreshUserList();
        return mUserList;
    }

    public void refreshUserList() {
        final List<Map<String,Object>> tempDocStorage = new ArrayList<Map<String, Object>>();

        mFirebaseDB.collection("users")
                .orderBy("name")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.w(TAG, "Listening to all users failed", error);
                            return;
                    }
                        for (QueryDocumentSnapshot document : value) {
                            Log.d(TAG, "HERE IT IS "+ document.getId() + " => " + document.getData());
                            tempDocStorage.add(document.getData());

                        }
                        mUserList.setValue(tempDocStorage);
                        for (Map<String, Object> testMap : mUserList.getValue()){
                            for (String key: testMap.keySet()){
                                Log.d(TAG, "KEY: "+ key + " VALUE: "+ testMap.get(key) );
                            }
                        }
                        //Log.d(TAG, "STORED: " +  tempDocStorage.get(0).get("name"))
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
                            int index = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "HERE IT IS "+ document.getId() + " => " + document.getData());
                                tempDocStorage.add(document.getData());
                                tempDocStorage.get(index).put("report_date", document.getId());
                                index++;
                            }
                            mUserReports.setValue(tempDocStorage);

                         /*   for (Map<String, Object> testMap : tempDocStorage){
                                for (String key: testMap.keySet()){
                                    Log.d(TAG, "KEY: "+ key + " VALUE: "+ testMap.get(key) );
                                }
                            }*/

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

    public void getReport(String reportPath){

        //Get all report modules
        mFirebaseDB.collection("report_set").document(reportPath).collection("report_prompts")
                .orderBy("question_id").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                for (QueryDocumentSnapshot doc : value) {
                    if (doc.get("input_type") != null) {
                        String inputType = (String) doc.get("input_type");
                        if (inputType.contains("number")) {
                            ReportPromptNum reportPromptNum = doc.toObject(ReportPromptNum.class);
                            mAllPrompts.getValue().add(reportPromptNum);
                            Log.d(TAG, "Log Prompts: " + reportPromptNum.getQuestion() + "\nPrompt id:  " + reportPromptNum.getQuestion_id());
                        } else if (inputType.contains("conditional_bool")) {
                            ReportPromptCond reportPromptCond = doc.toObject(ReportPromptCond.class);
                            mAllPrompts.getValue().add(reportPromptCond);
                            Log.d(TAG, "Log Prompts: " + reportPromptCond.getQuestion() + "\nPrompt id:  " + reportPromptCond.getQuestion_id());
                        } else if (inputType.contains("text_choices")) {
                            ReportPromptTextChoices reportPromptTextChoices = doc.toObject(ReportPromptTextChoices.class);
                            mAllPrompts.getValue().add(reportPromptTextChoices);
                            Log.d(TAG, "Log Prompts: " + reportPromptTextChoices.getQuestion() + "\nPrompt id:  " + reportPromptTextChoices.getQuestion_id());
                        } else if (inputType.contains("optional_choices")) {
                            ReportPromptOptional reportPromptOptional = doc.toObject(ReportPromptOptional.class);
                            mAllPrompts.getValue().add(reportPromptOptional);
                            Log.d(TAG, "Log Prompts: " + reportPromptOptional.getQuestion() + "\nPrompt id:  " + reportPromptOptional.getQuestion_id());
                        }
                    }

                }
                Collections.sort(mAllPrompts.getValue(), new Comparator<ReportPrompt>() {
                    @Override
                    public int compare(ReportPrompt o1, ReportPrompt o2) {

                        return o1.getQuestion_id()<o2.getQuestion_id()? -1: o1.getQuestion_id() == o2.getQuestion_id()? 0 : 1;
                    }
                });
                mAllPrompts.setValue(mAllPrompts.getValue());
            }
        });
    }



    public LiveData<List<ReportPrompt>> getAllPrompts(List<String> userRoles){
//        Log.d(TAG, "path: "+ reportPath+" size: "+ mUserRoles.size());
        if(userRoles.size()>0) {
            boolean containsBasicSet = false;
            if(userRoles.contains("basic_set")){
                containsBasicSet = true;
                getReport("basic_set");
            }
            /*List<ReportPrompt> prompts = new ArrayList<>();

            mFirebaseDB.collection("report_set").document("basic_set").collection("report_prompts")
                    .orderBy("question_id").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }

                    List<ReportPrompt> prompts = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : value) {
                        if (doc.get("input_type") != null) {
                            String inputType = (String) doc.get("input_type");
                            if (inputType.contains("number")) {
                                ReportPromptNum reportPromptNum = doc.toObject(ReportPromptNum.class);
                                prompts.add(reportPromptNum);
                            } else if (inputType.contains("conditional_bool")) {
                                ReportPromptCond reportPromptCond = doc.toObject(ReportPromptCond.class);
                                prompts.add(reportPromptCond);
                            } else if (inputType.contains("text_choices")) {
                                ReportPromptTextChoices reportPromptTextChoices = doc.toObject(ReportPromptTextChoices.class);
                                prompts.add(reportPromptTextChoices);
                            } else if (inputType.contains("optional_choices")) {
                                ReportPromptOptional reportPromptOptional = doc.toObject(ReportPromptOptional.class);
                                prompts.add(reportPromptOptional);
                            }


                        }
                    }
                    mAllPrompts.getValue().add(prompts);
                    for (ReportPrompt logPrompt : prompts) {
                        Log.d(TAG, "Log Prompts: " + logPrompt);
                    }

                }
            });
*/
            for (final String reportPath : userRoles) {
                if(containsBasicSet && reportPath.equals("basic_set")){
                    continue;
                }
                getReport(reportPath);
            }
        }
        else{
            Log.d(TAG, "Roles are empty!");
        }

        return mAllPrompts;


    }



    /* public LiveData<List<ReportPrompt>> getTestPrompts() {

        MutableLiveData<List<ReportPrompt>> mTestPrompts = new MutableLiveData<>();
        List<ReportPrompt> prompts = new ArrayList<>();
        ReportPromptNum testQ1 = new ReportPromptNum("How many litres of water did you use", "number", "", 900, 0);
        ReportPromptNum testQ2 = new ReportPromptNum("How many cables of trash did you use", "number", "", 20, 0);
        prompts.add(testQ1);
        prompts.add(testQ2);
        mTestPrompts.setValue(prompts);
        return mTestPrompts;

    }
*/


    public void addUser(Map<String, String> cookerUser) {
        Map<String, Object> parentData = new HashMap<>();
        Map<String, String> tempData = new HashMap<>();
        tempData.put(cookerUser.get("email"), cookerUser.get("role"));
        parentData.put("roles", tempData);
        //String[] arr = (String[]) cookerUser.keySet().toArray();
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

    public void addUserInfo(String uid, Map<String, Object> userInfo) {
        // allows updating of user info

        mFirebaseDB.collection("users").document(uid)
                .set(userInfo, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "UserInfo Added!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding UserInfo", e);
                    }
                });

    }
    public LiveData<ArrayList<String>> getCookerTypes(){
        refreshCookerTypes();
        return mCookerTypes;
    }
    private void refreshCookerTypes(){

        mFirebaseDB.collection("private_data").document("cookers")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                ArrayList<String> tempList = (ArrayList<String>) doc.get("types");
                mCookerTypes.setValue(tempList);
            }
        });



    }

    public Task<String> exportReport(String uid) {
        Map<String , Object> data = new HashMap<>();
        data.put("uid", uid);
        FirebaseFunctions functions = FirebaseFunctions.getInstance();
        return functions
                .getHttpsCallable("exportData")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        return (String) task.getResult().getData();
                    }
                });

    }

    public LiveData<UserEntity> getLocalUserInfo() {
        return(mUserDao.getUserLiveData());

    }
}
