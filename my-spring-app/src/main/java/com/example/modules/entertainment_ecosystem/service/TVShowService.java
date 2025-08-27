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

import com.example.modules.entertainment_ecosystem.model.StreamingPlatform;
import com.example.modules.entertainment_ecosystem.repository.StreamingPlatformRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class TVShowService extends BaseService<TVShow> {

    protected final TVShowRepository tvshowRepository;
    
    protected final SeasonRepository seasonsRepository;
    
    protected final ArtistRepository directorRepository;
    
    protected final GenreRepository genresRepository;
    
    protected final MerchandiseRepository relatedMerchandiseRepository;
    
    protected final ProductionCompanyRepository productionCompanyRepository;
    
    protected final ArtistRepository castRepository;
    
    protected final StreamingContentLicenseRepository streamingLicensesRepository;
    
    protected final ContentProviderRepository providerRepository;
    
    protected final TVShowStudioRepository tvShowStudioRepository;
    
    protected final ContentRatingRepository contentRatingRepository;
    
    protected final ContentTagRepository tagsRepository;
    
    protected final ContentLanguageRepository languagesRepository;
    
    protected final StreamingPlatformRepository platformsRepository;
    

    public TVShowService(TVShowRepository repository, SeasonRepository seasonsRepository, ArtistRepository directorRepository, GenreRepository genresRepository, MerchandiseRepository relatedMerchandiseRepository, ProductionCompanyRepository productionCompanyRepository, ArtistRepository castRepository, StreamingContentLicenseRepository streamingLicensesRepository, ContentProviderRepository providerRepository, TVShowStudioRepository tvShowStudioRepository, ContentRatingRepository contentRatingRepository, ContentTagRepository tagsRepository, ContentLanguageRepository languagesRepository, StreamingPlatformRepository platformsRepository)
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
        
        this.platformsRepository = platformsRepository;
        
    }

    @Transactional
    @Override
    public TVShow save(TVShow tvshow) {
    // ---------- OneToMany ----------
        if (tvshow.getSeasons() != null) {
            List<Season> managedSeasons = new ArrayList<>();
            for (Season item : tvshow.getSeasons()) {
                if (item.getId() != null) {
                    Season existingItem = seasonsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Season not found"));

                     existingItem.setShow(tvshow);
                     managedSeasons.add(existingItem);
                } else {
                    item.setShow(tvshow);
                    managedSeasons.add(item);
                }
            }
            tvshow.setSeasons(managedSeasons);
        }
    
        if (tvshow.getStreamingLicenses() != null) {
            List<StreamingContentLicense> managedStreamingLicenses = new ArrayList<>();
            for (StreamingContentLicense item : tvshow.getStreamingLicenses()) {
                if (item.getId() != null) {
                    StreamingContentLicense existingItem = streamingLicensesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("StreamingContentLicense not found"));

                     existingItem.setTvShow(tvshow);
                     managedStreamingLicenses.add(existingItem);
                } else {
                    item.setTvShow(tvshow);
                    managedStreamingLicenses.add(item);
                }
            }
            tvshow.setStreamingLicenses(managedStreamingLicenses);
        }
    
    // ---------- ManyToMany ----------
        if (tvshow.getGenres() != null &&
            !tvshow.getGenres().isEmpty()) {

            List<Genre> attachedGenres = new ArrayList<>();
            for (Genre item : tvshow.getGenres()) {
                if (item.getId() != null) {
                    Genre existingItem = genresRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Genre not found with id " + item.getId()));
                    attachedGenres.add(existingItem);
                } else {

                    Genre newItem = genresRepository.save(item);
                    attachedGenres.add(newItem);
                }
            }

            tvshow.setGenres(attachedGenres);

            // côté propriétaire (Genre → TVShow)
            attachedGenres.forEach(it -> it.getTvShows().add(tvshow));
        }
        
        if (tvshow.getRelatedMerchandise() != null &&
            !tvshow.getRelatedMerchandise().isEmpty()) {

            List<Merchandise> attachedRelatedMerchandise = new ArrayList<>();
            for (Merchandise item : tvshow.getRelatedMerchandise()) {
                if (item.getId() != null) {
                    Merchandise existingItem = relatedMerchandiseRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Merchandise not found with id " + item.getId()));
                    attachedRelatedMerchandise.add(existingItem);
                } else {

                    Merchandise newItem = relatedMerchandiseRepository.save(item);
                    attachedRelatedMerchandise.add(newItem);
                }
            }

            tvshow.setRelatedMerchandise(attachedRelatedMerchandise);

            // côté propriétaire (Merchandise → TVShow)
            attachedRelatedMerchandise.forEach(it -> it.getRelatedShows().add(tvshow));
        }
        
        if (tvshow.getCast() != null &&
            !tvshow.getCast().isEmpty()) {

            List<Artist> attachedCast = new ArrayList<>();
            for (Artist item : tvshow.getCast()) {
                if (item.getId() != null) {
                    Artist existingItem = castRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Artist not found with id " + item.getId()));
                    attachedCast.add(existingItem);
                } else {

                    Artist newItem = castRepository.save(item);
                    attachedCast.add(newItem);
                }
            }

            tvshow.setCast(attachedCast);

            // côté propriétaire (Artist → TVShow)
            attachedCast.forEach(it -> it.getActedInShows().add(tvshow));
        }
        
        if (tvshow.getTags() != null &&
            !tvshow.getTags().isEmpty()) {

            List<ContentTag> attachedTags = new ArrayList<>();
            for (ContentTag item : tvshow.getTags()) {
                if (item.getId() != null) {
                    ContentTag existingItem = tagsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ContentTag not found with id " + item.getId()));
                    attachedTags.add(existingItem);
                } else {

                    ContentTag newItem = tagsRepository.save(item);
                    attachedTags.add(newItem);
                }
            }

            tvshow.setTags(attachedTags);

            // côté propriétaire (ContentTag → TVShow)
            attachedTags.forEach(it -> it.getTvShows().add(tvshow));
        }
        
        if (tvshow.getLanguages() != null &&
            !tvshow.getLanguages().isEmpty()) {

            List<ContentLanguage> attachedLanguages = new ArrayList<>();
            for (ContentLanguage item : tvshow.getLanguages()) {
                if (item.getId() != null) {
                    ContentLanguage existingItem = languagesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ContentLanguage not found with id " + item.getId()));
                    attachedLanguages.add(existingItem);
                } else {

                    ContentLanguage newItem = languagesRepository.save(item);
                    attachedLanguages.add(newItem);
                }
            }

            tvshow.setLanguages(attachedLanguages);

            // côté propriétaire (ContentLanguage → TVShow)
            attachedLanguages.forEach(it -> it.getTvShows().add(tvshow));
        }
        
        if (tvshow.getPlatforms() != null &&
            !tvshow.getPlatforms().isEmpty()) {

            List<StreamingPlatform> attachedPlatforms = new ArrayList<>();
            for (StreamingPlatform item : tvshow.getPlatforms()) {
                if (item.getId() != null) {
                    StreamingPlatform existingItem = platformsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("StreamingPlatform not found with id " + item.getId()));
                    attachedPlatforms.add(existingItem);
                } else {

                    StreamingPlatform newItem = platformsRepository.save(item);
                    attachedPlatforms.add(newItem);
                }
            }

            tvshow.setPlatforms(attachedPlatforms);

            // côté propriétaire (StreamingPlatform → TVShow)
            attachedPlatforms.forEach(it -> it.getTvShows().add(tvshow));
        }
        
    // ---------- ManyToOne ----------
        if (tvshow.getDirector() != null) {
            if (tvshow.getDirector().getId() != null) {
                Artist existingDirector = directorRepository.findById(
                    tvshow.getDirector().getId()
                ).orElseThrow(() -> new RuntimeException("Artist not found with id "
                    + tvshow.getDirector().getId()));
                tvshow.setDirector(existingDirector);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Artist newDirector = directorRepository.save(tvshow.getDirector());
                tvshow.setDirector(newDirector);
            }
        }
        
        if (tvshow.getProductionCompany() != null) {
            if (tvshow.getProductionCompany().getId() != null) {
                ProductionCompany existingProductionCompany = productionCompanyRepository.findById(
                    tvshow.getProductionCompany().getId()
                ).orElseThrow(() -> new RuntimeException("ProductionCompany not found with id "
                    + tvshow.getProductionCompany().getId()));
                tvshow.setProductionCompany(existingProductionCompany);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                ProductionCompany newProductionCompany = productionCompanyRepository.save(tvshow.getProductionCompany());
                tvshow.setProductionCompany(newProductionCompany);
            }
        }
        
        if (tvshow.getProvider() != null) {
            if (tvshow.getProvider().getId() != null) {
                ContentProvider existingProvider = providerRepository.findById(
                    tvshow.getProvider().getId()
                ).orElseThrow(() -> new RuntimeException("ContentProvider not found with id "
                    + tvshow.getProvider().getId()));
                tvshow.setProvider(existingProvider);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                ContentProvider newProvider = providerRepository.save(tvshow.getProvider());
                tvshow.setProvider(newProvider);
            }
        }
        
        if (tvshow.getTvShowStudio() != null) {
            if (tvshow.getTvShowStudio().getId() != null) {
                TVShowStudio existingTvShowStudio = tvShowStudioRepository.findById(
                    tvshow.getTvShowStudio().getId()
                ).orElseThrow(() -> new RuntimeException("TVShowStudio not found with id "
                    + tvshow.getTvShowStudio().getId()));
                tvshow.setTvShowStudio(existingTvShowStudio);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                TVShowStudio newTvShowStudio = tvShowStudioRepository.save(tvshow.getTvShowStudio());
                tvshow.setTvShowStudio(newTvShowStudio);
            }
        }
        
        if (tvshow.getContentRating() != null) {
            if (tvshow.getContentRating().getId() != null) {
                ContentRating existingContentRating = contentRatingRepository.findById(
                    tvshow.getContentRating().getId()
                ).orElseThrow(() -> new RuntimeException("ContentRating not found with id "
                    + tvshow.getContentRating().getId()));
                tvshow.setContentRating(existingContentRating);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                ContentRating newContentRating = contentRatingRepository.save(tvshow.getContentRating());
                tvshow.setContentRating(newContentRating);
            }
        }
        
    // ---------- OneToOne ----------
    return tvshowRepository.save(tvshow);
}

    @Transactional
    @Override
    public TVShow update(Long id, TVShow tvshowRequest) {
        TVShow existing = tvshowRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("TVShow not found"));

    // Copier les champs simples
        existing.setTitle(tvshowRequest.getTitle());
        existing.setReleaseYear(tvshowRequest.getReleaseYear());
        existing.setTotalSeasons(tvshowRequest.getTotalSeasons());
        existing.setSynopsis(tvshowRequest.getSynopsis());

    // ---------- Relations ManyToOne ----------
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
        
    // ---------- Relations ManyToMany ----------
        if (tvshowRequest.getGenres() != null) {
            existing.getGenres().clear();

            List<Genre> genresList = tvshowRequest.getGenres().stream()
                .map(item -> genresRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Genre not found")))
                .collect(Collectors.toList());

            existing.getGenres().addAll(genresList);

            // Mettre à jour le côté inverse
            genresList.forEach(it -> {
                if (!it.getTvShows().contains(existing)) {
                    it.getTvShows().add(existing);
                }
            });
        }
        
        if (tvshowRequest.getRelatedMerchandise() != null) {
            existing.getRelatedMerchandise().clear();

            List<Merchandise> relatedMerchandiseList = tvshowRequest.getRelatedMerchandise().stream()
                .map(item -> relatedMerchandiseRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Merchandise not found")))
                .collect(Collectors.toList());

            existing.getRelatedMerchandise().addAll(relatedMerchandiseList);

            // Mettre à jour le côté inverse
            relatedMerchandiseList.forEach(it -> {
                if (!it.getRelatedShows().contains(existing)) {
                    it.getRelatedShows().add(existing);
                }
            });
        }
        
        if (tvshowRequest.getCast() != null) {
            existing.getCast().clear();

            List<Artist> castList = tvshowRequest.getCast().stream()
                .map(item -> castRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Artist not found")))
                .collect(Collectors.toList());

            existing.getCast().addAll(castList);

            // Mettre à jour le côté inverse
            castList.forEach(it -> {
                if (!it.getActedInShows().contains(existing)) {
                    it.getActedInShows().add(existing);
                }
            });
        }
        
        if (tvshowRequest.getTags() != null) {
            existing.getTags().clear();

            List<ContentTag> tagsList = tvshowRequest.getTags().stream()
                .map(item -> tagsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("ContentTag not found")))
                .collect(Collectors.toList());

            existing.getTags().addAll(tagsList);

            // Mettre à jour le côté inverse
            tagsList.forEach(it -> {
                if (!it.getTvShows().contains(existing)) {
                    it.getTvShows().add(existing);
                }
            });
        }
        
        if (tvshowRequest.getLanguages() != null) {
            existing.getLanguages().clear();

            List<ContentLanguage> languagesList = tvshowRequest.getLanguages().stream()
                .map(item -> languagesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("ContentLanguage not found")))
                .collect(Collectors.toList());

            existing.getLanguages().addAll(languagesList);

            // Mettre à jour le côté inverse
            languagesList.forEach(it -> {
                if (!it.getTvShows().contains(existing)) {
                    it.getTvShows().add(existing);
                }
            });
        }
        
        if (tvshowRequest.getPlatforms() != null) {
            existing.getPlatforms().clear();

            List<StreamingPlatform> platformsList = tvshowRequest.getPlatforms().stream()
                .map(item -> platformsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("StreamingPlatform not found")))
                .collect(Collectors.toList());

            existing.getPlatforms().addAll(platformsList);

            // Mettre à jour le côté inverse
            platformsList.forEach(it -> {
                if (!it.getTvShows().contains(existing)) {
                    it.getTvShows().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
        existing.getSeasons().clear();

        if (tvshowRequest.getSeasons() != null) {
            for (var item : tvshowRequest.getSeasons()) {
                Season existingItem;
                if (item.getId() != null) {
                    existingItem = seasonsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Season not found"));
                } else {
                existingItem = item;
                }

                existingItem.setShow(existing);
                existing.getSeasons().add(existingItem);
            }
        }
        
        existing.getStreamingLicenses().clear();

        if (tvshowRequest.getStreamingLicenses() != null) {
            for (var item : tvshowRequest.getStreamingLicenses()) {
                StreamingContentLicense existingItem;
                if (item.getId() != null) {
                    existingItem = streamingLicensesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("StreamingContentLicense not found"));
                } else {
                existingItem = item;
                }

                existingItem.setTvShow(existing);
                existing.getStreamingLicenses().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return tvshowRepository.save(existing);
}

    // Pagination simple
    public Page<TVShow> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<TVShow> search(Map<String, String> filters, Pageable pageable) {
        return super.search(TVShow.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<TVShow> saveAll(List<TVShow> tvshowList) {
        return super.saveAll(tvshowList);
    }

}