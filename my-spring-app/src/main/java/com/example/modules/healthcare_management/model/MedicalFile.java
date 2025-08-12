package com.example.modules.healthcare_management.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.healthcare_management.model.MedicalRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "medicalfile_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MedicalFile extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(max = 255)
    private String fileUrl;

    @NotNull@Size(max = 50)
    private String fileType;


// === Relations ===

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "record_id")
    @JsonIgnoreProperties("attachments")
    private MedicalRecord record;
    

}