package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Playlist;
import com.example.modules.entertainment_ecosystem.repository.PlaylistRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.PlaylistItem;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class PlaylistService extends BaseService<Playlist> {

    protected final PlaylistRepository playlistRepository;
    private final UserProfileRepository ownerRepository;

    public PlaylistService(PlaylistRepository repository,UserProfileRepository ownerRepository)
    {
        super(repository);
        this.playlistRepository = repository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Playlist save(Playlist playlist) {

        if (playlist.getOwner() != null && playlist.getOwner().getId() != null) {
        UserProfile owner = ownerRepository.findById(playlist.getOwner().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        playlist.setOwner(owner);
        }

        if (playlist.getItems() != null) {
            for (PlaylistItem item : playlist.getItems()) {
            item.setPlaylist(playlist);
            }
        }

        return playlistRepository.save(playlist);
    }


    public Playlist update(Long id, Playlist playlistRequest) {
        Playlist existing = playlistRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Playlist not found"));

    // Copier les champs simples
        existing.setName(playlistRequest.getName());
        existing.setCreationDate(playlistRequest.getCreationDate());
        existing.setIsPublic(playlistRequest.getIsPublic());

// Relations ManyToOne : mise à jour conditionnelle

        if (playlistRequest.getOwner() != null && playlistRequest.getOwner().getId() != null) {
        UserProfile owner = ownerRepository.findById(playlistRequest.getOwner().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setOwner(owner);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getItems().clear();
        if (playlistRequest.getItems() != null) {
            for (var item : playlistRequest.getItems()) {
            item.setPlaylist(existing);
            existing.getItems().add(item);
            }
        }

    

    


        return playlistRepository.save(existing);
    }
}