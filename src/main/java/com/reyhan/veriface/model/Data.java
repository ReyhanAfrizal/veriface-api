package com.reyhan.veriface.model;


/*
IntelliJ IDEA 2024.2.3 (Community Edition)
Build #IC-242.23339.11, built on September 25, 2024
@Author Reyhan a.k.a. Reyhan Afrizal
Java Developer
Created on 11/5/2024 9:41 AM
@Last Modified 11/5/2024 9:41 AM
Version 1.0
*/
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "MstData")
public class Data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String namaKonsumen;

    @Column(length = 200)
    private String fotoKtp;

    @Column(length = 200)
    private String fotoSelfie;

    @Column(length = 200)
    private String notes;

    @Column(length = 200)
    private String result;

    @Column(length = 20)
    private String createdDataBy;

    private Timestamp createdDataAt;

    @Column(length = 20)
    private String modifiedDataBy;

    private Timestamp modifiedDataAt;

    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    protected void onCreate() {
        createdDataAt = Timestamp.from(Instant.now());
        modifiedDataAt = createdDataAt;
        user = new User(); // Set default user ID
        user.setIdUser(1); // Default user ID
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDataAt = Timestamp.from(Instant.now());
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamaKonsumen() {
        return namaKonsumen;
    }

    public void setNamaKonsumen(String namaKonsumen) {
        this.namaKonsumen = namaKonsumen;
    }

    public String getFotoKtp() {
        return fotoKtp;
    }

    public void setFotoKtp(String fotoKtp) {
        this.fotoKtp = fotoKtp;
    }

    public String getFotoSelfie() {
        return fotoSelfie;
    }

    public void setFotoSelfie(String fotoSelfie) {
        this.fotoSelfie = fotoSelfie;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCreatedDataBy() {
        return createdDataBy;
    }

    public void setCreatedDataBy(String createdDataBy) {
        this.createdDataBy = createdDataBy;
    }

    public Timestamp getCreatedDataAt() {
        return createdDataAt;
    }

    public void setCreatedDataAt(Timestamp createdDataAt) {
        this.createdDataAt = createdDataAt;
    }

    public String getModifiedDataBy() {
        return modifiedDataBy;
    }

    public void setModifiedDataBy(String modifiedDataBy) {
        this.modifiedDataBy = modifiedDataBy;
    }

    public Timestamp getModifiedDataAt() {
        return modifiedDataAt;
    }

    public void setModifiedDataAt(Timestamp modifiedDataAt) {
        this.modifiedDataAt = modifiedDataAt;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
