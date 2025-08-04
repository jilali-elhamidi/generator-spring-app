package com.example.modules.elearning.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "instructor_tbl")
public class Instructor extends BaseEntity {


    private String firstName;

    private String lastName;

    private String email;



    
    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Course> courses;
    
    


// Getters et Setters

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
