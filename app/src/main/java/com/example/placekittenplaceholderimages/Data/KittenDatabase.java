package com.example.placekittenplaceholderimages.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {Kitten.class}, version = 1)
public abstract class KittenDatabase extends RoomDatabase {

    public abstract KittenDAO cmDAO();


}
