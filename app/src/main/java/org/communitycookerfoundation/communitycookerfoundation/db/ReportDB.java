package org.communitycookerfoundation.communitycookerfoundation.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.communitycookerfoundation.communitycookerfoundation.db.Dao.ReportDao;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.ReportEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ReportEntity.class}, version = 3, exportSchema = false)
abstract public class ReportDB extends RoomDatabase {
    private static volatile ReportDB mInstance;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract ReportDao reportDao();



    public static ReportDB getInstance(final Context context){
        if(mInstance == null){
            synchronized(ReportDB.class){
                if(mInstance == null){
                    mInstance = Room.databaseBuilder(context.getApplicationContext(),
                            ReportDB.class, "word_database").fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return mInstance;
    }


}
