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
@Table(name = "gamecurrency_tbl")
public class GameCurrency extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 50)
    @Column(unique = true, nullable = false)
    private String name;

    @NotNull
    @Size(max = 10)
    private String symbol;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "currency", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("currency")
    private List<GameTransaction> transactions = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
