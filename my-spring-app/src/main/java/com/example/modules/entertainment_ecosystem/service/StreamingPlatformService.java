package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.StreamingPlatform;
import com.example.modules.entertainment_ecosystem.repository.StreamingPlatformRepository;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;
import com.example.modules.entertainment_ecosystem.model.Subscription;
import com.example.modules.entertainment_ecosystem.repository.SubscriptionRepository;
import com.example.modules.entertainment_ecosystem.model.StreamingService;
import com.example.modules.entertainment_ecosystem.repository.StreamingServiceRepository;
import com.example.modules.entertainment_ecosystem.model.OnlinePlatform;
import com.example.modules.entertainment_ecosystem.repository.OnlinePlatformRepository;
import com.example.modules.entertainment_ecosystem.model.AdCampaign;
import com.example.modules.entertainment_ecosystem.repository.AdCampaignRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class StreamingPlatformService extends BaseService<StreamingPlatform> {

    protected final StreamingPlatformRepository streamingplatformRepository;
    private final MovieRepository moviesRepository;
    private final TVShowRepository tvShowsRepository;
    private final SubscriptionRepository subscriptionsRepository;
    private final StreamingServiceRepository streamingServiceRepository;
    private final OnlinePlatformRepository onlinePlatformRepository;
    private final AdCampaignRepository adCampaignsRepository;

    public StreamingPlatformService(StreamingPlatformRepository repository, MovieRepository moviesRepository, TVShowRepository tvShowsRepository, SubscriptionRepository subscriptionsRepository, StreamingServiceRepository streamingServiceRepository, OnlinePlatformRepository onlinePlatformRepository, AdCampaignRepository adCampaignsRepository)
    {
        super(repository);
        this.streamingplatformRepository = repository;
        this.moviesRepository = moviesRepository;
        this.tvShowsRepository = tvShowsRepository;
        this.subscriptionsRepository = subscriptionsRepository;
        this.streamingServiceRepository = streamingServiceRepository;
        this.onlinePlatformRepository = onlinePlatformRepository;
        this.adCampaignsRepository = adCampaignsRepository;
    }

    @Override
    public StreamingPlatform save(StreamingPlatform streamingplatform) {
    // ---------- OneToMany ----------
        if (streamingplatform.getSubscriptions() != null) {
            List<Subscription> managedSubscriptions = new ArrayList<>();
            for (Subscription item : streamingplatform.getSubscriptions()) {
                if (item.getId() != null) {
                    Subscription existingItem = subscriptionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Subscription not found"));

                     existingItem.setPlatform(streamingplatform);
                     managedSubscriptions.add(existingItem);
                } else {
                    item.setPlatform(streamingplatform);
                    managedSubscriptions.add(item);
                }
            }
            streamingplatform.setSubscriptions(managedSubscriptions);
        }
    
    // ---------- ManyToMany ----------
        if (streamingplatform.getMovies() != null &&
            !streamingplatform.getMovies().isEmpty()) {

            List<Movie> attachedMovies = new ArrayList<>();
            for (Movie item : streamingplatform.getMovies()) {
                if (item.getId() != null) {
                    Movie existingItem = moviesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Movie not found with id " + item.getId()));
                    attachedMovies.add(existingItem);
                } else {

                    Movie newItem = moviesRepository.save(item);
                    attachedMovies.add(newItem);
                }
            }

            streamingplatform.setMovies(attachedMovies);

            // côté propriétaire (Movie → StreamingPlatform)
            attachedMovies.forEach(it -> it.getPlatforms().add(streamingplatform));
        }
        
        if (streamingplatform.getTvShows() != null &&
            !streamingplatform.getTvShows().isEmpty()) {

            List<TVShow> attachedTvShows = new ArrayList<>();
            for (TVShow item : streamingplatform.getTvShows()) {
                if (item.getId() != null) {
                    TVShow existingItem = tvShowsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("TVShow not found with id " + item.getId()));
                    attachedTvShows.add(existingItem);
                } else {

                    TVShow newItem = tvShowsRepository.save(item);
                    attachedTvShows.add(newItem);
                }
            }

            streamingplatform.setTvShows(attachedTvShows);

            // côté propriétaire (TVShow → StreamingPlatform)
            attachedTvShows.forEach(it -> it.getPlatforms().add(streamingplatform));
        }
        
        if (streamingplatform.getAdCampaigns() != null &&
            !streamingplatform.getAdCampaigns().isEmpty()) {

            List<AdCampaign> attachedAdCampaigns = new ArrayList<>();
            for (AdCampaign item : streamingplatform.getAdCampaigns()) {
                if (item.getId() != null) {
                    AdCampaign existingItem = adCampaignsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("AdCampaign not found with id " + item.getId()));
                    attachedAdCampaigns.add(existingItem);
                } else {

                    AdCampaign newItem = adCampaignsRepository.save(item);
                    attachedAdCampaigns.add(newItem);
                }
            }

            streamingplatform.setAdCampaigns(attachedAdCampaigns);

            // côté propriétaire (AdCampaign → StreamingPlatform)
            attachedAdCampaigns.forEach(it -> it.getDisplayedOnPlatforms().add(streamingplatform));
        }
        
    // ---------- ManyToOne ----------
        if (streamingplatform.getStreamingService() != null) {
            if (streamingplatform.getStreamingService().getId() != null) {
                StreamingService existingStreamingService = streamingServiceRepository.findById(
                    streamingplatform.getStreamingService().getId()
                ).orElseThrow(() -> new RuntimeException("StreamingService not found with id "
                    + streamingplatform.getStreamingService().getId()));
                streamingplatform.setStreamingService(existingStreamingService);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                StreamingService newStreamingService = streamingServiceRepository.save(streamingplatform.getStreamingService());
                streamingplatform.setStreamingService(newStreamingService);
            }
        }
        
        if (streamingplatform.getOnlinePlatform() != null) {
            if (streamingplatform.getOnlinePlatform().getId() != null) {
                OnlinePlatform existingOnlinePlatform = onlinePlatformRepository.findById(
                    streamingplatform.getOnlinePlatform().getId()
                ).orElseThrow(() -> new RuntimeException("OnlinePlatform not found with id "
                    + streamingplatform.getOnlinePlatform().getId()));
                streamingplatform.setOnlinePlatform(existingOnlinePlatform);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                OnlinePlatform newOnlinePlatform = onlinePlatformRepository.save(streamingplatform.getOnlinePlatform());
                streamingplatform.setOnlinePlatform(newOnlinePlatform);
            }
        }
        
    // ---------- OneToOne ----------
    return streamingplatformRepository.save(streamingplatform);
}


    public StreamingPlatform update(Long id, StreamingPlatform streamingplatformRequest) {
        StreamingPlatform existing = streamingplatformRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("StreamingPlatform not found"));

    // Copier les champs simples
        existing.setName(streamingplatformRequest.getName());
        existing.setWebsite(streamingplatformRequest.getWebsite());

    // ---------- Relations ManyToOne ----------
        if (streamingplatformRequest.getStreamingService() != null &&
            streamingplatformRequest.getStreamingService().getId() != null) {

            StreamingService existingStreamingService = streamingServiceRepository.findById(
                streamingplatformRequest.getStreamingService().getId()
            ).orElseThrow(() -> new RuntimeException("StreamingService not found"));

            existing.setStreamingService(existingStreamingService);
        } else {
            existing.setStreamingService(null);
        }
        
        if (streamingplatformRequest.getOnlinePlatform() != null &&
            streamingplatformRequest.getOnlinePlatform().getId() != null) {

            OnlinePlatform existingOnlinePlatform = onlinePlatformRepository.findById(
                streamingplatformRequest.getOnlinePlatform().getId()
            ).orElseThrow(() -> new RuntimeException("OnlinePlatform not found"));

            existing.setOnlinePlatform(existingOnlinePlatform);
        } else {
            existing.setOnlinePlatform(null);
        }
        
    // ---------- Relations ManyToOne ----------
        if (streamingplatformRequest.getMovies() != null) {
            existing.getMovies().clear();

            List<Movie> moviesList = streamingplatformRequest.getMovies().stream()
                .map(item -> moviesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Movie not found")))
                .collect(Collectors.toList());

            existing.getMovies().addAll(moviesList);

            // Mettre à jour le côté inverse
            moviesList.forEach(it -> {
                if (!it.getPlatforms().contains(existing)) {
                    it.getPlatforms().add(existing);
                }
            });
        }
        
        if (streamingplatformRequest.getTvShows() != null) {
            existing.getTvShows().clear();

            List<TVShow> tvShowsList = streamingplatformRequest.getTvShows().stream()
                .map(item -> tvShowsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("TVShow not found")))
                .collect(Collectors.toList());

            existing.getTvShows().addAll(tvShowsList);

            // Mettre à jour le côté inverse
            tvShowsList.forEach(it -> {
                if (!it.getPlatforms().contains(existing)) {
                    it.getPlatforms().add(existing);
                }
            });
        }
        
        if (streamingplatformRequest.getAdCampaigns() != null) {
            existing.getAdCampaigns().clear();

            List<AdCampaign> adCampaignsList = streamingplatformRequest.getAdCampaigns().stream()
                .map(item -> adCampaignsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("AdCampaign not found")))
                .collect(Collectors.toList());

            existing.getAdCampaigns().addAll(adCampaignsList);

            // Mettre à jour le côté inverse
            adCampaignsList.forEach(it -> {
                if (!it.getDisplayedOnPlatforms().contains(existing)) {
                    it.getDisplayedOnPlatforms().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
        existing.getSubscriptions().clear();

        if (streamingplatformRequest.getSubscriptions() != null) {
            for (var item : streamingplatformRequest.getSubscriptions()) {
                Subscription existingItem;
                if (item.getId() != null) {
                    existingItem = subscriptionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Subscription not found"));
                } else {
                existingItem = item;
                }

                existingItem.setPlatform(existing);
                existing.getSubscriptions().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return streamingplatformRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<StreamingPlatform> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        StreamingPlatform entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getSubscriptions() != null) {
            for (var child : entity.getSubscriptions()) {
                // retirer la référence inverse
                child.setPlatform(null);
            }
            entity.getSubscriptions().clear();
        }
        
    // --- Dissocier ManyToMany ---
        if (entity.getMovies() != null) {
            for (Movie item : new ArrayList<>(entity.getMovies())) {
                
                item.getPlatforms().remove(entity); // retire côté inverse
            }
            entity.getMovies().clear(); // puis vide côté courant
        }
        
        if (entity.getTvShows() != null) {
            for (TVShow item : new ArrayList<>(entity.getTvShows())) {
                
                item.getPlatforms().remove(entity); // retire côté inverse
            }
            entity.getTvShows().clear(); // puis vide côté courant
        }
        
        if (entity.getAdCampaigns() != null) {
            for (AdCampaign item : new ArrayList<>(entity.getAdCampaigns())) {
                
                item.getDisplayedOnPlatforms().remove(entity); // retire côté inverse
            }
            entity.getAdCampaigns().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getStreamingService() != null) {
            entity.setStreamingService(null);
        }
        
        if (entity.getOnlinePlatform() != null) {
            entity.setOnlinePlatform(null);
        }
        
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<StreamingPlatform> saveAll(List<StreamingPlatform> streamingplatformList) {

        return streamingplatformRepository.saveAll(streamingplatformList);
    }

}