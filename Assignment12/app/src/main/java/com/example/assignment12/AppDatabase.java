package com.example.assignment12;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DetailsModel.class}, version = 7)
public abstract class AppDatabase extends RoomDatabase {
    public abstract Dao dao();

}
