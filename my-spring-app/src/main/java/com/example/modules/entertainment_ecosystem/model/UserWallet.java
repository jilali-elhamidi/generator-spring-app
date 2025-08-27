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
@Table(name = "userwallet_tbl")
public class UserWallet extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Column(unique = true, nullable = false)
    private Double balance;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "wallet", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("wallet")
    private List<Transaction> transactions = new ArrayList<>();
    

    // === Relations OneToOne ===
    @OneToOne(mappedBy = "wallet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("wallet")
    private UserProfile user;

    // === Relations ManyToMany ===
}
