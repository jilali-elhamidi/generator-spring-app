package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;
import com.example.modules.entertainment_ecosystem.model.Season;
import com.example.modules.entertainment_ecosystem.repository.SeasonRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;
import com.example.modules.entertainment_ecosystem.model.Genre;
import com.example.modules.entertainment_ecosystem.repository.GenreRepository;
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;
import com.example.modules.entertainment_ecosystem.model.ProductionCompany;
import com.example.modules.entertainment_ecosystem.repository.ProductionCompanyRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;
import com.example.modules.entertainment_ecosystem.model.StreamingContentLicense;
import com.example.modules.entertainment_ecosystem.repository.StreamingContentLicenseRepository;
import com.example.modules.entertainment_ecosystem.model.ContentProvider;
import com.example.modules.entertainment_ecosystem.repository.ContentProviderRepository;
import com.example.modules.entertainment_ecosystem.model.TVShowStudio;
import com.example.modules.entertainment_ecosystem.repository.TVShowStudioRepository;
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
import java.util.ArrayList;

@Service
public class TVShowService extends BaseService<TVShow> {

    protected final TVShowRepository tvshowRepository;
    private final SeasonRepository seasonsRepository;
    private final ArtistRepository directorRepository;
    private final GenreRepository genresRepository;
    private final MerchandiseRepository relatedMerchandiseRepository;
    private final ProductionCompanyRepository productionCompanyRepository;
    private final ArtistRepository castRepository;
    private final StreamingContentLicenseRepository streamingLicensesRepository;
    private final ContentProviderRepository providerRepository;
    private final TVShowStudioRepository tvShowStudioRepository;
    private final ContentRatingRepository contentRatingRepository;
    private final ContentTagRepository tagsRepository;
    private final ContentLanguageRepository languagesRepository;

    public TVShowService(TVShowRepository repository,SeasonRepository seasonsRepository,ArtistRepository directorRepository,GenreRepository genresRepository,MerchandiseRepository relatedMerchandiseRepository,ProductionCompanyRepository productionCompanyRepository,ArtistRepository castRepository,StreamingContentLicenseRepository streamingLicensesRepository,ContentProviderRepository providerRepository,TVShowStudioRepository tvShowStudioRepository,ContentRatingRepository contentRatingRepository,ContentTagRepository tagsRepository,ContentLanguageRepository languagesRepository)
    {
        super(repository);
        this.tvshowRepository = repository;
        this.seasonsRepository = seasonsRepository;
        this.directorRepository = directorRepository;
        this.genresRepository = genresRepository;
        this.relatedMerchandiseRepository = relatedMerchandiseRepository;
        this.productionCompanyRepository = productionCompanyRepository;
        this.castRepository = castRepository;
        this.streamingLicensesRepository = streamingLicensesRepository;
        this.providerRepository = providerRepository;
        this.tvShowStudioRepository = tvShowStudioRepository;
        this.contentRatingRepository = contentRatingRepository;
        this.tagsRepository = tagsRepository;
        this.languagesRepository = languagesRepository;
    }

