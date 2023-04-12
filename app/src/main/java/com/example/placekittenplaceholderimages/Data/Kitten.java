package com.example.placekittenplaceholderimages.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Kitten {



       @ColumnInfo(name = "id")
        @PrimaryKey(autoGenerate=true)
        public int id;

        @ColumnInfo(name="width")
        protected String width;


        @ColumnInfo(name="height")
        protected String height;


    @ColumnInfo(name="date")
    protected String date;

    @ColumnInfo(name="imagePath")
    protected String imagePath;

    public Kitten(String width, String height, String date, String imagePath) {
        this.width = width;
        this.height = height;
        this.date = date;
        this.imagePath = imagePath;
    }


    public int getId() {
        return id;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

    public String getDate() {
        return date;
    }

    public String getImagePath() {
        return imagePath;
    }
}
