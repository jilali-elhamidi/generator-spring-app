package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.LiveEvent;import com.example.modules.entertainment_ecosystem.model.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "eventlocation_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class EventLocation extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 2, max = 255)
    private String name;

    @NotNull@Size(min = 5, max = 255)
    private String address;


// === Relations ===

    @OneToMany(mappedBy = "location", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("location")
    private List<LiveEvent> liveEvents;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "employee_id")
    @JsonIgnoreProperties("managedLocations")
    private Employee contactPerson;
    

}