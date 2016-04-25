package com.example.daniel.entities.Pub_classes;

/**
 * Created by Balázs on 2016.03.26..
 */


enum Days {Monday, Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday};

public class Day {

    private Days actualDay;
    private int from_hour;
    private int from_min;
    private int to_hour;
    private int to_min;

    //Constructor
    public Day(Days day, int from_hour, int from_min, int to_hour, int to_min) {
        if(!Control(from_hour,from_min,to_hour,to_min))
            throw new IllegalArgumentException("Given arguments are not valid!");
        this.actualDay = day;
        this.from_hour = from_hour;
        this.from_min = from_min;
        this.to_hour = to_hour;
        this.to_min = to_min;
    }

    //ToString
    public String ToString()
    {
        return actualDay+": "+from_hour+":"+from_min+" - "+to_hour+":"+to_min;
    }

    private boolean Control(int from_hour, int from_min, int to_hour, int to_min)
    {
        return (from_hour < 0 || from_hour > 23 || to_hour > 23 || to_hour < 0 || from_min < 0 || from_min > 59 || to_min < 0 || to_min> 59) ? false : true;
    }


    //Properties of private fields

    public Days getActualDay() {
        return actualDay;
    }

    public void setActualDay(Days actualDay) {
        this.actualDay = actualDay;
    }

    public int getFrom_hour() {
        return from_hour;
    }

    public void setFrom_hour(int from_hour) {
        this.from_hour = from_hour;
    }

    public int getFrom_min() {
        return from_min;
    }

    public void setFrom_min(int from_min) {
        this.from_min = from_min;
    }

    public int getTo_hour() {
        return to_hour;
    }

    public void setTo_hour(int to_hour) {
        this.to_hour = to_hour;
    }

    public int getTo_min() {
        return to_min;
    }

    public void setTo_min(int to_min) {
        this.to_min = to_min;
    }
}
