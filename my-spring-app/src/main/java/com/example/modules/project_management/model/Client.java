package com.example.modules.project_management.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.project_management.model.Project;import com.example.modules.project_management.model.Invoice;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "client_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Client extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 2, max = 150)
    private String companyName;

        @NotNull@Size(min = 2, max = 100)
    private String contactPerson;

        @NotNull@Email
    private String email;

        @Size(max = 20)
    private String phoneNumber;


// === Relations ===

    @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("client")
        private List<Project> projects;
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("client")
        private List<Invoice> invoices;
    

}