package com.alaToo.scrs.service;

import com.alaToo.scrs.entity.Complaint;
import com.alaToo.scrs.entity.ComplaintFile;
import com.alaToo.scrs.repository.ComplaintFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class FileService {
    
    private final String uploadDir = "uploads/";
    
    @Autowired
    private ComplaintFileRepository complaintFileRepository;
    
    @Autowired
    private ComplaintService complaintService;
    
    public ComplaintFile uploadFile(Long complaintId, MultipartFile file) throws IOException {
        Complaint complaint = complaintService.findById(complaintId);
        
        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        // Generate unique filename
        String originalFileName = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFileName);
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        
        // Save file to disk
        Path filePath = uploadPath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        
        // Save file info to database
        ComplaintFile complaintFile = new ComplaintFile(
                originalFileName,
                filePath.toString(),
                file.getContentType(),
                file.getSize(),
                complaint
        );
        
        return complaintFileRepository.save(complaintFile);
    }
    
    public void deleteFile(Long fileId) {
        ComplaintFile file = complaintFileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found with id: " + fileId));
        
        try {
            // Delete file from disk
            Files.deleteIfExists(Paths.get(file.getFilePath()));
        } catch (IOException e) {
            System.err.println("Failed to delete file from disk: " + e.getMessage());
        }
        
        // Delete from database
        complaintFileRepository.delete(file);
    }
    
    public List<ComplaintFile> getFilesByComplaint(Long complaintId) {
        Complaint complaint = complaintService.findById(complaintId);
        return complaintFileRepository.findByComplaintOrderByUploadedAtDesc(complaint);
    }
    
    public ComplaintFile getFileById(Long fileId) {
        return complaintFileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found with id: " + fileId));
    }
    
    public void deleteFilesByComplaint(Long complaintId) {
        Complaint complaint = complaintService.findById(complaintId);
        List<ComplaintFile> files = complaintFileRepository.findByComplaint(complaint);
        
        for (ComplaintFile file : files) {
            try {
                Files.deleteIfExists(Paths.get(file.getFilePath()));
            } catch (IOException e) {
                System.err.println("Failed to delete file from disk: " + e.getMessage());
            }
        }
        
        complaintFileRepository.deleteByComplaint(complaint);
    }
    
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.'));
    }
    
    public boolean isValidFileType(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (
                contentType.equals("image/jpeg") ||
                contentType.equals("image/jpg") ||
                contentType.equals("image/png") ||
                contentType.equals("image/gif") ||
                contentType.equals("application/pdf") ||
                contentType.equals("text/plain") ||
                contentType.equals("application/msword") ||
                contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
        );
    }
}
