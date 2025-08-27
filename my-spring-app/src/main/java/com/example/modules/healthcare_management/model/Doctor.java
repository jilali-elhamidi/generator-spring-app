package com.example.modules.healthcare_management.model;

// === Java / Jakarta ===
import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


// === Lombok ===
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "doctor_tbl")
public class Doctor extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 50)
    @Column(unique = true, nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 50)
    private String lastName;

    @NotNull
    @Size(max = 100)
    private String specialty;

    @Email
    private String email;

    @Size(max = 20)
    private String phoneNumber;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "department_id")
    @JsonIgnoreProperties("doctors")
    private Department department;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("doctor")
    private List<Appointment> appointments = new ArrayList<>();
    
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("doctor")
    private List<Prescription> prescriptions = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
