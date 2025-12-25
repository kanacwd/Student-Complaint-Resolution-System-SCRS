package com.alaToo.scrs.controller;

import com.alaToo.scrs.entity.ComplaintFile;
import com.alaToo.scrs.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FileController {
    
    @Autowired
    private FileService fileService;
    
    @PostMapping("/upload/{complaintId}")
    public ResponseEntity<?> uploadFile(@PathVariable Long complaintId,
                                      @RequestParam("file") MultipartFile file,
                                      Authentication authentication) {
        try {
            // Check if file is empty
            if (file.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Please select a file to upload");
                return ResponseEntity.badRequest().body(error);
            }
            
            // Check file type
            if (!fileService.isValidFileType(file)) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Invalid file type. Only images, PDF, and documents are allowed");
                return ResponseEntity.badRequest().body(error);
            }
            
            // Check file size (10MB max)
            if (file.getSize() > 10 * 1024 * 1024) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "File size too large. Maximum size is 10MB");
                return ResponseEntity.badRequest().body(error);
            }
            
            ComplaintFile uploadedFile = fileService.uploadFile(complaintId, file);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "File uploaded successfully");
            response.put("fileId", uploadedFile.getId());
            response.put("fileName", uploadedFile.getFileName());
            response.put("fileSize", uploadedFile.getFileSize());
            response.put("uploadedAt", uploadedFile.getUploadedAt());
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to upload file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to upload file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    @GetMapping("/complaint/{complaintId}")
    public ResponseEntity<?> getFilesByComplaint(@PathVariable Long complaintId) {
        try {
            List<ComplaintFile> files = fileService.getFilesByComplaint(complaintId);
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to get files");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable Long fileId) {
        try {
            ComplaintFile file = fileService.getFileById(fileId);
            Path filePath = Paths.get(file.getFilePath());
            
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }
                
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{fileId}")
    public ResponseEntity<?> deleteFile(@PathVariable Long fileId, Authentication authentication) {
        try {
            fileService.deleteFile(fileId);
            
            Map<String, String> success = new HashMap<>();
            success.put("message", "File deleted successfully");
            
            return ResponseEntity.ok(success);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to delete file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    @DeleteMapping("/complaint/{complaintId}")
    public ResponseEntity<?> deleteAllFilesByComplaint(@PathVariable Long complaintId, Authentication authentication) {
        try {
            fileService.deleteFilesByComplaint(complaintId);
            
            Map<String, String> success = new HashMap<>();
            success.put("message", "All files for this complaint deleted successfully");
            
            return ResponseEntity.ok(success);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to delete files: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
