package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.PlaylistItem;
import com.example.modules.entertainment_ecosystem.repository.PlaylistItemRepository;
import com.example.modules.entertainment_ecosystem.model.Playlist;
import com.example.modules.entertainment_ecosystem.repository.PlaylistRepository;
import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import com.example.modules.entertainment_ecosystem.repository.MusicTrackRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class PlaylistItemService extends BaseService<PlaylistItem> {

    protected final PlaylistItemRepository playlistitemRepository;
    private final PlaylistRepository playlistRepository;
    private final MusicTrackRepository trackRepository;
    private final UserProfileRepository addedByRepository;

    public PlaylistItemService(PlaylistItemRepository repository,PlaylistRepository playlistRepository,MusicTrackRepository trackRepository,UserProfileRepository addedByRepository)
    {
        super(repository);
        this.playlistitemRepository = repository;
        this.playlistRepository = playlistRepository;
        this.trackRepository = trackRepository;
        this.addedByRepository = addedByRepository;
    }

    @Override
    public PlaylistItem save(PlaylistItem playlistitem) {


    

    

    


    

    

    

    if (playlistitem.getPlaylist() != null
        && playlistitem.getPlaylist().getId() != null) {
        Playlist existingPlaylist = playlistRepository.findById(
        playlistitem.getPlaylist().getId()
        ).orElseThrow(() -> new RuntimeException("Playlist not found"));
        playlistitem.setPlaylist(existingPlaylist);
        }
    
    if (playlistitem.getTrack() != null
        && playlistitem.getTrack().getId() != null) {
        MusicTrack existingTrack = trackRepository.findById(
        playlistitem.getTrack().getId()
        ).orElseThrow(() -> new RuntimeException("MusicTrack not found"));
        playlistitem.setTrack(existingTrack);
        }
    
    if (playlistitem.getAddedBy() != null
        && playlistitem.getAddedBy().getId() != null) {
        UserProfile existingAddedBy = addedByRepository.findById(
        playlistitem.getAddedBy().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        playlistitem.setAddedBy(existingAddedBy);
        }
    

        return playlistitemRepository.save(playlistitem);
    }


    public PlaylistItem update(Long id, PlaylistItem playlistitemRequest) {
        PlaylistItem existing = playlistitemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("PlaylistItem not found"));

    // Copier les champs simples
        existing.setPosition(playlistitemRequest.getPosition());

// Relations ManyToOne : mise à jour conditionnelle
        if (playlistitemRequest.getPlaylist() != null &&
        playlistitemRequest.getPlaylist().getId() != null) {

        Playlist existingPlaylist = playlistRepository.findById(
        playlistitemRequest.getPlaylist().getId()
        ).orElseThrow(() -> new RuntimeException("Playlist not found"));

        existing.setPlaylist(existingPlaylist);
        } else {
        existing.setPlaylist(null);
        }
        if (playlistitemRequest.getTrack() != null &&
        playlistitemRequest.getTrack().getId() != null) {

        MusicTrack existingTrack = trackRepository.findById(
        playlistitemRequest.getTrack().getId()
        ).orElseThrow(() -> new RuntimeException("MusicTrack not found"));

        existing.setTrack(existingTrack);
        } else {
        existing.setTrack(null);
        }
        if (playlistitemRequest.getAddedBy() != null &&
        playlistitemRequest.getAddedBy().getId() != null) {

        UserProfile existingAddedBy = addedByRepository.findById(
        playlistitemRequest.getAddedBy().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

        existing.setAddedBy(existingAddedBy);
        } else {
        existing.setAddedBy(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    

    


        return playlistitemRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<PlaylistItem> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

PlaylistItem entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    

    


// --- Dissocier ManyToMany ---

    

    

    



// --- Dissocier OneToOne ---

    

    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getPlaylist() != null) {
        entity.setPlaylist(null);
        }
    

    
        if (entity.getTrack() != null) {
        entity.setTrack(null);
        }
    

    
        if (entity.getAddedBy() != null) {
        entity.setAddedBy(null);
        }
    


repository.delete(entity);
return true;
}
}