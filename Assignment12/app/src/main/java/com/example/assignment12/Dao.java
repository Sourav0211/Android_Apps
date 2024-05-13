package com.example.assignment12;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@androidx.room.Dao
public interface Dao {
    @Query("SELECT * FROM details ORDER BY date ASC")
    List<DetailsModel> getAll();

    @Insert
    void insertAll(DetailsModel... detailsModels);
    @Delete
    void delete(DetailsModel detailsModel);
}
