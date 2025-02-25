package com.example.backend.repositories;

import com.example.backend.models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        Category category = new Category();
        category.setName("food");
        categoryRepository.save(category);
    }

    @Test
    void testFindByName() {
        Optional<Category> foundCategory = categoryRepository.findByName("food");

        assertTrue(foundCategory.isPresent());
    }

    @Test
    void testFindByNameNotExist() {
        Optional<Category> foundCategory = categoryRepository.findByName("Not food");

        assertTrue(foundCategory.isEmpty());
    }


}