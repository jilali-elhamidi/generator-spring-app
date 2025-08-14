package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Publisher;
import com.example.modules.entertainment_ecosystem.repository.PublisherRepository;
import com.example.modules.entertainment_ecosystem.model.Book;
import com.example.modules.entertainment_ecosystem.repository.BookRepository;
import com.example.modules.entertainment_ecosystem.model.Podcast;
import com.example.modules.entertainment_ecosystem.repository.PodcastRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class PublisherService extends BaseService<Publisher> {

    protected final PublisherRepository publisherRepository;
    private final BookRepository booksRepository;
    private final PodcastRepository podcastsRepository;
    private final VideoGameRepository videoGamesRepository;

    public PublisherService(PublisherRepository repository,BookRepository booksRepository,PodcastRepository podcastsRepository,VideoGameRepository videoGamesRepository)
    {
        super(repository);
        this.publisherRepository = repository;
        this.booksRepository = booksRepository;
        this.podcastsRepository = podcastsRepository;
        this.videoGamesRepository = videoGamesRepository;
    }

    @Override
    public Publisher save(Publisher publisher) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (publisher.getBooks() != null) {
            List<Book> managedBooks = new ArrayList<>();
            for (Book item : publisher.getBooks()) {
            if (item.getId() != null) {
            Book existingItem = booksRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Book not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setPublisher(publisher);
            managedBooks.add(existingItem);
            } else {
            item.setPublisher(publisher);
            managedBooks.add(item);
            }
            }
            publisher.setBooks(managedBooks);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (publisher.getPodcasts() != null) {
            List<Podcast> managedPodcasts = new ArrayList<>();
            for (Podcast item : publisher.getPodcasts()) {
            if (item.getId() != null) {
            Podcast existingItem = podcastsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Podcast not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setPublisher(publisher);
            managedPodcasts.add(existingItem);
            } else {
            item.setPublisher(publisher);
            managedPodcasts.add(item);
            }
            }
            publisher.setPodcasts(managedPodcasts);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
    

    
    
    

        return publisherRepository.save(publisher);
    }


    public Publisher update(Long id, Publisher publisherRequest) {
        Publisher existing = publisherRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Publisher not found"));

    // Copier les champs simples
        existing.setName(publisherRequest.getName());
        existing.setWebsite(publisherRequest.getWebsite());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        existing.getBooks().clear();

        if (publisherRequest.getBooks() != null) {
        List<Book> managedBooks = new ArrayList<>();

        for (var item : publisherRequest.getBooks()) {
        if (item.getId() != null) {
        Book existingItem = booksRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Book not found"));
        existingItem.setPublisher(existing);
        managedBooks.add(existingItem);
        } else {
        item.setPublisher(existing);
        managedBooks.add(item);
        }
        }
        existing.setBooks(managedBooks);
        }
        existing.getPodcasts().clear();

        if (publisherRequest.getPodcasts() != null) {
        List<Podcast> managedPodcasts = new ArrayList<>();

        for (var item : publisherRequest.getPodcasts()) {
        if (item.getId() != null) {
        Podcast existingItem = podcastsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Podcast not found"));
        existingItem.setPublisher(existing);
        managedPodcasts.add(existingItem);
        } else {
        item.setPublisher(existing);
        managedPodcasts.add(item);
        }
        }
        existing.setPodcasts(managedPodcasts);
        }
        existing.getVideoGames().clear();

        if (publisherRequest.getVideoGames() != null) {
        List<VideoGame> managedVideoGames = new ArrayList<>();

        for (var item : publisherRequest.getVideoGames()) {
        if (item.getId() != null) {
        VideoGame existingItem = videoGamesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("VideoGame not found"));
        existingItem.setPublisher(existing);
        managedVideoGames.add(existingItem);
        } else {
        item.setPublisher(existing);
        managedVideoGames.add(item);
        }
        }
        existing.setVideoGames(managedVideoGames);
        }

    

    

    


        return publisherRepository.save(existing);
    }


}