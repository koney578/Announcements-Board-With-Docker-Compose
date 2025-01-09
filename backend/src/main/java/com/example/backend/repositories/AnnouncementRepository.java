package com.example.backend.repositories;

import com.example.backend.models.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    @Query("SELECT a FROM Announcement a WHERE a.isReviewed = true")
    List<Announcement> findAllByReviewed();

    List<Announcement> findAllByUserId(Long userId);

    @Modifying
    @Query("UPDATE Announcement a SET a.category.id = 1 WHERE a.category.id = :id")
    void changeCategoryToDefault(Long id);
}