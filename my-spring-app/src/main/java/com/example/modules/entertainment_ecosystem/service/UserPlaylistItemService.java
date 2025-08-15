package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserPlaylistItem;
import com.example.modules.entertainment_ecosystem.repository.UserPlaylistItemRepository;
import com.example.modules.entertainment_ecosystem.model.UserPlaylist;
import com.example.modules.entertainment_ecosystem.repository.UserPlaylistRepository;
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
public class UserPlaylistItemService extends BaseService<UserPlaylistItem> {

    protected final UserPlaylistItemRepository userplaylistitemRepository;
    private final UserPlaylistRepository playlistRepository;
    private final MusicTrackRepository musicTrackRepository;
    private final UserProfileRepository addedByRepository;

    public UserPlaylistItemService(UserPlaylistItemRepository repository,UserPlaylistRepository playlistRepository,MusicTrackRepository musicTrackRepository,UserProfileRepository addedByRepository)
    {
        super(repository);
        this.userplaylistitemRepository = repository;
        this.playlistRepository = playlistRepository;
        this.musicTrackRepository = musicTrackRepository;
        this.addedByRepository = addedByRepository;
    }

    @Override
    public UserPlaylistItem save(UserPlaylistItem userplaylistitem) {


    

    

    

    if (userplaylistitem.getPlaylist() != null
        && userplaylistitem.getPlaylist().getId() != null) {
        UserPlaylist existingPlaylist = playlistRepository.findById(
        userplaylistitem.getPlaylist().getId()
        ).orElseThrow(() -> new RuntimeException("UserPlaylist not found"));
        userplaylistitem.setPlaylist(existingPlaylist);
        }
    
    if (userplaylistitem.getMusicTrack() != null
        && userplaylistitem.getMusicTrack().getId() != null) {
        MusicTrack existingMusicTrack = musicTrackRepository.findById(
        userplaylistitem.getMusicTrack().getId()
        ).orElseThrow(() -> new RuntimeException("MusicTrack not found"));
        userplaylistitem.setMusicTrack(existingMusicTrack);
        }
    
    if (userplaylistitem.getAddedBy() != null
        && userplaylistitem.getAddedBy().getId() != null) {
        UserProfile existingAddedBy = addedByRepository.findById(
        userplaylistitem.getAddedBy().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        userplaylistitem.setAddedBy(existingAddedBy);
        }
    

        return userplaylistitemRepository.save(userplaylistitem);
    }


    public UserPlaylistItem update(Long id, UserPlaylistItem userplaylistitemRequest) {
        UserPlaylistItem existing = userplaylistitemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserPlaylistItem not found"));

    // Copier les champs simples
        existing.setPosition(userplaylistitemRequest.getPosition());

// Relations ManyToOne : mise à jour conditionnelle
        if (userplaylistitemRequest.getPlaylist() != null &&
        userplaylistitemRequest.getPlaylist().getId() != null) {

        UserPlaylist existingPlaylist = playlistRepository.findById(
        userplaylistitemRequest.getPlaylist().getId()
        ).orElseThrow(() -> new RuntimeException("UserPlaylist not found"));

        existing.setPlaylist(existingPlaylist);
        } else {
        existing.setPlaylist(null);
        }
        if (userplaylistitemRequest.getMusicTrack() != null &&
        userplaylistitemRequest.getMusicTrack().getId() != null) {

        MusicTrack existingMusicTrack = musicTrackRepository.findById(
        userplaylistitemRequest.getMusicTrack().getId()
        ).orElseThrow(() -> new RuntimeException("MusicTrack not found"));

        existing.setMusicTrack(existingMusicTrack);
        } else {
        existing.setMusicTrack(null);
        }
        if (userplaylistitemRequest.getAddedBy() != null &&
        userplaylistitemRequest.getAddedBy().getId() != null) {

        UserProfile existingAddedBy = addedByRepository.findById(
        userplaylistitemRequest.getAddedBy().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

        existing.setAddedBy(existingAddedBy);
        } else {
        existing.setAddedBy(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    

    


        return userplaylistitemRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<UserPlaylistItem> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

UserPlaylistItem entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    

    


// --- Dissocier ManyToMany ---

    

    

    


// --- Dissocier OneToOne ---

    

    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getPlaylist() != null) {
        entity.setPlaylist(null);
        }
    

    
        if (entity.getMusicTrack() != null) {
        entity.setMusicTrack(null);
        }
    

    
        if (entity.getAddedBy() != null) {
        entity.setAddedBy(null);
        }
    


repository.delete(entity);
return true;
}
}