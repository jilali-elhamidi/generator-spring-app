package com.example.modules.healthcare_management.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.healthcare_management.model.Appointment;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "room_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Room extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(max = 10)
    private String roomNumber;

    @NotNull
    private String type;

    @Min(1)
    private Integer capacity;


// === Relations ===

    @OneToMany(mappedBy = "room", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("room")
    private List<Appointment> appointments;
    

}