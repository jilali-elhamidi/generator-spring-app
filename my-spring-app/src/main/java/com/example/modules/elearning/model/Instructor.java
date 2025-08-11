package com.example.modules.elearning.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;

import com.example.modules.elearning.model.Course;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "instructor_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Instructor extends BaseEntity {

// === Attributs simples ===

    private String firstName;

    private String lastName;

    private String email;


// === Relations ===

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("instructor")
    private List<Course> courses;
    

}