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

    public PodcastService(PodcastRepository repository,ArtistRepository hostRepository,PodcastEpisodeRepository episodesRepository,GenreRepository genresRepository,UserProfileRepository listenersRepository,PublisherRepository publisherRepository,PodcastCategoryRepository categoriesRepository,PodcastGuestRepository guestsRepository,ContentProviderRepository providerRepository,ContentLanguageRepository languagesRepository)
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


    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (podcast.getEpisodes() != null) {
            List<PodcastEpisode> managedEpisodes = new ArrayList<>();
            for (PodcastEpisode item : podcast.getEpisodes()) {
            if (item.getId() != null) {
            PodcastEpisode existingItem = episodesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("PodcastEpisode not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setPodcast(podcast);
            managedEpisodes.add(existingItem);
            } else {
            item.setPodcast(podcast);
            managedEpisodes.add(item);
            }
            }
            podcast.setEpisodes(managedEpisodes);
            }
        
    

    

    

    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (podcast.getGuests() != null) {
            List<PodcastGuest> managedGuests = new ArrayList<>();
            for (PodcastGuest item : podcast.getGuests()) {
            if (item.getId() != null) {
            PodcastGuest existingItem = guestsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("PodcastGuest not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setPodcast(podcast);
            managedGuests.add(existingItem);
            } else {
            item.setPodcast(podcast);
            managedGuests.add(item);
            }
            }
            podcast.setGuests(managedGuests);
            }
        
    

    

    

    if (podcast.getHost() != null
        && podcast.getHost().getId() != null) {
        Artist existingHost = hostRepository.findById(
        podcast.getHost().getId()
        ).orElseThrow(() -> new RuntimeException("Artist not found"));
        podcast.setHost(existingHost);
        }
    
    
    
    
    if (podcast.getPublisher() != null
        && podcast.getPublisher().getId() != null) {
        Publisher existingPublisher = publisherRepository.findById(
        podcast.getPublisher().getId()
        ).orElseThrow(() -> new RuntimeException("Publisher not found"));
        podcast.setPublisher(existingPublisher);
        }
    
    
    
    if (podcast.getProvider() != null
        && podcast.getProvider().getId() != null) {
        ContentProvider existingProvider = providerRepository.findById(
        podcast.getProvider().getId()
        ).orElseThrow(() -> new RuntimeException("ContentProvider not found"));
        podcast.setProvider(existingProvider);
        }
    
    

        return podcastRepository.save(podcast);
    }


    public Podcast update(Long id, Podcast podcastRequest) {
        Podcast existing = podcastRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Podcast not found"));

    // Copier les champs simples
        existing.setTitle(podcastRequest.getTitle());
        existing.setDescription(podcastRequest.getDescription());
        existing.setTotalEpisodes(podcastRequest.getTotalEpisodes());

// Relations ManyToOne : mise à jour conditionnelle
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

// Relations ManyToMany : synchronisation sécurisée

        if (podcastRequest.getGenres() != null) {
            existing.getGenres().clear();
            List<Genre> genresList = podcastRequest.getGenres().stream()
                .map(item -> genresRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Genre not found")))
                .collect(Collectors.toList());
        existing.getGenres().addAll(genresList);
        }

        if (podcastRequest.getListeners() != null) {
            existing.getListeners().clear();
            List<UserProfile> listenersList = podcastRequest.getListeners().stream()
                .map(item -> listenersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());
        existing.getListeners().addAll(listenersList);
        }

        if (podcastRequest.getCategories() != null) {
            existing.getCategories().clear();
            List<PodcastCategory> categoriesList = podcastRequest.getCategories().stream()
                .map(item -> categoriesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("PodcastCategory not found")))
                .collect(Collectors.toList());
        existing.getCategories().addAll(categoriesList);
        }

        if (podcastRequest.getLanguages() != null) {
            existing.getLanguages().clear();
            List<ContentLanguage> languagesList = podcastRequest.getLanguages().stream()
                .map(item -> languagesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("ContentLanguage not found")))
                .collect(Collectors.toList());
        existing.getLanguages().addAll(languagesList);
        }

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getEpisodes().clear();

        if (podcastRequest.getEpisodes() != null) {
        for (var item : podcastRequest.getEpisodes()) {
        PodcastEpisode existingItem;
        if (item.getId() != null) {
        existingItem = episodesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("PodcastEpisode not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setPodcast(existing);

        // Ajouter directement dans la collection existante
        existing.getEpisodes().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getGuests().clear();

        if (podcastRequest.getGuests() != null) {
        for (var item : podcastRequest.getGuests()) {
        PodcastGuest existingItem;
        if (item.getId() != null) {
        existingItem = guestsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("PodcastGuest not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setPodcast(existing);

        // Ajouter directement dans la collection existante
        existing.getGuests().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

    

    

    

    

    

    

    


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
        
            child.setPodcast(null); // retirer la référence inverse
        
        }
        entity.getEpisodes().clear();
        }
    

    

    

    

    

    
        if (entity.getGuests() != null) {
        for (var child : entity.getGuests()) {
        
            child.setPodcast(null); // retirer la référence inverse
        
        }
        entity.getGuests().clear();
        }
    

    

    


// --- Dissocier ManyToMany ---

    

    

    
        if (entity.getGenres() != null) {
        for (Genre item : new ArrayList<>(entity.getGenres())) {
        
        }
        entity.getGenres().clear(); // puis vide côté courant
        }
    

    
        if (entity.getListeners() != null) {
        for (UserProfile item : new ArrayList<>(entity.getListeners())) {
        
        }
        entity.getListeners().clear(); // puis vide côté courant
        }
    

    

    
        if (entity.getCategories() != null) {
        for (PodcastCategory item : new ArrayList<>(entity.getCategories())) {
        
        }
        entity.getCategories().clear(); // puis vide côté courant
        }
    

    

    

    
        if (entity.getLanguages() != null) {
        for (ContentLanguage item : new ArrayList<>(entity.getLanguages())) {
        
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