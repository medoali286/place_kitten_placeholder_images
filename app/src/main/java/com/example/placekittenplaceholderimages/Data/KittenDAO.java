package com.example.placekittenplaceholderimages.Data;

import androidx.room.Dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface KittenDAO {



    @Insert
    void insertKittenItem(Kitten k);


    @Query("Select * from Kitten")
    List<Kitten> getAllKittenItem();

    @Delete
    void deleteKittenItem(Kitten k);





}
