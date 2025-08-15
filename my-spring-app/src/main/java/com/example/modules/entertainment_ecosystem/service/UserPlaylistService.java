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
        // Vider la collection existante
        existing.getItems().clear();

        if (userplaylistRequest.getItems() != null) {
        for (var item : userplaylistRequest.getItems()) {
        UserPlaylistItem existingItem;
        if (item.getId() != null) {
        existingItem = itemsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserPlaylistItem not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setPlaylist(existing);

        // Ajouter directement dans la collection existante
        existing.getItems().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    


        return userplaylistRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<UserPlaylist> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

UserPlaylist entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    
        if (entity.getItems() != null) {
        for (var child : entity.getItems()) {
        
            child.setPlaylist(null); // retirer la référence inverse
        
        }
        entity.getItems().clear();
        }
    


// --- Dissocier ManyToMany ---

    

    


// --- Dissocier OneToOne ---

    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getUser() != null) {
        entity.setUser(null);
        }
    

    


repository.delete(entity);
return true;
}
}