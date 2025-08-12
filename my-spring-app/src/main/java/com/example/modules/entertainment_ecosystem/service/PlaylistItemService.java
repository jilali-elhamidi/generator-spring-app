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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

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

        if (playlistitem.getPlaylist() != null && playlistitem.getPlaylist().getId() != null) {
        Playlist playlist = playlistRepository.findById(playlistitem.getPlaylist().getId())
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        playlistitem.setPlaylist(playlist);
        }

        if (playlistitem.getTrack() != null && playlistitem.getTrack().getId() != null) {
        MusicTrack track = trackRepository.findById(playlistitem.getTrack().getId())
                .orElseThrow(() -> new RuntimeException("MusicTrack not found"));
        playlistitem.setTrack(track);
        }

        if (playlistitem.getAddedBy() != null && playlistitem.getAddedBy().getId() != null) {
        UserProfile addedBy = addedByRepository.findById(playlistitem.getAddedBy().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        playlistitem.setAddedBy(addedBy);
        }

        return playlistitemRepository.save(playlistitem);
    }


    public PlaylistItem update(Long id, PlaylistItem playlistitemRequest) {
        PlaylistItem existing = playlistitemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("PlaylistItem not found"));

    // Copier les champs simples
        existing.setPosition(playlistitemRequest.getPosition());

// Relations ManyToOne : mise à jour conditionnelle

        if (playlistitemRequest.getPlaylist() != null && playlistitemRequest.getPlaylist().getId() != null) {
        Playlist playlist = playlistRepository.findById(playlistitemRequest.getPlaylist().getId())
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        existing.setPlaylist(playlist);
        }

        if (playlistitemRequest.getTrack() != null && playlistitemRequest.getTrack().getId() != null) {
        MusicTrack track = trackRepository.findById(playlistitemRequest.getTrack().getId())
                .orElseThrow(() -> new RuntimeException("MusicTrack not found"));
        existing.setTrack(track);
        }

        if (playlistitemRequest.getAddedBy() != null && playlistitemRequest.getAddedBy().getId() != null) {
        UserProfile addedBy = addedByRepository.findById(playlistitemRequest.getAddedBy().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setAddedBy(addedBy);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        return playlistitemRepository.save(existing);
    }
}