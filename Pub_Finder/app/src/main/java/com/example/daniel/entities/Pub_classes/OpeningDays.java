package com.example.daniel.entities.Pub_classes;

import java.util.Random;

/**
 * Created by Balázs on 2016.03.26..
 */
public class OpeningDays {

    Day[] opening_days_array;

    public OpeningDays()
    {
        opening_days_array = new Day[7];
    }

    public boolean AddNewDay(Days day, int from_h, int from_m, int to_h, int to_m)
    {
        for (int i=0; i<opening_days_array.length;i++)
        {
            if(opening_days_array[i] != null && opening_days_array[i].getActualDay() == day)
            {
                return false;
            }
            if (opening_days_array[i] == null)
            {
                opening_days_array[i] = new Day(day,from_h,from_m,to_h,to_m);
                return true;
            }
        }
        return false;
    }

    public boolean RemoveDay(Days day)
    {
        for (int i=0; i<opening_days_array.length;i++)
        {
            if(opening_days_array[i] != null && opening_days_array[i].getActualDay() == day)
            {
                opening_days_array[i] = null;
                return true;
            }
        }
        return false;
    }

    public void RemoveAllDays()
    {
        opening_days_array = new Day[7];
    }

    public void FillWithRandomValues()
    {
        Random rnd = new Random();
        for (int i = 1; i<=opening_days_array.length;i++)
        {
            int from_hour = rnd.nextInt(14)+1;
            int from_min = rnd.nextInt(59);
            int to_min = rnd.nextInt(59);
            int to_hour = rnd.nextInt(8)+ from_hour;
            opening_days_array[i] = new Day(GetDayFromEnum(i),from_hour,from_min,to_hour,to_min);
        }
    }

    private Days GetDayFromEnum(int i)
    {
        switch(i)
        {
            case 1:
                return Days.Monday;
            case 2:
                return Days.Tuesday;
            case 3:
                return Days.Wednesday;
            case 4:
                return Days.Thursday;
            case 5:
                return Days.Friday;
            case 6:
                return Days.Saturday;
            case 7:
                return Days.Sunday;
        }
        return Days.Monday;
    }

    public String ToString()
    {
        String s = "";
        for(int i = 0; i< opening_days_array.length;i++)
        {
            if(opening_days_array[i] != null)
            s += opening_days_array[i].ToString()+"\n";
        }
        return s;
    }
}

