package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.FeatureFlag;
import com.example.modules.entertainment_ecosystem.repository.FeatureFlagRepository;

import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class FeatureFlagService extends BaseService<FeatureFlag> {

    protected final FeatureFlagRepository featureflagRepository;
    
    protected final UserProfileRepository enabledForUsersRepository;
    

    public FeatureFlagService(FeatureFlagRepository repository, UserProfileRepository enabledForUsersRepository)
    {
        super(repository);
        this.featureflagRepository = repository;
        
        this.enabledForUsersRepository = enabledForUsersRepository;
        
    }

    @Transactional
    @Override
    public FeatureFlag save(FeatureFlag featureflag) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (featureflag.getEnabledForUsers() != null &&
            !featureflag.getEnabledForUsers().isEmpty()) {

            List<UserProfile> attachedEnabledForUsers = new ArrayList<>();
            for (UserProfile item : featureflag.getEnabledForUsers()) {
                if (item.getId() != null) {
                    UserProfile existingItem = enabledForUsersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserProfile not found with id " + item.getId()));
                    attachedEnabledForUsers.add(existingItem);
                } else {

                    UserProfile newItem = enabledForUsersRepository.save(item);
                    attachedEnabledForUsers.add(newItem);
                }
            }

            featureflag.setEnabledForUsers(attachedEnabledForUsers);

            // côté propriétaire (UserProfile → FeatureFlag)
            attachedEnabledForUsers.forEach(it -> it.getEnabledFeatureFlags().add(featureflag));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return featureflagRepository.save(featureflag);
}

    @Transactional
    @Override
    public FeatureFlag update(Long id, FeatureFlag featureflagRequest) {
        FeatureFlag existing = featureflagRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("FeatureFlag not found"));

    // Copier les champs simples
        existing.setName(featureflagRequest.getName());
        existing.setEnabled(featureflagRequest.getEnabled());
        existing.setRolloutPercentage(featureflagRequest.getRolloutPercentage());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
        if (featureflagRequest.getEnabledForUsers() != null) {
            existing.getEnabledForUsers().clear();

            List<UserProfile> enabledForUsersList = featureflagRequest.getEnabledForUsers().stream()
                .map(item -> enabledForUsersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());

            existing.getEnabledForUsers().addAll(enabledForUsersList);

            // Mettre à jour le côté inverse
            enabledForUsersList.forEach(it -> {
                if (!it.getEnabledFeatureFlags().contains(existing)) {
                    it.getEnabledFeatureFlags().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return featureflagRepository.save(existing);
}

    // Pagination simple
    public Page<FeatureFlag> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<FeatureFlag> search(Map<String, String> filters, Pageable pageable) {
        return super.search(FeatureFlag.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<FeatureFlag> saveAll(List<FeatureFlag> featureflagList) {
        return super.saveAll(featureflagList);
    }

}