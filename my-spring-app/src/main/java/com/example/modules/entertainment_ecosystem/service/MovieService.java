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
import com.example.modules.entertainment_ecosystem.model.DigitalPurchase;
import com.example.modules.entertainment_ecosystem.model.MovieFormat;
import com.example.modules.entertainment_ecosystem.repository.MovieFormatRepository;
import com.example.modules.entertainment_ecosystem.model.StreamingContentLicense;
import com.example.modules.entertainment_ecosystem.model.ContentProvider;
import com.example.modules.entertainment_ecosystem.repository.ContentProviderRepository;
import com.example.modules.entertainment_ecosystem.model.MovieStudio;
import com.example.modules.entertainment_ecosystem.repository.MovieStudioRepository;
import com.example.modules.entertainment_ecosystem.model.ContentRating;
import com.example.modules.entertainment_ecosystem.repository.ContentRatingRepository;
import com.example.modules.entertainment_ecosystem.model.ContentTag;
import com.example.modules.entertainment_ecosystem.repository.ContentTagRepository;
import com.example.modules.entertainment_ecosystem.model.ContentLanguage;
import com.example.modules.entertainment_ecosystem.repository.ContentLanguageRepository;

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
    private final MovieFormatRepository formatsRepository;
    private final ContentProviderRepository providerRepository;
    private final MovieStudioRepository movieStudioRepository;
    private final ContentRatingRepository contentRatingRepository;
    private final ContentTagRepository tagsRepository;
    private final ContentLanguageRepository languagesRepository;

    public MovieService(MovieRepository repository,ArtistRepository castRepository,ArtistRepository directorRepository,GenreRepository genresRepository,UserProfileRepository watchlistUsersRepository,MerchandiseRepository relatedMerchandiseRepository,ProductionCompanyRepository productionCompanyRepository,MovieFormatRepository formatsRepository,ContentProviderRepository providerRepository,MovieStudioRepository movieStudioRepository,ContentRatingRepository contentRatingRepository,ContentTagRepository tagsRepository,ContentLanguageRepository languagesRepository)
    {
        super(repository);
        this.movieRepository = repository;
        this.castRepository = castRepository;
        this.directorRepository = directorRepository;
        this.genresRepository = genresRepository;
        this.watchlistUsersRepository = watchlistUsersRepository;
        this.relatedMerchandiseRepository = relatedMerchandiseRepository;
        this.productionCompanyRepository = productionCompanyRepository;
        this.formatsRepository = formatsRepository;
        this.providerRepository = providerRepository;
        this.movieStudioRepository = movieStudioRepository;
        this.contentRatingRepository = contentRatingRepository;
        this.tagsRepository = tagsRepository;
        this.languagesRepository = languagesRepository;
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

        if (movie.getProvider() != null && movie.getProvider().getId() != null) {
        ContentProvider provider = providerRepository.findById(movie.getProvider().getId())
                .orElseThrow(() -> new RuntimeException("ContentProvider not found"));
        movie.setProvider(provider);
        }

        if (movie.getMovieStudio() != null && movie.getMovieStudio().getId() != null) {
        MovieStudio movieStudio = movieStudioRepository.findById(movie.getMovieStudio().getId())
                .orElseThrow(() -> new RuntimeException("MovieStudio not found"));
        movie.setMovieStudio(movieStudio);
        }

        if (movie.getContentRating() != null && movie.getContentRating().getId() != null) {
        ContentRating contentRating = contentRatingRepository.findById(movie.getContentRating().getId())
                .orElseThrow(() -> new RuntimeException("ContentRating not found"));
        movie.setContentRating(contentRating);
        }

        if (movie.getReviews() != null) {
            for (Review item : movie.getReviews()) {
            item.setMovie(movie);
            }
        }

        if (movie.getPurchases() != null) {
            for (DigitalPurchase item : movie.getPurchases()) {
            item.setMovie(movie);
            }
        }

        if (movie.getStreamingLicenses() != null) {
            for (StreamingContentLicense item : movie.getStreamingLicenses()) {
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

        if (movieRequest.getProvider() != null && movieRequest.getProvider().getId() != null) {
        ContentProvider provider = providerRepository.findById(movieRequest.getProvider().getId())
                .orElseThrow(() -> new RuntimeException("ContentProvider not found"));
        existing.setProvider(provider);
        }

        if (movieRequest.getMovieStudio() != null && movieRequest.getMovieStudio().getId() != null) {
        MovieStudio movieStudio = movieStudioRepository.findById(movieRequest.getMovieStudio().getId())
                .orElseThrow(() -> new RuntimeException("MovieStudio not found"));
        existing.setMovieStudio(movieStudio);
        }

        if (movieRequest.getContentRating() != null && movieRequest.getContentRating().getId() != null) {
        ContentRating contentRating = contentRatingRepository.findById(movieRequest.getContentRating().getId())
                .orElseThrow(() -> new RuntimeException("ContentRating not found"));
        existing.setContentRating(contentRating);
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

        if (movieRequest.getFormats() != null) {
            existing.getFormats().clear();
            List<MovieFormat> formatsList = movieRequest.getFormats().stream()
                .map(item -> formatsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("MovieFormat not found")))
                .collect(Collectors.toList());
        existing.getFormats().addAll(formatsList);
        }

        if (movieRequest.getTags() != null) {
            existing.getTags().clear();
            List<ContentTag> tagsList = movieRequest.getTags().stream()
                .map(item -> tagsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("ContentTag not found")))
                .collect(Collectors.toList());
        existing.getTags().addAll(tagsList);
        }

        if (movieRequest.getLanguages() != null) {
            existing.getLanguages().clear();
            List<ContentLanguage> languagesList = movieRequest.getLanguages().stream()
                .map(item -> languagesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("ContentLanguage not found")))
                .collect(Collectors.toList());
        existing.getLanguages().addAll(languagesList);
        }

// Relations OneToMany : synchronisation sécurisée

        existing.getReviews().clear();
        if (movieRequest.getReviews() != null) {
            for (var item : movieRequest.getReviews()) {
            item.setMovie(existing);
            existing.getReviews().add(item);
            }
        }

        existing.getPurchases().clear();
        if (movieRequest.getPurchases() != null) {
            for (var item : movieRequest.getPurchases()) {
            item.setMovie(existing);
            existing.getPurchases().add(item);
            }
        }

        existing.getStreamingLicenses().clear();
        if (movieRequest.getStreamingLicenses() != null) {
            for (var item : movieRequest.getStreamingLicenses()) {
            item.setMovie(existing);
            existing.getStreamingLicenses().add(item);
            }
        }

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    


        return movieRepository.save(existing);
    }
}