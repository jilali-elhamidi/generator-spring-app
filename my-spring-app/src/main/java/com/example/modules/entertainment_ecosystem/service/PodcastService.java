package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Podcast;
import com.example.modules.entertainment_ecosystem.repository.PodcastRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;
import com.example.modules.entertainment_ecosystem.model.PodcastEpisode;
import com.example.modules.entertainment_ecosystem.repository.PodcastEpisodeRepository;
import com.example.modules.entertainment_ecosystem.model.Genre;
import com.example.modules.entertainment_ecosystem.repository.GenreRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.Publisher;
import com.example.modules.entertainment_ecosystem.repository.PublisherRepository;
import com.example.modules.entertainment_ecosystem.model.PodcastCategory;
import com.example.modules.entertainment_ecosystem.repository.PodcastCategoryRepository;
import com.example.modules.entertainment_ecosystem.model.PodcastGuest;
import com.example.modules.entertainment_ecosystem.repository.PodcastGuestRepository;
import com.example.modules.entertainment_ecosystem.model.ContentProvider;
import com.example.modules.entertainment_ecosystem.repository.ContentProviderRepository;
import com.example.modules.entertainment_ecosystem.model.ContentLanguage;
import com.example.modules.entertainment_ecosystem.repository.ContentLanguageRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class PodcastService extends BaseService<Podcast> {

    protected final PodcastRepository podcastRepository;
    private final ArtistRepository hostRepository;
    private final PodcastEpisodeRepository episodesRepository;
    private final GenreRepository genresRepository;
    private final UserProfileRepository listenersRepository;
    private final PublisherRepository publisherRepository;
    private final PodcastCategoryRepository categoriesRepository;
    private final PodcastGuestRepository guestsRepository;
    private final ContentProviderRepository providerRepository;
    private final ContentLanguageRepository languagesRepository;

    public PodcastService(PodcastRepository repository, ArtistRepository hostRepository, PodcastEpisodeRepository episodesRepository, GenreRepository genresRepository, UserProfileRepository listenersRepository, PublisherRepository publisherRepository, PodcastCategoryRepository categoriesRepository, PodcastGuestRepository guestsRepository, ContentProviderRepository providerRepository, ContentLanguageRepository languagesRepository)
    {
        super(repository);
        this.podcastRepository = repository;
        this.hostRepository = hostRepository;
        this.episodesRepository = episodesRepository;
        this.genresRepository = genresRepository;
        this.listenersRepository = listenersRepository;
        this.publisherRepository = publisherRepository;
        this.categoriesRepository = categoriesRepository;
        this.guestsRepository = guestsRepository;
        this.providerRepository = providerRepository;
        this.languagesRepository = languagesRepository;
    }

    @Override
    public Podcast save(Podcast podcast) {
    // ---------- OneToMany ----------
        if (podcast.getEpisodes() != null) {
            List<PodcastEpisode> managedEpisodes = new ArrayList<>();
            for (PodcastEpisode item : podcast.getEpisodes()) {
                if (item.getId() != null) {
                    PodcastEpisode existingItem = episodesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("PodcastEpisode not found"));

                     existingItem.setPodcast(podcast);
                     managedEpisodes.add(existingItem);
                } else {
                    item.setPodcast(podcast);
                    managedEpisodes.add(item);
                }
            }
            podcast.setEpisodes(managedEpisodes);
        }
    
        if (podcast.getGuests() != null) {
            List<PodcastGuest> managedGuests = new ArrayList<>();
            for (PodcastGuest item : podcast.getGuests()) {
                if (item.getId() != null) {
                    PodcastGuest existingItem = guestsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("PodcastGuest not found"));

                     existingItem.setPodcast(podcast);
                     managedGuests.add(existingItem);
                } else {
                    item.setPodcast(podcast);
                    managedGuests.add(item);
                }
            }
            podcast.setGuests(managedGuests);
        }
    
    // ---------- ManyToMany ----------
        if (podcast.getGenres() != null &&
            !podcast.getGenres().isEmpty()) {

            List<Genre> attachedGenres = new ArrayList<>();
            for (Genre item : podcast.getGenres()) {
                if (item.getId() != null) {
                    Genre existingItem = genresRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Genre not found with id " + item.getId()));
                    attachedGenres.add(existingItem);
                } else {

                    Genre newItem = genresRepository.save(item);
                    attachedGenres.add(newItem);
                }
            }

            podcast.setGenres(attachedGenres);

            // côté propriétaire (Genre → Podcast)
            attachedGenres.forEach(it -> it.getPodcasts().add(podcast));
        }
        
        if (podcast.getListeners() != null &&
            !podcast.getListeners().isEmpty()) {

            List<UserProfile> attachedListeners = new ArrayList<>();
            for (UserProfile item : podcast.getListeners()) {
                if (item.getId() != null) {
                    UserProfile existingItem = listenersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserProfile not found with id " + item.getId()));
                    attachedListeners.add(existingItem);
                } else {

                    UserProfile newItem = listenersRepository.save(item);
                    attachedListeners.add(newItem);
                }
            }

            podcast.setListeners(attachedListeners);

            // côté propriétaire (UserProfile → Podcast)
            attachedListeners.forEach(it -> it.getLibraryPodcasts().add(podcast));
        }
        
        if (podcast.getCategories() != null &&
            !podcast.getCategories().isEmpty()) {

            List<PodcastCategory> attachedCategories = new ArrayList<>();
            for (PodcastCategory item : podcast.getCategories()) {
                if (item.getId() != null) {
                    PodcastCategory existingItem = categoriesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("PodcastCategory not found with id " + item.getId()));
                    attachedCategories.add(existingItem);
                } else {

                    PodcastCategory newItem = categoriesRepository.save(item);
                    attachedCategories.add(newItem);
                }
            }

            podcast.setCategories(attachedCategories);

            // côté propriétaire (PodcastCategory → Podcast)
            attachedCategories.forEach(it -> it.getPodcasts().add(podcast));
        }
        
        if (podcast.getLanguages() != null &&
            !podcast.getLanguages().isEmpty()) {

            List<ContentLanguage> attachedLanguages = new ArrayList<>();
            for (ContentLanguage item : podcast.getLanguages()) {
                if (item.getId() != null) {
                    ContentLanguage existingItem = languagesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ContentLanguage not found with id " + item.getId()));
                    attachedLanguages.add(existingItem);
                } else {

                    ContentLanguage newItem = languagesRepository.save(item);
                    attachedLanguages.add(newItem);
                }
            }

            podcast.setLanguages(attachedLanguages);

            // côté propriétaire (ContentLanguage → Podcast)
            attachedLanguages.forEach(it -> it.getPodcasts().add(podcast));
        }
        
    // ---------- ManyToOne ----------
        if (podcast.getHost() != null) {
            if (podcast.getHost().getId() != null) {
                Artist existingHost = hostRepository.findById(
                    podcast.getHost().getId()
                ).orElseThrow(() -> new RuntimeException("Artist not found with id "
                    + podcast.getHost().getId()));
                podcast.setHost(existingHost);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Artist newHost = hostRepository.save(podcast.getHost());
                podcast.setHost(newHost);
            }
        }
        
        if (podcast.getPublisher() != null) {
            if (podcast.getPublisher().getId() != null) {
                Publisher existingPublisher = publisherRepository.findById(
                    podcast.getPublisher().getId()
                ).orElseThrow(() -> new RuntimeException("Publisher not found with id "
                    + podcast.getPublisher().getId()));
                podcast.setPublisher(existingPublisher);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Publisher newPublisher = publisherRepository.save(podcast.getPublisher());
                podcast.setPublisher(newPublisher);
            }
        }
        
        if (podcast.getProvider() != null) {
            if (podcast.getProvider().getId() != null) {
                ContentProvider existingProvider = providerRepository.findById(
                    podcast.getProvider().getId()
                ).orElseThrow(() -> new RuntimeException("ContentProvider not found with id "
                    + podcast.getProvider().getId()));
                podcast.setProvider(existingProvider);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                ContentProvider newProvider = providerRepository.save(podcast.getProvider());
                podcast.setProvider(newProvider);
            }
        }
        
    // ---------- OneToOne ----------
    return podcastRepository.save(podcast);
}


    public Podcast update(Long id, Podcast podcastRequest) {
        Podcast existing = podcastRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Podcast not found"));

    // Copier les champs simples
        existing.setTitle(podcastRequest.getTitle());
        existing.setDescription(podcastRequest.getDescription());
        existing.setTotalEpisodes(podcastRequest.getTotalEpisodes());

    // ---------- Relations ManyToOne ----------
        if (podcastRequest.getHost() != null &&
            podcastRequest.getHost().getId() != null) {

            Artist existingHost = hostRepository.findById(
                podcastRequest.getHost().getId()
            ).orElseThrow(() -> new RuntimeException("Artist not found"));

            existing.setHost(existingHost);
        } else {
            existing.setHost(null);
        }
        
        if (podcastRequest.getPublisher() != null &&
            podcastRequest.getPublisher().getId() != null) {

            Publisher existingPublisher = publisherRepository.findById(
                podcastRequest.getPublisher().getId()
            ).orElseThrow(() -> new RuntimeException("Publisher not found"));

            existing.setPublisher(existingPublisher);
        } else {
            existing.setPublisher(null);
        }
        
        if (podcastRequest.getProvider() != null &&
            podcastRequest.getProvider().getId() != null) {

            ContentProvider existingProvider = providerRepository.findById(
                podcastRequest.getProvider().getId()
            ).orElseThrow(() -> new RuntimeException("ContentProvider not found"));

            existing.setProvider(existingProvider);
        } else {
            existing.setProvider(null);
        }
        
    // ---------- Relations ManyToOne ----------
        if (podcastRequest.getGenres() != null) {
            existing.getGenres().clear();

            List<Genre> genresList = podcastRequest.getGenres().stream()
                .map(item -> genresRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Genre not found")))
                .collect(Collectors.toList());

            existing.getGenres().addAll(genresList);

            // Mettre à jour le côté inverse
            genresList.forEach(it -> {
                if (!it.getPodcasts().contains(existing)) {
                    it.getPodcasts().add(existing);
                }
            });
        }
        
        if (podcastRequest.getListeners() != null) {
            existing.getListeners().clear();

            List<UserProfile> listenersList = podcastRequest.getListeners().stream()
                .map(item -> listenersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());

            existing.getListeners().addAll(listenersList);

            // Mettre à jour le côté inverse
            listenersList.forEach(it -> {
                if (!it.getLibraryPodcasts().contains(existing)) {
                    it.getLibraryPodcasts().add(existing);
                }
            });
        }
        
        if (podcastRequest.getCategories() != null) {
            existing.getCategories().clear();

            List<PodcastCategory> categoriesList = podcastRequest.getCategories().stream()
                .map(item -> categoriesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("PodcastCategory not found")))
                .collect(Collectors.toList());

            existing.getCategories().addAll(categoriesList);

            // Mettre à jour le côté inverse
            categoriesList.forEach(it -> {
                if (!it.getPodcasts().contains(existing)) {
                    it.getPodcasts().add(existing);
                }
            });
        }
        
        if (podcastRequest.getLanguages() != null) {
            existing.getLanguages().clear();

            List<ContentLanguage> languagesList = podcastRequest.getLanguages().stream()
                .map(item -> languagesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("ContentLanguage not found")))
                .collect(Collectors.toList());

            existing.getLanguages().addAll(languagesList);

            // Mettre à jour le côté inverse
            languagesList.forEach(it -> {
                if (!it.getPodcasts().contains(existing)) {
                    it.getPodcasts().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
        existing.getEpisodes().clear();

        if (podcastRequest.getEpisodes() != null) {
            for (var item : podcastRequest.getEpisodes()) {
                PodcastEpisode existingItem;
                if (item.getId() != null) {
                    existingItem = episodesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("PodcastEpisode not found"));
                } else {
                existingItem = item;
                }

                existingItem.setPodcast(existing);
                existing.getEpisodes().add(existingItem);
            }
        }
        
        existing.getGuests().clear();

        if (podcastRequest.getGuests() != null) {
            for (var item : podcastRequest.getGuests()) {
                PodcastGuest existingItem;
                if (item.getId() != null) {
                    existingItem = guestsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("PodcastGuest not found"));
                } else {
                existingItem = item;
                }

                existingItem.setPodcast(existing);
                existing.getGuests().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return podcastRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Podcast> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Podcast entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getEpisodes() != null) {
            for (var child : entity.getEpisodes()) {
                // retirer la référence inverse
                child.setPodcast(null);
            }
            entity.getEpisodes().clear();
        }
        
        if (entity.getGuests() != null) {
            for (var child : entity.getGuests()) {
                // retirer la référence inverse
                child.setPodcast(null);
            }
            entity.getGuests().clear();
        }
        
    // --- Dissocier ManyToMany ---
        if (entity.getGenres() != null) {
            for (Genre item : new ArrayList<>(entity.getGenres())) {
                
                item.getPodcasts().remove(entity); // retire côté inverse
            }
            entity.getGenres().clear(); // puis vide côté courant
        }
        
        if (entity.getListeners() != null) {
            for (UserProfile item : new ArrayList<>(entity.getListeners())) {
                
                item.getLibraryPodcasts().remove(entity); // retire côté inverse
            }
            entity.getListeners().clear(); // puis vide côté courant
        }
        
        if (entity.getCategories() != null) {
            for (PodcastCategory item : new ArrayList<>(entity.getCategories())) {
                
                item.getPodcasts().remove(entity); // retire côté inverse
            }
            entity.getCategories().clear(); // puis vide côté courant
        }
        
        if (entity.getLanguages() != null) {
            for (ContentLanguage item : new ArrayList<>(entity.getLanguages())) {
                
                item.getPodcasts().remove(entity); // retire côté inverse
            }
            entity.getLanguages().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getHost() != null) {
            entity.setHost(null);
        }
        
        if (entity.getPublisher() != null) {
            entity.setPublisher(null);
        }
        
        if (entity.getProvider() != null) {
            entity.setProvider(null);
        }
        
        repository.delete(entity);
        return true;
    }
}