package com.example.modules.healthcare_management.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.healthcare_management.model.Appointment;import com.example.modules.healthcare_management.model.Prescription;import com.example.modules.healthcare_management.model.Department;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "doctor_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Doctor extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 2, max = 50)
    private String firstName;

    @NotNull@Size(min = 2, max = 50)
    private String lastName;

    @NotNull@Size(max = 100)
    private String specialty;

    @Email
    private String email;

    @Size(max = 20)
    private String phoneNumber;


// === Relations ===

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("doctor")
    private List<Appointment> appointments;
    
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("doctor")
    private List<Prescription> prescriptions;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "department_id")
    @JsonIgnoreProperties("doctors")
    private Department department;
    

}