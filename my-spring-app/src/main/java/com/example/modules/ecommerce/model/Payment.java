package com.example.modules.ecommerce.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;






    import java.util.Date;


@Entity
@Table(name = "payment_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Payment extends BaseEntity {


    private String method;

    private Date paymentDate;

    private Double amount;




// Getters et Setters


    public String getMethod() {
    return method;
    }

    public void setMethod(String method) {
    this.method = method;
    }

    public Date getPaymentDate() {
    return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
    this.paymentDate = paymentDate;
    }

    public Double getAmount() {
    return amount;
    }

    public void setAmount(Double amount) {
    this.amount = amount;
    }



}
