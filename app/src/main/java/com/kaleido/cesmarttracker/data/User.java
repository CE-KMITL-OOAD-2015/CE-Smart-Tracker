package com.kaleido.cesmarttracker.data;

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

    public String getFullname() {
        return this.fullname;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getRegCode() {
        return this.regCode;
    }

    public String getEmail() {
        return this.email;
    }

    public int getID() {
        return this.id;
    }
}
