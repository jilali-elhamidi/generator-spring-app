package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.FeatureFlag;
import com.example.modules.entertainment_ecosystem.repository.FeatureFlagRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

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


    


    
        if (featureflag.getEnabledForUsers() != null
        && !featureflag.getEnabledForUsers().isEmpty()) {

        List<UserProfile> attachedEnabledForUsers = featureflag.getEnabledForUsers().stream()
        .map(item -> enabledForUsersRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserProfile not found with id " + item.getId())))
        .toList();

        featureflag.setEnabledForUsers(attachedEnabledForUsers);

        // côté propriétaire (UserProfile → FeatureFlag)
        attachedEnabledForUsers.forEach(it -> it.getEnabledFeatureFlags().add(featureflag));
        }
    

    

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

        // Mettre à jour le côté inverse
        enabledForUsersList.forEach(it -> {
        if (!it.getEnabledFeatureFlags().contains(existing)) {
        it.getEnabledFeatureFlags().add(existing);
        }
        });
        }

// Relations OneToMany : synchronisation sécurisée

    


        return featureflagRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<FeatureFlag> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

FeatureFlag entity = entityOpt.get();

// --- Dissocier OneToMany ---

    


// --- Dissocier ManyToMany ---

    
        if (entity.getEnabledForUsers() != null) {
        for (UserProfile item : new ArrayList<>(entity.getEnabledForUsers())) {
        
            item.getEnabledFeatureFlags().remove(entity); // retire côté inverse
        
        }
        entity.getEnabledForUsers().clear(); // puis vide côté courant
        }
    



// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}