    @Override
    public TVShow save(TVShow tvshow) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (tvshow.getSeasons() != null) {
            List<Season> managedSeasons = new ArrayList<>();
            for (Season item : tvshow.getSeasons()) {
            if (item.getId() != null) {
            Season existingItem = seasonsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Season not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setShow(tvshow);
            managedSeasons.add(existingItem);
            } else {
            item.setShow(tvshow);
            managedSeasons.add(item);
            }
            }
            tvshow.setSeasons(managedSeasons);
            }
        
    

    

    

    

    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (tvshow.getStreamingLicenses() != null) {
            List<StreamingContentLicense> managedStreamingLicenses = new ArrayList<>();
            for (StreamingContentLicense item : tvshow.getStreamingLicenses()) {
            if (item.getId() != null) {
            StreamingContentLicense existingItem = streamingLicensesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("StreamingContentLicense not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setTvShow(tvshow);
            managedStreamingLicenses.add(existingItem);
            } else {
            item.setTvShow(tvshow);
            managedStreamingLicenses.add(item);
            }
            }
            tvshow.setStreamingLicenses(managedStreamingLicenses);
            }
        
    

    

    

    

    

    

    
    if (tvshow.getDirector() != null
        && tvshow.getDirector().getId() != null) {
        Artist existingDirector = directorRepository.findById(
        tvshow.getDirector().getId()
        ).orElseThrow(() -> new RuntimeException("Artist not found"));
        tvshow.setDirector(existingDirector);
        }
    
    
    
    if (tvshow.getProductionCompany() != null
        && tvshow.getProductionCompany().getId() != null) {
        ProductionCompany existingProductionCompany = productionCompanyRepository.findById(
        tvshow.getProductionCompany().getId()
        ).orElseThrow(() -> new RuntimeException("ProductionCompany not found"));
        tvshow.setProductionCompany(existingProductionCompany);
        }
    
    
    
    if (tvshow.getProvider() != null
        && tvshow.getProvider().getId() != null) {
        ContentProvider existingProvider = providerRepository.findById(
        tvshow.getProvider().getId()
        ).orElseThrow(() -> new RuntimeException("ContentProvider not found"));
        tvshow.setProvider(existingProvider);
        }
    
    if (tvshow.getTvShowStudio() != null
        && tvshow.getTvShowStudio().getId() != null) {
        TVShowStudio existingTvShowStudio = tvShowStudioRepository.findById(
        tvshow.getTvShowStudio().getId()
        ).orElseThrow(() -> new RuntimeException("TVShowStudio not found"));
        tvshow.setTvShowStudio(existingTvShowStudio);
        }
    
    if (tvshow.getContentRating() != null
        && tvshow.getContentRating().getId() != null) {
        ContentRating existingContentRating = contentRatingRepository.findById(
        tvshow.getContentRating().getId()
        ).orElseThrow(() -> new RuntimeException("ContentRating not found"));
        tvshow.setContentRating(existingContentRating);
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
        if (tvshowRequest.getDirector() != null &&
        tvshowRequest.getDirector().getId() != null) {

        Artist existingDirector = directorRepository.findById(
        tvshowRequest.getDirector().getId()
        ).orElseThrow(() -> new RuntimeException("Artist not found"));

        existing.setDirector(existingDirector);
        } else {
        existing.setDirector(null);
        }
        if (tvshowRequest.getProductionCompany() != null &&
        tvshowRequest.getProductionCompany().getId() != null) {

        ProductionCompany existingProductionCompany = productionCompanyRepository.findById(
        tvshowRequest.getProductionCompany().getId()
        ).orElseThrow(() -> new RuntimeException("ProductionCompany not found"));

        existing.setProductionCompany(existingProductionCompany);
        } else {
        existing.setProductionCompany(null);
        }
        if (tvshowRequest.getProvider() != null &&
        tvshowRequest.getProvider().getId() != null) {

        ContentProvider existingProvider = providerRepository.findById(
        tvshowRequest.getProvider().getId()
        ).orElseThrow(() -> new RuntimeException("ContentProvider not found"));

        existing.setProvider(existingProvider);
        } else {
        existing.setProvider(null);
        }
        if (tvshowRequest.getTvShowStudio() != null &&
        tvshowRequest.getTvShowStudio().getId() != null) {

        TVShowStudio existingTvShowStudio = tvShowStudioRepository.findById(
        tvshowRequest.getTvShowStudio().getId()
        ).orElseThrow(() -> new RuntimeException("TVShowStudio not found"));

        existing.setTvShowStudio(existingTvShowStudio);
        } else {
        existing.setTvShowStudio(null);
        }
        if (tvshowRequest.getContentRating() != null &&
        tvshowRequest.getContentRating().getId() != null) {

        ContentRating existingContentRating = contentRatingRepository.findById(
        tvshowRequest.getContentRating().getId()
        ).orElseThrow(() -> new RuntimeException("ContentRating not found"));

        existing.setContentRating(existingContentRating);
        } else {
        existing.setContentRating(null);
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

        if (tvshowRequest.getCast() != null) {
            existing.getCast().clear();
            List<Artist> castList = tvshowRequest.getCast().stream()
                .map(item -> castRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Artist not found")))
                .collect(Collectors.toList());
        existing.getCast().addAll(castList);
        }

        if (tvshowRequest.getTags() != null) {
            existing.getTags().clear();
            List<ContentTag> tagsList = tvshowRequest.getTags().stream()
                .map(item -> tagsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("ContentTag not found")))
                .collect(Collectors.toList());
        existing.getTags().addAll(tagsList);
        }

        if (tvshowRequest.getLanguages() != null) {
            existing.getLanguages().clear();
            List<ContentLanguage> languagesList = tvshowRequest.getLanguages().stream()
                .map(item -> languagesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("ContentLanguage not found")))
                .collect(Collectors.toList());
        existing.getLanguages().addAll(languagesList);
        }

// Relations OneToMany : synchronisation sécurisée
        existing.getSeasons().clear();

        if (tvshowRequest.getSeasons() != null) {
        List<Season> managedSeasons = new ArrayList<>();

        for (var item : tvshowRequest.getSeasons()) {
        if (item.getId() != null) {
        Season existingItem = seasonsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Season not found"));
        existingItem.setShow(existing);
        managedSeasons.add(existingItem);
        } else {
        item.setShow(existing);
        managedSeasons.add(item);
        }
        }
        existing.setSeasons(managedSeasons);
        }
        existing.getStreamingLicenses().clear();

        if (tvshowRequest.getStreamingLicenses() != null) {
        List<StreamingContentLicense> managedStreamingLicenses = new ArrayList<>();

        for (var item : tvshowRequest.getStreamingLicenses()) {
        if (item.getId() != null) {
        StreamingContentLicense existingItem = streamingLicensesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("StreamingContentLicense not found"));
        existingItem.setTvShow(existing);
        managedStreamingLicenses.add(existingItem);
        } else {
        item.setTvShow(existing);
        managedStreamingLicenses.add(item);
        }
        }
        existing.setStreamingLicenses(managedStreamingLicenses);
        }

    

    

    

    

    

    

    

    

    

    

    

    


        return tvshowRepository.save(existing);
    }


}