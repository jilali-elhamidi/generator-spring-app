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
@Table(name = "contract_tbl")
public class Contract extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 5, max = 50)
    @Column(unique = true, nullable = false)
    private String contractNumber;

    @Size(max = 2000)
    private String terms;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===

    // === Relations OneToOne ===
    @OneToOne(mappedBy = "contract", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("contract")
    private EventSponsorship sponsorship;

    // === Relations ManyToMany ===
}
