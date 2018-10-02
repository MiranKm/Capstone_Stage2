package com.example.miranpc.knowmore.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.miranpc.knowmore.Model.Fact;

@Database(entities = {Fact.class}, version = 1, exportSchema = false)
public abstract class FactDB extends RoomDatabase {

    private static final String LOG_TAG = FactDB.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "fact";
    private static FactDB sInstance;

    public static FactDB getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        FactDB.class, FactDB.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract FactDao factDao();

}
