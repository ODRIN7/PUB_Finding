package com.example.daniel.pub_finder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.daniel.entities.Pub;
import com.example.daniel.entities.User;
import com.example.daniel.facades.DataBaseHelper;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;

public class GpsActivity extends AppCompatActivity {
    Pub pub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("TAG", "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("TAG", "An error occurred: " + status);
            }
        });
        DataBaseHelper<Pub> pubDataBaseHelper = new DataBaseHelper<>(this, Pub.class);
        Intent myIntent = getIntent();
        int iD = myIntent.getIntExtra("pub_id", 0);
        try {

            Where<Pub, Integer> pubwhere = pubDataBaseHelper.getGenericDao().queryBuilder().where().eq("pub_id", iD);
            pub = pubwhere.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
