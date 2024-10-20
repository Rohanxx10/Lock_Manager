package com.example.lock;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "store")
public class Store {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Name")
    private String name;


    @ColumnInfo(name = "UserName")
    private String username;

    @ColumnInfo(name = "Password")
    private String password;

    // Constructor with ID
    public Store(int id, String username, String password,String name) {
        this.id = id;
        this.name=name;
        this.username = username;  // Updated from userName to username
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Constructor without ID
    @Ignore
    public Store(String username, String password,String name) {
        this.username = username;  // Updated from userName to username
        this.password = password;
        this.name=name;
    }




}
