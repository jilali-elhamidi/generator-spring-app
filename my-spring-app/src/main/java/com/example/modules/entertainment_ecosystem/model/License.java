package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.model.DigitalAsset;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "license_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class License extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 10, max = 100)
    private String licenseKey;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;


// === Relations ===

    @OneToOne
    @JoinColumn(name = "asset_id")
    @JsonIgnoreProperties("license")
    private DigitalAsset asset;
            

}