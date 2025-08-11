package com.example.modules.elearning.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;

import com.example.modules.elearning.model.Lesson;import com.example.modules.elearning.model.Instructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "course_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Course extends BaseEntity {

// === Attributs simples ===

    private String title;

    private String description;


// === Relations ===

    @OneToMany(mappedBy = "course", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("course")
    private List<Lesson> lessons;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "instructor_id")
    @JsonIgnoreProperties("courses")
    private Instructor instructor;
    

}