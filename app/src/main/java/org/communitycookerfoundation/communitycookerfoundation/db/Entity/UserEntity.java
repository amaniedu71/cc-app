package org.communitycookerfoundation.communitycookerfoundation.db.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;


@Entity(tableName = "user_table")
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "name")
    @NonNull
    private String mName;

    @ColumnInfo(name = "role")
    @NonNull
    private List<String>  mRole;




    @ColumnInfo(name = "authuid")
    @NonNull
    private String mAuthUID;



    public UserEntity(@NonNull String name, @NonNull String authUID, @NonNull List<String> role) {
        this.mName = name;
        this.mAuthUID = authUID;
        this.mRole= role;
    }

    public String getName() { return mName;}

    public List<String> getRole() { return mRole;}

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    @NonNull
    public String getAuthUID() {
        return mAuthUID;
    }


}
