package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ArtistAward;
import com.example.modules.entertainment_ecosystem.repository.ArtistAwardRepository;

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
public class ArtistAwardService extends BaseService<ArtistAward> {

    protected final ArtistAwardRepository artistawardRepository;
    
    protected final ArtistRepository artistRepository;
    

    public ArtistAwardService(ArtistAwardRepository repository, ArtistRepository artistRepository)
    {
        super(repository);
        this.artistawardRepository = repository;
        
        this.artistRepository = artistRepository;
        
    }

    @Transactional
    @Override
    public ArtistAward save(ArtistAward artistaward) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (artistaward.getArtist() != null) {
            if (artistaward.getArtist().getId() != null) {
                Artist existingArtist = artistRepository.findById(
                    artistaward.getArtist().getId()
                ).orElseThrow(() -> new RuntimeException("Artist not found with id "
                    + artistaward.getArtist().getId()));
                artistaward.setArtist(existingArtist);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Artist newArtist = artistRepository.save(artistaward.getArtist());
                artistaward.setArtist(newArtist);
            }
        }
        
    // ---------- OneToOne ----------
    return artistawardRepository.save(artistaward);
}

    @Transactional
    @Override
    public ArtistAward update(Long id, ArtistAward artistawardRequest) {
        ArtistAward existing = artistawardRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ArtistAward not found"));

    // Copier les champs simples
        existing.setName(artistawardRequest.getName());
        existing.setYear(artistawardRequest.getYear());

    // ---------- Relations ManyToOne ----------
        if (artistawardRequest.getArtist() != null &&
            artistawardRequest.getArtist().getId() != null) {

            Artist existingArtist = artistRepository.findById(
                artistawardRequest.getArtist().getId()
            ).orElseThrow(() -> new RuntimeException("Artist not found"));

            existing.setArtist(existingArtist);
        } else {
            existing.setArtist(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return artistawardRepository.save(existing);
}

    // Pagination simple
    public Page<ArtistAward> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<ArtistAward> search(Map<String, String> filters, Pageable pageable) {
        return super.search(ArtistAward.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<ArtistAward> saveAll(List<ArtistAward> artistawardList) {
        return super.saveAll(artistawardList);
    }

}