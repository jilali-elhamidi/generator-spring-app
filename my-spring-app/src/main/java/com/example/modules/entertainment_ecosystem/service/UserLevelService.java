package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserLevel;
import com.example.modules.entertainment_ecosystem.repository.UserLevelRepository;

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
public class UserLevelService extends BaseService<UserLevel> {

    protected final UserLevelRepository userlevelRepository;
    
    protected final UserProfileRepository usersRepository;
    

    public UserLevelService(UserLevelRepository repository, UserProfileRepository usersRepository)
    {
        super(repository);
        this.userlevelRepository = repository;
        
        this.usersRepository = usersRepository;
        
    }

    @Transactional
    @Override
    public UserLevel save(UserLevel userlevel) {
    // ---------- OneToMany ----------
        if (userlevel.getUsers() != null) {
            List<UserProfile> managedUsers = new ArrayList<>();
            for (UserProfile item : userlevel.getUsers()) {
                if (item.getId() != null) {
                    UserProfile existingItem = usersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserProfile not found"));

                     existingItem.setUserLevel(userlevel);
                     managedUsers.add(existingItem);
                } else {
                    item.setUserLevel(userlevel);
                    managedUsers.add(item);
                }
            }
            userlevel.setUsers(managedUsers);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return userlevelRepository.save(userlevel);
}

    @Transactional
    @Override
    public UserLevel update(Long id, UserLevel userlevelRequest) {
        UserLevel existing = userlevelRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserLevel not found"));

    // Copier les champs simples
        existing.setLevelNumber(userlevelRequest.getLevelNumber());
        existing.setName(userlevelRequest.getName());
        existing.setPointsRequired(userlevelRequest.getPointsRequired());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getUsers().clear();

        if (userlevelRequest.getUsers() != null) {
            for (var item : userlevelRequest.getUsers()) {
                UserProfile existingItem;
                if (item.getId() != null) {
                    existingItem = usersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserProfile not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUserLevel(existing);
                existing.getUsers().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return userlevelRepository.save(existing);
}

    // Pagination simple
    public Page<UserLevel> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<UserLevel> search(Map<String, String> filters, Pageable pageable) {
        return super.search(UserLevel.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<UserLevel> saveAll(List<UserLevel> userlevelList) {
        return super.saveAll(userlevelList);
    }

}