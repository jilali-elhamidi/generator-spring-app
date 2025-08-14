package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.UserPlaylist;import com.example.modules.entertainment_ecosystem.model.MusicTrack;import com.example.modules.entertainment_ecosystem.model.UserProfile;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "userplaylistitem_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserPlaylistItem extends BaseEntity {

// === Attributs simples ===

    @NotNull@Min(1)
    private Integer position;


// === Relations ===

    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "playlist_id")
        
        private UserPlaylist playlist;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "musicTrack_id")
        
        private MusicTrack musicTrack;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "addedBy_id")
        
        private UserProfile addedBy;
    
    

}