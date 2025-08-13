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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

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

        if (userplaylistitem.getPlaylist() != null && userplaylistitem.getPlaylist().getId() != null) {
        UserPlaylist playlist = playlistRepository.findById(userplaylistitem.getPlaylist().getId())
                .orElseThrow(() -> new RuntimeException("UserPlaylist not found"));
        userplaylistitem.setPlaylist(playlist);
        }

        if (userplaylistitem.getMusicTrack() != null && userplaylistitem.getMusicTrack().getId() != null) {
        MusicTrack musicTrack = musicTrackRepository.findById(userplaylistitem.getMusicTrack().getId())
                .orElseThrow(() -> new RuntimeException("MusicTrack not found"));
        userplaylistitem.setMusicTrack(musicTrack);
        }

        if (userplaylistitem.getAddedBy() != null && userplaylistitem.getAddedBy().getId() != null) {
        UserProfile addedBy = addedByRepository.findById(userplaylistitem.getAddedBy().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        userplaylistitem.setAddedBy(addedBy);
        }

        return userplaylistitemRepository.save(userplaylistitem);
    }


    public UserPlaylistItem update(Long id, UserPlaylistItem userplaylistitemRequest) {
        UserPlaylistItem existing = userplaylistitemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserPlaylistItem not found"));

    // Copier les champs simples
        existing.setPosition(userplaylistitemRequest.getPosition());

// Relations ManyToOne : mise à jour conditionnelle

        if (userplaylistitemRequest.getPlaylist() != null && userplaylistitemRequest.getPlaylist().getId() != null) {
        UserPlaylist playlist = playlistRepository.findById(userplaylistitemRequest.getPlaylist().getId())
                .orElseThrow(() -> new RuntimeException("UserPlaylist not found"));
        existing.setPlaylist(playlist);
        }

        if (userplaylistitemRequest.getMusicTrack() != null && userplaylistitemRequest.getMusicTrack().getId() != null) {
        MusicTrack musicTrack = musicTrackRepository.findById(userplaylistitemRequest.getMusicTrack().getId())
                .orElseThrow(() -> new RuntimeException("MusicTrack not found"));
        existing.setMusicTrack(musicTrack);
        }

        if (userplaylistitemRequest.getAddedBy() != null && userplaylistitemRequest.getAddedBy().getId() != null) {
        UserProfile addedBy = addedByRepository.findById(userplaylistitemRequest.getAddedBy().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setAddedBy(addedBy);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    

    


        return userplaylistitemRepository.save(existing);
    }
}