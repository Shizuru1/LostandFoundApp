package com.example.lostandfoundapp.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;

import java.io.Serializable;

@Entity(tableName = "item_table")
public class Item implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private boolean found;
    private String name;
    private String phone;
    private String description;
    private String date;
    private String location;
    private double lat;
    private double lng;

    public Item(int id, boolean found, String name, String phone, String description, String date, String location, double lat, double lng) {
        this.id = id;
        this.found = found;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.location = location;
        this.lat = lat;
        this.lng = lng;
    }
    public int getId() {
        return id;
    }

    public boolean isFound() {
        return found;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
