package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseType;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseTypeRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MerchandiseService extends BaseService<Merchandise> {

    protected final MerchandiseRepository merchandiseRepository;
    private final ArtistRepository artistRepository;
    private final MovieRepository relatedMoviesRepository;
    private final TVShowRepository relatedShowsRepository;
    private final UserProfileRepository ownedByUsersRepository;
    private final MerchandiseTypeRepository productTypeRepository;

    public MerchandiseService(MerchandiseRepository repository,ArtistRepository artistRepository,MovieRepository relatedMoviesRepository,TVShowRepository relatedShowsRepository,UserProfileRepository ownedByUsersRepository,MerchandiseTypeRepository productTypeRepository)
    {
        super(repository);
        this.merchandiseRepository = repository;
        this.artistRepository = artistRepository;
        this.relatedMoviesRepository = relatedMoviesRepository;
        this.relatedShowsRepository = relatedShowsRepository;
        this.ownedByUsersRepository = ownedByUsersRepository;
        this.productTypeRepository = productTypeRepository;
    }

    @Override
    public Merchandise save(Merchandise merchandise) {

        if (merchandise.getArtist() != null && merchandise.getArtist().getId() != null) {
        Artist artist = artistRepository.findById(merchandise.getArtist().getId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        merchandise.setArtist(artist);
        }

        if (merchandise.getProductType() != null && merchandise.getProductType().getId() != null) {
        MerchandiseType productType = productTypeRepository.findById(merchandise.getProductType().getId())
                .orElseThrow(() -> new RuntimeException("MerchandiseType not found"));
        merchandise.setProductType(productType);
        }

        return merchandiseRepository.save(merchandise);
    }


    public Merchandise update(Long id, Merchandise merchandiseRequest) {
        Merchandise existing = merchandiseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Merchandise not found"));

    // Copier les champs simples
        existing.setName(merchandiseRequest.getName());
        existing.setDescription(merchandiseRequest.getDescription());
        existing.setPrice(merchandiseRequest.getPrice());

// Relations ManyToOne : mise à jour conditionnelle

        if (merchandiseRequest.getArtist() != null && merchandiseRequest.getArtist().getId() != null) {
        Artist artist = artistRepository.findById(merchandiseRequest.getArtist().getId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        existing.setArtist(artist);
        }

        if (merchandiseRequest.getProductType() != null && merchandiseRequest.getProductType().getId() != null) {
        MerchandiseType productType = productTypeRepository.findById(merchandiseRequest.getProductType().getId())
                .orElseThrow(() -> new RuntimeException("MerchandiseType not found"));
        existing.setProductType(productType);
        }

// Relations ManyToMany : synchronisation sécurisée

        if (merchandiseRequest.getRelatedMovies() != null) {
            existing.getRelatedMovies().clear();
            List<Movie> relatedMoviesList = merchandiseRequest.getRelatedMovies().stream()
                .map(item -> relatedMoviesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Movie not found")))
                .collect(Collectors.toList());
        existing.getRelatedMovies().addAll(relatedMoviesList);
        }

        if (merchandiseRequest.getRelatedShows() != null) {
            existing.getRelatedShows().clear();
            List<TVShow> relatedShowsList = merchandiseRequest.getRelatedShows().stream()
                .map(item -> relatedShowsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("TVShow not found")))
                .collect(Collectors.toList());
        existing.getRelatedShows().addAll(relatedShowsList);
        }

        if (merchandiseRequest.getOwnedByUsers() != null) {
            existing.getOwnedByUsers().clear();
            List<UserProfile> ownedByUsersList = merchandiseRequest.getOwnedByUsers().stream()
                .map(item -> ownedByUsersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());
        existing.getOwnedByUsers().addAll(ownedByUsersList);
        }

// Relations OneToMany : synchronisation sécurisée

    

    

    

    

    


        return merchandiseRepository.save(existing);
    }
}