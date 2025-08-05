package com.example.modules.elearning.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


    import com.example.modules.elearning.model.Lesson;

    import com.example.modules.elearning.model.Instructor;

import java.util.List;


@Entity
@Table(name = "course_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Course extends BaseEntity {


    private String title;

    private String description;



    
        @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("course") // éviter boucle infinie
        private List<Lesson> lessons;
    

    

    

    

    
        @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "instructor_id")
        @JsonIgnoreProperties("course") // éviter boucle
        private Instructor instructor;
    

    


// Getters et Setters


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
