package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MusicFormat;
import com.example.modules.entertainment_ecosystem.repository.MusicFormatRepository;

import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import com.example.modules.entertainment_ecosystem.repository.MusicTrackRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MusicFormatService extends BaseService<MusicFormat> {

    protected final MusicFormatRepository musicformatRepository;
    
    protected final MusicTrackRepository musicTracksRepository;
    

    public MusicFormatService(MusicFormatRepository repository, MusicTrackRepository musicTracksRepository)
    {
        super(repository);
        this.musicformatRepository = repository;
        
        this.musicTracksRepository = musicTracksRepository;
        
    }

    @Transactional
    @Override
    public MusicFormat save(MusicFormat musicformat) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (musicformat.getMusicTracks() != null &&
            !musicformat.getMusicTracks().isEmpty()) {

            List<MusicTrack> attachedMusicTracks = new ArrayList<>();
            for (MusicTrack item : musicformat.getMusicTracks()) {
                if (item.getId() != null) {
                    MusicTrack existingItem = musicTracksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MusicTrack not found with id " + item.getId()));
                    attachedMusicTracks.add(existingItem);
                } else {

                    MusicTrack newItem = musicTracksRepository.save(item);
                    attachedMusicTracks.add(newItem);
                }
            }

            musicformat.setMusicTracks(attachedMusicTracks);

            // côté propriétaire (MusicTrack → MusicFormat)
            attachedMusicTracks.forEach(it -> it.getFormats().add(musicformat));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return musicformatRepository.save(musicformat);
}

    @Transactional
    @Override
    public MusicFormat update(Long id, MusicFormat musicformatRequest) {
        MusicFormat existing = musicformatRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MusicFormat not found"));

    // Copier les champs simples
        existing.setName(musicformatRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<MusicFormat> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<MusicFormat> search(Map<String, String> filters, Pageable pageable) {
        return super.search(MusicFormat.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<MusicFormat> saveAll(List<MusicFormat> musicformatList) {
        return super.saveAll(musicformatList);
    }

}