package com.graduate.hou.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import com.graduate.hou.service.StorageService;

@Service
public class FileSystemStrorageService implements StorageService{

    private final Path rootProductImgLocation;
    private final Path rootUserImgLocation;

    public FileSystemStrorageService(){
        this.rootProductImgLocation = Paths.get("src/main/resources/static/uploads/product");
        this.rootUserImgLocation = Paths.get("src/main/resources/static/uploads/user");
    }

    @Override
    public void storeProductImage(MultipartFile file, String newFileName) {
        try {
            Path destinationFile = this.rootProductImgLocation.resolve(
                Paths.get(newFileName)
            ).normalize().toAbsolutePath();
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootProductImgLocation);
            Files.createDirectories(rootUserImgLocation);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public void removeProductImage(String filePath) {
        try {
            Path locationFile = this.rootProductImgLocation.resolve(
                Paths.get(filePath)
            ).normalize().toAbsolutePath();
            Files.deleteIfExists(locationFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void storeUserImage(MultipartFile file, String newFileName) {
        try {
            Path destinationFile = this.rootUserImgLocation.resolve(
                Paths.get(newFileName)
            ).normalize().toAbsolutePath();
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserImage(String filePath) {
        try {
            Path locationFile = this.rootUserImgLocation.resolve(
                Paths.get(filePath)
            ).normalize().toAbsolutePath();
            Files.deleteIfExists(locationFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
