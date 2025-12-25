package com.alaToo.scrs.controller;

import com.alaToo.scrs.entity.ComplaintVote;
import com.alaToo.scrs.entity.User;
import com.alaToo.scrs.service.ComplaintVoteService;
import com.alaToo.scrs.service.UserService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/votes")
@CrossOrigin(origins = "*", maxAge = 3600)
public class VoteController {
//
//    @Autowired
//    private ComplaintVoteService voteService;
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/{complaintId}")
//    public ResponseEntity<?> voteOnComplaint(@PathVariable Long complaintId,
//                                           @Min(1) @Max(5) @RequestParam Integer rating,
//                                           Authentication authentication) {
//        try {
//            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//            User user = userService.findByUsername(userPrincipal.getUsername()).orElseThrow();
//
//            ComplaintVote vote = voteService.createVote(complaintId, user, rating);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("message", "Vote submitted successfully");
//            response.put("voteCount", voteService.getVoteCount(complaintId));
//            response.put("averageRating", voteService.getAverageRating(complaintId));
//
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Failed to submit vote: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//
//    @DeleteMapping("/{complaintId}")
//    public ResponseEntity<?> removeVote(@PathVariable Long complaintId, Authentication authentication) {
//        try {
//            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//            User user = userService.findByUsername(userPrincipal.getUsername()).orElseThrow();
//
//            voteService.deleteVote(complaintId, user);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("message", "Vote removed successfully");
//            response.put("voteCount", voteService.getVoteCount(complaintId));
//            response.put("averageRating", voteService.getAverageRating(complaintId));
//
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Failed to remove vote: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//
//    @GetMapping("/{complaintId}")
//    public ResponseEntity<?> getVotesForComplaint(@PathVariable Long complaintId) {
//        try {
//            List<ComplaintVote> votes = voteService.findByComplaint(complaintId);
//            Long voteCount = voteService.getVoteCount(complaintId);
//            Double averageRating = voteService.getAverageRating(complaintId);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("votes", votes);
//            response.put("voteCount", voteCount);
//            response.put("averageRating", averageRating != null ? Math.round(averageRating * 100.0) / 100.0 : 0.0);
//
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Failed to get votes");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//
//    @GetMapping("/{complaintId}/user-vote")
//    public ResponseEntity<?> getUserVote(@PathVariable Long complaintId, Authentication authentication) {
//        try {
//            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//            User user = userService.findByUsername(userPrincipal.getUsername()).orElseThrow();
//
//            return voteService.findByComplaintAndUser(complaintId, user)
//                    .map(vote -> ResponseEntity.ok(vote))
//                    .orElse(ResponseEntity.noContent().build());
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Failed to get user vote");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//
//    @GetMapping("/{complaintId}/has-voted")
//    public ResponseEntity<?> hasUserVoted(@PathVariable Long complaintId, Authentication authentication) {
//        try {
//            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//            User user = userService.findByUsername(userPrincipal.getUsername()).orElseThrow();
//
//            boolean hasVoted = voteService.hasUserVoted(complaintId, user);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("hasVoted", hasVoted);
//
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Failed to check vote status");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//
//    @GetMapping("/complaint/{complaintId}/stats")
//    public ResponseEntity<?> getComplaintVoteStats(@PathVariable Long complaintId) {
//        try {
//            Long voteCount = voteService.getVoteCount(complaintId);
//            Double averageRating = voteService.getAverageRating(complaintId);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("voteCount", voteCount);
//            response.put("averageRating", averageRating != null ? Math.round(averageRating * 100.0) / 100.0 : 0.0);
//
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Failed to get vote statistics");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
}
