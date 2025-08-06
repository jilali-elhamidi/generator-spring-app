package com.example.modules.elearning.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.util.List;

import com.example.modules.elearning.model.Course;

@Entity
@Table(name = "lesson_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Lesson extends BaseEntity {

// === Attributs simples ===

    
    private String title;

    
    private String content;


// === Relations ===

    

    
        @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "course_id")
        @JsonIgnoreProperties("lessons")
        
        private Course course;
    

    


// === Getters & Setters ===

    public String getTitle() {
    return title;
    }

    public void setTitle(String title) {
    this.title = title;
    }

    public String getContent() {
    return content;
    }

    public void setContent(String content) {
    this.content = content;
    }



    public Course getCourse() {
    return course;
    }

    public void setCourse(Course course) {
    this.course = course;
    }

}
