package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Audiobook;
import com.example.modules.entertainment_ecosystem.repository.AudiobookRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;
import com.example.modules.entertainment_ecosystem.model.Publisher;
import com.example.modules.entertainment_ecosystem.repository.PublisherRepository;
import com.example.modules.entertainment_ecosystem.model.AudiobookChapter;
import com.example.modules.entertainment_ecosystem.repository.AudiobookChapterRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class AudiobookService extends BaseService<Audiobook> {

    protected final AudiobookRepository audiobookRepository;
    private final ArtistRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final AudiobookChapterRepository chaptersRepository;

    public AudiobookService(AudiobookRepository repository, ArtistRepository authorRepository, PublisherRepository publisherRepository, AudiobookChapterRepository chaptersRepository)
    {
        super(repository);
        this.audiobookRepository = repository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.chaptersRepository = chaptersRepository;
    }

    @Override
    public Audiobook save(Audiobook audiobook) {
    // ---------- OneToMany ----------
        if (audiobook.getChapters() != null) {
            List<AudiobookChapter> managedChapters = new ArrayList<>();
            for (AudiobookChapter item : audiobook.getChapters()) {
                if (item.getId() != null) {
                    AudiobookChapter existingItem = chaptersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("AudiobookChapter not found"));

                     existingItem.setAudiobook(audiobook);
                     managedChapters.add(existingItem);
                } else {
                    item.setAudiobook(audiobook);
                    managedChapters.add(item);
                }
            }
            audiobook.setChapters(managedChapters);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (audiobook.getAuthor() != null) {
            if (audiobook.getAuthor().getId() != null) {
                Artist existingAuthor = authorRepository.findById(
                    audiobook.getAuthor().getId()
                ).orElseThrow(() -> new RuntimeException("Artist not found with id "
                    + audiobook.getAuthor().getId()));
                audiobook.setAuthor(existingAuthor);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Artist newAuthor = authorRepository.save(audiobook.getAuthor());
                audiobook.setAuthor(newAuthor);
            }
        }
        
        if (audiobook.getPublisher() != null) {
            if (audiobook.getPublisher().getId() != null) {
                Publisher existingPublisher = publisherRepository.findById(
                    audiobook.getPublisher().getId()
                ).orElseThrow(() -> new RuntimeException("Publisher not found with id "
                    + audiobook.getPublisher().getId()));
                audiobook.setPublisher(existingPublisher);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Publisher newPublisher = publisherRepository.save(audiobook.getPublisher());
                audiobook.setPublisher(newPublisher);
            }
        }
        
    // ---------- OneToOne ----------
    return audiobookRepository.save(audiobook);
}


    public Audiobook update(Long id, Audiobook audiobookRequest) {
        Audiobook existing = audiobookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Audiobook not found"));

    // Copier les champs simples
        existing.setTitle(audiobookRequest.getTitle());
        existing.setReleaseDate(audiobookRequest.getReleaseDate());
        existing.setDurationHours(audiobookRequest.getDurationHours());
        existing.setNarrator(audiobookRequest.getNarrator());

    // ---------- Relations ManyToOne ----------
        if (audiobookRequest.getAuthor() != null &&
            audiobookRequest.getAuthor().getId() != null) {

            Artist existingAuthor = authorRepository.findById(
                audiobookRequest.getAuthor().getId()
            ).orElseThrow(() -> new RuntimeException("Artist not found"));

            existing.setAuthor(existingAuthor);
        } else {
            existing.setAuthor(null);
        }
        
        if (audiobookRequest.getPublisher() != null &&
            audiobookRequest.getPublisher().getId() != null) {

            Publisher existingPublisher = publisherRepository.findById(
                audiobookRequest.getPublisher().getId()
            ).orElseThrow(() -> new RuntimeException("Publisher not found"));

            existing.setPublisher(existingPublisher);
        } else {
            existing.setPublisher(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getChapters().clear();

        if (audiobookRequest.getChapters() != null) {
            for (var item : audiobookRequest.getChapters()) {
                AudiobookChapter existingItem;
                if (item.getId() != null) {
                    existingItem = chaptersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("AudiobookChapter not found"));
                } else {
                existingItem = item;
                }

                existingItem.setAudiobook(existing);
                existing.getChapters().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return audiobookRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Audiobook> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Audiobook entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getChapters() != null) {
            for (var child : entity.getChapters()) {
                // retirer la référence inverse
                child.setAudiobook(null);
            }
            entity.getChapters().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getAuthor() != null) {
            entity.setAuthor(null);
        }
        
        if (entity.getPublisher() != null) {
            entity.setPublisher(null);
        }
        
        repository.delete(entity);
        return true;
    }
}