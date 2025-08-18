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
import com.example.modules.project_management.model.Project;import com.example.modules.project_management.model.TeamMember;import com.example.modules.project_management.model.Subtask;import com.example.modules.project_management.model.Comment;import com.example.modules.project_management.model.Tag;import com.example.modules.project_management.model.Attachment;import com.example.modules.project_management.model.TimeLog;import com.example.modules.project_management.model.Milestone;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "task_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Task extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 3, max = 150)
    private String title;

        @Size(max = 500)
    private String description;

        @NotNull
    private Date dueDate;

        @NotNull
    private String priority;

        @NotNull
    private String status;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "project_id")
        @JsonIgnoreProperties("tasks")
        private Project project;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "assignee_id")
        @JsonIgnoreProperties("assignedTasks")
        private TeamMember assignee;
    
    @OneToMany(mappedBy = "parentTask", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("parentTask")
        private List<Subtask> subtasks;
    
    @OneToMany(mappedBy = "task", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("task")
        private List<Comment> comments;
    
    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "task_tags",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
            @JsonIgnoreProperties("")
            private List<Tag> tags;
            
    @OneToMany(mappedBy = "task", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("task")
        private List<Attachment> attachments;
    
    @OneToMany(mappedBy = "task", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("task")
        private List<TimeLog> log;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "milestone_id")
        @JsonIgnoreProperties("relatedTasks")
        private Milestone milestone;
    

}