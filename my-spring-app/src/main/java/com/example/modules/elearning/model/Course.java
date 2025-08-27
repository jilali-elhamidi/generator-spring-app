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
@Table(name = "course_tbl")
public class Course extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 3, max = 100)
    @Column(unique = true, nullable = false)
    private String title;

    @Size(max = 500)
    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "instructor_id")
    @JsonIgnoreProperties("courses")
    private Instructor instructor;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "course", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("course")
    private List<Lesson> lessons = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
