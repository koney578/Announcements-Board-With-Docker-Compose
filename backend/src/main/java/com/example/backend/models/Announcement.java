package com.example.backend.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "announcements")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime endsAt;

    @Column(nullable = false)
    private Boolean isReviewed;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;

    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne(targetEntity = Category.class, fetch = FetchType.EAGER)
    private Category category;
}