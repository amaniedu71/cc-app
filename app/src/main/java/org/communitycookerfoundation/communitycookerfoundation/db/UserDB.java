package org.communitycookerfoundation.communitycookerfoundation.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import org.communitycookerfoundation.communitycookerfoundation.db.Dao.ReportDao;
import org.communitycookerfoundation.communitycookerfoundation.db.Dao.UserDao;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.UserEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {UserEntity.class}, version = 2, exportSchema = false)
@TypeConverters({org.communitycookerfoundation.communitycookerfoundation.util.TypeConverters.class})
abstract public class UserDB extends RoomDatabase {
    private static volatile UserDB mInstance;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract UserDao userDao();



    public static UserDB getInstance(final Context context){
        if(mInstance == null){
            synchronized(UserDB.class){
                if(mInstance == null){
                    mInstance = Room.databaseBuilder(context.getApplicationContext(),
                            UserDB.class, "user_database").fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return mInstance;
    }


}
