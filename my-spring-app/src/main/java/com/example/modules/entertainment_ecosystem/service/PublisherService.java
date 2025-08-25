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
    private final AudiobookRepository audiobooksRepository;

    public PublisherService(PublisherRepository repository, BookRepository booksRepository, PodcastRepository podcastsRepository, VideoGameRepository videoGamesRepository, AudiobookRepository audiobooksRepository)
    {
        super(repository);
        this.publisherRepository = repository;
        this.booksRepository = booksRepository;
        this.podcastsRepository = podcastsRepository;
        this.videoGamesRepository = videoGamesRepository;
        this.audiobooksRepository = audiobooksRepository;
    }

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


    public Publisher update(Long id, Publisher publisherRequest) {
        Publisher existing = publisherRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Publisher not found"));

    // Copier les champs simples
        existing.setName(publisherRequest.getName());
        existing.setWebsite(publisherRequest.getWebsite());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
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
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Publisher> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Publisher entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getBooks() != null) {
            for (var child : entity.getBooks()) {
                // retirer la référence inverse
                child.setPublisher(null);
            }
            entity.getBooks().clear();
        }
        
        if (entity.getPodcasts() != null) {
            for (var child : entity.getPodcasts()) {
                // retirer la référence inverse
                child.setPublisher(null);
            }
            entity.getPodcasts().clear();
        }
        
        if (entity.getVideoGames() != null) {
            for (var child : entity.getVideoGames()) {
                // retirer la référence inverse
                child.setPublisher(null);
            }
            entity.getVideoGames().clear();
        }
        
        if (entity.getAudiobooks() != null) {
            for (var child : entity.getAudiobooks()) {
                // retirer la référence inverse
                child.setPublisher(null);
            }
            entity.getAudiobooks().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<Publisher> saveAll(List<Publisher> publisherList) {

        return publisherRepository.saveAll(publisherList);
    }

}