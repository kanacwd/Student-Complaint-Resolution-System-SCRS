package com.alaToo.scrs.service;

import com.alaToo.scrs.dto.ComplaintRequest;
import com.alaToo.scrs.entity.*;
import com.alaToo.scrs.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ComplaintService {
    
    @Autowired
    private ComplaintRepository complaintRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ComplaintFileRepository complaintFileRepository;
    
    @Autowired
    private ComplaintVoteRepository complaintVoteRepository;
    
    public Complaint createComplaint(ComplaintRequest complaintRequest, User createdBy) {
        // Find or create department
        Department department = departmentRepository.findByName(complaintRequest.getTargetDepartment())
                .orElseGet(() -> {
                    Department newDept = new Department();
                    newDept.setName(complaintRequest.getTargetDepartment());
                    newDept.setDescription("Auto-created department");
                    newDept.setType(complaintRequest.getDepartmentType());
                    newDept.setHeadOfDepartment("TBD");
                    newDept.setEmail("dept@aiu.edu.kg");
                    newDept.setPhone("+996");
                    return departmentRepository.save(newDept);
                });
        
        Complaint complaint = new Complaint(
                complaintRequest.getTitle(),
                complaintRequest.getDescription(),
                complaintRequest.getType(),
                createdBy,
                department
        );
        
        return complaintRepository.save(complaint);
    }
    
    public Complaint updateComplaint(Long id, ComplaintRequest complaintRequest) {
        Complaint complaint = findById(id);
        
        complaint.setTitle(complaintRequest.getTitle());
        complaint.setDescription(complaintRequest.getDescription());
        complaint.setType(complaintRequest.getType());
        
        return complaintRepository.save(complaint);
    }
    
    public Complaint updateStatus(Long id, ComplaintStatus status, String resolution, User updatedBy) {
        Complaint complaint = findById(id);
        
        complaint.setStatus(status);
        complaint.setResolution(resolution);
        complaint.setUpdatedAt(LocalDateTime.now());
        
        if (status == ComplaintStatus.RESOLUTION_ANNOUNCED) {
            complaint.setResolvedAt(LocalDateTime.now());
        }
        
        return complaintRepository.save(complaint);
    }
    
    public Complaint assignComplaint(Long id, User assignedTo) {
        Complaint complaint = findById(id);
        complaint.setAssignedTo(assignedTo);
        complaint.setStatus(ComplaintStatus.ASSIGNED);
        return complaintRepository.save(complaint);
    }
    
    public Complaint confirmResolution(Long id, User user) {
        Complaint complaint = findById(id);
        if (!complaint.getCreatedBy().getId().equals(user.getId())) {
            throw new RuntimeException("Only the complaint creator can confirm the resolution");
        }
        
        complaint.setStudentConfirmation(true);
        complaint.setStatus(ComplaintStatus.CONFIRMED_BY_STUDENT);
        return complaintRepository.save(complaint);
    }
    
    public Complaint closeComplaint(Long id) {
        Complaint complaint = findById(id);
        complaint.setStatus(ComplaintStatus.CLOSED);
        return complaintRepository.save(complaint);
    }
    
    public Complaint findById(Long id) {
        return complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found with id: " + id));
    }
    
    public List<Complaint> findAll() {
        return complaintRepository.findAll();
    }
    
    public List<Complaint> findByCreatedBy(User user) {
        return complaintRepository.findByCreatedByOrderByCreatedAtDesc(user);
    }
    
    public List<Complaint> findByStatus(ComplaintStatus status) {
        return complaintRepository.findByStatusOrderByCreatedAtDesc(status);
    }
    
    public List<Complaint> findByType(ComplaintType type) {
        return complaintRepository.findByTypeOrderByCreatedAtDesc(type);
    }
    
    public List<Complaint> findByDepartment(Long departmentId) {
        return complaintRepository.findByDepartmentIdOrderByCreatedAtDesc(departmentId);
    }
    
    public List<Complaint> findNewComplaints() {
        return complaintRepository.findNewComplaints();
    }
    
    public List<Complaint> findAssignedToUser(User user) {
        return complaintRepository.findByAssignedToOrderByUpdatedAtDesc(user);
    }
    
    public List<Complaint> findConfirmedComplaints() {
        return complaintRepository.findConfirmedComplaints();
    }
    
    public void deleteComplaint(Long id) {
        complaintRepository.deleteById(id);
    }
    
    public Long getVoteCount(Long complaintId) {
        Complaint complaint = findById(complaintId);
        return complaintVoteRepository.getVoteCountForComplaint(complaint);
    }
    
    public Double getAverageRating(Long complaintId) {
        Complaint complaint = findById(complaintId);
        return complaintVoteRepository.getAverageRatingForComplaint(complaint);
    }
}
