package com.reyhan.veriface.dto;


/*
IntelliJ IDEA 2024.2.3 (Community Edition)
Build #IC-242.23339.11, built on September 25, 2024
@Author Reyhan a.k.a. Reyhan Afrizal
Java Developer
Created on 11/25/2024 4:27 PM
@Last Modified 11/25/2024 4:27 PM
Version 1.0
*/

public class LoginDTO {
    private String password;
    private String nip; // Keep nip for login

    // Getters and Setters

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }
}
