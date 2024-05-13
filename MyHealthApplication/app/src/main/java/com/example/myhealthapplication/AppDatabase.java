package com.example.myhealthapplication;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Entry.class , version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EntryDao entryDao();
}
