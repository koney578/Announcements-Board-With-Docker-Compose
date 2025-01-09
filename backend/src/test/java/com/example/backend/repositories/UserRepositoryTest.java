package com.example.backend.repositories;

import com.example.backend.enums.UserType;
import com.example.backend.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("user123");
        user.setEmail("user123@mail.com");
        user.setPassword("zaq1@WSX");
        user.setUserType(UserType.BASIC);
        userRepository.save(user);
    }

    @Test
    void newUserIdShouldNotBeNull() {
        assertNotNull(user.getId());
    }

    @Test
    void testFindByUsername() {
        Optional<User> foundUser = userRepository.findByUsername("user123");
        assertTrue(foundUser.isPresent());
    }

    @Test
    void testFindByUsernameNotExistingUser() {
        Optional<User> foundUser = userRepository.findByUsername("abcd");
        assertTrue(foundUser.isEmpty());
    }

    @Test
    void testFindByEmail() {
        Optional<User> foundUser = userRepository.findByEmail("user123@mail.com");
        assertTrue(foundUser.isPresent());
    }

    @Test
    void testFindByEmailNotExistingUser() {
        Optional<User> foundUser = userRepository.findByEmail("abc@mail.com");
        assertTrue(foundUser.isEmpty());
    }

}