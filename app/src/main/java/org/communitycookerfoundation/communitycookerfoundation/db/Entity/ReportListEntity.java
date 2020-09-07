package org.communitycookerfoundation.communitycookerfoundation.db.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;


//@Entity(tableName = "report_table")
public class ReportListEntity extends BasicReportEntity{

//    @PrimaryKey(autoGenerate = true)
    private Integer id;

//    @ColumnInfo(name = "response")
    @NonNull
    private List<String> mResponses;

//    @ColumnInfo(name = "prompt")
    @NonNull
    private String mPrompt;


    public ReportListEntity(@NonNull String prompt, @NonNull List<String> responses) {
        this.mResponses = responses;
        this.mPrompt = prompt;

    }
    public List<String> getResponses() { return mResponses;}

    @NonNull
    public String getPrompt() {
        return mPrompt;
    }
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}



}
