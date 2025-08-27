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
@Table(name = "medicalfile_tbl")
public class MedicalFile extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(max = 255)
    @Column(unique = true, nullable = false)
    private String fileUrl;

    @NotNull
    @Size(max = 50)
    private String fileType;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "record_id")
    @JsonIgnoreProperties("attachments")
    private MedicalRecord record;
    

    // === Relations OneToMany ===

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
