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
import com.example.modules.healthcare_management.model.Patient;import com.example.modules.healthcare_management.model.Doctor;import com.example.modules.healthcare_management.model.MedicalFile;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "medicalrecord_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MedicalRecord extends BaseEntity {

// === Attributs simples ===

    @NotNull
    private Date recordDate;

    @NotNull@Size(max = 255)
    private String diagnosis;

    @Size(max = 500)
    private String notes;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "patient_id")
    @JsonIgnoreProperties("medicalRecords")
    private Patient patient;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "doctor_id")
    @JsonIgnoreProperties("")
    private Doctor doctor;
    
    @OneToMany(mappedBy = "record", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("record")
    private List<MedicalFile> attachments;
    

}