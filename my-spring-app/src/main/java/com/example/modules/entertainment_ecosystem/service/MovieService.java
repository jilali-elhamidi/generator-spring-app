package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;
import com.example.modules.entertainment_ecosystem.model.Review;
import com.example.modules.entertainment_ecosystem.repository.ReviewRepository;
import com.example.modules.entertainment_ecosystem.model.Genre;
import com.example.modules.entertainment_ecosystem.repository.GenreRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;
import com.example.modules.entertainment_ecosystem.model.ProductionCompany;
import com.example.modules.entertainment_ecosystem.repository.ProductionCompanyRepository;
import com.example.modules.entertainment_ecosystem.model.DigitalPurchase;
import com.example.modules.entertainment_ecosystem.repository.DigitalPurchaseRepository;
import com.example.modules.entertainment_ecosystem.model.MovieFormat;
import com.example.modules.entertainment_ecosystem.repository.MovieFormatRepository;
import com.example.modules.entertainment_ecosystem.model.StreamingContentLicense;
import com.example.modules.entertainment_ecosystem.repository.StreamingContentLicenseRepository;
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
import com.example.modules.entertainment_ecosystem.model.StreamingPlatform;
import com.example.modules.entertainment_ecosystem.repository.StreamingPlatformRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MovieService extends BaseService<Movie> {

    protected final MovieRepository movieRepository;
    private final ArtistRepository castRepository;
    private final ArtistRepository directorRepository;
    private final ReviewRepository reviewsRepository;
    private final GenreRepository genresRepository;
    private final UserProfileRepository watchlistUsersRepository;
    private final MerchandiseRepository relatedMerchandiseRepository;
    private final ProductionCompanyRepository productionCompanyRepository;
    private final DigitalPurchaseRepository purchasesRepository;
    private final MovieFormatRepository formatsRepository;
    private final StreamingContentLicenseRepository streamingLicensesRepository;
    private final ContentProviderRepository providerRepository;
    private final MovieStudioRepository movieStudioRepository;
    private final ContentRatingRepository contentRatingRepository;
    private final ContentTagRepository tagsRepository;
    private final ContentLanguageRepository languagesRepository;
    private final StreamingPlatformRepository platformsRepository;

    public MovieService(MovieRepository repository,ArtistRepository castRepository,ArtistRepository directorRepository,ReviewRepository reviewsRepository,GenreRepository genresRepository,UserProfileRepository watchlistUsersRepository,MerchandiseRepository relatedMerchandiseRepository,ProductionCompanyRepository productionCompanyRepository,DigitalPurchaseRepository purchasesRepository,MovieFormatRepository formatsRepository,StreamingContentLicenseRepository streamingLicensesRepository,ContentProviderRepository providerRepository,MovieStudioRepository movieStudioRepository,ContentRatingRepository contentRatingRepository,ContentTagRepository tagsRepository,ContentLanguageRepository languagesRepository,StreamingPlatformRepository platformsRepository)
    {
        super(repository);
        this.movieRepository = repository;
        this.castRepository = castRepository;
        this.directorRepository = directorRepository;
        this.reviewsRepository = reviewsRepository;
        this.genresRepository = genresRepository;
        this.watchlistUsersRepository = watchlistUsersRepository;
        this.relatedMerchandiseRepository = relatedMerchandiseRepository;
        this.productionCompanyRepository = productionCompanyRepository;
        this.purchasesRepository = purchasesRepository;
        this.formatsRepository = formatsRepository;
        this.streamingLicensesRepository = streamingLicensesRepository;
        this.providerRepository = providerRepository;
        this.movieStudioRepository = movieStudioRepository;
        this.contentRatingRepository = contentRatingRepository;
        this.tagsRepository = tagsRepository;
        this.languagesRepository = languagesRepository;
        this.platformsRepository = platformsRepository;
    }

    @Override
    public Movie save(Movie movie) {


    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (movie.getReviews() != null) {
            List<Review> managedReviews = new ArrayList<>();
            for (Review item : movie.getReviews()) {
            if (item.getId() != null) {
            Review existingItem = reviewsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Review not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setMovie(movie);
            managedReviews.add(existingItem);
            } else {
            item.setMovie(movie);
            managedReviews.add(item);
            }
            }
            movie.setReviews(managedReviews);
            }
        
    

    

    

    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (movie.getPurchases() != null) {
            List<DigitalPurchase> managedPurchases = new ArrayList<>();
            for (DigitalPurchase item : movie.getPurchases()) {
            if (item.getId() != null) {
            DigitalPurchase existingItem = purchasesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("DigitalPurchase not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setMovie(movie);
            managedPurchases.add(existingItem);
            } else {
            item.setMovie(movie);
            managedPurchases.add(item);
            }
            }
            movie.setPurchases(managedPurchases);
            }
        
    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (movie.getStreamingLicenses() != null) {
            List<StreamingContentLicense> managedStreamingLicenses = new ArrayList<>();
            for (StreamingContentLicense item : movie.getStreamingLicenses()) {
            if (item.getId() != null) {
            StreamingContentLicense existingItem = streamingLicensesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("StreamingContentLicense not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setMovie(movie);
            managedStreamingLicenses.add(existingItem);
            } else {
            item.setMovie(movie);
            managedStreamingLicenses.add(item);
            }
            }
            movie.setStreamingLicenses(managedStreamingLicenses);
            }
        
    

    

    

    

    

    

    

    
    if (movie.getDirector() != null
        && movie.getDirector().getId() != null) {
        Artist existingDirector = directorRepository.findById(
        movie.getDirector().getId()
        ).orElseThrow(() -> new RuntimeException("Artist not found"));
        movie.setDirector(existingDirector);
        }
    
    
    
    
    
    if (movie.getProductionCompany() != null
        && movie.getProductionCompany().getId() != null) {
        ProductionCompany existingProductionCompany = productionCompanyRepository.findById(
        movie.getProductionCompany().getId()
        ).orElseThrow(() -> new RuntimeException("ProductionCompany not found"));
        movie.setProductionCompany(existingProductionCompany);
        }
    
    
    
    
    if (movie.getProvider() != null
        && movie.getProvider().getId() != null) {
        ContentProvider existingProvider = providerRepository.findById(
        movie.getProvider().getId()
        ).orElseThrow(() -> new RuntimeException("ContentProvider not found"));
        movie.setProvider(existingProvider);
        }
    
    if (movie.getMovieStudio() != null
        && movie.getMovieStudio().getId() != null) {
        MovieStudio existingMovieStudio = movieStudioRepository.findById(
        movie.getMovieStudio().getId()
        ).orElseThrow(() -> new RuntimeException("MovieStudio not found"));
        movie.setMovieStudio(existingMovieStudio);
        }
    
    if (movie.getContentRating() != null
        && movie.getContentRating().getId() != null) {
        ContentRating existingContentRating = contentRatingRepository.findById(
        movie.getContentRating().getId()
        ).orElseThrow(() -> new RuntimeException("ContentRating not found"));
        movie.setContentRating(existingContentRating);
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
        if (movieRequest.getDirector() != null &&
        movieRequest.getDirector().getId() != null) {

        Artist existingDirector = directorRepository.findById(
        movieRequest.getDirector().getId()
        ).orElseThrow(() -> new RuntimeException("Artist not found"));

        existing.setDirector(existingDirector);
        } else {
        existing.setDirector(null);
        }
        if (movieRequest.getProductionCompany() != null &&
        movieRequest.getProductionCompany().getId() != null) {

        ProductionCompany existingProductionCompany = productionCompanyRepository.findById(
        movieRequest.getProductionCompany().getId()
        ).orElseThrow(() -> new RuntimeException("ProductionCompany not found"));

        existing.setProductionCompany(existingProductionCompany);
        } else {
        existing.setProductionCompany(null);
        }
        if (movieRequest.getProvider() != null &&
        movieRequest.getProvider().getId() != null) {

        ContentProvider existingProvider = providerRepository.findById(
        movieRequest.getProvider().getId()
        ).orElseThrow(() -> new RuntimeException("ContentProvider not found"));

        existing.setProvider(existingProvider);
        } else {
        existing.setProvider(null);
        }
        if (movieRequest.getMovieStudio() != null &&
        movieRequest.getMovieStudio().getId() != null) {

        MovieStudio existingMovieStudio = movieStudioRepository.findById(
        movieRequest.getMovieStudio().getId()
        ).orElseThrow(() -> new RuntimeException("MovieStudio not found"));

        existing.setMovieStudio(existingMovieStudio);
        } else {
        existing.setMovieStudio(null);
        }
        if (movieRequest.getContentRating() != null &&
        movieRequest.getContentRating().getId() != null) {

        ContentRating existingContentRating = contentRatingRepository.findById(
        movieRequest.getContentRating().getId()
        ).orElseThrow(() -> new RuntimeException("ContentRating not found"));

        existing.setContentRating(existingContentRating);
        } else {
        existing.setContentRating(null);
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

        if (movieRequest.getPlatforms() != null) {
            existing.getPlatforms().clear();
            List<StreamingPlatform> platformsList = movieRequest.getPlatforms().stream()
                .map(item -> platformsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("StreamingPlatform not found")))
                .collect(Collectors.toList());
        existing.getPlatforms().addAll(platformsList);
        }

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getReviews().clear();

        if (movieRequest.getReviews() != null) {
        for (var item : movieRequest.getReviews()) {
        Review existingItem;
        if (item.getId() != null) {
        existingItem = reviewsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Review not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setMovie(existing);

        // Ajouter directement dans la collection existante
        existing.getReviews().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getPurchases().clear();

        if (movieRequest.getPurchases() != null) {
        for (var item : movieRequest.getPurchases()) {
        DigitalPurchase existingItem;
        if (item.getId() != null) {
        existingItem = purchasesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("DigitalPurchase not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setMovie(existing);

        // Ajouter directement dans la collection existante
        existing.getPurchases().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getStreamingLicenses().clear();

        if (movieRequest.getStreamingLicenses() != null) {
        for (var item : movieRequest.getStreamingLicenses()) {
        StreamingContentLicense existingItem;
        if (item.getId() != null) {
        existingItem = streamingLicensesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("StreamingContentLicense not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setMovie(existing);

        // Ajouter directement dans la collection existante
        existing.getStreamingLicenses().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    


        return movieRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<Movie> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

Movie entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    

    
        if (entity.getReviews() != null) {
        for (var child : entity.getReviews()) {
        
            child.setMovie(null); // retirer la référence inverse
        
        }
        entity.getReviews().clear();
        }
    

    

    

    

    

    
        if (entity.getPurchases() != null) {
        for (var child : entity.getPurchases()) {
        
            child.setMovie(null); // retirer la référence inverse
        
        }
        entity.getPurchases().clear();
        }
    

    

    
        if (entity.getStreamingLicenses() != null) {
        for (var child : entity.getStreamingLicenses()) {
        
            child.setMovie(null); // retirer la référence inverse
        
        }
        entity.getStreamingLicenses().clear();
        }
    

    

    

    

    

    

    


// --- Dissocier ManyToMany ---

    
        if (entity.getCast() != null) {
        for (Artist item : new ArrayList<>(entity.getCast())) {
        
            item.getActedInMovies().remove(entity); // retire côté inverse
        
        }
        entity.getCast().clear(); // puis vide côté courant
        }
    

    

    

    
        if (entity.getGenres() != null) {
        for (Genre item : new ArrayList<>(entity.getGenres())) {
        
            item.getMovies().remove(entity); // retire côté inverse
        
        }
        entity.getGenres().clear(); // puis vide côté courant
        }
    

    
        if (entity.getWatchlistUsers() != null) {
        for (UserProfile item : new ArrayList<>(entity.getWatchlistUsers())) {
        
            item.getWatchlistMovies().remove(entity); // retire côté inverse
        
        }
        entity.getWatchlistUsers().clear(); // puis vide côté courant
        }
    

    
        if (entity.getRelatedMerchandise() != null) {
        for (Merchandise item : new ArrayList<>(entity.getRelatedMerchandise())) {
        
            item.getRelatedMovies().remove(entity); // retire côté inverse
        
        }
        entity.getRelatedMerchandise().clear(); // puis vide côté courant
        }
    

    

    

    
        if (entity.getFormats() != null) {
        for (MovieFormat item : new ArrayList<>(entity.getFormats())) {
        
            item.getMovies().remove(entity); // retire côté inverse
        
        }
        entity.getFormats().clear(); // puis vide côté courant
        }
    

    

    

    

    

    
        if (entity.getTags() != null) {
        for (ContentTag item : new ArrayList<>(entity.getTags())) {
        
            item.getMovies().remove(entity); // retire côté inverse
        
        }
        entity.getTags().clear(); // puis vide côté courant
        }
    

    
        if (entity.getLanguages() != null) {
        for (ContentLanguage item : new ArrayList<>(entity.getLanguages())) {
        
            item.getMovies().remove(entity); // retire côté inverse
        
        }
        entity.getLanguages().clear(); // puis vide côté courant
        }
    

    
        if (entity.getPlatforms() != null) {
        for (StreamingPlatform item : new ArrayList<>(entity.getPlatforms())) {
        
            item.getMovies().remove(entity); // retire côté inverse
        
        }
        entity.getPlatforms().clear(); // puis vide côté courant
        }
    



// --- Dissocier OneToOne ---

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    


// --- Dissocier ManyToOne ---

    

    
        if (entity.getDirector() != null) {
        entity.setDirector(null);
        }
    

    

    

    

    

    
        if (entity.getProductionCompany() != null) {
        entity.setProductionCompany(null);
        }
    

    

    

    

    
        if (entity.getProvider() != null) {
        entity.setProvider(null);
        }
    

    
        if (entity.getMovieStudio() != null) {
        entity.setMovieStudio(null);
        }
    

    
        if (entity.getContentRating() != null) {
        entity.setContentRating(null);
        }
    

    

    

    


repository.delete(entity);
return true;
}
}