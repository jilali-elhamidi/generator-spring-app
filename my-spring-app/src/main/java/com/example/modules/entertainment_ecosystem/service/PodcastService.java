package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Podcast;
import com.example.modules.entertainment_ecosystem.repository.PodcastRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;
import com.example.modules.entertainment_ecosystem.model.PodcastEpisode;
import com.example.modules.entertainment_ecosystem.model.Genre;
import com.example.modules.entertainment_ecosystem.repository.GenreRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.Publisher;
import com.example.modules.entertainment_ecosystem.repository.PublisherRepository;
import com.example.modules.entertainment_ecosystem.model.PodcastCategory;
import com.example.modules.entertainment_ecosystem.repository.PodcastCategoryRepository;
import com.example.modules.entertainment_ecosystem.model.PodcastGuest;
import com.example.modules.entertainment_ecosystem.model.ContentProvider;
import com.example.modules.entertainment_ecosystem.repository.ContentProviderRepository;
import com.example.modules.entertainment_ecosystem.model.ContentLanguage;
import com.example.modules.entertainment_ecosystem.repository.ContentLanguageRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class PodcastService extends BaseService<Podcast> {

    protected final PodcastRepository podcastRepository;
    private final ArtistRepository hostRepository;
    private final GenreRepository genresRepository;
    private final UserProfileRepository listenersRepository;
    private final PublisherRepository publisherRepository;
    private final PodcastCategoryRepository categoriesRepository;
    private final ContentProviderRepository providerRepository;
    private final ContentLanguageRepository languagesRepository;

    public PodcastService(PodcastRepository repository,ArtistRepository hostRepository,GenreRepository genresRepository,UserProfileRepository listenersRepository,PublisherRepository publisherRepository,PodcastCategoryRepository categoriesRepository,ContentProviderRepository providerRepository,ContentLanguageRepository languagesRepository)
    {
        super(repository);
        this.podcastRepository = repository;
        this.hostRepository = hostRepository;
        this.genresRepository = genresRepository;
        this.listenersRepository = listenersRepository;
        this.publisherRepository = publisherRepository;
        this.categoriesRepository = categoriesRepository;
        this.providerRepository = providerRepository;
        this.languagesRepository = languagesRepository;
    }

    @Override
    public Podcast save(Podcast podcast) {

        if (podcast.getHost() != null && podcast.getHost().getId() != null) {
        Artist host = hostRepository.findById(podcast.getHost().getId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        podcast.setHost(host);
        }

        if (podcast.getPublisher() != null && podcast.getPublisher().getId() != null) {
        Publisher publisher = publisherRepository.findById(podcast.getPublisher().getId())
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        podcast.setPublisher(publisher);
        }

        if (podcast.getProvider() != null && podcast.getProvider().getId() != null) {
        ContentProvider provider = providerRepository.findById(podcast.getProvider().getId())
                .orElseThrow(() -> new RuntimeException("ContentProvider not found"));
        podcast.setProvider(provider);
        }

        if (podcast.getEpisodes() != null) {
            for (PodcastEpisode item : podcast.getEpisodes()) {
            item.setPodcast(podcast);
            }
        }

        if (podcast.getGuests() != null) {
            for (PodcastGuest item : podcast.getGuests()) {
            item.setPodcast(podcast);
            }
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

        if (podcastRequest.getHost() != null && podcastRequest.getHost().getId() != null) {
        Artist host = hostRepository.findById(podcastRequest.getHost().getId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        existing.setHost(host);
        }

        if (podcastRequest.getPublisher() != null && podcastRequest.getPublisher().getId() != null) {
        Publisher publisher = publisherRepository.findById(podcastRequest.getPublisher().getId())
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        existing.setPublisher(publisher);
        }

        if (podcastRequest.getProvider() != null && podcastRequest.getProvider().getId() != null) {
        ContentProvider provider = providerRepository.findById(podcastRequest.getProvider().getId())
                .orElseThrow(() -> new RuntimeException("ContentProvider not found"));
        existing.setProvider(provider);
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

        existing.getEpisodes().clear();
        if (podcastRequest.getEpisodes() != null) {
            for (var item : podcastRequest.getEpisodes()) {
            item.setPodcast(existing);
            existing.getEpisodes().add(item);
            }
        }

        existing.getGuests().clear();
        if (podcastRequest.getGuests() != null) {
            for (var item : podcastRequest.getGuests()) {
            item.setPodcast(existing);
            existing.getGuests().add(item);
            }
        }

    

    

    

    

    

    

    

    

    


        return podcastRepository.save(existing);
    }
}