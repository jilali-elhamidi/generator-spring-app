package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserBadge;
import com.example.modules.entertainment_ecosystem.repository.UserBadgeRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class UserBadgeService extends BaseService<UserBadge> {

    protected final UserBadgeRepository userbadgeRepository;
    private final UserProfileRepository usersRepository;

    public UserBadgeService(UserBadgeRepository repository,UserProfileRepository usersRepository)
    {
        super(repository);
        this.userbadgeRepository = repository;
        this.usersRepository = usersRepository;
    }

    @Override
    public UserBadge save(UserBadge userbadge) {


    


    
        if (userbadge.getUsers() != null
        && !userbadge.getUsers().isEmpty()) {

        List<UserProfile> attachedUsers = userbadge.getUsers().stream()
        .map(item -> usersRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserProfile not found with id " + item.getId())))
        .toList();

        userbadge.setUsers(attachedUsers);

        // côté propriétaire (UserProfile → UserBadge)
        attachedUsers.forEach(it -> it.getBadges().add(userbadge));
        }
    

    

        return userbadgeRepository.save(userbadge);
    }


    public UserBadge update(Long id, UserBadge userbadgeRequest) {
        UserBadge existing = userbadgeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserBadge not found"));

    // Copier les champs simples
        existing.setName(userbadgeRequest.getName());
        existing.setDescription(userbadgeRequest.getDescription());
        existing.setImageUrl(userbadgeRequest.getImageUrl());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée
        if (userbadgeRequest.getUsers() != null) {
        existing.getUsers().clear();

        List<UserProfile> usersList = userbadgeRequest.getUsers().stream()
        .map(item -> usersRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserProfile not found")))
        .collect(Collectors.toList());

        existing.getUsers().addAll(usersList);

        // Mettre à jour le côté inverse
        usersList.forEach(it -> {
        if (!it.getBadges().contains(existing)) {
        it.getBadges().add(existing);
        }
        });
        }

// Relations OneToMany : synchronisation sécurisée

    


        return userbadgeRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<UserBadge> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

UserBadge entity = entityOpt.get();

// --- Dissocier OneToMany ---

    


// --- Dissocier ManyToMany ---

    
        if (entity.getUsers() != null) {
        for (UserProfile item : new ArrayList<>(entity.getUsers())) {
        
            item.getBadges().remove(entity); // retire côté inverse
        
        }
        entity.getUsers().clear(); // puis vide côté courant
        }
    



// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}