package org.communitycookerfoundation.communitycookerfoundation.db.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;


import org.communitycookerfoundation.communitycookerfoundation.db.Entity.UserEntity;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserEntity user);

    @Query("DELETE FROM user_table")
    void deleteAll();

    @Query("SELECT * from user_table LIMIT 1")
    LiveData<UserEntity> getUserLiveData();

    @Query("SELECT * from user_table LIMIT 1")
    UserEntity getUserNormal();



    @Query("SELECT COUNT(*) FROM user_table")
    LiveData<Integer> getCount();

}
