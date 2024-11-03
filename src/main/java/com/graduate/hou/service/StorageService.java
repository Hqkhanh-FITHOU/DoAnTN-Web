package com.graduate.hou.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void storeProductImage(MultipartFile file, String newFileName);
    void storeUserImage(MultipartFile file, String newFileName);
    void removeProductImage(String filePath);
    void removeUserImage(String filePath);
    void init();
}
