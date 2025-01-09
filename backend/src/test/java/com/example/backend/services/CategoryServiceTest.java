package com.example.backend.services;

import com.example.backend.exceptions.ConflictException;
import com.example.backend.exceptions.ResourceNotFoundException;
import com.example.backend.models.Category;
import com.example.backend.repositories.AnnouncementRepository;
import com.example.backend.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private AnnouncementRepository announcementRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addCategory_categoryDoesNotExist_savesCategory() {
        when(categoryRepository.findByName(any(String.class))).thenReturn(Optional.empty());

        categoryService.addCategory("New Category");

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    public void addCategory_categoryExists_throwsConflictException() {
        when(categoryRepository.findByName(any(String.class))).thenReturn(Optional.of(new Category()));

        assertThrows(ConflictException.class, () -> categoryService.addCategory("Existing Category"));

        verify(categoryRepository, times(0)).save(any(Category.class));
    }

    @Test
    public void deleteCategory_categoryExists_deletesCategory() {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(new Category()));

        categoryService.deleteCategory(2L);

        verify(categoryRepository, times(1)).deleteById(any(Long.class));
    }

    @Test
    public void deleteCategory_categoryDoesNotExist_throwsResourceNotFoundException() {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteCategory(2L));

        verify(categoryRepository, times(0)).deleteById(any(Long.class));
    }

    @Test
    public void updateCategory_categoryExists_updatesCategory() {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(new Category()));
        when(categoryRepository.findByName(any(String.class))).thenReturn(Optional.empty());

        categoryService.updateCategory(1L, "New Name");

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    public void updateCategory_categoryDoesNotExist_throwsResourceNotFoundException() {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.updateCategory(1L, "New Name"));

        verify(categoryRepository, times(0)).save(any(Category.class));
    }

    @Test
    public void updateCategory_newNameAlreadyExists_throwsConflictException() {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(new Category()));
        when(categoryRepository.findByName(any(String.class))).thenReturn(Optional.of(new Category()));

        assertThrows(ConflictException.class, () -> categoryService.updateCategory(1L, "Existing Name"));

        verify(categoryRepository, times(0)).save(any(Category.class));
    }
}