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
@Table(name = "mediafile_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MediaFile extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(max = 255)
    private String url;

    @NotNull@Size(max = 50)
    private String type;


// === Relations ===

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "post_id")
    @JsonIgnoreProperties("media")
    private Post post;
    

}