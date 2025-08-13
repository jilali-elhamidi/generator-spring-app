package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Publisher;
import com.example.modules.entertainment_ecosystem.repository.PublisherRepository;
import com.example.modules.entertainment_ecosystem.model.Book;
import com.example.modules.entertainment_ecosystem.model.Podcast;
import com.example.modules.entertainment_ecosystem.model.VideoGame;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class PublisherService extends BaseService<Publisher> {

    protected final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository repository)
    {
        super(repository);
        this.publisherRepository = repository;
    }

    @Override
    public Publisher save(Publisher publisher) {

        if (publisher.getBooks() != null) {
            for (Book item : publisher.getBooks()) {
            item.setPublisher(publisher);
            }
        }

        if (publisher.getPodcasts() != null) {
            for (Podcast item : publisher.getPodcasts()) {
            item.setPublisher(publisher);
            }
        }

        if (publisher.getVideoGames() != null) {
            for (VideoGame item : publisher.getVideoGames()) {
            item.setPublisher(publisher);
            }
        }

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
            for (var item : publisherRequest.getBooks()) {
            item.setPublisher(existing);
            existing.getBooks().add(item);
            }
        }

        existing.getPodcasts().clear();
        if (publisherRequest.getPodcasts() != null) {
            for (var item : publisherRequest.getPodcasts()) {
            item.setPublisher(existing);
            existing.getPodcasts().add(item);
            }
        }

        existing.getVideoGames().clear();
        if (publisherRequest.getVideoGames() != null) {
            for (var item : publisherRequest.getVideoGames()) {
            item.setPublisher(existing);
            existing.getVideoGames().add(item);
            }
        }

    

    

    


        return publisherRepository.save(existing);
    }
}