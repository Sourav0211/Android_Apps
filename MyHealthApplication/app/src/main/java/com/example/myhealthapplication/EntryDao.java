package com.example.myhealthapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EntryDao {



    @Query("SELECT * From entry")
    List<Entry> getAll();


    @Insert
    void insertAll(Entry... entry);
    @Delete
    void delete (Entry entry);


}
