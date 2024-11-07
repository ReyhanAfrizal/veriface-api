package com.reyhan.veriface.controller;

import com.reyhan.veriface.dto.DataDTO;
import com.reyhan.veriface.model.Data;
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

    // Create a new data entry with file uploads
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Data> createData(
            @RequestPart("mid") String mid,
            @RequestPart("notes") String notes,
            @RequestPart("result") String result,
            @RequestPart("fotoKtp") MultipartFile fotoKtp,
            @RequestPart("fotoSelfie") MultipartFile fotoSelfie) {

        try {
            DataDTO dataDTO = new DataDTO();
            dataDTO.setMid(mid);
            dataDTO.setNotes(notes);
            dataDTO.setResult(result);

            // Create the Data entry
            Data createdData = dataService.createData(dataDTO, fotoKtp, fotoSelfie);

            // Start a new thread to trigger Python server endpoint asynchronously
            new Thread(() -> {
                try {
                    URL url = new URL("http://localhost:5000/run_verification");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);

                    // Example JSON payload to send to Python server
                    String jsonPayload = String.format(
                            "{\"mid\": \"%s\", \"result\": \"%s\"}", mid, result
                    );

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
            }).start();

            return new ResponseEntity<>(createdData, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
