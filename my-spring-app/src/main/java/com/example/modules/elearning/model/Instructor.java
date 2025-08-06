package com.example.modules.elearning.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.util.List;

import com.example.modules.elearning.model.Course;

@Entity
@Table(name = "instructor_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Instructor extends BaseEntity {

// === Attributs simples ===

    
    private String firstName;

    
    private String lastName;

    
    private String email;


// === Relations ===

    
        @OneToMany(mappedBy = "instructor", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("instructor")
        private List<Course> courses;
    

    

    


// === Getters & Setters ===

    public String getFirstName() {
    return firstName;
    }

    public void setFirstName(String firstName) {
    this.firstName = firstName;
    }

    public String getLastName() {
    return lastName;
    }

    public void setLastName(String lastName) {
    this.lastName = lastName;
    }

    public String getEmail() {
    return email;
    }

    public void setEmail(String email) {
    this.email = email;
    }



    public List<Course> getCourses() {
    return courses;
    }

    public void setCourses(List<Course> courses) {
    this.courses = courses;
    }

}
