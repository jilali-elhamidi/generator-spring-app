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
@Table(name = "category_tbl")
public class Category extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 100)
    @Column(unique = true, nullable = false)
    private String name;

    @Size(max = 255)
    private String description;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("category")
    private List<Product> products = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
