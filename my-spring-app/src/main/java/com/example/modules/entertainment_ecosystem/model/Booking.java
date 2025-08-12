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
import com.example.modules.entertainment_ecosystem.model.Ticket;import com.example.modules.entertainment_ecosystem.model.Payment;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "booking_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Booking extends BaseEntity {

// === Attributs simples ===

    @NotNull
    private Date bookingDate;

    @NotNull
    private Double totalAmount;


// === Relations ===

    @OneToMany(mappedBy = "booking", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("booking")
    private List<Ticket> tickets;
    
    @OneToOne
    @JoinColumn(name = "payment_id")
    @JsonIgnoreProperties("booking")
    private Payment payment;
            

}