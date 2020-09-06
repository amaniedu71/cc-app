package org.communitycookerfoundation.communitycookerfoundation.db.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "report_table")
public class ReportEntity {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "response")
    @NonNull
    private String mResponse;

    @ColumnInfo(name = "prompt")
    @NonNull
    private String mPrompt;


    public ReportEntity(@NonNull String prompt, @NonNull String response) {
        this.mResponse = response;
        this.mPrompt = prompt;

    }
    public String getResponse() { return mResponse;}

    @NonNull
    public String getPrompt() {
        return mPrompt;
    }
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}



}
