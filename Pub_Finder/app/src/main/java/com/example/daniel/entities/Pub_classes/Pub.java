package com.example.daniel.entities.Pub_classes;

import com.j256.ormlite.field.DatabaseField;

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
    private String[] detailed_ratings; //Array but List would be better!
  @DatabaseField
    private OpeningDays openingDays;

    public Pub(String name,String description,String address)
    {
        this.name = name;
        this.description = description;
        this.address = address;
        this.rating = Rating.NotRatedYet;
        openingDays = new OpeningDays();

        //Optional!
        openingDays.FillWithRandomValues();
    }

    public String OpeningDays_ToString()
    {
        return openingDays.ToString();
    }

    public Integer getPub_id() {
        return pub_id;
    }

    public void setPub_id(Integer pub_id) {
        this.pub_id = pub_id;
    }

    //Properties
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

    public String[] getDetailed_ratings() {
        return detailed_ratings;
    }

    public void setDetailed_ratings(String[] detailed_ratings) {
        this.detailed_ratings = detailed_ratings;
    }

}


