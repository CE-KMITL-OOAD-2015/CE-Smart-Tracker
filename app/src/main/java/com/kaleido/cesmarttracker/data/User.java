package com.kaleido.cesmarttracker.data;

/**
 * Created by pirushprechathavanich on 9/8/15 AD.
 */
public class User {
    private String id;
    private String email;
    private String username;
    private String password;
    private int role;
    private String facebookId;

    public User() {

    }

    public User(String id, String email, String username, String password, int role) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String email, String username, String password, int role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String email, String username, String password) { //Default role set to 2(Student)
        this.email = email;
        this.username = username;
        this.password = password;
        role = 2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }
}


//public class User {
//    private String fullname, username, password, regCode, email;
//    private int id;
//
//    public User(String fullname, String username, String password, String regCode, String email, int id) {
//        this.fullname = fullname;
//        this.username = username;
//        this.password = password;
//        this.regCode = regCode;
//        this.email = email;
//        this.id = id;
//    }
//
//    public User(String username, String password) {
//        this.username = username;
//        this.password = password;
//    }
//
//    public String getFullname() {
//        return this.fullname;
//    }
//
//    public String getUsername() {
//        return this.username;
//    }
//
//    public String getPassword() {
//        return this.password;
//    }
//
//    public String getRegCode() {
//        return this.regCode;
//    }
//
//    public String getEmail() {
//        return this.email;
//    }
//
//    public int getID() {
//        return this.id;
//    }
//}
