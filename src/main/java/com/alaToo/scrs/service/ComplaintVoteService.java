package com.alaToo.scrs.service;

import com.alaToo.scrs.entity.Complaint;
import com.alaToo.scrs.entity.ComplaintVote;
import com.alaToo.scrs.entity.User;
import com.alaToo.scrs.repository.ComplaintVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ComplaintVoteService {
    
    @Autowired
    private ComplaintVoteRepository complaintVoteRepository;
    
    @Autowired
    private ComplaintService complaintService;
    
    public ComplaintVote createVote(Long complaintId, User user, Integer rating) {
        Complaint complaint = complaintService.findById(complaintId);
        
        // Check if user already voted for this complaint
        Optional<ComplaintVote> existingVote = complaintVoteRepository.findByComplaintAndUser(complaint, user);
        
        if (existingVote.isPresent()) {
            // Update existing vote
            ComplaintVote vote = existingVote.get();
            vote.setRating(rating);
            vote.setVotedAt(java.time.LocalDateTime.now());
            return complaintVoteRepository.save(vote);
        } else {
            // Create new vote
            ComplaintVote vote = new ComplaintVote(complaint, user, rating);
            return complaintVoteRepository.save(vote);
        }
    }
    
    public void deleteVote(Long complaintId, User user) {
        Complaint complaint = complaintService.findById(complaintId);
        Optional<ComplaintVote> vote = complaintVoteRepository.findByComplaintAndUser(complaint, user);
        
        if (vote.isPresent()) {
            complaintVoteRepository.delete(vote.get());
        }
    }
    
    public List<ComplaintVote> findByComplaint(Long complaintId) {
        Complaint complaint = complaintService.findById(complaintId);
        return complaintVoteRepository.findByComplaintOrderByVotedAtDesc(complaint);
    }
    
    public List<ComplaintVote> findByUser(User user) {
        return complaintVoteRepository.findByUser(user);
    }
    
    public Optional<ComplaintVote> findByComplaintAndUser(Long complaintId, User user) {
        Complaint complaint = complaintService.findById(complaintId);
        return complaintVoteRepository.findByComplaintAndUser(complaint, user);
    }
    
    public boolean hasUserVoted(Long complaintId, User user) {
        Complaint complaint = complaintService.findById(complaintId);
        return complaintVoteRepository.existsByComplaintAndUser(complaint, user);
    }
    
    public Long getVoteCount(Long complaintId) {
        return complaintVoteRepository.getVoteCountForComplaint(complaintService.findById(complaintId));
    }
    
    public Double getAverageRating(Long complaintId) {
        return complaintVoteRepository.getAverageRatingForComplaint(complaintService.findById(complaintId));
    }
    
    public List<ComplaintVote> findAll() {
        return complaintVoteRepository.findAll();
    }
}
