package com.example.expensetracker;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Query("SELECT * FROM expense")
    List<Expense> getAll();

    @Insert
    void insertAll(Expense... expenses);
    @Delete
    void delete(Expense expense);
}
