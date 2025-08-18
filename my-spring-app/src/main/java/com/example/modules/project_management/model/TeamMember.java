package com.example.modules.project_management.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.project_management.model.Task;import com.example.modules.project_management.model.Project;import com.example.modules.project_management.model.Project;import com.example.modules.project_management.model.Comment;import com.example.modules.project_management.model.Team;import com.example.modules.project_management.model.Team;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "teammember_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TeamMember extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 3, max = 100)
    private String name;

        @NotNull@Email
    private String email;

        @NotNull@Size(min = 2, max = 50)
    private String role;


// === Relations ===

    @OneToMany(mappedBy = "assignee", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("assignee")
        private List<Task> assignedTasks;
    
    @ManyToMany(mappedBy = "teamMembers", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("teamMembers")
            private List<Project> projects;
        
    @OneToMany(mappedBy = "projectManager", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("projectManager")
        private List<Project> managedProjects;
    
    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("author")
        private List<Comment> createdComments;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "team_id")
        @JsonIgnoreProperties("members")
        private Team team;
    
    @OneToOne(mappedBy = "teamLead", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
            @JsonIgnoreProperties("teamLead")
            private Team managedTeam;
        

}