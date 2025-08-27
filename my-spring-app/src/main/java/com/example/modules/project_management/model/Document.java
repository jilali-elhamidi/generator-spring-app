package com.example.modules.project_management.model;

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
@Table(name = "document_tbl")
public class Document extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 200)
    @Column(unique = true, nullable = false)
    private String title;

    @NotNull
    @Size(max = 500)
    private String fileUrl;

    @NotNull
    private Date uploadDate;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties("documents")
    private Project project;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "uploader_id")
    @JsonIgnoreProperties("uploadedDocuments")
    private TeamMember uploadedBy;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "type_id")
    @JsonIgnoreProperties("documents")
    private DocumentType type;
    

    // === Relations OneToMany ===

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
