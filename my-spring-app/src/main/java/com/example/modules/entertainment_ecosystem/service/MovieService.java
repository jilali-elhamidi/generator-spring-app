package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;
import com.example.modules.entertainment_ecosystem.model.Review;
import com.example.modules.entertainment_ecosystem.model.Genre;
import com.example.modules.entertainment_ecosystem.repository.GenreRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;
import com.example.modules.entertainment_ecosystem.model.ProductionCompany;
import com.example.modules.entertainment_ecosystem.repository.ProductionCompanyRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MovieService extends BaseService<Movie> {

    protected final MovieRepository movieRepository;
    private final ArtistRepository castRepository;
    private final ArtistRepository directorRepository;
    private final GenreRepository genresRepository;
    private final UserProfileRepository watchlistUsersRepository;
    private final MerchandiseRepository relatedMerchandiseRepository;
    private final ProductionCompanyRepository productionCompanyRepository;

    public MovieService(MovieRepository repository,ArtistRepository castRepository,ArtistRepository directorRepository,GenreRepository genresRepository,UserProfileRepository watchlistUsersRepository,MerchandiseRepository relatedMerchandiseRepository,ProductionCompanyRepository productionCompanyRepository)
    {
        super(repository);
        this.movieRepository = repository;
        this.castRepository = castRepository;
        this.directorRepository = directorRepository;
        this.genresRepository = genresRepository;
        this.watchlistUsersRepository = watchlistUsersRepository;
        this.relatedMerchandiseRepository = relatedMerchandiseRepository;
        this.productionCompanyRepository = productionCompanyRepository;
    }

    @Override
    public Movie save(Movie movie) {

        if (movie.getDirector() != null && movie.getDirector().getId() != null) {
        Artist director = directorRepository.findById(movie.getDirector().getId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        movie.setDirector(director);
        }

        if (movie.getProductionCompany() != null && movie.getProductionCompany().getId() != null) {
        ProductionCompany productionCompany = productionCompanyRepository.findById(movie.getProductionCompany().getId())
                .orElseThrow(() -> new RuntimeException("ProductionCompany not found"));
        movie.setProductionCompany(productionCompany);
        }

        if (movie.getReviews() != null) {
            for (Review item : movie.getReviews()) {
            item.setMovie(movie);
            }
        }

        return movieRepository.save(movie);
    }


    public Movie update(Long id, Movie movieRequest) {
        Movie existing = movieRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Movie not found"));

    // Copier les champs simples
        existing.setTitle(movieRequest.getTitle());
        existing.setReleaseDate(movieRequest.getReleaseDate());
        existing.setDurationMinutes(movieRequest.getDurationMinutes());
        existing.setSynopsis(movieRequest.getSynopsis());
        existing.setBoxOfficeRevenue(movieRequest.getBoxOfficeRevenue());

// Relations ManyToOne : mise à jour conditionnelle

        if (movieRequest.getDirector() != null && movieRequest.getDirector().getId() != null) {
        Artist director = directorRepository.findById(movieRequest.getDirector().getId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        existing.setDirector(director);
        }

        if (movieRequest.getProductionCompany() != null && movieRequest.getProductionCompany().getId() != null) {
        ProductionCompany productionCompany = productionCompanyRepository.findById(movieRequest.getProductionCompany().getId())
                .orElseThrow(() -> new RuntimeException("ProductionCompany not found"));
        existing.setProductionCompany(productionCompany);
        }

// Relations ManyToMany : synchronisation sécurisée

        if (movieRequest.getCast() != null) {
            existing.getCast().clear();
            List<Artist> castList = movieRequest.getCast().stream()
                .map(item -> castRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Artist not found")))
                .collect(Collectors.toList());
        existing.getCast().addAll(castList);
        }

        if (movieRequest.getGenres() != null) {
            existing.getGenres().clear();
            List<Genre> genresList = movieRequest.getGenres().stream()
                .map(item -> genresRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Genre not found")))
                .collect(Collectors.toList());
        existing.getGenres().addAll(genresList);
        }

        if (movieRequest.getWatchlistUsers() != null) {
            existing.getWatchlistUsers().clear();
            List<UserProfile> watchlistUsersList = movieRequest.getWatchlistUsers().stream()
                .map(item -> watchlistUsersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());
        existing.getWatchlistUsers().addAll(watchlistUsersList);
        }

        if (movieRequest.getRelatedMerchandise() != null) {
            existing.getRelatedMerchandise().clear();
            List<Merchandise> relatedMerchandiseList = movieRequest.getRelatedMerchandise().stream()
                .map(item -> relatedMerchandiseRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Merchandise not found")))
                .collect(Collectors.toList());
        existing.getRelatedMerchandise().addAll(relatedMerchandiseList);
        }

// Relations OneToMany : synchronisation sécurisée

        existing.getReviews().clear();
        if (movieRequest.getReviews() != null) {
            for (var item : movieRequest.getReviews()) {
            item.setMovie(existing);
            existing.getReviews().add(item);
            }
        }

    

    

    

    

    

    

    


        return movieRepository.save(existing);
    }
}