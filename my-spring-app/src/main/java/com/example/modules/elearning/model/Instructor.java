package com.example.modules.elearning.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

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

    @NotNull@Size(min = 2, max = 50)
    private String firstName;

    @NotNull@Size(min = 2, max = 50)
    private String lastName;

    @NotNull@Email
    private String email;


// === Relations ===

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("instructor")
    private List<Course> courses;
    

}