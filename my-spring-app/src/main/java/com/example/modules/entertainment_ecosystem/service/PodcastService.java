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

    public PodcastService(PodcastRepository repository,ArtistRepository hostRepository,GenreRepository genresRepository,UserProfileRepository listenersRepository,PublisherRepository publisherRepository)
    {
        super(repository);
        this.podcastRepository = repository;
        this.hostRepository = hostRepository;
        this.genresRepository = genresRepository;
        this.listenersRepository = listenersRepository;
        this.publisherRepository = publisherRepository;
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

        if (podcast.getEpisodes() != null) {
            for (PodcastEpisode item : podcast.getEpisodes()) {
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

// Relations OneToMany : synchronisation sécurisée

        existing.getEpisodes().clear();
        if (podcastRequest.getEpisodes() != null) {
            for (var item : podcastRequest.getEpisodes()) {
            item.setPodcast(existing);
            existing.getEpisodes().add(item);
            }
        }

    

    

    

    

    


        return podcastRepository.save(existing);
    }
}