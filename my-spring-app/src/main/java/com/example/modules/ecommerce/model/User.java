package com.example.modules.ecommerce.model;

// === Java / Jakarta ===
import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


// === Lombok ===
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_tbl")
public class User extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 3, max = 50)
    @Column(unique = true, nullable = false)
    private String username;

    @NotNull
    @Email
    private String email;

    @Size(max = 20)
    private String phone;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<Address> addresses = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<Order> orders = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
