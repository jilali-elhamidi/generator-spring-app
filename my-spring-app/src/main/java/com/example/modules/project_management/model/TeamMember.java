package com.example.modules.project_management.model;

// === Java / Jakarta ===
import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;


// === Jackson ===
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

// === Lombok ===
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "teammember_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TeamMember extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 3, max = 100)
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 2, max = 50)
    private String role;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "team_id")
    @JsonIgnoreProperties("members")
    private Team team;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "assignee", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("assignee")
    private List<Task> assignedTasks = new ArrayList<>();
    
    @OneToMany(mappedBy = "projectManager", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("projectManager")
    private List<Project> managedProjects = new ArrayList<>();
    
    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("author")
    private List<Comment> createdComments = new ArrayList<>();
    

    // === Relations OneToOne ===
    @OneToOne(mappedBy = "teamLead", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("teamLead")
    private Team managedTeam;

    // === Relations ManyToMany ===
    @ManyToMany(mappedBy = "teamMembers", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("teamMembers")
    private List<Project> projects = new ArrayList<>();
    
}
