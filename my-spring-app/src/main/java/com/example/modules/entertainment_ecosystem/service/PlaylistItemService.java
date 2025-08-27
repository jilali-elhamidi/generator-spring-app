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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class PlaylistItemService extends BaseService<PlaylistItem> {

    protected final PlaylistItemRepository playlistitemRepository;
    
    protected final PlaylistRepository playlistRepository;
    
    protected final MusicTrackRepository trackRepository;
    
    protected final UserProfileRepository addedByRepository;
    

    public PlaylistItemService(PlaylistItemRepository repository, PlaylistRepository playlistRepository, MusicTrackRepository trackRepository, UserProfileRepository addedByRepository)
    {
        super(repository);
        this.playlistitemRepository = repository;
        
        this.playlistRepository = playlistRepository;
        
        this.trackRepository = trackRepository;
        
        this.addedByRepository = addedByRepository;
        
    }

    @Transactional
    @Override
    public PlaylistItem save(PlaylistItem playlistitem) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (playlistitem.getPlaylist() != null) {
            if (playlistitem.getPlaylist().getId() != null) {
                Playlist existingPlaylist = playlistRepository.findById(
                    playlistitem.getPlaylist().getId()
                ).orElseThrow(() -> new RuntimeException("Playlist not found with id "
                    + playlistitem.getPlaylist().getId()));
                playlistitem.setPlaylist(existingPlaylist);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Playlist newPlaylist = playlistRepository.save(playlistitem.getPlaylist());
                playlistitem.setPlaylist(newPlaylist);
            }
        }
        
        if (playlistitem.getTrack() != null) {
            if (playlistitem.getTrack().getId() != null) {
                MusicTrack existingTrack = trackRepository.findById(
                    playlistitem.getTrack().getId()
                ).orElseThrow(() -> new RuntimeException("MusicTrack not found with id "
                    + playlistitem.getTrack().getId()));
                playlistitem.setTrack(existingTrack);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                MusicTrack newTrack = trackRepository.save(playlistitem.getTrack());
                playlistitem.setTrack(newTrack);
            }
        }
        
        if (playlistitem.getAddedBy() != null) {
            if (playlistitem.getAddedBy().getId() != null) {
                UserProfile existingAddedBy = addedByRepository.findById(
                    playlistitem.getAddedBy().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + playlistitem.getAddedBy().getId()));
                playlistitem.setAddedBy(existingAddedBy);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newAddedBy = addedByRepository.save(playlistitem.getAddedBy());
                playlistitem.setAddedBy(newAddedBy);
            }
        }
        
    // ---------- OneToOne ----------
    return playlistitemRepository.save(playlistitem);
}

    @Transactional
    @Override
    public PlaylistItem update(Long id, PlaylistItem playlistitemRequest) {
        PlaylistItem existing = playlistitemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("PlaylistItem not found"));

    // Copier les champs simples
        existing.setPosition(playlistitemRequest.getPosition());

    // ---------- Relations ManyToOne ----------
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
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return playlistitemRepository.save(existing);
}

    // Pagination simple
    public Page<PlaylistItem> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<PlaylistItem> search(Map<String, String> filters, Pageable pageable) {
        return super.search(PlaylistItem.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<PlaylistItem> saveAll(List<PlaylistItem> playlistitemList) {
        return super.saveAll(playlistitemList);
    }

}