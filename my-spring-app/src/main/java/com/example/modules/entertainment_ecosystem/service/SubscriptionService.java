package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Subscription;
import com.example.modules.entertainment_ecosystem.repository.SubscriptionRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.StreamingPlatform;
import com.example.modules.entertainment_ecosystem.repository.StreamingPlatformRepository;
import com.example.modules.entertainment_ecosystem.model.SubscriptionPlan;
import com.example.modules.entertainment_ecosystem.repository.SubscriptionPlanRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class SubscriptionService extends BaseService<Subscription> {

    protected final SubscriptionRepository subscriptionRepository;
    private final UserProfileRepository userRepository;
    private final StreamingPlatformRepository platformRepository;
    private final SubscriptionPlanRepository planRepository;

    public SubscriptionService(SubscriptionRepository repository,UserProfileRepository userRepository,StreamingPlatformRepository platformRepository,SubscriptionPlanRepository planRepository)
    {
        super(repository);
        this.subscriptionRepository = repository;
        this.userRepository = userRepository;
        this.platformRepository = platformRepository;
        this.planRepository = planRepository;
    }

    @Override
    public Subscription save(Subscription subscription) {


    

    

    

    if (subscription.getUser() != null
        && subscription.getUser().getId() != null) {
        UserProfile existingUser = userRepository.findById(
        subscription.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        subscription.setUser(existingUser);
        }
    
    if (subscription.getPlatform() != null
        && subscription.getPlatform().getId() != null) {
        StreamingPlatform existingPlatform = platformRepository.findById(
        subscription.getPlatform().getId()
        ).orElseThrow(() -> new RuntimeException("StreamingPlatform not found"));
        subscription.setPlatform(existingPlatform);
        }
    
    if (subscription.getPlan() != null
        && subscription.getPlan().getId() != null) {
        SubscriptionPlan existingPlan = planRepository.findById(
        subscription.getPlan().getId()
        ).orElseThrow(() -> new RuntimeException("SubscriptionPlan not found"));
        subscription.setPlan(existingPlan);
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
        if (subscriptionRequest.getUser() != null &&
        subscriptionRequest.getUser().getId() != null) {

        UserProfile existingUser = userRepository.findById(
        subscriptionRequest.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

        existing.setUser(existingUser);
        } else {
        existing.setUser(null);
        }
        if (subscriptionRequest.getPlatform() != null &&
        subscriptionRequest.getPlatform().getId() != null) {

        StreamingPlatform existingPlatform = platformRepository.findById(
        subscriptionRequest.getPlatform().getId()
        ).orElseThrow(() -> new RuntimeException("StreamingPlatform not found"));

        existing.setPlatform(existingPlatform);
        } else {
        existing.setPlatform(null);
        }
        if (subscriptionRequest.getPlan() != null &&
        subscriptionRequest.getPlan().getId() != null) {

        SubscriptionPlan existingPlan = planRepository.findById(
        subscriptionRequest.getPlan().getId()
        ).orElseThrow(() -> new RuntimeException("SubscriptionPlan not found"));

        existing.setPlan(existingPlan);
        } else {
        existing.setPlan(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    

    


        return subscriptionRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<Subscription> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

Subscription entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    

    


// --- Dissocier ManyToMany ---

    

    

    



// --- Dissocier OneToOne ---

    

    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getUser() != null) {
        entity.setUser(null);
        }
    

    
        if (entity.getPlatform() != null) {
        entity.setPlatform(null);
        }
    

    
        if (entity.getPlan() != null) {
        entity.setPlan(null);
        }
    


repository.delete(entity);
return true;
}
}