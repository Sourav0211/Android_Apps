package com.example.myhealthapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {


    AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(this,AppDatabase.class,"health-data-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();


        db.entryDao().insertAll(new Entry(0.5,15.6,4.5,5),
                new Entry(0.10,15.6,4.5,4),
                new Entry(12.4,4.5,4.9,3));


        Log.d("TAG", "onCreate: "+db.entryDao().getAll());
    }


}