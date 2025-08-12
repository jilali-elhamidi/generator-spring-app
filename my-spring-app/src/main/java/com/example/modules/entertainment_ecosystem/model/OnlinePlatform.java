package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.OnlineEvent;import com.example.modules.entertainment_ecosystem.model.StreamingPlatform;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "onlineplatform_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OnlinePlatform extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 2, max = 100)
    private String name;

    @NotNull@Size(max = 255)
    private String url;


// === Relations ===

    @OneToMany(mappedBy = "platform", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("platform")
    private List<OnlineEvent> liveEvents;
    
    @OneToMany(mappedBy = "onlinePlatform", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("onlinePlatform")
    private List<StreamingPlatform> streams;
    

}