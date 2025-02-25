package com.example.backend.services;

import com.example.backend.exceptions.ConflictException;
import com.example.backend.exceptions.ResourceNotFoundException;
import com.example.backend.models.Category;
import com.example.backend.repositories.AnnouncementRepository;
import com.example.backend.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryRepository categoryRepository;
    private final AnnouncementRepository announcementRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void addCategory(String name) {
        Optional<Category> categoryName = categoryRepository.findByName(name);
        if (categoryName.isPresent()) {
            logger.warn("Category: {} already exists", name);
            throw new ConflictException("Kategoria '" + name + "' już istnieje!");
        }
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (id == 1) {
            logger.warn("Category with id: {} cannot be deleted", id);
            throw new ConflictException("Kategoria o id 1 nie może zostać usunięta!");
        }

        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            logger.warn("Category with id: {} does not exists", id);
            throw new ResourceNotFoundException("Kategoria o id '" + id + "' nie istnieje!");
        }
        announcementRepository.changeCategoryToDefault(id);
        categoryRepository.deleteById(id);
    }

    public void updateCategory(Long id, String newName) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Category with id: {} does not exists", id);
                    return new ResourceNotFoundException("Nie znaleziono kategorii o id: " + id);
                });

        Optional<Category> categoryName = categoryRepository.findByName(newName);
        if (categoryName.isPresent()) {
            logger.warn("Category: {} already exists", newName);
            throw new ConflictException("Kategoria '" + newName+ "' już istnieje!");
        }

        category.setName(newName);
        categoryRepository.save(category);
    }
}