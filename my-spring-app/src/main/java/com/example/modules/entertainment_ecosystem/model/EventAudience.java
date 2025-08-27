package com.example.modules.entertainment_ecosystem.model;

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
@Table(name = "eventaudience_tbl")
public class EventAudience extends BaseEntity {

    // === Attributs simples ===
    @Column(unique = true, nullable = false)
    private Integer count;

    private String audienceType;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===

    // === Relations OneToOne ===
    @OneToOne
    @JoinColumn(name = "event_id")
    @JsonIgnoreProperties("audience")
    private LiveEvent event;
    

    // === Relations ManyToMany ===
}
