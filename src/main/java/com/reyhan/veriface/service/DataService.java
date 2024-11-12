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

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DataService {

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    // Create a new data entry with file uploads to Cloudinary
    public Data createData(DataDTO dataDTO, MultipartFile fotoKtp, MultipartFile fotoSelfie) throws IOException {
        Data data = new Data();
        data.setNamaKonsumen(dataDTO.getNamaKonsumen());
        data.setNotes(dataDTO.getNotes());
        data.setResult(dataDTO.getResult());

        // Upload files to Cloudinary and get URLs
        String fotoKtpUrl = cloudinaryService.uploadFile(fotoKtp);
        String fotoSelfieUrl = cloudinaryService.uploadFile(fotoSelfie);

        // Set the URLs in the Data entity
        data.setFotoKtp(fotoKtpUrl);
        data.setFotoSelfie(fotoSelfieUrl);

        // Save and return the data entity
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
            data.setNamaKonsumen(dataDTO.getNamaKonsumen());
            data.setNotes(dataDTO.getNotes());
            data.setResult(dataDTO.getResult());

            // Upload new files to Cloudinary if provided, and update URLs
            if (fotoKtp != null && !fotoKtp.isEmpty()) {
                try {
                    String fotoKtpUrl = cloudinaryService.uploadFile(fotoKtp);
                    data.setFotoKtp(fotoKtpUrl);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to upload fotoKtp", e);
                }
            }

            if (fotoSelfie != null && !fotoSelfie.isEmpty()) {
                try {
                    String fotoSelfieUrl = cloudinaryService.uploadFile(fotoSelfie);
                    data.setFotoSelfie(fotoSelfieUrl);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to upload fotoSelfie", e);
                }
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
}
