package com.example.modules.social_media.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;

import com.example.modules.social_media.model.Post;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tag_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Tag extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 1, max = 50)
    private String name;


// === Relations ===

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("tags")
            private List<Post> posts;
        

}