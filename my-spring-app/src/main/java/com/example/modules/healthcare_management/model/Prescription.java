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
import com.example.modules.healthcare_management.model.Patient;import com.example.modules.healthcare_management.model.Doctor;import com.example.modules.healthcare_management.model.Medication;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "prescription_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Prescription extends BaseEntity {

// === Attributs simples ===

    @NotNull
    private Date prescriptionDate;

    @NotNull@Size(max = 255)
    private String dosageInstructions;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "patient_id")
    @JsonIgnoreProperties("prescriptions")
    private Patient patient;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "doctor_id")
    @JsonIgnoreProperties("prescriptions")
    private Doctor doctor;
    
    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "prescription_medications",
            joinColumns = @JoinColumn(name = "prescription_id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id"))
            @JsonIgnoreProperties("")
            private List<Medication> medications;
            

}