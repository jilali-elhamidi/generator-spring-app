package com.example.modules.elearning.model;

// === Java / Jakarta ===
import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


// === Lombok ===
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lesson_tbl")
public class Lesson extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 3, max = 100)
    @Column(unique = true, nullable = false)
    private String title;

    @NotNull
    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "course_id")
    @JsonIgnoreProperties("lessons")
    private Course course;
    

    // === Relations OneToMany ===

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
