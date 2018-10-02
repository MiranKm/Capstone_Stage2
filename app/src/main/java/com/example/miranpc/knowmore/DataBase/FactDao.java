package com.example.miranpc.knowmore.DataBase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.miranpc.knowmore.Model.Fact;

import java.util.List;

@Dao
public interface FactDao {

    @Query("SELECT * FROM Fact")
    LiveData<List<Fact>> loadAllFacts();

    @Query("SELECT * FROM Fact  WHERE id = :id")
    Fact loadFactById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFact(Fact fact);

    @Delete()
    void deleteFact(Fact fact);

}
