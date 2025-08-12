package com.example.modules.healthcare_management.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Date;
import com.example.modules.healthcare_management.model.MedicalRecord;import com.example.modules.healthcare_management.model.Appointment;import com.example.modules.healthcare_management.model.Prescription;import com.example.modules.healthcare_management.model.Invoice;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "patient_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Patient extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 2, max = 50)
    private String firstName;

    @NotNull@Size(min = 2, max = 50)
    private String lastName;

    @NotNull
    private Date dateOfBirth;

    @NotNull
    private String gender;

    @Email
    private String email;

    @Size(max = 20)
    private String phoneNumber;


// === Relations ===

    @OneToMany(mappedBy = "patient", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("patient")
    private List<MedicalRecord> medicalRecords;
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("patient")
    private List<Appointment> appointments;
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("patient")
    private List<Prescription> prescriptions;
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("patient")
    private List<Invoice> invoices;
    

}