package com.example.modules.healthcare_management.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.healthcare_management.model.Doctor;import com.example.modules.healthcare_management.model.Doctor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "department_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Department extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 3, max = 100)
    private String name;

    @Size(max = 255)
    private String description;


// === Relations ===

    @OneToMany(mappedBy = "department", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("department")
    private List<Doctor> doctors;
    
    @OneToOne(mappedBy = "", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("")
    private Doctor head;
        

}