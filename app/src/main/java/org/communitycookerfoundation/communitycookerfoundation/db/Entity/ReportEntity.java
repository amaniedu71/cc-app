package org.communitycookerfoundation.communitycookerfoundation.db.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "report_table")
public class ReportEntity {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "liters")
    @NonNull
    private String mLiters;


    @ColumnInfo(name = "prompt1")
    @NonNull
    private String mPrompt1;
    @NonNull
    private String mDate;


    public ReportEntity(@NonNull String prompt1, @NonNull String liters, String date) {
        this.mLiters = liters;
        this.mDate = date;
        this.mPrompt1 = prompt1;
    }
    public String getDate() { return mDate;}
    public String getLiters() { return mLiters;}

    @NonNull
    public String getPrompt1() {
        return mPrompt1;
    }
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}



}
