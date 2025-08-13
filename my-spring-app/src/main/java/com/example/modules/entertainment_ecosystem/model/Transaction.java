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
import com.example.modules.entertainment_ecosystem.model.UserWallet;import com.example.modules.entertainment_ecosystem.model.Invoice;import com.example.modules.entertainment_ecosystem.model.DigitalPurchase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "transaction_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Transaction extends BaseEntity {

// === Attributs simples ===

    @NotNull
    private Double amount;

    @NotNull
    private Date transactionDate;

    @NotNull
    private String type;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "wallet_id")
    @JsonIgnoreProperties("transactions")
    private UserWallet wallet;
    
    @OneToOne(mappedBy = "transaction", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("transaction")
    private Invoice relatedInvoice;
        
    @OneToOne(mappedBy = "transaction", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("transaction")
    private DigitalPurchase digitalPurchase;
        

}