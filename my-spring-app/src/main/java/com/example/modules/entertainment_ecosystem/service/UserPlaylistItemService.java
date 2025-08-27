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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class UserPlaylistItemService extends BaseService<UserPlaylistItem> {

    protected final UserPlaylistItemRepository userplaylistitemRepository;
    
    protected final UserPlaylistRepository playlistRepository;
    
    protected final MusicTrackRepository musicTrackRepository;
    
    protected final UserProfileRepository addedByRepository;
    

    public UserPlaylistItemService(UserPlaylistItemRepository repository, UserPlaylistRepository playlistRepository, MusicTrackRepository musicTrackRepository, UserProfileRepository addedByRepository)
    {
        super(repository);
        this.userplaylistitemRepository = repository;
        
        this.playlistRepository = playlistRepository;
        
        this.musicTrackRepository = musicTrackRepository;
        
        this.addedByRepository = addedByRepository;
        
    }

    @Transactional
    @Override
    public UserPlaylistItem save(UserPlaylistItem userplaylistitem) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (userplaylistitem.getPlaylist() != null) {
            if (userplaylistitem.getPlaylist().getId() != null) {
                UserPlaylist existingPlaylist = playlistRepository.findById(
                    userplaylistitem.getPlaylist().getId()
                ).orElseThrow(() -> new RuntimeException("UserPlaylist not found with id "
                    + userplaylistitem.getPlaylist().getId()));
                userplaylistitem.setPlaylist(existingPlaylist);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserPlaylist newPlaylist = playlistRepository.save(userplaylistitem.getPlaylist());
                userplaylistitem.setPlaylist(newPlaylist);
            }
        }
        
        if (userplaylistitem.getMusicTrack() != null) {
            if (userplaylistitem.getMusicTrack().getId() != null) {
                MusicTrack existingMusicTrack = musicTrackRepository.findById(
                    userplaylistitem.getMusicTrack().getId()
                ).orElseThrow(() -> new RuntimeException("MusicTrack not found with id "
                    + userplaylistitem.getMusicTrack().getId()));
                userplaylistitem.setMusicTrack(existingMusicTrack);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                MusicTrack newMusicTrack = musicTrackRepository.save(userplaylistitem.getMusicTrack());
                userplaylistitem.setMusicTrack(newMusicTrack);
            }
        }
        
        if (userplaylistitem.getAddedBy() != null) {
            if (userplaylistitem.getAddedBy().getId() != null) {
                UserProfile existingAddedBy = addedByRepository.findById(
                    userplaylistitem.getAddedBy().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + userplaylistitem.getAddedBy().getId()));
                userplaylistitem.setAddedBy(existingAddedBy);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newAddedBy = addedByRepository.save(userplaylistitem.getAddedBy());
                userplaylistitem.setAddedBy(newAddedBy);
            }
        }
        
    // ---------- OneToOne ----------
    return userplaylistitemRepository.save(userplaylistitem);
}

    @Transactional
    @Override
    public UserPlaylistItem update(Long id, UserPlaylistItem userplaylistitemRequest) {
        UserPlaylistItem existing = userplaylistitemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserPlaylistItem not found"));

    // Copier les champs simples
        existing.setPosition(userplaylistitemRequest.getPosition());

    // ---------- Relations ManyToOne ----------
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
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return userplaylistitemRepository.save(existing);
}

    // Pagination simple
    public Page<UserPlaylistItem> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<UserPlaylistItem> search(Map<String, String> filters, Pageable pageable) {
        return super.search(UserPlaylistItem.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<UserPlaylistItem> saveAll(List<UserPlaylistItem> userplaylistitemList) {
        return super.saveAll(userplaylistitemList);
    }

}