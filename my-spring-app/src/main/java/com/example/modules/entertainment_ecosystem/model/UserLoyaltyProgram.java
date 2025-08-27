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
@Table(name = "userloyaltyprogram_tbl")
public class UserLoyaltyProgram extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 50)
    @Column(unique = true, nullable = false)
    private String name;

    @NotNull
    private Integer pointsBalance;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===

    // === Relations OneToOne ===
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("loyaltyProgram")
    private UserProfile user;
    

    // === Relations ManyToMany ===
}
