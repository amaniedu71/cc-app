package org.communitycookerfoundation.communitycookerfoundation.db.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.communitycookerfoundation.communitycookerfoundation.db.Entity.ReportEntity;

import java.util.List;

@Dao
public interface ReportDao {
    @Insert
    void insert(ReportEntity report);

    @Query("DELETE FROM report_table")
    void deleteAll();

    @Query("SELECT * from report_table")
    LiveData<List<ReportEntity>> getAllReports();


}
