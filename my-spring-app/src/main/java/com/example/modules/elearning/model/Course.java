package com.example.modules.elearning.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.util.List;

import com.example.modules.elearning.model.Lesson;import com.example.modules.elearning.model.Instructor;

@Entity
@Table(name = "course_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
    

    


// === Getters & Setters ===

    public String getTitle() {
    return title;
    }

    public void setTitle(String title) {
    this.title = title;
    }

    public String getDescription() {
    return description;
    }

    public void setDescription(String description) {
    this.description = description;
    }



    public List<Lesson> getLessons() {
    return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
    this.lessons = lessons;
    }

    public Instructor getInstructor() {
    return instructor;
    }

    public void setInstructor(Instructor instructor) {
    this.instructor = instructor;
    }

}
