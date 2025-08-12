package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Sponsor;
import com.example.modules.entertainment_ecosystem.repository.SponsorRepository;
import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class SponsorService extends BaseService<Sponsor> {

    protected final SponsorRepository sponsorRepository;
    private final MovieRepository sponsoredMoviesRepository;
    private final TVShowRepository sponsoredShowsRepository;

    public SponsorService(SponsorRepository repository,MovieRepository sponsoredMoviesRepository,TVShowRepository sponsoredShowsRepository)
    {
        super(repository);
        this.sponsorRepository = repository;
        this.sponsoredMoviesRepository = sponsoredMoviesRepository;
        this.sponsoredShowsRepository = sponsoredShowsRepository;
    }

    @Override
    public Sponsor save(Sponsor sponsor) {

        if (sponsor.getSponsoredEvents() != null) {
            for (LiveEvent item : sponsor.getSponsoredEvents()) {
            item.setSponsor(sponsor);
            }
        }

        return sponsorRepository.save(sponsor);
    }


    public Sponsor update(Long id, Sponsor sponsorRequest) {
        Sponsor existing = sponsorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Sponsor not found"));

    // Copier les champs simples
        existing.setName(sponsorRequest.getName());
        existing.setContactEmail(sponsorRequest.getContactEmail());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

        if (sponsorRequest.getSponsoredMovies() != null) {
            existing.getSponsoredMovies().clear();
            List<Movie> sponsoredMoviesList = sponsorRequest.getSponsoredMovies().stream()
                .map(item -> sponsoredMoviesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Movie not found")))
                .collect(Collectors.toList());
        existing.getSponsoredMovies().addAll(sponsoredMoviesList);
        }

        if (sponsorRequest.getSponsoredShows() != null) {
            existing.getSponsoredShows().clear();
            List<TVShow> sponsoredShowsList = sponsorRequest.getSponsoredShows().stream()
                .map(item -> sponsoredShowsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("TVShow not found")))
                .collect(Collectors.toList());
        existing.getSponsoredShows().addAll(sponsoredShowsList);
        }

// Relations OneToMany : synchronisation sécurisée

        existing.getSponsoredEvents().clear();
        if (sponsorRequest.getSponsoredEvents() != null) {
            for (var item : sponsorRequest.getSponsoredEvents()) {
            item.setSponsor(existing);
            existing.getSponsoredEvents().add(item);
            }
        }

        return sponsorRepository.save(existing);
    }
}