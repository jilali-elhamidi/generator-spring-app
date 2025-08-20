package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MusicFormat;
import com.example.modules.entertainment_ecosystem.repository.MusicFormatRepository;
import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import com.example.modules.entertainment_ecosystem.repository.MusicTrackRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MusicFormatService extends BaseService<MusicFormat> {

    protected final MusicFormatRepository musicformatRepository;
    private final MusicTrackRepository musicTracksRepository;

    public MusicFormatService(MusicFormatRepository repository, MusicTrackRepository musicTracksRepository)
    {
        super(repository);
        this.musicformatRepository = repository;
        this.musicTracksRepository = musicTracksRepository;
    }

    @Override
    public MusicFormat save(MusicFormat musicformat) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (musicformat.getMusicTracks() != null &&
            !musicformat.getMusicTracks().isEmpty()) {

            List<MusicTrack> attachedMusicTracks = musicformat.getMusicTracks().stream()
            .map(item -> musicTracksRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("MusicTrack not found with id " + item.getId())))
            .toList();

            musicformat.setMusicTracks(attachedMusicTracks);

            // côté propriétaire (MusicTrack → MusicFormat)
            attachedMusicTracks.forEach(it -> it.getFormats().add(musicformat));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------

    return musicformatRepository.save(musicformat);
}


    public MusicFormat update(Long id, MusicFormat musicformatRequest) {
        MusicFormat existing = musicformatRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MusicFormat not found"));

    // Copier les champs simples
        existing.setName(musicformatRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
        if (musicformatRequest.getMusicTracks() != null) {
            existing.getMusicTracks().clear();

            List<MusicTrack> musicTracksList = musicformatRequest.getMusicTracks().stream()
                .map(item -> musicTracksRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("MusicTrack not found")))
                .collect(Collectors.toList());

            existing.getMusicTracks().addAll(musicTracksList);

            // Mettre à jour le côté inverse
            musicTracksList.forEach(it -> {
                if (!it.getFormats().contains(existing)) {
                    it.getFormats().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------

    return musicformatRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<MusicFormat> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        MusicFormat entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
        if (entity.getMusicTracks() != null) {
            for (MusicTrack item : new ArrayList<>(entity.getMusicTracks())) {
                
                item.getFormats().remove(entity); // retire côté inverse
                
            }
            entity.getMusicTracks().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}