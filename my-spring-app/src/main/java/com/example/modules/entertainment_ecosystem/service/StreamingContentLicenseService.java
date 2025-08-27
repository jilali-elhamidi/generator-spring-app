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

import com.example.modules.entertainment_ecosystem.model.ContentLicenseType;
import com.example.modules.entertainment_ecosystem.repository.ContentLicenseTypeRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class StreamingContentLicenseService extends BaseService<StreamingContentLicense> {

    protected final StreamingContentLicenseRepository streamingcontentlicenseRepository;
    
    protected final SubscriptionPlanRepository subscriptionPlanRepository;
    
    protected final MovieRepository movieRepository;
    
    protected final TVShowRepository tvShowRepository;
    
    protected final MusicTrackRepository musicTrackRepository;
    
    protected final ContentLicenseTypeRepository licenseTypeRepository;
    

    public StreamingContentLicenseService(StreamingContentLicenseRepository repository, SubscriptionPlanRepository subscriptionPlanRepository, MovieRepository movieRepository, TVShowRepository tvShowRepository, MusicTrackRepository musicTrackRepository, ContentLicenseTypeRepository licenseTypeRepository)
    {
        super(repository);
        this.streamingcontentlicenseRepository = repository;
        
        this.subscriptionPlanRepository = subscriptionPlanRepository;
        
        this.movieRepository = movieRepository;
        
        this.tvShowRepository = tvShowRepository;
        
        this.musicTrackRepository = musicTrackRepository;
        
        this.licenseTypeRepository = licenseTypeRepository;
        
    }

    @Transactional
    @Override
    public StreamingContentLicense save(StreamingContentLicense streamingcontentlicense) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (streamingcontentlicense.getSubscriptionPlan() != null) {
            if (streamingcontentlicense.getSubscriptionPlan().getId() != null) {
                SubscriptionPlan existingSubscriptionPlan = subscriptionPlanRepository.findById(
                    streamingcontentlicense.getSubscriptionPlan().getId()
                ).orElseThrow(() -> new RuntimeException("SubscriptionPlan not found with id "
                    + streamingcontentlicense.getSubscriptionPlan().getId()));
                streamingcontentlicense.setSubscriptionPlan(existingSubscriptionPlan);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                SubscriptionPlan newSubscriptionPlan = subscriptionPlanRepository.save(streamingcontentlicense.getSubscriptionPlan());
                streamingcontentlicense.setSubscriptionPlan(newSubscriptionPlan);
            }
        }
        
        if (streamingcontentlicense.getMovie() != null) {
            if (streamingcontentlicense.getMovie().getId() != null) {
                Movie existingMovie = movieRepository.findById(
                    streamingcontentlicense.getMovie().getId()
                ).orElseThrow(() -> new RuntimeException("Movie not found with id "
                    + streamingcontentlicense.getMovie().getId()));
                streamingcontentlicense.setMovie(existingMovie);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Movie newMovie = movieRepository.save(streamingcontentlicense.getMovie());
                streamingcontentlicense.setMovie(newMovie);
            }
        }
        
        if (streamingcontentlicense.getTvShow() != null) {
            if (streamingcontentlicense.getTvShow().getId() != null) {
                TVShow existingTvShow = tvShowRepository.findById(
                    streamingcontentlicense.getTvShow().getId()
                ).orElseThrow(() -> new RuntimeException("TVShow not found with id "
                    + streamingcontentlicense.getTvShow().getId()));
                streamingcontentlicense.setTvShow(existingTvShow);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                TVShow newTvShow = tvShowRepository.save(streamingcontentlicense.getTvShow());
                streamingcontentlicense.setTvShow(newTvShow);
            }
        }
        
        if (streamingcontentlicense.getMusicTrack() != null) {
            if (streamingcontentlicense.getMusicTrack().getId() != null) {
                MusicTrack existingMusicTrack = musicTrackRepository.findById(
                    streamingcontentlicense.getMusicTrack().getId()
                ).orElseThrow(() -> new RuntimeException("MusicTrack not found with id "
                    + streamingcontentlicense.getMusicTrack().getId()));
                streamingcontentlicense.setMusicTrack(existingMusicTrack);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                MusicTrack newMusicTrack = musicTrackRepository.save(streamingcontentlicense.getMusicTrack());
                streamingcontentlicense.setMusicTrack(newMusicTrack);
            }
        }
        
        if (streamingcontentlicense.getLicenseType() != null) {
            if (streamingcontentlicense.getLicenseType().getId() != null) {
                ContentLicenseType existingLicenseType = licenseTypeRepository.findById(
                    streamingcontentlicense.getLicenseType().getId()
                ).orElseThrow(() -> new RuntimeException("ContentLicenseType not found with id "
                    + streamingcontentlicense.getLicenseType().getId()));
                streamingcontentlicense.setLicenseType(existingLicenseType);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                ContentLicenseType newLicenseType = licenseTypeRepository.save(streamingcontentlicense.getLicenseType());
                streamingcontentlicense.setLicenseType(newLicenseType);
            }
        }
        
    // ---------- OneToOne ----------
    return streamingcontentlicenseRepository.save(streamingcontentlicense);
}

    @Transactional
    @Override
    public StreamingContentLicense update(Long id, StreamingContentLicense streamingcontentlicenseRequest) {
        StreamingContentLicense existing = streamingcontentlicenseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("StreamingContentLicense not found"));

    // Copier les champs simples
        existing.setStartDate(streamingcontentlicenseRequest.getStartDate());
        existing.setEndDate(streamingcontentlicenseRequest.getEndDate());
        existing.setRegion(streamingcontentlicenseRequest.getRegion());

    // ---------- Relations ManyToOne ----------
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
        
        if (streamingcontentlicenseRequest.getLicenseType() != null &&
            streamingcontentlicenseRequest.getLicenseType().getId() != null) {

            ContentLicenseType existingLicenseType = licenseTypeRepository.findById(
                streamingcontentlicenseRequest.getLicenseType().getId()
            ).orElseThrow(() -> new RuntimeException("ContentLicenseType not found"));

            existing.setLicenseType(existingLicenseType);
        } else {
            existing.setLicenseType(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return streamingcontentlicenseRepository.save(existing);
}

    // Pagination simple
    public Page<StreamingContentLicense> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<StreamingContentLicense> search(Map<String, String> filters, Pageable pageable) {
        return super.search(StreamingContentLicense.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<StreamingContentLicense> saveAll(List<StreamingContentLicense> streamingcontentlicenseList) {
        return super.saveAll(streamingcontentlicenseList);
    }

}