package com.alaToo.scrs.repository;

import com.alaToo.scrs.entity.Complaint;
import com.alaToo.scrs.entity.ComplaintStatus;
import com.alaToo.scrs.entity.ComplaintType;
import com.alaToo.scrs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByCreatedBy(User user);
    
    List<Complaint> findByStatus(ComplaintStatus status);
    
    List<Complaint> findByType(ComplaintType type);
    
    List<Complaint> findByDepartmentId(Long departmentId);
    
    @Query("SELECT c FROM Complaint c WHERE c.createdBy = :user ORDER BY c.createdAt DESC")
    List<Complaint> findByCreatedByOrderByCreatedAtDesc(@Param("user") User user);
    
    @Query("SELECT c FROM Complaint c WHERE c.status = :status ORDER BY c.createdAt DESC")
    List<Complaint> findByStatusOrderByCreatedAtDesc(@Param("status") ComplaintStatus status);
    
    @Query("SELECT c FROM Complaint c WHERE c.type = :type ORDER BY c.createdAt DESC")
    List<Complaint> findByTypeOrderByCreatedAtDesc(@Param("type") ComplaintType type);
    
    @Query("SELECT c FROM Complaint c WHERE c.department.id = :departmentId ORDER BY c.createdAt DESC")
    List<Complaint> findByDepartmentIdOrderByCreatedAtDesc(@Param("departmentId") Long departmentId);
    
    @Query("SELECT c FROM Complaint c WHERE c.status = 'NEW' ORDER BY c.createdAt DESC")
    List<Complaint> findNewComplaints();
    
    @Query("SELECT c FROM Complaint c WHERE c.assignedTo = :user ORDER BY c.updatedAt DESC")
    List<Complaint> findByAssignedToOrderByUpdatedAtDesc(@Param("user") User user);
    
    @Query("SELECT c FROM Complaint c WHERE c.studentConfirmation = true AND c.status = 'CONFIRMED_BY_STUDENT' ORDER BY c.updatedAt DESC")
    List<Complaint> findConfirmedComplaints();
    
    @Query("SELECT c FROM Complaint c WHERE c.createdAt BETWEEN :startDate AND :endDate ORDER BY c.createdAt DESC")
    List<Complaint> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
