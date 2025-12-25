package com.alaToo.scrs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "complaint_votes")
public class ComplaintVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id")
    private Complaint complaint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer rating;

    private java.time.LocalDateTime votedAt = java.time.LocalDateTime.now();

    public ComplaintVote() {
    }

    public ComplaintVote(Complaint complaint, User user, Integer rating) {
        this.complaint = complaint;
        this.user = user;
        this.rating = rating;
    }

}
