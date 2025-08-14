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
import com.example.modules.entertainment_ecosystem.model.Merchandise;import com.example.modules.entertainment_ecosystem.model.Warehouse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "merchandiseinventory_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MerchandiseInventory extends BaseEntity {

// === Attributs simples ===

    @NotNull
    private Integer stockQuantity;

    @NotNull
    private Date lastUpdated;


// === Relations ===


    @OneToOne
    @JoinColumn(name = "merchandise_id") // clé étrangère
    @JsonIgnoreProperties("inventory")
    private Merchandise merchandiseItem;
        
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "warehouse_id")
        
        private Warehouse warehouse;
    
    

}