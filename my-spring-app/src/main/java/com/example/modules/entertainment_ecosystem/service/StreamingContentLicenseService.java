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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

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

        if (streamingcontentlicense.getSubscriptionPlan() != null && streamingcontentlicense.getSubscriptionPlan().getId() != null) {
        SubscriptionPlan subscriptionPlan = subscriptionPlanRepository.findById(streamingcontentlicense.getSubscriptionPlan().getId())
                .orElseThrow(() -> new RuntimeException("SubscriptionPlan not found"));
        streamingcontentlicense.setSubscriptionPlan(subscriptionPlan);
        }

        if (streamingcontentlicense.getMovie() != null && streamingcontentlicense.getMovie().getId() != null) {
        Movie movie = movieRepository.findById(streamingcontentlicense.getMovie().getId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        streamingcontentlicense.setMovie(movie);
        }

        if (streamingcontentlicense.getTvShow() != null && streamingcontentlicense.getTvShow().getId() != null) {
        TVShow tvShow = tvShowRepository.findById(streamingcontentlicense.getTvShow().getId())
                .orElseThrow(() -> new RuntimeException("TVShow not found"));
        streamingcontentlicense.setTvShow(tvShow);
        }

        if (streamingcontentlicense.getMusicTrack() != null && streamingcontentlicense.getMusicTrack().getId() != null) {
        MusicTrack musicTrack = musicTrackRepository.findById(streamingcontentlicense.getMusicTrack().getId())
                .orElseThrow(() -> new RuntimeException("MusicTrack not found"));
        streamingcontentlicense.setMusicTrack(musicTrack);
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

        if (streamingcontentlicenseRequest.getSubscriptionPlan() != null && streamingcontentlicenseRequest.getSubscriptionPlan().getId() != null) {
        SubscriptionPlan subscriptionPlan = subscriptionPlanRepository.findById(streamingcontentlicenseRequest.getSubscriptionPlan().getId())
                .orElseThrow(() -> new RuntimeException("SubscriptionPlan not found"));
        existing.setSubscriptionPlan(subscriptionPlan);
        }

        if (streamingcontentlicenseRequest.getMovie() != null && streamingcontentlicenseRequest.getMovie().getId() != null) {
        Movie movie = movieRepository.findById(streamingcontentlicenseRequest.getMovie().getId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        existing.setMovie(movie);
        }

        if (streamingcontentlicenseRequest.getTvShow() != null && streamingcontentlicenseRequest.getTvShow().getId() != null) {
        TVShow tvShow = tvShowRepository.findById(streamingcontentlicenseRequest.getTvShow().getId())
                .orElseThrow(() -> new RuntimeException("TVShow not found"));
        existing.setTvShow(tvShow);
        }

        if (streamingcontentlicenseRequest.getMusicTrack() != null && streamingcontentlicenseRequest.getMusicTrack().getId() != null) {
        MusicTrack musicTrack = musicTrackRepository.findById(streamingcontentlicenseRequest.getMusicTrack().getId())
                .orElseThrow(() -> new RuntimeException("MusicTrack not found"));
        existing.setMusicTrack(musicTrack);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    

    

    


        return streamingcontentlicenseRepository.save(existing);
    }
}