package com.reyhan.veriface.controller;

/*
IntelliJ IDEA 2024.2.3 (Community Edition)
Build #IC-242.23339.11, built on September 25, 2024
@Author Reyhan a.k.a. Reyhan Afrizal
Java Developer
Created on 11/5/2024 11:57 AM
@Last Modified 11/5/2024 11:57 AM
Version 1.0
*/

import com.reyhan.veriface.dto.DataDTO;
import com.reyhan.veriface.model.Data;
import com.reyhan.veriface.service.CloudinaryService;
import com.reyhan.veriface.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/data")
public class DataController {

    @Autowired
    private DataService dataService;

    @Autowired
    private CloudinaryService cloudinaryService;

    // Create a new data entry with file uploads
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Data> createData(
            @RequestPart("mid") String mid,
            @RequestPart("notes") String notes,
            @RequestPart("result") String result,
            @RequestPart("fotoKtp") MultipartFile fotoKtp,
            @RequestPart("fotoSelfie") MultipartFile fotoSelfie) {

        try {
            // Upload images to Cloudinary
            String fotoKtpUrl = cloudinaryService.uploadFile(fotoKtp);
            String fotoSelfieUrl = cloudinaryService.uploadFile(fotoSelfie);

            // Set URLs in DataDTO
            DataDTO dataDTO = new DataDTO();
            dataDTO.setMid(mid);
            dataDTO.setNotes(notes);
            dataDTO.setResult(result);
            dataDTO.setFotoKtpUrl(fotoKtpUrl);
            dataDTO.setFotoSelfieUrl(fotoSelfieUrl);

            // Save Data entry
            Data createdData = dataService.createData(dataDTO, fotoKtp, fotoSelfie);

            // Start asynchronous verification (Python server call)
            new Thread(() -> startVerification(mid, result)).start();

            return new ResponseEntity<>(createdData, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void startVerification(String mid, String result) {
        try {
            URL url = new URL("http://localhost:5000/run_verification");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonPayload = String.format("{\"mid\": \"%s\", \"result\": \"%s\"}", mid, result);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Verification process started successfully.");
            } else {
                System.out.println("Failed to start verification process, response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get a data entry by ID
    @GetMapping("/{id}")
    public ResponseEntity<Data> getDataById(@PathVariable Integer id) {
        Optional<Data> data = dataService.getDataById(id);
        return data.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all data entries
    @GetMapping
    public List<Data> getAllData() {
        return dataService.getAllData();
    }

    // Update a data entry by ID
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Data> updateData(
            @PathVariable Integer id,
            @RequestPart("mid") String mid,
            @RequestPart("notes") String notes,
            @RequestPart("result") String result,
            @RequestPart(value = "fotoKtp", required = false) MultipartFile fotoKtp,
            @RequestPart(value = "fotoSelfie", required = false) MultipartFile fotoSelfie) {

        try {
            DataDTO dataDTO = new DataDTO();
            dataDTO.setMid(mid);
            dataDTO.setNotes(notes);
            dataDTO.setResult(result);

            // Optional Cloudinary upload if new files are provided
            if (fotoKtp != null) {
                String fotoKtpUrl = cloudinaryService.uploadFile(fotoKtp);
                dataDTO.setFotoKtpUrl(fotoKtpUrl);
            }
            if (fotoSelfie != null) {
                String fotoSelfieUrl = cloudinaryService.uploadFile(fotoSelfie);
                dataDTO.setFotoSelfieUrl(fotoSelfieUrl);
            }

            Optional<Data> updatedData = dataService.updateData(id, dataDTO, fotoKtp, fotoSelfie);
            return updatedData.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Delete a data entry by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteData(@PathVariable Integer id) {
        boolean isDeleted = dataService.deleteDataById(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
