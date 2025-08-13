package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.FeatureFlag;
import com.example.modules.entertainment_ecosystem.repository.FeatureFlagRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class FeatureFlagService extends BaseService<FeatureFlag> {

    protected final FeatureFlagRepository featureflagRepository;
    private final UserProfileRepository enabledForUsersRepository;

    public FeatureFlagService(FeatureFlagRepository repository,UserProfileRepository enabledForUsersRepository)
    {
        super(repository);
        this.featureflagRepository = repository;
        this.enabledForUsersRepository = enabledForUsersRepository;
    }

    @Override
    public FeatureFlag save(FeatureFlag featureflag) {

        return featureflagRepository.save(featureflag);
    }


    public FeatureFlag update(Long id, FeatureFlag featureflagRequest) {
        FeatureFlag existing = featureflagRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("FeatureFlag not found"));

    // Copier les champs simples
        existing.setName(featureflagRequest.getName());
        existing.setEnabled(featureflagRequest.getEnabled());
        existing.setRolloutPercentage(featureflagRequest.getRolloutPercentage());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

        if (featureflagRequest.getEnabledForUsers() != null) {
            existing.getEnabledForUsers().clear();
            List<UserProfile> enabledForUsersList = featureflagRequest.getEnabledForUsers().stream()
                .map(item -> enabledForUsersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());
        existing.getEnabledForUsers().addAll(enabledForUsersList);
        }

// Relations OneToMany : synchronisation sécurisée

    


        return featureflagRepository.save(existing);
    }
}