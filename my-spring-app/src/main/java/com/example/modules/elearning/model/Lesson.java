package com.example.modules.elearning.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


    import com.example.modules.elearning.model.Course;






@Entity
@Table(name = "lesson_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Lesson extends BaseEntity {


    private String title;

    private String content;



    

    
        @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "course_id")
        @JsonIgnoreProperties("lesson") // éviter boucle
        private Course course;
    

    


// Getters et Setters


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
