package entities.Pub_classes;

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
    private String name;
    private String description;
    private String address;
    private Rating rating;
    private String[] detailed_ratings; //Array but List would be better!
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


