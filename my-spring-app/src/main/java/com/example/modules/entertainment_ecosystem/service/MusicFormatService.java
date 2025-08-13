package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MusicFormat;
import com.example.modules.entertainment_ecosystem.repository.MusicFormatRepository;
import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import com.example.modules.entertainment_ecosystem.repository.MusicTrackRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MusicFormatService extends BaseService<MusicFormat> {

    protected final MusicFormatRepository musicformatRepository;
    private final MusicTrackRepository musicTracksRepository;

    public MusicFormatService(MusicFormatRepository repository,MusicTrackRepository musicTracksRepository)
    {
        super(repository);
        this.musicformatRepository = repository;
        this.musicTracksRepository = musicTracksRepository;
    }

    @Override
    public MusicFormat save(MusicFormat musicformat) {

        return musicformatRepository.save(musicformat);
    }


    public MusicFormat update(Long id, MusicFormat musicformatRequest) {
        MusicFormat existing = musicformatRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MusicFormat not found"));

    // Copier les champs simples
        existing.setName(musicformatRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

        if (musicformatRequest.getMusicTracks() != null) {
            existing.getMusicTracks().clear();
            List<MusicTrack> musicTracksList = musicformatRequest.getMusicTracks().stream()
                .map(item -> musicTracksRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("MusicTrack not found")))
                .collect(Collectors.toList());
        existing.getMusicTracks().addAll(musicTracksList);
        }

// Relations OneToMany : synchronisation sécurisée

    


        return musicformatRepository.save(existing);
    }
}