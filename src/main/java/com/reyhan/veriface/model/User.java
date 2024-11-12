package com.reyhan.veriface.model;


/*
IntelliJ IDEA 2024.2.3 (Community Edition)
Build #IC-242.23339.11, built on September 25, 2024
@Author Reyhan a.k.a. Reyhan Afrizal
Java Developer
Created on 11/5/2024 9:42 AM
@Last Modified 11/5/2024 9:42 AM
Version 1.0
*/

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "MstUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

    @Column(length = 20)
    private String namaUser;

    @Column(length = 8)
    private String nip;

    @Column(length = 64)
    private String password;

    @Column(length = 50)
    private String email;

    private Timestamp createdUserAt;

    private Timestamp modifiedUserAt;

    private Boolean isActiveUser = true;

    @PrePersist
    protected void onCreate() {
        createdUserAt = Timestamp.from(Instant.now());
        modifiedUserAt = createdUserAt;
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedUserAt = Timestamp.from(Instant.now());
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreatedUserAt() {
        return createdUserAt;
    }

    public void setCreatedUserAt(Timestamp createdUserAt) {
        this.createdUserAt = createdUserAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getModifiedUserAt() {
        return modifiedUserAt;
    }

    public void setModifiedUserAt(Timestamp modifiedUserAt) {
        this.modifiedUserAt = modifiedUserAt;
    }

    public Boolean getActiveUser() {
        return isActiveUser;
    }

    public void setActiveUser(Boolean activeUser) {
        isActiveUser = activeUser;
    }
}

