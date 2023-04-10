package com.example.placekittenplaceholderimages.Data;

import androidx.room.Dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface KittenItemDAO  {



    @Insert
    void insertKittenItem(KittenItem k);


    @Query("Select * from KittenItem")
    List<KittenItem> getAllKittenItem();

    @Delete
    void deleteKittenItem(KittenItem k);





}
