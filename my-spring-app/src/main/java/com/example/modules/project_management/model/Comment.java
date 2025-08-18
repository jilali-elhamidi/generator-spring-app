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
import com.example.modules.project_management.model.TeamMember;import com.example.modules.project_management.model.Task;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "comment_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Comment extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 1, max = 500)
    private String content;

        @NotNull
    private Date commentDate;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "author_id")
        @JsonIgnoreProperties("createdComments")
        private TeamMember author;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "task_id")
        @JsonIgnoreProperties("comments")
        private Task task;
    

}