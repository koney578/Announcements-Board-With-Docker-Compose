package com.example.backend.controllers;

import com.example.backend.dtos.requests.AnnouncementAddRequestDto;
import com.example.backend.dtos.responses.AnnouncementResponseDto;
import com.example.backend.models.Announcement;
import com.example.backend.services.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/announcements")
@RequiredArgsConstructor
public class AnnouncementController {
    private static final Logger logger = LoggerFactory.getLogger(AnnouncementController.class);
    private final AnnouncementService announcementService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<AnnouncementResponseDto> getAllAnnouncements() {
        logger.info("Getting all announcements");
        return announcementService.getAllAnnouncements();
    }

    @GetMapping("/reviewed")
    @ResponseStatus(HttpStatus.OK)
    public List<AnnouncementResponseDto> getAllAnnouncementsReviewed() {
        logger.info("Getting all reviewed announcements");
        return announcementService.getAllAnnouncementsReviewed();
    }

    @GetMapping("/user-announcements")
    @ResponseStatus(HttpStatus.OK)
    public List<AnnouncementResponseDto> getAllUserAnnouncements(Authentication authentication) {
        logger.info("Getting all user announcements");
        return announcementService.getAllUserAnnouncements(authentication);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AnnouncementResponseDto getAnnouncementById(@PathVariable("id") Long id) {
        logger.info("Getting announcement by id: {}", id);
        return announcementService.getAnnouncementById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnnouncement(@PathVariable("id") Long id, Authentication auth) {
        logger.info("Deleting announcement by id: {}", id);
        announcementService.deleteAnnouncement(id, auth);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addAnnouncement(@RequestBody AnnouncementAddRequestDto requestDto, Authentication auth) {
        logger.info("Adding new announcement");
        announcementService.addAnnouncement(requestDto, auth);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateAnnouncement(@PathVariable("id") Long id, @RequestBody AnnouncementAddRequestDto requestDto) {
        logger.info("Updating announcement by id: {}", id);
        announcementService.updateAnnouncement(id, requestDto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void setReviewed(@PathVariable("id") Long id){
        logger.info("Changing 'isRevieved' for announcement with id: {}", id);
        announcementService.setReviewed(id);
    }
}