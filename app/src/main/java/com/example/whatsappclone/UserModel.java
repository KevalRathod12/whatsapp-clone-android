package com.example.whatsappclone;

/**
 * UserModel — Firebase ma store thayel user ane represent kare che
 * /users/{uid}/ node ma save thay che
 *
 * Fields:
 *   uid   — Firebase Authentication UID
 *   name  — user nu naam
 *   email — user no email
 */
public class UserModel {

    public String uid;
    public String name;
    public String email;

    // Firebase requires a no-arg constructor to deserialize data
    public UserModel() {}

    public UserModel(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }
}
