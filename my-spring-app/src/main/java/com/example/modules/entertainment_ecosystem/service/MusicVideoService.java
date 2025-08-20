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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MusicVideoService extends BaseService<MusicVideo> {

    protected final MusicVideoRepository musicvideoRepository;
    private final MusicTrackRepository musicTrackRepository;
    private final ArtistRepository directorRepository;

    public MusicVideoService(MusicVideoRepository repository,MusicTrackRepository musicTrackRepository,ArtistRepository directorRepository)
    {
        super(repository);
        this.musicvideoRepository = repository;
        this.musicTrackRepository = musicTrackRepository;
        this.directorRepository = directorRepository;
    }

    @Override
    public MusicVideo save(MusicVideo musicvideo) {


    

    


    

    

    if (musicvideo.getMusicTrack() != null
        && musicvideo.getMusicTrack().getId() != null) {
        MusicTrack existingMusicTrack = musicTrackRepository.findById(
        musicvideo.getMusicTrack().getId()
        ).orElseThrow(() -> new RuntimeException("MusicTrack not found"));
        musicvideo.setMusicTrack(existingMusicTrack);
        }
    
    if (musicvideo.getDirector() != null
        && musicvideo.getDirector().getId() != null) {
        Artist existingDirector = directorRepository.findById(
        musicvideo.getDirector().getId()
        ).orElseThrow(() -> new RuntimeException("Artist not found"));
        musicvideo.setDirector(existingDirector);
        }
    

        return musicvideoRepository.save(musicvideo);
    }


    public MusicVideo update(Long id, MusicVideo musicvideoRequest) {
        MusicVideo existing = musicvideoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MusicVideo not found"));

    // Copier les champs simples
        existing.setTitle(musicvideoRequest.getTitle());
        existing.setReleaseDate(musicvideoRequest.getReleaseDate());
        existing.setDurationSeconds(musicvideoRequest.getDurationSeconds());

// Relations ManyToOne : mise à jour conditionnelle
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

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    


        return musicvideoRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<MusicVideo> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

MusicVideo entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    


// --- Dissocier ManyToMany ---

    

    



// --- Dissocier OneToOne ---

    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getMusicTrack() != null) {
        entity.setMusicTrack(null);
        }
    

    
        if (entity.getDirector() != null) {
        entity.setDirector(null);
        }
    


repository.delete(entity);
return true;
}
}