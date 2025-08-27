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

import com.example.modules.entertainment_ecosystem.model.MovieSoundtrack;
import com.example.modules.entertainment_ecosystem.repository.MovieSoundtrackRepository;

import com.example.modules.entertainment_ecosystem.model.MovieFestival;
import com.example.modules.entertainment_ecosystem.repository.MovieFestivalRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MovieService extends BaseService<Movie> {

    protected final MovieRepository movieRepository;
    
    protected final ArtistRepository castRepository;
    
    protected final ArtistRepository directorRepository;
    
    protected final ReviewRepository reviewsRepository;
    
    protected final GenreRepository genresRepository;
    
    protected final UserProfileRepository watchlistUsersRepository;
    
    protected final MerchandiseRepository relatedMerchandiseRepository;
    
    protected final ProductionCompanyRepository productionCompanyRepository;
    
    protected final DigitalPurchaseRepository purchasesRepository;
    
    protected final MovieFormatRepository formatsRepository;
    
    protected final StreamingContentLicenseRepository streamingLicensesRepository;
    
    protected final ContentProviderRepository providerRepository;
    
    protected final MovieStudioRepository movieStudioRepository;
    
    protected final ContentRatingRepository contentRatingRepository;
    
    protected final ContentTagRepository tagsRepository;
    
    protected final ContentLanguageRepository languagesRepository;
    
    protected final StreamingPlatformRepository platformsRepository;
    
    protected final MovieSoundtrackRepository soundtrackRepository;
    
    protected final MovieFestivalRepository festivalsRepository;
    

    public MovieService(MovieRepository repository, ArtistRepository castRepository, ArtistRepository directorRepository, ReviewRepository reviewsRepository, GenreRepository genresRepository, UserProfileRepository watchlistUsersRepository, MerchandiseRepository relatedMerchandiseRepository, ProductionCompanyRepository productionCompanyRepository, DigitalPurchaseRepository purchasesRepository, MovieFormatRepository formatsRepository, StreamingContentLicenseRepository streamingLicensesRepository, ContentProviderRepository providerRepository, MovieStudioRepository movieStudioRepository, ContentRatingRepository contentRatingRepository, ContentTagRepository tagsRepository, ContentLanguageRepository languagesRepository, StreamingPlatformRepository platformsRepository, MovieSoundtrackRepository soundtrackRepository, MovieFestivalRepository festivalsRepository)
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
        
        this.soundtrackRepository = soundtrackRepository;
        
        this.festivalsRepository = festivalsRepository;
        
    }

    @Transactional
    @Override
    public Movie save(Movie movie) {
    // ---------- OneToMany ----------
        if (movie.getReviews() != null) {
            List<Review> managedReviews = new ArrayList<>();
            for (Review item : movie.getReviews()) {
                if (item.getId() != null) {
                    Review existingItem = reviewsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Review not found"));

                     existingItem.setMovie(movie);
                     managedReviews.add(existingItem);
                } else {
                    item.setMovie(movie);
                    managedReviews.add(item);
                }
            }
            movie.setReviews(managedReviews);
        }
    
        if (movie.getPurchases() != null) {
            List<DigitalPurchase> managedPurchases = new ArrayList<>();
            for (DigitalPurchase item : movie.getPurchases()) {
                if (item.getId() != null) {
                    DigitalPurchase existingItem = purchasesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("DigitalPurchase not found"));

                     existingItem.setMovie(movie);
                     managedPurchases.add(existingItem);
                } else {
                    item.setMovie(movie);
                    managedPurchases.add(item);
                }
            }
            movie.setPurchases(managedPurchases);
        }
    
        if (movie.getStreamingLicenses() != null) {
            List<StreamingContentLicense> managedStreamingLicenses = new ArrayList<>();
            for (StreamingContentLicense item : movie.getStreamingLicenses()) {
                if (item.getId() != null) {
                    StreamingContentLicense existingItem = streamingLicensesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("StreamingContentLicense not found"));

                     existingItem.setMovie(movie);
                     managedStreamingLicenses.add(existingItem);
                } else {
                    item.setMovie(movie);
                    managedStreamingLicenses.add(item);
                }
            }
            movie.setStreamingLicenses(managedStreamingLicenses);
        }
    
    // ---------- ManyToMany ----------
        if (movie.getCast() != null &&
            !movie.getCast().isEmpty()) {

            List<Artist> attachedCast = new ArrayList<>();
            for (Artist item : movie.getCast()) {
                if (item.getId() != null) {
                    Artist existingItem = castRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Artist not found with id " + item.getId()));
                    attachedCast.add(existingItem);
                } else {

                    Artist newItem = castRepository.save(item);
                    attachedCast.add(newItem);
                }
            }

            movie.setCast(attachedCast);

            // côté propriétaire (Artist → Movie)
            attachedCast.forEach(it -> it.getActedInMovies().add(movie));
        }
        
        if (movie.getGenres() != null &&
            !movie.getGenres().isEmpty()) {

            List<Genre> attachedGenres = new ArrayList<>();
            for (Genre item : movie.getGenres()) {
                if (item.getId() != null) {
                    Genre existingItem = genresRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Genre not found with id " + item.getId()));
                    attachedGenres.add(existingItem);
                } else {

                    Genre newItem = genresRepository.save(item);
                    attachedGenres.add(newItem);
                }
            }

            movie.setGenres(attachedGenres);

            // côté propriétaire (Genre → Movie)
            attachedGenres.forEach(it -> it.getMovies().add(movie));
        }
        
        if (movie.getWatchlistUsers() != null &&
            !movie.getWatchlistUsers().isEmpty()) {

            List<UserProfile> attachedWatchlistUsers = new ArrayList<>();
            for (UserProfile item : movie.getWatchlistUsers()) {
                if (item.getId() != null) {
                    UserProfile existingItem = watchlistUsersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserProfile not found with id " + item.getId()));
                    attachedWatchlistUsers.add(existingItem);
                } else {

                    UserProfile newItem = watchlistUsersRepository.save(item);
                    attachedWatchlistUsers.add(newItem);
                }
            }

            movie.setWatchlistUsers(attachedWatchlistUsers);

            // côté propriétaire (UserProfile → Movie)
            attachedWatchlistUsers.forEach(it -> it.getWatchlistMovies().add(movie));
        }
        
        if (movie.getRelatedMerchandise() != null &&
            !movie.getRelatedMerchandise().isEmpty()) {

            List<Merchandise> attachedRelatedMerchandise = new ArrayList<>();
            for (Merchandise item : movie.getRelatedMerchandise()) {
                if (item.getId() != null) {
                    Merchandise existingItem = relatedMerchandiseRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Merchandise not found with id " + item.getId()));
                    attachedRelatedMerchandise.add(existingItem);
                } else {

                    Merchandise newItem = relatedMerchandiseRepository.save(item);
                    attachedRelatedMerchandise.add(newItem);
                }
            }

            movie.setRelatedMerchandise(attachedRelatedMerchandise);

            // côté propriétaire (Merchandise → Movie)
            attachedRelatedMerchandise.forEach(it -> it.getRelatedMovies().add(movie));
        }
        
        if (movie.getFormats() != null &&
            !movie.getFormats().isEmpty()) {

            List<MovieFormat> attachedFormats = new ArrayList<>();
            for (MovieFormat item : movie.getFormats()) {
                if (item.getId() != null) {
                    MovieFormat existingItem = formatsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MovieFormat not found with id " + item.getId()));
                    attachedFormats.add(existingItem);
                } else {

                    MovieFormat newItem = formatsRepository.save(item);
                    attachedFormats.add(newItem);
                }
            }

            movie.setFormats(attachedFormats);

            // côté propriétaire (MovieFormat → Movie)
            attachedFormats.forEach(it -> it.getMovies().add(movie));
        }
        
        if (movie.getTags() != null &&
            !movie.getTags().isEmpty()) {

            List<ContentTag> attachedTags = new ArrayList<>();
            for (ContentTag item : movie.getTags()) {
                if (item.getId() != null) {
                    ContentTag existingItem = tagsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ContentTag not found with id " + item.getId()));
                    attachedTags.add(existingItem);
                } else {

                    ContentTag newItem = tagsRepository.save(item);
                    attachedTags.add(newItem);
                }
            }

            movie.setTags(attachedTags);

            // côté propriétaire (ContentTag → Movie)
            attachedTags.forEach(it -> it.getMovies().add(movie));
        }
        
        if (movie.getLanguages() != null &&
            !movie.getLanguages().isEmpty()) {

            List<ContentLanguage> attachedLanguages = new ArrayList<>();
            for (ContentLanguage item : movie.getLanguages()) {
                if (item.getId() != null) {
                    ContentLanguage existingItem = languagesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ContentLanguage not found with id " + item.getId()));
                    attachedLanguages.add(existingItem);
                } else {

                    ContentLanguage newItem = languagesRepository.save(item);
                    attachedLanguages.add(newItem);
                }
            }

            movie.setLanguages(attachedLanguages);

            // côté propriétaire (ContentLanguage → Movie)
            attachedLanguages.forEach(it -> it.getMovies().add(movie));
        }
        
        if (movie.getPlatforms() != null &&
            !movie.getPlatforms().isEmpty()) {

            List<StreamingPlatform> attachedPlatforms = new ArrayList<>();
            for (StreamingPlatform item : movie.getPlatforms()) {
                if (item.getId() != null) {
                    StreamingPlatform existingItem = platformsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("StreamingPlatform not found with id " + item.getId()));
                    attachedPlatforms.add(existingItem);
                } else {

                    StreamingPlatform newItem = platformsRepository.save(item);
                    attachedPlatforms.add(newItem);
                }
            }

            movie.setPlatforms(attachedPlatforms);

            // côté propriétaire (StreamingPlatform → Movie)
            attachedPlatforms.forEach(it -> it.getMovies().add(movie));
        }
        
        if (movie.getFestivals() != null &&
            !movie.getFestivals().isEmpty()) {

            List<MovieFestival> attachedFestivals = new ArrayList<>();
            for (MovieFestival item : movie.getFestivals()) {
                if (item.getId() != null) {
                    MovieFestival existingItem = festivalsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MovieFestival not found with id " + item.getId()));
                    attachedFestivals.add(existingItem);
                } else {

                    MovieFestival newItem = festivalsRepository.save(item);
                    attachedFestivals.add(newItem);
                }
            }

            movie.setFestivals(attachedFestivals);

            // côté propriétaire (MovieFestival → Movie)
            attachedFestivals.forEach(it -> it.getMovies().add(movie));
        }
        
    // ---------- ManyToOne ----------
        if (movie.getDirector() != null) {
            if (movie.getDirector().getId() != null) {
                Artist existingDirector = directorRepository.findById(
                    movie.getDirector().getId()
                ).orElseThrow(() -> new RuntimeException("Artist not found with id "
                    + movie.getDirector().getId()));
                movie.setDirector(existingDirector);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Artist newDirector = directorRepository.save(movie.getDirector());
                movie.setDirector(newDirector);
            }
        }
        
        if (movie.getProductionCompany() != null) {
            if (movie.getProductionCompany().getId() != null) {
                ProductionCompany existingProductionCompany = productionCompanyRepository.findById(
                    movie.getProductionCompany().getId()
                ).orElseThrow(() -> new RuntimeException("ProductionCompany not found with id "
                    + movie.getProductionCompany().getId()));
                movie.setProductionCompany(existingProductionCompany);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                ProductionCompany newProductionCompany = productionCompanyRepository.save(movie.getProductionCompany());
                movie.setProductionCompany(newProductionCompany);
            }
        }
        
        if (movie.getProvider() != null) {
            if (movie.getProvider().getId() != null) {
                ContentProvider existingProvider = providerRepository.findById(
                    movie.getProvider().getId()
                ).orElseThrow(() -> new RuntimeException("ContentProvider not found with id "
                    + movie.getProvider().getId()));
                movie.setProvider(existingProvider);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                ContentProvider newProvider = providerRepository.save(movie.getProvider());
                movie.setProvider(newProvider);
            }
        }
        
        if (movie.getMovieStudio() != null) {
            if (movie.getMovieStudio().getId() != null) {
                MovieStudio existingMovieStudio = movieStudioRepository.findById(
                    movie.getMovieStudio().getId()
                ).orElseThrow(() -> new RuntimeException("MovieStudio not found with id "
                    + movie.getMovieStudio().getId()));
                movie.setMovieStudio(existingMovieStudio);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                MovieStudio newMovieStudio = movieStudioRepository.save(movie.getMovieStudio());
                movie.setMovieStudio(newMovieStudio);
            }
        }
        
        if (movie.getContentRating() != null) {
            if (movie.getContentRating().getId() != null) {
                ContentRating existingContentRating = contentRatingRepository.findById(
                    movie.getContentRating().getId()
                ).orElseThrow(() -> new RuntimeException("ContentRating not found with id "
                    + movie.getContentRating().getId()));
                movie.setContentRating(existingContentRating);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                ContentRating newContentRating = contentRatingRepository.save(movie.getContentRating());
                movie.setContentRating(newContentRating);
            }
        }
        
    // ---------- OneToOne ----------
        if (movie.getSoundtrack() != null) {
            if (movie.getSoundtrack().getId() != null) {
                MovieSoundtrack existingSoundtrack = soundtrackRepository.findById(movie.getSoundtrack().getId())
                    .orElseThrow(() -> new RuntimeException("MovieSoundtrack not found with id "
                        + movie.getSoundtrack().getId()));
                movie.setSoundtrack(existingSoundtrack);
            } else {
                // Nouvel objet → sauvegarde d'abord
                MovieSoundtrack newSoundtrack = soundtrackRepository.save(movie.getSoundtrack());
                movie.setSoundtrack(newSoundtrack);
            }

            movie.getSoundtrack().setMovie(movie);
        }
        
    return movieRepository.save(movie);
}

    @Transactional
    @Override
    public Movie update(Long id, Movie movieRequest) {
        Movie existing = movieRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Movie not found"));

    // Copier les champs simples
        existing.setTitle(movieRequest.getTitle());
        existing.setReleaseDate(movieRequest.getReleaseDate());
        existing.setDurationMinutes(movieRequest.getDurationMinutes());
        existing.setSynopsis(movieRequest.getSynopsis());
        existing.setBoxOfficeRevenue(movieRequest.getBoxOfficeRevenue());

    // ---------- Relations ManyToOne ----------
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
        
    // ---------- Relations ManyToMany ----------
        if (movieRequest.getCast() != null) {
            existing.getCast().clear();

            List<Artist> castList = movieRequest.getCast().stream()
                .map(item -> castRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Artist not found")))
                .collect(Collectors.toList());

            existing.getCast().addAll(castList);

            // Mettre à jour le côté inverse
            castList.forEach(it -> {
                if (!it.getActedInMovies().contains(existing)) {
                    it.getActedInMovies().add(existing);
                }
            });
        }
        
        if (movieRequest.getGenres() != null) {
            existing.getGenres().clear();

            List<Genre> genresList = movieRequest.getGenres().stream()
                .map(item -> genresRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Genre not found")))
                .collect(Collectors.toList());

            existing.getGenres().addAll(genresList);

            // Mettre à jour le côté inverse
            genresList.forEach(it -> {
                if (!it.getMovies().contains(existing)) {
                    it.getMovies().add(existing);
                }
            });
        }
        
        if (movieRequest.getWatchlistUsers() != null) {
            existing.getWatchlistUsers().clear();

            List<UserProfile> watchlistUsersList = movieRequest.getWatchlistUsers().stream()
                .map(item -> watchlistUsersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());

            existing.getWatchlistUsers().addAll(watchlistUsersList);

            // Mettre à jour le côté inverse
            watchlistUsersList.forEach(it -> {
                if (!it.getWatchlistMovies().contains(existing)) {
                    it.getWatchlistMovies().add(existing);
                }
            });
        }
        
        if (movieRequest.getRelatedMerchandise() != null) {
            existing.getRelatedMerchandise().clear();

            List<Merchandise> relatedMerchandiseList = movieRequest.getRelatedMerchandise().stream()
                .map(item -> relatedMerchandiseRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Merchandise not found")))
                .collect(Collectors.toList());

            existing.getRelatedMerchandise().addAll(relatedMerchandiseList);

            // Mettre à jour le côté inverse
            relatedMerchandiseList.forEach(it -> {
                if (!it.getRelatedMovies().contains(existing)) {
                    it.getRelatedMovies().add(existing);
                }
            });
        }
        
        if (movieRequest.getFormats() != null) {
            existing.getFormats().clear();

            List<MovieFormat> formatsList = movieRequest.getFormats().stream()
                .map(item -> formatsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("MovieFormat not found")))
                .collect(Collectors.toList());

            existing.getFormats().addAll(formatsList);

            // Mettre à jour le côté inverse
            formatsList.forEach(it -> {
                if (!it.getMovies().contains(existing)) {
                    it.getMovies().add(existing);
                }
            });
        }
        
        if (movieRequest.getTags() != null) {
            existing.getTags().clear();

            List<ContentTag> tagsList = movieRequest.getTags().stream()
                .map(item -> tagsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("ContentTag not found")))
                .collect(Collectors.toList());

            existing.getTags().addAll(tagsList);

            // Mettre à jour le côté inverse
            tagsList.forEach(it -> {
                if (!it.getMovies().contains(existing)) {
                    it.getMovies().add(existing);
                }
            });
        }
        
        if (movieRequest.getLanguages() != null) {
            existing.getLanguages().clear();

            List<ContentLanguage> languagesList = movieRequest.getLanguages().stream()
                .map(item -> languagesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("ContentLanguage not found")))
                .collect(Collectors.toList());

            existing.getLanguages().addAll(languagesList);

            // Mettre à jour le côté inverse
            languagesList.forEach(it -> {
                if (!it.getMovies().contains(existing)) {
                    it.getMovies().add(existing);
                }
            });
        }
        
        if (movieRequest.getPlatforms() != null) {
            existing.getPlatforms().clear();

            List<StreamingPlatform> platformsList = movieRequest.getPlatforms().stream()
                .map(item -> platformsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("StreamingPlatform not found")))
                .collect(Collectors.toList());

            existing.getPlatforms().addAll(platformsList);

            // Mettre à jour le côté inverse
            platformsList.forEach(it -> {
                if (!it.getMovies().contains(existing)) {
                    it.getMovies().add(existing);
                }
            });
        }
        
        if (movieRequest.getFestivals() != null) {
            existing.getFestivals().clear();

            List<MovieFestival> festivalsList = movieRequest.getFestivals().stream()
                .map(item -> festivalsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("MovieFestival not found")))
                .collect(Collectors.toList());

            existing.getFestivals().addAll(festivalsList);

            // Mettre à jour le côté inverse
            festivalsList.forEach(it -> {
                if (!it.getMovies().contains(existing)) {
                    it.getMovies().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
        existing.getReviews().clear();

        if (movieRequest.getReviews() != null) {
            for (var item : movieRequest.getReviews()) {
                Review existingItem;
                if (item.getId() != null) {
                    existingItem = reviewsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Review not found"));
                } else {
                existingItem = item;
                }

                existingItem.setMovie(existing);
                existing.getReviews().add(existingItem);
            }
        }
        
        existing.getPurchases().clear();

        if (movieRequest.getPurchases() != null) {
            for (var item : movieRequest.getPurchases()) {
                DigitalPurchase existingItem;
                if (item.getId() != null) {
                    existingItem = purchasesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("DigitalPurchase not found"));
                } else {
                existingItem = item;
                }

                existingItem.setMovie(existing);
                existing.getPurchases().add(existingItem);
            }
        }
        
        existing.getStreamingLicenses().clear();

        if (movieRequest.getStreamingLicenses() != null) {
            for (var item : movieRequest.getStreamingLicenses()) {
                StreamingContentLicense existingItem;
                if (item.getId() != null) {
                    existingItem = streamingLicensesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("StreamingContentLicense not found"));
                } else {
                existingItem = item;
                }

                existingItem.setMovie(existing);
                existing.getStreamingLicenses().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
        if (movieRequest.getSoundtrack() != null &&movieRequest.getSoundtrack().getId() != null) {

        MovieSoundtrack soundtrack = soundtrackRepository.findById(movieRequest.getSoundtrack().getId())
                .orElseThrow(() -> new RuntimeException("MovieSoundtrack not found"));

        existing.setSoundtrack(soundtrack);
        soundtrack.setMovie(existing);
        
        }
    
    return movieRepository.save(existing);
}

    // Pagination simple
    public Page<Movie> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Movie> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Movie.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Movie> saveAll(List<Movie> movieList) {
        return super.saveAll(movieList);
    }

}