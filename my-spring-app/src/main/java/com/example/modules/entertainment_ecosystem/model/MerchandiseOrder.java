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
import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.MerchandiseOrderItem;import com.example.modules.entertainment_ecosystem.model.MerchandiseShipping;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "merchandiseorder_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MerchandiseOrder extends BaseEntity {

// === Attributs simples ===

    @NotNull
    private Date orderDate;

    @NotNull
    private String status;


// === Relations ===

    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "user_id")
        
        private UserProfile user;
    
    
    
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<MerchandiseOrderItem> items;
    
    
    @OneToOne
    @JoinColumn(name = "shipping_id")
    @JsonIgnoreProperties("order")
    private MerchandiseShipping shippingDetails;
            

}