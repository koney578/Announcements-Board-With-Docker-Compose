package com.example.backend.services;

import com.example.backend.dtos.requests.AnnouncementAddRequestDto;
import com.example.backend.exceptions.ResourceNotFoundException;
import com.example.backend.mappers.AnnouncementMapper;
import com.example.backend.models.Announcement;
import com.example.backend.models.Category;
import com.example.backend.models.User;
import com.example.backend.repositories.AnnouncementRepository;
import com.example.backend.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AnnouncementServiceTest {
    @Mock
    private AnnouncementRepository announcementRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private AnnouncementMapper announcementMapper;

    @Mock
    Authentication authentication;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AnnouncementService announcementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAnnouncementById_announcementExists_returnsAnnouncement() {
        when(announcementRepository.findById(any(Long.class))).thenReturn(Optional.of(new Announcement()));

        announcementService.getAnnouncementById(1L);

        verify(announcementRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void getAnnouncementById_announcementDoesNotExist_throwsResourceNotFoundException() {
        when(announcementRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> announcementService.getAnnouncementById(1L));

        verify(announcementRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void addAnnouncement_categoryExists_savesAnnouncement() {
        when(announcementMapper.requestDtoToEntity(any(AnnouncementAddRequestDto.class))).thenReturn(new Announcement());
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(new Category()));
        when(authentication.getPrincipal()).thenReturn(new User());

        announcementService.addAnnouncement(new AnnouncementAddRequestDto(
                "Tytul", "Opis", 1L, LocalDateTime.now()), authentication);

        verify(announcementRepository, times(1)).save(any(Announcement.class));
    }

    @Test
    public void addAnnouncement_categoryDoesNotExist_throwsResourceNotFoundException() {
        when(announcementMapper.requestDtoToEntity(any(AnnouncementAddRequestDto.class))).thenReturn(new Announcement());
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        when(authentication.getPrincipal()).thenReturn(new User());

        assertThrows(ResourceNotFoundException.class, () ->
                announcementService.addAnnouncement(new AnnouncementAddRequestDto(
                "Tytul", "Opis", 1L, LocalDateTime.now()), authentication));

        verify(announcementRepository, times(0)).save(any(Announcement.class));
    }
}