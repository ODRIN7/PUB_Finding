package com.example.daniel.pub_finder;

import android.content.Context;

import com.example.daniel.entities.User;
import com.example.daniel.facades.DataBaseHelper;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;

/**
 * Created by DANIEL on 2016. 03. 24..
 */
public class Login {

    DataBaseHelper<User> userDataBaseHelper;

    public Login(Context context) {
        this.userDataBaseHelper = new DataBaseHelper<User>(context,User.class);
    }

    public User doLogin(String email, String password) throws SQLException {
        Where<User, Integer>userIntegerWhere= userDataBaseHelper.getGenericDao().queryBuilder().where().eq("emailaddress",email).and().eq("password",(password));
        return userIntegerWhere.queryForFirst();
    }

}
