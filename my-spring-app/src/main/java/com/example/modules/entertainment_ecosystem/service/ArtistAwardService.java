package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ArtistAward;
import com.example.modules.entertainment_ecosystem.repository.ArtistAwardRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class ArtistAwardService extends BaseService<ArtistAward> {

    protected final ArtistAwardRepository artistawardRepository;
    private final ArtistRepository artistRepository;

    public ArtistAwardService(ArtistAwardRepository repository,ArtistRepository artistRepository)
    {
        super(repository);
        this.artistawardRepository = repository;
        this.artistRepository = artistRepository;
    }

    @Override
    public ArtistAward save(ArtistAward artistaward) {

        if (artistaward.getArtist() != null && artistaward.getArtist().getId() != null) {
        Artist artist = artistRepository.findById(artistaward.getArtist().getId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        artistaward.setArtist(artist);
        }

        return artistawardRepository.save(artistaward);
    }


    public ArtistAward update(Long id, ArtistAward artistawardRequest) {
        ArtistAward existing = artistawardRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ArtistAward not found"));

    // Copier les champs simples
        existing.setName(artistawardRequest.getName());
        existing.setYear(artistawardRequest.getYear());

// Relations ManyToOne : mise à jour conditionnelle

        if (artistawardRequest.getArtist() != null && artistawardRequest.getArtist().getId() != null) {
        Artist artist = artistRepository.findById(artistawardRequest.getArtist().getId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        existing.setArtist(artist);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    


        return artistawardRepository.save(existing);
    }
}