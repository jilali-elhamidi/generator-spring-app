package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.StreamingContentLicense;
import com.example.modules.entertainment_ecosystem.repository.StreamingContentLicenseRepository;
import com.example.modules.entertainment_ecosystem.model.SubscriptionPlan;
import com.example.modules.entertainment_ecosystem.repository.SubscriptionPlanRepository;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;
import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import com.example.modules.entertainment_ecosystem.repository.MusicTrackRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class StreamingContentLicenseService extends BaseService<StreamingContentLicense> {

    protected final StreamingContentLicenseRepository streamingcontentlicenseRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final MovieRepository movieRepository;
    private final TVShowRepository tvShowRepository;
    private final MusicTrackRepository musicTrackRepository;

    public StreamingContentLicenseService(StreamingContentLicenseRepository repository,SubscriptionPlanRepository subscriptionPlanRepository,MovieRepository movieRepository,TVShowRepository tvShowRepository,MusicTrackRepository musicTrackRepository)
    {
        super(repository);
        this.streamingcontentlicenseRepository = repository;
        this.subscriptionPlanRepository = subscriptionPlanRepository;
        this.movieRepository = movieRepository;
        this.tvShowRepository = tvShowRepository;
        this.musicTrackRepository = musicTrackRepository;
    }

    @Override
    public StreamingContentLicense save(StreamingContentLicense streamingcontentlicense) {


    

    

    

    

    if (streamingcontentlicense.getSubscriptionPlan() != null
        && streamingcontentlicense.getSubscriptionPlan().getId() != null) {
        SubscriptionPlan existingSubscriptionPlan = subscriptionPlanRepository.findById(
        streamingcontentlicense.getSubscriptionPlan().getId()
        ).orElseThrow(() -> new RuntimeException("SubscriptionPlan not found"));
        streamingcontentlicense.setSubscriptionPlan(existingSubscriptionPlan);
        }
    
    if (streamingcontentlicense.getMovie() != null
        && streamingcontentlicense.getMovie().getId() != null) {
        Movie existingMovie = movieRepository.findById(
        streamingcontentlicense.getMovie().getId()
        ).orElseThrow(() -> new RuntimeException("Movie not found"));
        streamingcontentlicense.setMovie(existingMovie);
        }
    
    if (streamingcontentlicense.getTvShow() != null
        && streamingcontentlicense.getTvShow().getId() != null) {
        TVShow existingTvShow = tvShowRepository.findById(
        streamingcontentlicense.getTvShow().getId()
        ).orElseThrow(() -> new RuntimeException("TVShow not found"));
        streamingcontentlicense.setTvShow(existingTvShow);
        }
    
    if (streamingcontentlicense.getMusicTrack() != null
        && streamingcontentlicense.getMusicTrack().getId() != null) {
        MusicTrack existingMusicTrack = musicTrackRepository.findById(
        streamingcontentlicense.getMusicTrack().getId()
        ).orElseThrow(() -> new RuntimeException("MusicTrack not found"));
        streamingcontentlicense.setMusicTrack(existingMusicTrack);
        }
    

        return streamingcontentlicenseRepository.save(streamingcontentlicense);
    }


    public StreamingContentLicense update(Long id, StreamingContentLicense streamingcontentlicenseRequest) {
        StreamingContentLicense existing = streamingcontentlicenseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("StreamingContentLicense not found"));

    // Copier les champs simples
        existing.setStartDate(streamingcontentlicenseRequest.getStartDate());
        existing.setEndDate(streamingcontentlicenseRequest.getEndDate());
        existing.setRegion(streamingcontentlicenseRequest.getRegion());

// Relations ManyToOne : mise à jour conditionnelle
        if (streamingcontentlicenseRequest.getSubscriptionPlan() != null &&
        streamingcontentlicenseRequest.getSubscriptionPlan().getId() != null) {

        SubscriptionPlan existingSubscriptionPlan = subscriptionPlanRepository.findById(
        streamingcontentlicenseRequest.getSubscriptionPlan().getId()
        ).orElseThrow(() -> new RuntimeException("SubscriptionPlan not found"));

        existing.setSubscriptionPlan(existingSubscriptionPlan);
        } else {
        existing.setSubscriptionPlan(null);
        }
        if (streamingcontentlicenseRequest.getMovie() != null &&
        streamingcontentlicenseRequest.getMovie().getId() != null) {

        Movie existingMovie = movieRepository.findById(
        streamingcontentlicenseRequest.getMovie().getId()
        ).orElseThrow(() -> new RuntimeException("Movie not found"));

        existing.setMovie(existingMovie);
        } else {
        existing.setMovie(null);
        }
        if (streamingcontentlicenseRequest.getTvShow() != null &&
        streamingcontentlicenseRequest.getTvShow().getId() != null) {

        TVShow existingTvShow = tvShowRepository.findById(
        streamingcontentlicenseRequest.getTvShow().getId()
        ).orElseThrow(() -> new RuntimeException("TVShow not found"));

        existing.setTvShow(existingTvShow);
        } else {
        existing.setTvShow(null);
        }
        if (streamingcontentlicenseRequest.getMusicTrack() != null &&
        streamingcontentlicenseRequest.getMusicTrack().getId() != null) {

        MusicTrack existingMusicTrack = musicTrackRepository.findById(
        streamingcontentlicenseRequest.getMusicTrack().getId()
        ).orElseThrow(() -> new RuntimeException("MusicTrack not found"));

        existing.setMusicTrack(existingMusicTrack);
        } else {
        existing.setMusicTrack(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    

    

    


        return streamingcontentlicenseRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<StreamingContentLicense> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

StreamingContentLicense entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    

    

    


// --- Dissocier ManyToMany ---

    

    

    

    



// --- Dissocier OneToOne ---

    

    

    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getSubscriptionPlan() != null) {
        entity.setSubscriptionPlan(null);
        }
    

    
        if (entity.getMovie() != null) {
        entity.setMovie(null);
        }
    

    
        if (entity.getTvShow() != null) {
        entity.setTvShow(null);
        }
    

    
        if (entity.getMusicTrack() != null) {
        entity.setMusicTrack(null);
        }
    


repository.delete(entity);
return true;
}
}