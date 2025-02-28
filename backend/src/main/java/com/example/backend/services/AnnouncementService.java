package com.example.backend.services;

import com.example.backend.dtos.requests.AnnouncementAddRequestDto;
import com.example.backend.dtos.responses.AnnouncementResponseDto;
import com.example.backend.enums.UserType;
import com.example.backend.exceptions.ResourceNotFoundException;
import com.example.backend.mappers.AnnouncementMapper;
import com.example.backend.models.Announcement;
import com.example.backend.models.Category;
import com.example.backend.models.User;
import com.example.backend.repositories.AnnouncementRepository;
import com.example.backend.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnnouncementService {
    private final static Logger logger = LoggerFactory.getLogger(AnnouncementService.class);
    private final AnnouncementRepository announcementRepository;
    private final CategoryRepository categoryRepository;
    private final AnnouncementMapper announcementMapper;
    private final EmailService emailService;

    @Async
    @Scheduled(cron = "0,30 * * * * *")
    public void cleanup() {
        List<Announcement> announcementList = announcementRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        for(Announcement announcement: announcementList){
            if(announcement.getEndsAt().isBefore(now)){
                String title = "Twoje ogłoszenie wygasło";
                String message = "Twoje ogłoszenie o nazwie '" + announcement.getTitle()  +
                        "' wygasło i zostało usunięte z serwisu.";
                emailService.sendSimpleMessage(announcement.getUser().getEmail(), title, message);
                announcementRepository.delete(announcement);
                logger.info("Announcement with id: {} was successfully deleted (expired)", announcement.getId());
            }
        }
    }

    public List<AnnouncementResponseDto> getAllAnnouncements() {
        return announcementRepository.findAll()
                .stream()
                .map(announcementMapper::entityToResponseDto)
                .toList();
    }

    public List<AnnouncementResponseDto> getAllAnnouncementsReviewed() {
        return announcementRepository.findAllByReviewed()
                .stream()
                .map(announcementMapper::entityToResponseDto)
                .toList();
    }

    public List<AnnouncementResponseDto> getAllUserAnnouncements(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        return announcementRepository.findAllByUserId(userId)
                .stream()
                .map(announcementMapper::entityToResponseDto)
                .toList();
    }

    public AnnouncementResponseDto getAnnouncementById(Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Announcement with id: {} was not found", id);
                    return new ResourceNotFoundException("Nie znaleziono ogłoszenia o id: " + id);
                });
        return announcementMapper.entityToResponseDto(announcement);
    }

    public void addAnnouncement(AnnouncementAddRequestDto requestDto, Authentication authentication) {
        Announcement announcement = announcementMapper.requestDtoToEntity(requestDto);
        Long categoryId = requestDto.categoryId();

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->  {
                    logger.warn("Category with id: {} was not found", categoryId);
                    return new ResourceNotFoundException("Nie znaleziono kategorii o id: " + categoryId);
                });

        User user = (User) authentication.getPrincipal();
        announcement.setUser(user);
        announcement.setCategory(category);
        announcement.setIsReviewed(false);

        announcementRepository.save(announcement);

        String title = "Nowe ogłoszenie czeka na akceptacje";
        String message = "Utworzone nowe ogłoszenie o nazwie: " + announcement.getTitle() + ".\n" +
                "Ogłoszenie czeka na akceptację administratora.";
        emailService.sendSimpleMessage(announcement.getUser().getEmail(), title, message);
    }

    public void deleteAnnouncement(Long id, Authentication authentication) {
        Optional<Announcement> optionalAnnouncement = announcementRepository.findById(id);
        if (optionalAnnouncement.isEmpty()) {
            logger.warn("Announcement with id: {} does not exists", id);
            throw new ResourceNotFoundException("Ogłoszenie o id '" + id + "' nie istnieje!");
        }

        Announcement announcement = optionalAnnouncement.get();
        User user = (User) authentication.getPrincipal();
        boolean isAdmin = user.getUserType().equals(UserType.ADMIN);
        Long userId = user.getId();

        if (isAdmin || announcement.getUser().getId().equals(userId)) {
            announcementRepository.deleteById(id);
            logger.info("Announcement with id: {} was successfully deleted", id);
        }
        else {
            logger.warn("User with id: {} is not allowed to delete announcement with id: {}", userId, id);
            throw new AccessDeniedException("Nie masz uprawnień do usunięcia tego ogłoszenia!");
        }
    }

    public void updateAnnouncement(Long id, AnnouncementAddRequestDto requestDto) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Announcement with id: {} was not found", id);
                    return new ResourceNotFoundException("Nie znaleziono ogłoszenia o id: " + id);
                });

        Category category = categoryRepository.findById(requestDto.categoryId())
                .orElseThrow(() ->  {
                    logger.warn("Category with id: {} was not found", requestDto.categoryId());
                    return new ResourceNotFoundException("Nie znaleziono kategorii o id: " + requestDto.categoryId());
                });

        announcement.setDescription(requestDto.description());
        announcement.setTitle(requestDto.title());
        announcement.setEndsAt(requestDto.endsAt());
        announcement.setCategory(category);
        announcementRepository.save(announcement);
    }

    public void setReviewed(Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Announcement with id: {} was not found", id);
                    return new ResourceNotFoundException("Nie znaleziono ogłoszenia o id: " + id);
                });
        announcement.setIsReviewed(true);
        announcementRepository.save(announcement);

        String title = "Zaakceptowano twoje ogłoszenie!";
        String message = "Administrator zaakceptował Twoje ogłoszenie o nazwie: " + announcement.getTitle() + ".\n" +
                "Ogłoszenie jest już widzoczne w serwisie.";
        emailService.sendSimpleMessage(announcement.getUser().getEmail(), title, message);
    }
}