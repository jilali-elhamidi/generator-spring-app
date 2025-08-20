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
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Entity
@Table(name = "merchandisestockhistory_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MerchandiseStockHistory extends BaseEntity {

// === Attributs simples ===

        @NotNull
    private Date changeDate;

        @NotNull
    private Integer quantityChange;

        @Size(max = 255)
    private String reason;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "merchandise_id")
        @JsonIgnoreProperties("stockHistory")
        private Merchandise merchandiseItem;
    

}