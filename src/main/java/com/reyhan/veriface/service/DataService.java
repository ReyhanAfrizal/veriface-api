package com.reyhan.veriface.service;


/*
IntelliJ IDEA 2024.2.3 (Community Edition)
Build #IC-242.23339.11, built on September 25, 2024
@Author Reyhan a.k.a. Reyhan Afrizal
Java Developer
Created on 11/5/2024 4:18 PM
@Last Modified 11/5/2024 4:18 PM
Version 1.0
*/
import com.reyhan.veriface.dto.DataDTO;
import com.reyhan.veriface.model.Data;
import com.reyhan.veriface.repo.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class DataService {

    @Autowired
    private DataRepository dataRepository;

    private final String uploadDir = "uploads/"; // Set your upload directory

    // Create a new data entry with file uploads
    public Data createData(DataDTO dataDTO, MultipartFile fotoKtp, MultipartFile fotoSelfie) throws IOException {
        Data data = new Data();
        data.setMid(dataDTO.getMid());
        data.setNotes(dataDTO.getNotes());
        data.setResult(dataDTO.getResult());

        // Save uploaded files and set their paths in the Data object
        String fotoKtpPath = saveFile(fotoKtp);
        String fotoSelfiePath = saveFile(fotoSelfie);

        data.setFotoKtp(fotoKtpPath);
        data.setFotoSelfie(fotoSelfiePath);

        // User is set automatically in the model
        return dataRepository.save(data);
    }

    // Get a data entry by ID
    public Optional<Data> getDataById(Integer id) {
        return dataRepository.findById(id);
    }

    // Get all data entries
    public List<Data> getAllData() {
        return dataRepository.findAll();
    }

    // Update a data entry by ID
    public Optional<Data> updateData(Integer id, DataDTO dataDTO, MultipartFile fotoKtp, MultipartFile fotoSelfie) throws IOException {
        return dataRepository.findById(id).map(data -> {
            data.setMid(dataDTO.getMid());
            data.setNotes(dataDTO.getNotes());
            data.setResult(dataDTO.getResult());

            // Save uploaded files if provided
            if (fotoKtp != null && !fotoKtp.isEmpty()) {
                String fotoKtpPath = null;
                try {
                    fotoKtpPath = saveFile(fotoKtp);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                data.setFotoKtp(fotoKtpPath);
            }

            if (fotoSelfie != null && !fotoSelfie.isEmpty()) {
                String fotoSelfiePath = null;
                try {
                    fotoSelfiePath = saveFile(fotoSelfie);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                data.setFotoSelfie(fotoSelfiePath);
            }

            return dataRepository.save(data);
        });
    }

    // Delete a data entry by ID
    public boolean deleteDataById(Integer id) {
        if (dataRepository.existsById(id)) {
            dataRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    // File saving logic
    private String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Failed to store empty file.");
        }

        // Create directory if it doesn't exist
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Save the file
        Path path = Paths.get(uploadDir + file.getOriginalFilename());
        Files.write(path, file.getBytes());
        return path.toString(); // Return the file path for storage
    }
}
