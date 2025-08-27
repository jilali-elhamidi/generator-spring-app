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
@Table(name = "transaction_tbl")
public class Transaction extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Column(unique = true, nullable = false)
    private Double amount;

    @NotNull
    private Date transactionDate;

    @NotNull
    private String type;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "wallet_id")
    @JsonIgnoreProperties("transactions")
    private UserWallet wallet;
    

    // === Relations OneToMany ===

    // === Relations OneToOne ===
    @OneToOne(mappedBy = "transaction", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("transaction")
    private Invoice relatedInvoice;
    @OneToOne(mappedBy = "transaction", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("transaction")
    private DigitalPurchase digitalPurchase;

    // === Relations ManyToMany ===
}
