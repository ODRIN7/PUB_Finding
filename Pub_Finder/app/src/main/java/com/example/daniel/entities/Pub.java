package com.example.daniel.entities;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Balázs on 2016.03.26..
 */

enum Rating {
    Perfect,
    Very_nice,
    Nice,
    Middling,
    Bad,
    Very_bad,
    Horrible,
    NotRatedYet}
@DatabaseTable
public class Pub {
    @DatabaseField(generatedId = true)
    private Integer pub_id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String description;
    @DatabaseField
    private String address;
    @DatabaseField
    private Rating rating;

    @DatabaseField
    private Integer lat;
    @DatabaseField
    private Integer lon;
    @DatabaseField
    private String drinkURI;

    public static final String User_ID_FIELD = "user_id";
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = User_ID_FIELD)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getLat() {
        return lat;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

    public Integer getLon() {
        return lon;
    }

    public void setLon(Integer lon) {
        this.lon = lon;
    }

    public String getDrinkURI() {
        return drinkURI;
    }

    public void setDrinkURI(String drinkURI) {
        this.drinkURI = drinkURI;
    }

    public Pub() {

    }

    public Pub(String name, String description, String address)
    {
        this.name = name;
        this.description = description;
        this.address = address;
        this.rating = Rating.NotRatedYet;
    }

    public Integer getPub_id() {
        return pub_id;
    }

    public void setPub_id(Integer pub_id) {
        this.pub_id = pub_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }


}


