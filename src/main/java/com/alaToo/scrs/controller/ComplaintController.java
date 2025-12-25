package com.alaToo.scrs.controller;

import com.alaToo.scrs.dto.ComplaintRequest;
import com.alaToo.scrs.entity.*;
import com.alaToo.scrs.service.ComplaintService;
import com.alaToo.scrs.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    private final UserService userService;

    private User getCurrentUser(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        return userService.findByUsername(userPrincipal.getUsername());
    }

    @GetMapping("/student/test")
    public String test() {
        return "It's student";
    }

    @GetMapping("/admin/test")
    public String adminTest() {
        return "It's admin";
    }

    @PostMapping
    public ResponseEntity<?> createComplaint(@Valid @RequestBody ComplaintRequest complaintRequest,
                                             Authentication authentication) {
        try {
            User user = getCurrentUser(authentication);
            Complaint complaint = complaintService.createComplaint(complaintRequest, user);

            // Формируем чистый JSON ответ
            Map<String, Object> response = new HashMap<>();
            response.put("id", complaint.getId());
            response.put("title", complaint.getTitle());
            response.put("message", "Complaint created successfully");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllComplaints(Authentication authentication) {
        try {
            User user = getCurrentUser(authentication);
            List<Complaint> complaints;

            // Логика ролей: админ видит всё, студент только своё
            if (user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.STAFF) {
                complaints = complaintService.findAll();
            } else {
                complaints = complaintService.findByCreatedBy(user);
            }

            return ResponseEntity.ok(complaints);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to get complaints"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getComplaintById(@PathVariable Long id, Authentication authentication) {
        try {
            User user = getCurrentUser(authentication);
            Complaint complaint = complaintService.findById(id);

            // Проверка прав доступа
            boolean isAdminOrStaff = user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.STAFF;
            boolean isOwner = complaint.getCreatedBy().getId().equals(user.getId());

            if (!isAdminOrStaff && !isOwner) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Access denied"));
            }

            return ResponseEntity.ok(complaint);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Complaint not found"));
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateComplaintStatus(@PathVariable Long id,
                                                   @RequestParam ComplaintStatus status,
                                                   @RequestParam(required = false) String resolution,
                                                   Authentication authentication) {
        try {
            User user = getCurrentUser(authentication);

            // Только STAFF и ADMIN могут менять статус
            if (user.getRole() == UserRole.STUDENT) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Only staff can update status"));
            }

            complaintService.updateStatus(id, status, resolution, user);
            return ResponseEntity.ok(Map.of("message", "Status updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComplaint(@PathVariable Long id, Authentication authentication) {
        try {
            User user = getCurrentUser(authentication);
            Complaint complaint = complaintService.findById(id);

            // Удалить может только автор или админ
            if (user.getRole() != UserRole.ADMIN && !complaint.getCreatedBy().getId().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            complaintService.deleteComplaint(id);
            return ResponseEntity.ok(Map.of("message", "Deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to delete"));
        }
    }
}