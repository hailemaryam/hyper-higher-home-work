package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String storeFile(MultipartFile file, String subDirectory) {
        try {
            Path targetLocation = Paths.get(uploadDir + subDirectory).toAbsolutePath().normalize();
            Files.createDirectories(targetLocation);

            Path targetPath = targetLocation.resolve(UUID.randomUUID().toString() + file.getOriginalFilename());
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            return targetPath.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Could not store file " + file.getOriginalFilename() + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) throws MalformedURLException {
        Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if(resource.exists()) {
            return resource;
        } else {
            throw new RuntimeException("File not found " + fileName);
        }
    }
}

