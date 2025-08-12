package com.example.modules.healthcare_management.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Date;
import com.example.modules.healthcare_management.model.Invoice;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "reimbursement_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Reimbursement extends BaseEntity {

// === Attributs simples ===

    @NotNull
    private Date ReimbursementDate;

    @NotNull
    private Double amount;

    @NotNull
    private String method;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "invoice_id")
    @JsonIgnoreProperties("Reimbursements")
    private Invoice invoice;
    

}