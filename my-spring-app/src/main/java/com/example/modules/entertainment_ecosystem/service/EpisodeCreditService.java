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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class EpisodeCreditService extends BaseService<EpisodeCredit> {

    protected final EpisodeCreditRepository episodecreditRepository;
    
    protected final EpisodeRepository episodeRepository;
    
    protected final ArtistRepository artistRepository;
    

    public EpisodeCreditService(EpisodeCreditRepository repository, EpisodeRepository episodeRepository, ArtistRepository artistRepository)
    {
        super(repository);
        this.episodecreditRepository = repository;
        
        this.episodeRepository = episodeRepository;
        
        this.artistRepository = artistRepository;
        
    }

    @Transactional
    @Override
    public EpisodeCredit save(EpisodeCredit episodecredit) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (episodecredit.getEpisode() != null) {
            if (episodecredit.getEpisode().getId() != null) {
                Episode existingEpisode = episodeRepository.findById(
                    episodecredit.getEpisode().getId()
                ).orElseThrow(() -> new RuntimeException("Episode not found with id "
                    + episodecredit.getEpisode().getId()));
                episodecredit.setEpisode(existingEpisode);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Episode newEpisode = episodeRepository.save(episodecredit.getEpisode());
                episodecredit.setEpisode(newEpisode);
            }
        }
        
        if (episodecredit.getArtist() != null) {
            if (episodecredit.getArtist().getId() != null) {
                Artist existingArtist = artistRepository.findById(
                    episodecredit.getArtist().getId()
                ).orElseThrow(() -> new RuntimeException("Artist not found with id "
                    + episodecredit.getArtist().getId()));
                episodecredit.setArtist(existingArtist);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Artist newArtist = artistRepository.save(episodecredit.getArtist());
                episodecredit.setArtist(newArtist);
            }
        }
        
    // ---------- OneToOne ----------
    return episodecreditRepository.save(episodecredit);
}

    @Transactional
    @Override
    public EpisodeCredit update(Long id, EpisodeCredit episodecreditRequest) {
        EpisodeCredit existing = episodecreditRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("EpisodeCredit not found"));

    // Copier les champs simples
        existing.setRole(episodecreditRequest.getRole());

    // ---------- Relations ManyToOne ----------
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
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return episodecreditRepository.save(existing);
}

    // Pagination simple
    public Page<EpisodeCredit> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<EpisodeCredit> search(Map<String, String> filters, Pageable pageable) {
        return super.search(EpisodeCredit.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<EpisodeCredit> saveAll(List<EpisodeCredit> episodecreditList) {
        return super.saveAll(episodecreditList);
    }

}