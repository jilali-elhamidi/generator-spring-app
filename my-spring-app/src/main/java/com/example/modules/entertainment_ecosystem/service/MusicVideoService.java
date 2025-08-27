package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MusicVideo;
import com.example.modules.entertainment_ecosystem.repository.MusicVideoRepository;

import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import com.example.modules.entertainment_ecosystem.repository.MusicTrackRepository;

import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MusicVideoService extends BaseService<MusicVideo> {

    protected final MusicVideoRepository musicvideoRepository;
    
    protected final MusicTrackRepository musicTrackRepository;
    
    protected final ArtistRepository directorRepository;
    

    public MusicVideoService(MusicVideoRepository repository, MusicTrackRepository musicTrackRepository, ArtistRepository directorRepository)
    {
        super(repository);
        this.musicvideoRepository = repository;
        
        this.musicTrackRepository = musicTrackRepository;
        
        this.directorRepository = directorRepository;
        
    }

    @Transactional
    @Override
    public MusicVideo save(MusicVideo musicvideo) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (musicvideo.getMusicTrack() != null) {
            if (musicvideo.getMusicTrack().getId() != null) {
                MusicTrack existingMusicTrack = musicTrackRepository.findById(
                    musicvideo.getMusicTrack().getId()
                ).orElseThrow(() -> new RuntimeException("MusicTrack not found with id "
                    + musicvideo.getMusicTrack().getId()));
                musicvideo.setMusicTrack(existingMusicTrack);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                MusicTrack newMusicTrack = musicTrackRepository.save(musicvideo.getMusicTrack());
                musicvideo.setMusicTrack(newMusicTrack);
            }
        }
        
        if (musicvideo.getDirector() != null) {
            if (musicvideo.getDirector().getId() != null) {
                Artist existingDirector = directorRepository.findById(
                    musicvideo.getDirector().getId()
                ).orElseThrow(() -> new RuntimeException("Artist not found with id "
                    + musicvideo.getDirector().getId()));
                musicvideo.setDirector(existingDirector);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Artist newDirector = directorRepository.save(musicvideo.getDirector());
                musicvideo.setDirector(newDirector);
            }
        }
        
    // ---------- OneToOne ----------
    return musicvideoRepository.save(musicvideo);
}

    @Transactional
    @Override
    public MusicVideo update(Long id, MusicVideo musicvideoRequest) {
        MusicVideo existing = musicvideoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MusicVideo not found"));

    // Copier les champs simples
        existing.setTitle(musicvideoRequest.getTitle());
        existing.setReleaseDate(musicvideoRequest.getReleaseDate());
        existing.setDurationSeconds(musicvideoRequest.getDurationSeconds());

    // ---------- Relations ManyToOne ----------
        if (musicvideoRequest.getMusicTrack() != null &&
            musicvideoRequest.getMusicTrack().getId() != null) {

            MusicTrack existingMusicTrack = musicTrackRepository.findById(
                musicvideoRequest.getMusicTrack().getId()
            ).orElseThrow(() -> new RuntimeException("MusicTrack not found"));

            existing.setMusicTrack(existingMusicTrack);
        } else {
            existing.setMusicTrack(null);
        }
        
        if (musicvideoRequest.getDirector() != null &&
            musicvideoRequest.getDirector().getId() != null) {

            Artist existingDirector = directorRepository.findById(
                musicvideoRequest.getDirector().getId()
            ).orElseThrow(() -> new RuntimeException("Artist not found"));

            existing.setDirector(existingDirector);
        } else {
            existing.setDirector(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return musicvideoRepository.save(existing);
}

    // Pagination simple
    public Page<MusicVideo> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<MusicVideo> search(Map<String, String> filters, Pageable pageable) {
        return super.search(MusicVideo.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<MusicVideo> saveAll(List<MusicVideo> musicvideoList) {
        return super.saveAll(musicvideoList);
    }

}