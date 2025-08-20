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

    public AudiobookService(AudiobookRepository repository,ArtistRepository authorRepository,PublisherRepository publisherRepository,AudiobookChapterRepository chaptersRepository)
    {
        super(repository);
        this.audiobookRepository = repository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.chaptersRepository = chaptersRepository;
    }

    @Override
    public Audiobook save(Audiobook audiobook) {


    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (audiobook.getChapters() != null) {
            List<AudiobookChapter> managedChapters = new ArrayList<>();
            for (AudiobookChapter item : audiobook.getChapters()) {
            if (item.getId() != null) {
            AudiobookChapter existingItem = chaptersRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("AudiobookChapter not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setAudiobook(audiobook);
            managedChapters.add(existingItem);
            } else {
            item.setAudiobook(audiobook);
            managedChapters.add(item);
            }
            }
            audiobook.setChapters(managedChapters);
            }
        
    


    

    

    

    if (audiobook.getAuthor() != null
        && audiobook.getAuthor().getId() != null) {
        Artist existingAuthor = authorRepository.findById(
        audiobook.getAuthor().getId()
        ).orElseThrow(() -> new RuntimeException("Artist not found"));
        audiobook.setAuthor(existingAuthor);
        }
    
    if (audiobook.getPublisher() != null
        && audiobook.getPublisher().getId() != null) {
        Publisher existingPublisher = publisherRepository.findById(
        audiobook.getPublisher().getId()
        ).orElseThrow(() -> new RuntimeException("Publisher not found"));
        audiobook.setPublisher(existingPublisher);
        }
    
    

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

// Relations ManyToOne : mise à jour conditionnelle
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

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getChapters().clear();

        if (audiobookRequest.getChapters() != null) {
        for (var item : audiobookRequest.getChapters()) {
        AudiobookChapter existingItem;
        if (item.getId() != null) {
        existingItem = chaptersRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("AudiobookChapter not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setAudiobook(existing);

        // Ajouter directement dans la collection existante
        existing.getChapters().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

    


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
        
            child.setAudiobook(null); // retirer la référence inverse
        
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