package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Subscription;
import com.example.modules.entertainment_ecosystem.repository.SubscriptionRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.StreamingPlatform;
import com.example.modules.entertainment_ecosystem.repository.StreamingPlatformRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class SubscriptionService extends BaseService<Subscription> {

    protected final SubscriptionRepository subscriptionRepository;
    private final UserProfileRepository userRepository;
    private final StreamingPlatformRepository platformRepository;

    public SubscriptionService(SubscriptionRepository repository,UserProfileRepository userRepository,StreamingPlatformRepository platformRepository)
    {
        super(repository);
        this.subscriptionRepository = repository;
        this.userRepository = userRepository;
        this.platformRepository = platformRepository;
    }

    @Override
    public Subscription save(Subscription subscription) {

        if (subscription.getUser() != null && subscription.getUser().getId() != null) {
        UserProfile user = userRepository.findById(subscription.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        subscription.setUser(user);
        }

        if (subscription.getPlatform() != null && subscription.getPlatform().getId() != null) {
        StreamingPlatform platform = platformRepository.findById(subscription.getPlatform().getId())
                .orElseThrow(() -> new RuntimeException("StreamingPlatform not found"));
        subscription.setPlatform(platform);
        }

        return subscriptionRepository.save(subscription);
    }


    public Subscription update(Long id, Subscription subscriptionRequest) {
        Subscription existing = subscriptionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Subscription not found"));

    // Copier les champs simples
        existing.setStartDate(subscriptionRequest.getStartDate());
        existing.setEndDate(subscriptionRequest.getEndDate());
        existing.setStatus(subscriptionRequest.getStatus());

// Relations ManyToOne : mise à jour conditionnelle

        if (subscriptionRequest.getUser() != null && subscriptionRequest.getUser().getId() != null) {
        UserProfile user = userRepository.findById(subscriptionRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setUser(user);
        }

        if (subscriptionRequest.getPlatform() != null && subscriptionRequest.getPlatform().getId() != null) {
        StreamingPlatform platform = platformRepository.findById(subscriptionRequest.getPlatform().getId())
                .orElseThrow(() -> new RuntimeException("StreamingPlatform not found"));
        existing.setPlatform(platform);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        return subscriptionRepository.save(existing);
    }
}