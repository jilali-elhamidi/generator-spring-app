package com.example.modules.ecommerce.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Date;
import com.example.modules.ecommerce.model.Order;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "payment_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Payment extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(max = 50)
    private String method;

        @NotNull
    private Date paymentDate;

        @NotNull@Min(2)
    private Double amount;


// === Relations ===

    @OneToOne(mappedBy = "payment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
            @JsonIgnoreProperties("payment")
            private Order order;
        

}