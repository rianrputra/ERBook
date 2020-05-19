package com.riandev.erbook.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Book implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public long id;
    public String title, category, isbn, releasedate;
}
