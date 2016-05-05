package com.example.daniel.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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
    private MyPubRating rating;

    @DatabaseField
    private double lat;
    @DatabaseField
    private double lon;
    @DatabaseField
    private String drinkURI;
    @DatabaseField
    private String phonenumber;

    public static final String User_ID_FIELD = "user_id";
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = User_ID_FIELD)
    private User user;

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
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

    public Pub(String name, String description, String address, MyPubRating rating, double lat, double lon, String drinkURI, String phonenumber) {


        this.name = name;
        this.description = description;
        this.address = address;
        this.rating = rating;
        this.lat = lat;
        this.lon = lon;
        this.drinkURI = drinkURI;
        this.phonenumber = phonenumber;
        this.user = null;
    }

    public Pub(String name, String description, String address)
    {
        this.name = name;
        this.description = description;
        this.address = address;
        this.rating = MyPubRating.NotRatedYet;
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

    public MyPubRating getRating() {
        return rating;
    }

    public void setRating(MyPubRating rating) {
        this.rating = rating;
    }


}


