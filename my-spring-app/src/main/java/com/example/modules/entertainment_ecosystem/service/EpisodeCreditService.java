package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.EpisodeCredit;
import com.example.modules.entertainment_ecosystem.repository.EpisodeCreditRepository;
import com.example.modules.entertainment_ecosystem.model.Episode;
import com.example.modules.entertainment_ecosystem.repository.EpisodeRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class EpisodeCreditService extends BaseService<EpisodeCredit> {

    protected final EpisodeCreditRepository episodecreditRepository;
    private final EpisodeRepository episodeRepository;
    private final ArtistRepository artistRepository;

    public EpisodeCreditService(EpisodeCreditRepository repository,EpisodeRepository episodeRepository,ArtistRepository artistRepository)
    {
        super(repository);
        this.episodecreditRepository = repository;
        this.episodeRepository = episodeRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public EpisodeCredit save(EpisodeCredit episodecredit) {


    

    


    

    

    if (episodecredit.getEpisode() != null
        && episodecredit.getEpisode().getId() != null) {
        Episode existingEpisode = episodeRepository.findById(
        episodecredit.getEpisode().getId()
        ).orElseThrow(() -> new RuntimeException("Episode not found"));
        episodecredit.setEpisode(existingEpisode);
        }
    
    if (episodecredit.getArtist() != null
        && episodecredit.getArtist().getId() != null) {
        Artist existingArtist = artistRepository.findById(
        episodecredit.getArtist().getId()
        ).orElseThrow(() -> new RuntimeException("Artist not found"));
        episodecredit.setArtist(existingArtist);
        }
    

        return episodecreditRepository.save(episodecredit);
    }


    public EpisodeCredit update(Long id, EpisodeCredit episodecreditRequest) {
        EpisodeCredit existing = episodecreditRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("EpisodeCredit not found"));

    // Copier les champs simples
        existing.setRole(episodecreditRequest.getRole());

// Relations ManyToOne : mise à jour conditionnelle
        if (episodecreditRequest.getEpisode() != null &&
        episodecreditRequest.getEpisode().getId() != null) {

        Episode existingEpisode = episodeRepository.findById(
        episodecreditRequest.getEpisode().getId()
        ).orElseThrow(() -> new RuntimeException("Episode not found"));

        existing.setEpisode(existingEpisode);
        } else {
        existing.setEpisode(null);
        }
        if (episodecreditRequest.getArtist() != null &&
        episodecreditRequest.getArtist().getId() != null) {

        Artist existingArtist = artistRepository.findById(
        episodecreditRequest.getArtist().getId()
        ).orElseThrow(() -> new RuntimeException("Artist not found"));

        existing.setArtist(existingArtist);
        } else {
        existing.setArtist(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    


        return episodecreditRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<EpisodeCredit> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

EpisodeCredit entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    


// --- Dissocier ManyToMany ---

    

    



// --- Dissocier OneToOne ---

    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getEpisode() != null) {
        entity.setEpisode(null);
        }
    

    
        if (entity.getArtist() != null) {
        entity.setArtist(null);
        }
    


repository.delete(entity);
return true;
}
}