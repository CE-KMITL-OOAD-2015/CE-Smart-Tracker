package com.kaleido.cesmarttracker;

/**
 * Created by pirushprechathavanich on 9/8/15 AD.
 */
public class User {
    private String fullname, username, password, regCode, email;
    private int id;

    public User(String fullname, String username, String password, String regCode, String email, int id) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.regCode = regCode;
        this.email = email;
        this.id = id;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
