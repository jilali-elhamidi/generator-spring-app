package com.example.modules.healthcare_management.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.healthcare_management.model.Prescription;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "medication_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Medication extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(max = 100)
    private String name;

    @Size(max = 50)
    private String type;

    @Size(max = 100)
    private String manufacturer;


// === Relations ===

    @ManyToMany(mappedBy = "medications", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("medications")
            private List<Prescription> prescriptions;
        

}