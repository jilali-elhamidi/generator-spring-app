package com.example.modules.project_management.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Date;
import com.example.modules.project_management.model.Project;import com.example.modules.project_management.model.TeamMember;import com.example.modules.project_management.model.DocumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "document_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Document extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 2, max = 200)
    private String title;

        @NotNull@Size(max = 500)
    private String fileUrl;

        @NotNull
    private Date uploadDate;


// === Relations ===

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
    

}