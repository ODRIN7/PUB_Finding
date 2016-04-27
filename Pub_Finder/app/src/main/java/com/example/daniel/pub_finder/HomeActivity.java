package com.example.daniel.pub_finder;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.daniel.entities.User;
import com.example.daniel.facades.DataBaseHelper;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;

public class HomeActivity extends AppCompatActivity {

    AppCompatSpinner pubSpinner;
    private User user;

    DataBaseHelper<User> userDataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        this.userDataBaseHelper = new DataBaseHelper<User>(this,User.class);
        Intent myIntent = getIntent();
        int iD =myIntent.getIntExtra("user_id",0);
        pubSpinner = (AppCompatSpinner) findViewById(R.id.spr_select);
        try {

            Where<User, Integer> userFind= userDataBaseHelper.getGenericDao().queryBuilder().where().eq("user_id",iD);
            user =userFind.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        pubSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if(pubSpinner.getSelectedItem().toString().equals("Logout"))
                {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class );
                    startActivity(intent);
                }
                else   if(pubSpinner.getSelectedItem().toString().equals("Pubs")){


                }

                Toast.makeText(HomeActivity.this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
