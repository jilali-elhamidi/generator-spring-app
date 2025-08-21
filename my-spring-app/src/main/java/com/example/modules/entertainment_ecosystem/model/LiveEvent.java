package com.example.modules.entertainment_ecosystem.model;

// === Java / Jakarta ===
import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Date;

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
@Table(name = "liveevent_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class LiveEvent extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 255)
        @Column(unique = true, nullable = false)
    private String name;

    @NotNull
    private Date eventDate;

    @Size(max = 1000)
    private String description;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "event_type_id")
    @JsonIgnoreProperties("events")
    private EventType eventType;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "location_id")
    @JsonIgnoreProperties("liveEvents")
    private EventLocation location;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "sponsor_id")
    @JsonIgnoreProperties("sponsoredEvents")
    private Sponsor sponsor;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "venue_id")
    @JsonIgnoreProperties("concerts")
    private ConcertVenue venue;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "tour_id")
    @JsonIgnoreProperties("liveEvents")
    private Tour tour;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "event", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("event")
    private List<Ticket> tickets = new ArrayList<>();
    
    @OneToMany(mappedBy = "event", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("event")
    private List<EventSponsorship> sponsorships = new ArrayList<>();
    

    // === Relations OneToOne ===
    @OneToOne
    @JoinColumn(name = "audience_id")
    @JsonIgnoreProperties("event")
    private EventAudience audience;
    

    // === Relations ManyToMany ===
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "event_performers",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "artist_id"))
    @JsonIgnoreProperties("participatedInEvents")
    private List<Artist> performers = new ArrayList<>();
    
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "event_tags",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @JsonIgnoreProperties("liveEvents")
    private List<ContentTag> tags = new ArrayList<>();
    
    
}
