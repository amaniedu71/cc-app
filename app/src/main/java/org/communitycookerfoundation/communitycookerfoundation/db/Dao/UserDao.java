package org.communitycookerfoundation.communitycookerfoundation.db.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;


import org.communitycookerfoundation.communitycookerfoundation.db.Entity.UserEntity;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserEntity user);

    @Query("DELETE FROM user_table")
    void deleteAll();

    @Query("SELECT * from user_table")
    LiveData<List<UserEntity>> getAllUsers();

    @Query("SELECT * FROM user_table WHERE authuid = :id")
    LiveData<UserEntity> findByUserId(String id);

    @Query("SELECT COUNT(*) FROM user_table")
    LiveData<Integer> getCount();

}
