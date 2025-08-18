package com.example.modules.project_management.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.project_management.model.TeamMember;import com.example.modules.project_management.model.TeamMember;import com.example.modules.project_management.model.Project;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "team_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Team extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 2, max = 100)
    private String name;

        @Size(max = 255)
    private String motto;


// === Relations ===

    @OneToMany(mappedBy = "team", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("team")
        private List<TeamMember> members;
    
    @OneToOne
            @JoinColumn(name = "lead_id")
            @JsonIgnoreProperties("managedTeam")
            private TeamMember teamLead;
            
    @OneToMany(mappedBy = "team", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("team")
        private List<Project> teamProjects;
    

}