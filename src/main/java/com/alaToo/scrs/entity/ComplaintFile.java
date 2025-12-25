package com.alaToo.scrs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "complaint_files")
public class ComplaintFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;


    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String fileType;

    private Long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id")
    private Complaint complaint;

    private java.time.LocalDateTime uploadedAt = java.time.LocalDateTime.now();

    public ComplaintFile() {
    }

    public ComplaintFile(String fileName, String filePath, String fileType, Long fileSize, Complaint complaint) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.complaint = complaint;
    }

}
