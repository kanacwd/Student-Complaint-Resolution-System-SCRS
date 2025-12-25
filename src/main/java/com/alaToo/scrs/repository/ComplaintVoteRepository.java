package com.alaToo.scrs.repository;

import com.alaToo.scrs.entity.Complaint;
import com.alaToo.scrs.entity.ComplaintVote;
import com.alaToo.scrs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintVoteRepository extends JpaRepository<ComplaintVote, Long> {
    Optional<ComplaintVote> findByComplaintAndUser(Complaint complaint, User user);
    
    List<ComplaintVote> findByComplaint(Complaint complaint);
    
    List<ComplaintVote> findByUser(User user);
    
    @Query("SELECT cv FROM ComplaintVote cv WHERE cv.complaint = :complaint ORDER BY cv.votedAt DESC")
    List<ComplaintVote> findByComplaintOrderByVotedAtDesc(@Param("complaint") Complaint complaint);
    
    @Query("SELECT AVG(cv.rating) FROM ComplaintVote cv WHERE cv.complaint = :complaint")
    Double getAverageRatingForComplaint(@Param("complaint") Complaint complaint);
    
    @Query("SELECT COUNT(cv) FROM ComplaintVote cv WHERE cv.complaint = :complaint")
    Long getVoteCountForComplaint(@Param("complaint") Complaint complaint);
    
    boolean existsByComplaintAndUser(Complaint complaint, User user);
}
