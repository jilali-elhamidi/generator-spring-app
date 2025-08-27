package com.example.modules.project_management.model;

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
@Table(name = "subtask_tbl")
public class Subtask extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 3, max = 150)
    @Column(unique = true, nullable = false)
    private String title;

    @NotNull
    private String status;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "task_id")
    @JsonIgnoreProperties("subtasks")
    private Task parentTask;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "assignee_id")
    @JsonIgnoreProperties("assignedSubtasks")
    private TeamMember assignee;
    

    // === Relations OneToMany ===

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
