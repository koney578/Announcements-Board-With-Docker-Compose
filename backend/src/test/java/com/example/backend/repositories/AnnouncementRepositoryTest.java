package com.example.backend.repositories;

import com.example.backend.models.Announcement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AnnouncementRepositoryTest {

    @Autowired
    private AnnouncementRepository announcementRepository;

    private Announcement announcement;

    @BeforeEach
    void setUp() {
        announcement = new Announcement();
        announcement.setTitle("Title");
        announcement.setDescription("Description");
        announcement.setEndsAt(LocalDateTime.now().plusDays(1));
        announcement.setIsReviewed(true);
        announcementRepository.save(announcement);
    }

    @Test
    void testFindAllByReviewed() {
        List<Announcement> foundAnnouncements = announcementRepository.findAllByReviewed();

        assertThat(foundAnnouncements).isNotEmpty();
        assertThat(foundAnnouncements).contains(announcement);
    }

    @Test
    void testFindAllByUserId() {
        List<Announcement> foundAnnouncements = announcementRepository.findAllByUserId(14L);

        assertThat(foundAnnouncements).isEmpty();
    }

}