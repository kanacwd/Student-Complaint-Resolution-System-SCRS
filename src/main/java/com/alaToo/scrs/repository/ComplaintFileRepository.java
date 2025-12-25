package com.alaToo.scrs.repository;

import com.alaToo.scrs.entity.Complaint;
import com.alaToo.scrs.entity.ComplaintFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintFileRepository extends JpaRepository<ComplaintFile, Long> {
    List<ComplaintFile> findByComplaint(Complaint complaint);

    @Query("SELECT cf FROM ComplaintFile cf WHERE cf.complaint = :complaint ORDER BY cf.uploadedAt DESC")
    List<ComplaintFile> findByComplaintOrderByUploadedAtDesc(@Param("complaint") Complaint complaint);
    
    void deleteByComplaint(Complaint complaint);
}
