package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserBadge;
import com.example.modules.entertainment_ecosystem.repository.UserBadgeRepository;

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
public class UserBadgeService extends BaseService<UserBadge> {

    protected final UserBadgeRepository userbadgeRepository;
    
    protected final UserProfileRepository usersRepository;
    

    public UserBadgeService(UserBadgeRepository repository, UserProfileRepository usersRepository)
    {
        super(repository);
        this.userbadgeRepository = repository;
        
        this.usersRepository = usersRepository;
        
    }

    @Transactional
    @Override
    public UserBadge save(UserBadge userbadge) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (userbadge.getUsers() != null &&
            !userbadge.getUsers().isEmpty()) {

            List<UserProfile> attachedUsers = new ArrayList<>();
            for (UserProfile item : userbadge.getUsers()) {
                if (item.getId() != null) {
                    UserProfile existingItem = usersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserProfile not found with id " + item.getId()));
                    attachedUsers.add(existingItem);
                } else {

                    UserProfile newItem = usersRepository.save(item);
                    attachedUsers.add(newItem);
                }
            }

            userbadge.setUsers(attachedUsers);

            // côté propriétaire (UserProfile → UserBadge)
            attachedUsers.forEach(it -> it.getBadges().add(userbadge));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return userbadgeRepository.save(userbadge);
}

    @Transactional
    @Override
    public UserBadge update(Long id, UserBadge userbadgeRequest) {
        UserBadge existing = userbadgeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserBadge not found"));

    // Copier les champs simples
        existing.setName(userbadgeRequest.getName());
        existing.setDescription(userbadgeRequest.getDescription());
        existing.setImageUrl(userbadgeRequest.getImageUrl());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return userbadgeRepository.save(existing);
}

    // Pagination simple
    public Page<UserBadge> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<UserBadge> search(Map<String, String> filters, Pageable pageable) {
        return super.search(UserBadge.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<UserBadge> saveAll(List<UserBadge> userbadgeList) {
        return super.saveAll(userbadgeList);
    }

}