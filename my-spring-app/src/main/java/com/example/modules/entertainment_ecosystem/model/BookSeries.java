package com.example.modules.entertainment_ecosystem.model;

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
@Table(name = "bookseries_tbl")
public class BookSeries extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 255)
    @Column(unique = true, nullable = false)
    private String name;

    @Size(max = 1000)
    private String description;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "series", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("series")
    private List<Book> books = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
