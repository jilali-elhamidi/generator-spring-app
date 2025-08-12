package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;
import com.example.modules.entertainment_ecosystem.model.Season;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;
import com.example.modules.entertainment_ecosystem.model.Genre;
import com.example.modules.entertainment_ecosystem.repository.GenreRepository;
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;
import com.example.modules.entertainment_ecosystem.model.ProductionCompany;
import com.example.modules.entertainment_ecosystem.repository.ProductionCompanyRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class TVShowService extends BaseService<TVShow> {

    protected final TVShowRepository tvshowRepository;
    private final ArtistRepository directorRepository;
    private final GenreRepository genresRepository;
    private final MerchandiseRepository relatedMerchandiseRepository;
    private final ProductionCompanyRepository productionCompanyRepository;

    public TVShowService(TVShowRepository repository,ArtistRepository directorRepository,GenreRepository genresRepository,MerchandiseRepository relatedMerchandiseRepository,ProductionCompanyRepository productionCompanyRepository)
    {
        super(repository);
        this.tvshowRepository = repository;
        this.directorRepository = directorRepository;
        this.genresRepository = genresRepository;
        this.relatedMerchandiseRepository = relatedMerchandiseRepository;
        this.productionCompanyRepository = productionCompanyRepository;
    }

    @Override
    public TVShow save(TVShow tvshow) {

        if (tvshow.getDirector() != null && tvshow.getDirector().getId() != null) {
        Artist director = directorRepository.findById(tvshow.getDirector().getId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        tvshow.setDirector(director);
        }

        if (tvshow.getProductionCompany() != null && tvshow.getProductionCompany().getId() != null) {
        ProductionCompany productionCompany = productionCompanyRepository.findById(tvshow.getProductionCompany().getId())
                .orElseThrow(() -> new RuntimeException("ProductionCompany not found"));
        tvshow.setProductionCompany(productionCompany);
        }

        if (tvshow.getSeasons() != null) {
            for (Season item : tvshow.getSeasons()) {
            item.setShow(tvshow);
            }
        }

        return tvshowRepository.save(tvshow);
    }


    public TVShow update(Long id, TVShow tvshowRequest) {
        TVShow existing = tvshowRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("TVShow not found"));

    // Copier les champs simples
        existing.setTitle(tvshowRequest.getTitle());
        existing.setReleaseYear(tvshowRequest.getReleaseYear());
        existing.setTotalSeasons(tvshowRequest.getTotalSeasons());
        existing.setSynopsis(tvshowRequest.getSynopsis());

// Relations ManyToOne : mise à jour conditionnelle

        if (tvshowRequest.getDirector() != null && tvshowRequest.getDirector().getId() != null) {
        Artist director = directorRepository.findById(tvshowRequest.getDirector().getId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        existing.setDirector(director);
        }

        if (tvshowRequest.getProductionCompany() != null && tvshowRequest.getProductionCompany().getId() != null) {
        ProductionCompany productionCompany = productionCompanyRepository.findById(tvshowRequest.getProductionCompany().getId())
                .orElseThrow(() -> new RuntimeException("ProductionCompany not found"));
        existing.setProductionCompany(productionCompany);
        }

// Relations ManyToMany : synchronisation sécurisée

        if (tvshowRequest.getGenres() != null) {
            existing.getGenres().clear();
            List<Genre> genresList = tvshowRequest.getGenres().stream()
                .map(item -> genresRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Genre not found")))
                .collect(Collectors.toList());
        existing.getGenres().addAll(genresList);
        }

        if (tvshowRequest.getRelatedMerchandise() != null) {
            existing.getRelatedMerchandise().clear();
            List<Merchandise> relatedMerchandiseList = tvshowRequest.getRelatedMerchandise().stream()
                .map(item -> relatedMerchandiseRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Merchandise not found")))
                .collect(Collectors.toList());
        existing.getRelatedMerchandise().addAll(relatedMerchandiseList);
        }

// Relations OneToMany : synchronisation sécurisée

        existing.getSeasons().clear();
        if (tvshowRequest.getSeasons() != null) {
            for (var item : tvshowRequest.getSeasons()) {
            item.setShow(existing);
            existing.getSeasons().add(item);
            }
        }

        return tvshowRepository.save(existing);
    }
}