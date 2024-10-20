package com.example.lock;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Store.class,exportSchema = false,version = 2)
public abstract class dataBaseHelper extends RoomDatabase {

    private static final String DB_NAME="storedb";

   public static dataBaseHelper instanse;

    public static synchronized dataBaseHelper getDB(Context context){

        if(instanse==null){
            instanse= Room.databaseBuilder(context,dataBaseHelper.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return instanse;
    }

    public abstract StoreDoe storeDoe();

}