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
@Table(name = "license_tbl")
public class License extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 10, max = 100)
    @Column(unique = true, nullable = false)
    private String licenseKey;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===

    // === Relations OneToOne ===
    @OneToOne
    @JoinColumn(name = "asset_id")
    @JsonIgnoreProperties("license")
    private DigitalAsset asset;
    

    // === Relations ManyToMany ===
}
