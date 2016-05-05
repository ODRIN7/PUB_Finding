package com.example.daniel.pub_finder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;

import com.example.daniel.entities.User;
import com.example.daniel.facades.DataBaseHelper;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;

public class activity_fragment_layout extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_fragment_layout);

    }


}
