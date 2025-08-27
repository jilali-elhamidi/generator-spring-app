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
@Table(name = "milestone_tbl")
public class Milestone extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 100)
    @Column(unique = true, nullable = false)
    private String title;

    @NotNull
    private Date dueDate;

    @NotNull
    private String status;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties("milestones")
    private Project project;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "milestone", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("milestone")
    private List<Task> relatedTasks = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
