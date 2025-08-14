package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserPlaylist;
import com.example.modules.entertainment_ecosystem.repository.UserPlaylistRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.UserPlaylistItem;
import com.example.modules.entertainment_ecosystem.repository.UserPlaylistItemRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class UserPlaylistService extends BaseService<UserPlaylist> {

    protected final UserPlaylistRepository userplaylistRepository;
    private final UserProfileRepository userRepository;
    private final UserPlaylistItemRepository itemsRepository;

    public UserPlaylistService(UserPlaylistRepository repository,UserProfileRepository userRepository,UserPlaylistItemRepository itemsRepository)
    {
        super(repository);
        this.userplaylistRepository = repository;
        this.userRepository = userRepository;
        this.itemsRepository = itemsRepository;
    }

    @Override
    public UserPlaylist save(UserPlaylist userplaylist) {


    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userplaylist.getItems() != null) {
            List<UserPlaylistItem> managedItems = new ArrayList<>();
            for (UserPlaylistItem item : userplaylist.getItems()) {
            if (item.getId() != null) {
            UserPlaylistItem existingItem = itemsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("UserPlaylistItem not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setPlaylist(userplaylist);
            managedItems.add(existingItem);
            } else {
            item.setPlaylist(userplaylist);
            managedItems.add(item);
            }
            }
            userplaylist.setItems(managedItems);
            }
        
    

    if (userplaylist.getUser() != null
        && userplaylist.getUser().getId() != null) {
        UserProfile existingUser = userRepository.findById(
        userplaylist.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        userplaylist.setUser(existingUser);
        }
    
    

        return userplaylistRepository.save(userplaylist);
    }


    public UserPlaylist update(Long id, UserPlaylist userplaylistRequest) {
        UserPlaylist existing = userplaylistRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserPlaylist not found"));

    // Copier les champs simples
        existing.setName(userplaylistRequest.getName());
        existing.setIsPublic(userplaylistRequest.getIsPublic());

// Relations ManyToOne : mise à jour conditionnelle
        if (userplaylistRequest.getUser() != null &&
        userplaylistRequest.getUser().getId() != null) {

        UserProfile existingUser = userRepository.findById(
        userplaylistRequest.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

        existing.setUser(existingUser);
        } else {
        existing.setUser(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        existing.getItems().clear();

        if (userplaylistRequest.getItems() != null) {
        List<UserPlaylistItem> managedItems = new ArrayList<>();

        for (var item : userplaylistRequest.getItems()) {
        if (item.getId() != null) {
        UserPlaylistItem existingItem = itemsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserPlaylistItem not found"));
        existingItem.setPlaylist(existing);
        managedItems.add(existingItem);
        } else {
        item.setPlaylist(existing);
        managedItems.add(item);
        }
        }
        existing.setItems(managedItems);
        }

    

    


        return userplaylistRepository.save(existing);
    }


}