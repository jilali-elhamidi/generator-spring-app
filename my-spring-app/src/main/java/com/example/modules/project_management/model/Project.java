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
import com.example.modules.project_management.model.Task;import com.example.modules.project_management.model.TeamMember;import com.example.modules.project_management.model.TeamMember;import com.example.modules.project_management.model.Document;import com.example.modules.project_management.model.Milestone;import com.example.modules.project_management.model.Client;import com.example.modules.project_management.model.Team;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "project_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Project extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 5, max = 200)
    private String projectName;

        @Size(max = 1000)
    private String description;

        @NotNull
    private Date startDate;

        @NotNull
    private Date deadline;

        @NotNull
    private String status;


// === Relations ===

    @OneToMany(mappedBy = "project", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("project")
        private List<Task> tasks;
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "project_members",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
            @JsonIgnoreProperties("")
            private List<TeamMember> teamMembers;
            
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "manager_id")
        @JsonIgnoreProperties("managedProjects")
        private TeamMember projectManager;
    
    @OneToMany(mappedBy = "project", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("project")
        private List<Document> documents;
    
    @OneToMany(mappedBy = "project", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("project")
        private List<Milestone> milestones;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "client_id")
        @JsonIgnoreProperties("projects")
        private Client client;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "team_id")
        @JsonIgnoreProperties("teamProjects")
        private Team team;
    

}