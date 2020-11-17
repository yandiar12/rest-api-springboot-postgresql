/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api.service;

import com.yandiar.api.common.FileStorageProperties;
import com.yandiar.api.exception.FileStorageException;
import com.yandiar.api.exception.MyFileNotFoundException;
import com.yandiar.api.model.entity.UploadFile;
import com.yandiar.api.repository.UploadFileRepository;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author YAR
 */
@Service
@Slf4j
public class UploadFileService {
    
    @Autowired
    private UploadFileRepository uploadRepo;
    
    private final Path fileStorageLocation;
    
    @Autowired
    public UploadFileService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
            throw new FileStorageException("Could not create directories. " + ex.getLocalizedMessage(), ex);
        }
    }
    
    public String storeFile(MultipartFile file) {
        // Normalize filename
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        
        try {
            // check if file contains invalid character
            if (fileName.contains("..")) {
                throw new FileStorageException("Filename contains invalid path squences.");
            }
            
            // save content details to table
            UploadFile uploadData = new UploadFile();
            
            Boolean isExists = uploadRepo.existsByFileName(fileName);
            if (isExists) {
                uploadData = uploadRepo.findByFileName(fileName);
            }
            
            uploadData.setFileName(fileName);
            uploadData.setFileType(file.getContentType());
            uploadData.setFileSize(file.getSize());
            uploadData.setPathDirectory(this.fileStorageLocation.toString());
            uploadRepo.save(uploadData);
            
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            
        } catch (Exception ex) {
            log.error("Error: {} ",ex.getLocalizedMessage());
            throw new FileStorageException("Could not store file " + fileName + " Please try again.", ex);
        }
        
        return fileName;
    }
    
    public Resource loadFileAsResource(String filename) {
        try {
            Path filePath = this.fileStorageLocation.resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + filename);
            }
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
            throw new MyFileNotFoundException("File not found " + filename, ex);
        }
    }
}
