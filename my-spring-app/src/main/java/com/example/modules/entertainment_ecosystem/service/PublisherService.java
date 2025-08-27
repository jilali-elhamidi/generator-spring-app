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

import com.example.modules.entertainment_ecosystem.model.Audiobook;
import com.example.modules.entertainment_ecosystem.repository.AudiobookRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class PublisherService extends BaseService<Publisher> {

    protected final PublisherRepository publisherRepository;
    
    protected final BookRepository booksRepository;
    
    protected final PodcastRepository podcastsRepository;
    
    protected final VideoGameRepository videoGamesRepository;
    
    protected final AudiobookRepository audiobooksRepository;
    

    public PublisherService(PublisherRepository repository, BookRepository booksRepository, PodcastRepository podcastsRepository, VideoGameRepository videoGamesRepository, AudiobookRepository audiobooksRepository)
    {
        super(repository);
        this.publisherRepository = repository;
        
        this.booksRepository = booksRepository;
        
        this.podcastsRepository = podcastsRepository;
        
        this.videoGamesRepository = videoGamesRepository;
        
        this.audiobooksRepository = audiobooksRepository;
        
    }

    @Transactional
    @Override
    public Publisher save(Publisher publisher) {
    // ---------- OneToMany ----------
        if (publisher.getBooks() != null) {
            List<Book> managedBooks = new ArrayList<>();
            for (Book item : publisher.getBooks()) {
                if (item.getId() != null) {
                    Book existingItem = booksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Book not found"));

                     existingItem.setPublisher(publisher);
                     managedBooks.add(existingItem);
                } else {
                    item.setPublisher(publisher);
                    managedBooks.add(item);
                }
            }
            publisher.setBooks(managedBooks);
        }
    
        if (publisher.getPodcasts() != null) {
            List<Podcast> managedPodcasts = new ArrayList<>();
            for (Podcast item : publisher.getPodcasts()) {
                if (item.getId() != null) {
                    Podcast existingItem = podcastsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Podcast not found"));

                     existingItem.setPublisher(publisher);
                     managedPodcasts.add(existingItem);
                } else {
                    item.setPublisher(publisher);
                    managedPodcasts.add(item);
                }
            }
            publisher.setPodcasts(managedPodcasts);
        }
    
    
        if (publisher.getAudiobooks() != null) {
            List<Audiobook> managedAudiobooks = new ArrayList<>();
            for (Audiobook item : publisher.getAudiobooks()) {
                if (item.getId() != null) {
                    Audiobook existingItem = audiobooksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Audiobook not found"));

                     existingItem.setPublisher(publisher);
                     managedAudiobooks.add(existingItem);
                } else {
                    item.setPublisher(publisher);
                    managedAudiobooks.add(item);
                }
            }
            publisher.setAudiobooks(managedAudiobooks);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return publisherRepository.save(publisher);
}

    @Transactional
    @Override
    public Publisher update(Long id, Publisher publisherRequest) {
        Publisher existing = publisherRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Publisher not found"));

    // Copier les champs simples
        existing.setName(publisherRequest.getName());
        existing.setWebsite(publisherRequest.getWebsite());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getBooks().clear();

        if (publisherRequest.getBooks() != null) {
            for (var item : publisherRequest.getBooks()) {
                Book existingItem;
                if (item.getId() != null) {
                    existingItem = booksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Book not found"));
                } else {
                existingItem = item;
                }

                existingItem.setPublisher(existing);
                existing.getBooks().add(existingItem);
            }
        }
        
        existing.getPodcasts().clear();

        if (publisherRequest.getPodcasts() != null) {
            for (var item : publisherRequest.getPodcasts()) {
                Podcast existingItem;
                if (item.getId() != null) {
                    existingItem = podcastsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Podcast not found"));
                } else {
                existingItem = item;
                }

                existingItem.setPublisher(existing);
                existing.getPodcasts().add(existingItem);
            }
        }
        
        existing.getVideoGames().clear();

        if (publisherRequest.getVideoGames() != null) {
            for (var item : publisherRequest.getVideoGames()) {
                VideoGame existingItem;
                if (item.getId() != null) {
                    existingItem = videoGamesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("VideoGame not found"));
                } else {
                existingItem = item;
                }

                existingItem.setPublisher(existing);
                existing.getVideoGames().add(existingItem);
            }
        }
        
        existing.getAudiobooks().clear();

        if (publisherRequest.getAudiobooks() != null) {
            for (var item : publisherRequest.getAudiobooks()) {
                Audiobook existingItem;
                if (item.getId() != null) {
                    existingItem = audiobooksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Audiobook not found"));
                } else {
                existingItem = item;
                }

                existingItem.setPublisher(existing);
                existing.getAudiobooks().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return publisherRepository.save(existing);
}

    // Pagination simple
    public Page<Publisher> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Publisher> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Publisher.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Publisher> saveAll(List<Publisher> publisherList) {
        return super.saveAll(publisherList);
    }

}