package com.example.modules.entertainment_ecosystem.model;

// === Java / Jakarta ===
import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

// === Lombok ===
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "userblockedlist_tbl")
public class UserBlockedList extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Column(unique = true, nullable = false)
    private Date blockedDate;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "blocker_id")
    @JsonIgnoreProperties("blockedUsers")
    private UserProfile blocker;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "blocked_id")
    @JsonIgnoreProperties("blockedByUsers")
    private UserProfile blocked;
    

    // === Relations OneToMany ===

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
