package com.example.modules.project_management.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.project_management.model.Task;import com.example.modules.project_management.model.TeamMember;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "subtask_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Subtask extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 3, max = 150)
    private String title;

        @NotNull
    private String status;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "task_id")
        @JsonIgnoreProperties("subtasks")
        private Task parentTask;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "assignee_id")
        @JsonIgnoreProperties("assignedSubtasks")
        private TeamMember assignee;
    

}