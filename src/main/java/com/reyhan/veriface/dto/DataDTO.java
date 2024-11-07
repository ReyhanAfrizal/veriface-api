package com.reyhan.veriface.dto;


/*
IntelliJ IDEA 2024.2.3 (Community Edition)
Build #IC-242.23339.11, built on September 25, 2024
@Author Reyhan a.k.a. Reyhan Afrizal
Java Developer
Created on 11/5/2024 4:10 PM
@Last Modified 11/5/2024 4:10 PM
Version 1.0
*/

public class DataDTO {
    private String mid;
    private String notes;
    private String result;
    private String fotoKtpUrl;      // Cloudinary URL for KTP photo
    private String fotoSelfieUrl;   // Cloudinary URL for selfie photo

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
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

    public String getFotoKtpUrl() {
        return fotoKtpUrl;
    }

    public void setFotoKtpUrl(String fotoKtpUrl) {
        this.fotoKtpUrl = fotoKtpUrl;
    }

    public String getFotoSelfieUrl() {
        return fotoSelfieUrl;
    }

    public void setFotoSelfieUrl(String fotoSelfieUrl) {
        this.fotoSelfieUrl = fotoSelfieUrl;
    }
}
