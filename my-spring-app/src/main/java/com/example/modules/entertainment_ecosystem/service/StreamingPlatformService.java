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

    public StreamingPlatformService(StreamingPlatformRepository repository,MovieRepository moviesRepository,TVShowRepository tvShowsRepository,SubscriptionRepository subscriptionsRepository,StreamingServiceRepository streamingServiceRepository,OnlinePlatformRepository onlinePlatformRepository,AdCampaignRepository adCampaignsRepository)
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


    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (streamingplatform.getSubscriptions() != null) {
            List<Subscription> managedSubscriptions = new ArrayList<>();
            for (Subscription item : streamingplatform.getSubscriptions()) {
            if (item.getId() != null) {
            Subscription existingItem = subscriptionsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Subscription not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setPlatform(streamingplatform);
            managedSubscriptions.add(existingItem);
            } else {
            item.setPlatform(streamingplatform);
            managedSubscriptions.add(item);
            }
            }
            streamingplatform.setSubscriptions(managedSubscriptions);
            }
        
    

    

    

    

    
    
    
    if (streamingplatform.getStreamingService() != null
        && streamingplatform.getStreamingService().getId() != null) {
        StreamingService existingStreamingService = streamingServiceRepository.findById(
        streamingplatform.getStreamingService().getId()
        ).orElseThrow(() -> new RuntimeException("StreamingService not found"));
        streamingplatform.setStreamingService(existingStreamingService);
        }
    
    if (streamingplatform.getOnlinePlatform() != null
        && streamingplatform.getOnlinePlatform().getId() != null) {
        OnlinePlatform existingOnlinePlatform = onlinePlatformRepository.findById(
        streamingplatform.getOnlinePlatform().getId()
        ).orElseThrow(() -> new RuntimeException("OnlinePlatform not found"));
        streamingplatform.setOnlinePlatform(existingOnlinePlatform);
        }
    
    

        return streamingplatformRepository.save(streamingplatform);
    }


    public StreamingPlatform update(Long id, StreamingPlatform streamingplatformRequest) {
        StreamingPlatform existing = streamingplatformRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("StreamingPlatform not found"));

    // Copier les champs simples
        existing.setName(streamingplatformRequest.getName());
        existing.setWebsite(streamingplatformRequest.getWebsite());

// Relations ManyToOne : mise à jour conditionnelle
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

// Relations ManyToMany : synchronisation sécurisée

        if (streamingplatformRequest.getMovies() != null) {
            existing.getMovies().clear();
            List<Movie> moviesList = streamingplatformRequest.getMovies().stream()
                .map(item -> moviesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Movie not found")))
                .collect(Collectors.toList());
        existing.getMovies().addAll(moviesList);
        }

        if (streamingplatformRequest.getTvShows() != null) {
            existing.getTvShows().clear();
            List<TVShow> tvShowsList = streamingplatformRequest.getTvShows().stream()
                .map(item -> tvShowsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("TVShow not found")))
                .collect(Collectors.toList());
        existing.getTvShows().addAll(tvShowsList);
        }

        if (streamingplatformRequest.getAdCampaigns() != null) {
            existing.getAdCampaigns().clear();
            List<AdCampaign> adCampaignsList = streamingplatformRequest.getAdCampaigns().stream()
                .map(item -> adCampaignsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("AdCampaign not found")))
                .collect(Collectors.toList());
        existing.getAdCampaigns().addAll(adCampaignsList);
        }

// Relations OneToMany : synchronisation sécurisée
        existing.getSubscriptions().clear();

        if (streamingplatformRequest.getSubscriptions() != null) {
        List<Subscription> managedSubscriptions = new ArrayList<>();

        for (var item : streamingplatformRequest.getSubscriptions()) {
        if (item.getId() != null) {
        Subscription existingItem = subscriptionsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Subscription not found"));
        existingItem.setPlatform(existing);
        managedSubscriptions.add(existingItem);
        } else {
        item.setPlatform(existing);
        managedSubscriptions.add(item);
        }
        }
        existing.setSubscriptions(managedSubscriptions);
        }

    

    

    

    

    

    


        return streamingplatformRepository.save(existing);
    }


}