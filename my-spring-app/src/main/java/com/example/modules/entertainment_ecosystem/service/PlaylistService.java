package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Playlist;
import com.example.modules.entertainment_ecosystem.repository.PlaylistRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.PlaylistItem;
import com.example.modules.entertainment_ecosystem.repository.PlaylistItemRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class PlaylistService extends BaseService<Playlist> {

    protected final PlaylistRepository playlistRepository;
    private final UserProfileRepository ownerRepository;
    private final PlaylistItemRepository itemsRepository;

    public PlaylistService(PlaylistRepository repository, UserProfileRepository ownerRepository, PlaylistItemRepository itemsRepository)
    {
        super(repository);
        this.playlistRepository = repository;
        this.ownerRepository = ownerRepository;
        this.itemsRepository = itemsRepository;
    }

    @Override
    public Playlist save(Playlist playlist) {
    // ---------- OneToMany ----------
        if (playlist.getItems() != null) {
            List<PlaylistItem> managedItems = new ArrayList<>();
            for (PlaylistItem item : playlist.getItems()) {
                if (item.getId() != null) {
                    PlaylistItem existingItem = itemsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("PlaylistItem not found"));

                     existingItem.setPlaylist(playlist);
                     managedItems.add(existingItem);
                } else {
                    item.setPlaylist(playlist);
                    managedItems.add(item);
                }
            }
            playlist.setItems(managedItems);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (playlist.getOwner() != null) {
            if (playlist.getOwner().getId() != null) {
                UserProfile existingOwner = ownerRepository.findById(
                    playlist.getOwner().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + playlist.getOwner().getId()));
                playlist.setOwner(existingOwner);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newOwner = ownerRepository.save(playlist.getOwner());
                playlist.setOwner(newOwner);
            }
        }
        
    // ---------- OneToOne ----------
    return playlistRepository.save(playlist);
}


    public Playlist update(Long id, Playlist playlistRequest) {
        Playlist existing = playlistRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Playlist not found"));

    // Copier les champs simples
        existing.setName(playlistRequest.getName());
        existing.setCreationDate(playlistRequest.getCreationDate());
        existing.setIsPublic(playlistRequest.getIsPublic());

    // ---------- Relations ManyToOne ----------
        if (playlistRequest.getOwner() != null &&
            playlistRequest.getOwner().getId() != null) {

            UserProfile existingOwner = ownerRepository.findById(
                playlistRequest.getOwner().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setOwner(existingOwner);
        } else {
            existing.setOwner(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getItems().clear();

        if (playlistRequest.getItems() != null) {
            for (var item : playlistRequest.getItems()) {
                PlaylistItem existingItem;
                if (item.getId() != null) {
                    existingItem = itemsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("PlaylistItem not found"));
                } else {
                existingItem = item;
                }

                existingItem.setPlaylist(existing);
                existing.getItems().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return playlistRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Playlist> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Playlist entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getItems() != null) {
            for (var child : entity.getItems()) {
                // retirer la référence inverse
                child.setPlaylist(null);
            }
            entity.getItems().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getOwner() != null) {
            entity.setOwner(null);
        }
        
        repository.delete(entity);
        return true;
    }
}