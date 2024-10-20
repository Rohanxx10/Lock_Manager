package com.example.lock;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StoreDoe {

    @Query("Select * from store")
    List<Store> getAllStore();

    @Insert
    void add(Store store);

    @Query("UPDATE store SET Name = :name, UserName = :userName, Password = :password WHERE id = :id\n")
    void updates(int id, String name, String userName,String password);


    @Delete
    void delete(Store store);


}
