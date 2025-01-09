package com.example.backend.models;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
}