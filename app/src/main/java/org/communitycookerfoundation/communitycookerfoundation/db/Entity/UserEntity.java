package org.communitycookerfoundation.communitycookerfoundation.db.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "user_table")
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "name")
    @NonNull
    private String mName;

    @ColumnInfo(name = "isadmin")
    @NonNull
    private Boolean mIsAdmin;




    @ColumnInfo(name = "authuid")
    @NonNull
    private String mAuthUID;



    public UserEntity(@NonNull String name, String authUID, Boolean isAdmin) {
        this.mName = name;
        this.mAuthUID = authUID;
        this.mIsAdmin = isAdmin;
    }

    public String getName() { return mName;}

    public Boolean getIsAdmin() { return mIsAdmin;}

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    @NonNull
    public String getAuthUID() {
        return mAuthUID;
    }


}
