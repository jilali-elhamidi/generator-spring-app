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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class SubscriptionService extends BaseService<Subscription> {

    protected final SubscriptionRepository subscriptionRepository;
    
    protected final UserProfileRepository userRepository;
    
    protected final StreamingPlatformRepository platformRepository;
    
    protected final SubscriptionPlanRepository planRepository;
    

    public SubscriptionService(SubscriptionRepository repository, UserProfileRepository userRepository, StreamingPlatformRepository platformRepository, SubscriptionPlanRepository planRepository)
    {
        super(repository);
        this.subscriptionRepository = repository;
        
        this.userRepository = userRepository;
        
        this.platformRepository = platformRepository;
        
        this.planRepository = planRepository;
        
    }

    @Transactional
    @Override
    public Subscription save(Subscription subscription) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (subscription.getUser() != null) {
            if (subscription.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    subscription.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + subscription.getUser().getId()));
                subscription.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(subscription.getUser());
                subscription.setUser(newUser);
            }
        }
        
        if (subscription.getPlatform() != null) {
            if (subscription.getPlatform().getId() != null) {
                StreamingPlatform existingPlatform = platformRepository.findById(
                    subscription.getPlatform().getId()
                ).orElseThrow(() -> new RuntimeException("StreamingPlatform not found with id "
                    + subscription.getPlatform().getId()));
                subscription.setPlatform(existingPlatform);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                StreamingPlatform newPlatform = platformRepository.save(subscription.getPlatform());
                subscription.setPlatform(newPlatform);
            }
        }
        
        if (subscription.getPlan() != null) {
            if (subscription.getPlan().getId() != null) {
                SubscriptionPlan existingPlan = planRepository.findById(
                    subscription.getPlan().getId()
                ).orElseThrow(() -> new RuntimeException("SubscriptionPlan not found with id "
                    + subscription.getPlan().getId()));
                subscription.setPlan(existingPlan);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                SubscriptionPlan newPlan = planRepository.save(subscription.getPlan());
                subscription.setPlan(newPlan);
            }
        }
        
    // ---------- OneToOne ----------
    return subscriptionRepository.save(subscription);
}

    @Transactional
    @Override
    public Subscription update(Long id, Subscription subscriptionRequest) {
        Subscription existing = subscriptionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Subscription not found"));

    // Copier les champs simples
        existing.setStartDate(subscriptionRequest.getStartDate());
        existing.setEndDate(subscriptionRequest.getEndDate());
        existing.setStatus(subscriptionRequest.getStatus());

    // ---------- Relations ManyToOne ----------
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
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return subscriptionRepository.save(existing);
}

    // Pagination simple
    public Page<Subscription> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Subscription> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Subscription.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Subscription> saveAll(List<Subscription> subscriptionList) {
        return super.saveAll(subscriptionList);
    }

}