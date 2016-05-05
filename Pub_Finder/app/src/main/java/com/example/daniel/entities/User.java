package com.example.daniel.entities;

import com.example.daniel.facades.DataBaseHelper;
import com.j256.ormlite.dao.EagerForeignCollection;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.dao.LazyForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DANIEL on 2016. 03. 24..
 */
@DatabaseTable
public class User {
    @DatabaseField(generatedId = true)
    private Integer user_id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String password;
    @DatabaseField
    private String emailaddress;
    @ForeignCollectionField(eager = true)
    private ForeignCollection<Pub> best_pub;

    public Integer getUser_id() {
        return user_id;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ForeignCollection<Pub> getBest_pub() {
        return best_pub;
    }

    public void setBest_pub(ForeignCollection<Pub> best_pub) {
        this.best_pub = best_pub;
    }

    public User() throws SQLException {


    }


}
