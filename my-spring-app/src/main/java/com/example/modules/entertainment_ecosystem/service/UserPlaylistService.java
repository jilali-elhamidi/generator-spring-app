package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserPlaylist;
import com.example.modules.entertainment_ecosystem.repository.UserPlaylistRepository;

import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import com.example.modules.entertainment_ecosystem.model.UserPlaylistItem;
import com.example.modules.entertainment_ecosystem.repository.UserPlaylistItemRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class UserPlaylistService extends BaseService<UserPlaylist> {

    protected final UserPlaylistRepository userplaylistRepository;
    
    protected final UserProfileRepository userRepository;
    
    protected final UserPlaylistItemRepository itemsRepository;
    

    public UserPlaylistService(UserPlaylistRepository repository, UserProfileRepository userRepository, UserPlaylistItemRepository itemsRepository)
    {
        super(repository);
        this.userplaylistRepository = repository;
        
        this.userRepository = userRepository;
        
        this.itemsRepository = itemsRepository;
        
    }

    @Transactional
    @Override
    public UserPlaylist save(UserPlaylist userplaylist) {
    // ---------- OneToMany ----------
        if (userplaylist.getItems() != null) {
            List<UserPlaylistItem> managedItems = new ArrayList<>();
            for (UserPlaylistItem item : userplaylist.getItems()) {
                if (item.getId() != null) {
                    UserPlaylistItem existingItem = itemsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserPlaylistItem not found"));

                     existingItem.setPlaylist(userplaylist);
                     managedItems.add(existingItem);
                } else {
                    item.setPlaylist(userplaylist);
                    managedItems.add(item);
                }
            }
            userplaylist.setItems(managedItems);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (userplaylist.getUser() != null) {
            if (userplaylist.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    userplaylist.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + userplaylist.getUser().getId()));
                userplaylist.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(userplaylist.getUser());
                userplaylist.setUser(newUser);
            }
        }
        
    // ---------- OneToOne ----------
    return userplaylistRepository.save(userplaylist);
}

    @Transactional
    @Override
    public UserPlaylist update(Long id, UserPlaylist userplaylistRequest) {
        UserPlaylist existing = userplaylistRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserPlaylist not found"));

    // Copier les champs simples
        existing.setName(userplaylistRequest.getName());
        existing.setIsPublic(userplaylistRequest.getIsPublic());

    // ---------- Relations ManyToOne ----------
        if (userplaylistRequest.getUser() != null &&
            userplaylistRequest.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                userplaylistRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getItems().clear();

        if (userplaylistRequest.getItems() != null) {
            for (var item : userplaylistRequest.getItems()) {
                UserPlaylistItem existingItem;
                if (item.getId() != null) {
                    existingItem = itemsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserPlaylistItem not found"));
                } else {
                existingItem = item;
                }

                existingItem.setPlaylist(existing);
                existing.getItems().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return userplaylistRepository.save(existing);
}

    // Pagination simple
    public Page<UserPlaylist> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<UserPlaylist> search(Map<String, String> filters, Pageable pageable) {
        return super.search(UserPlaylist.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<UserPlaylist> saveAll(List<UserPlaylist> userplaylistList) {
        return super.saveAll(userplaylistList);
    }

}