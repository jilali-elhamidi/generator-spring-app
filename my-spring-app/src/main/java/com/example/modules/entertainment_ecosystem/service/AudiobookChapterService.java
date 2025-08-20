package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.AudiobookChapter;
import com.example.modules.entertainment_ecosystem.repository.AudiobookChapterRepository;
import com.example.modules.entertainment_ecosystem.model.Audiobook;
import com.example.modules.entertainment_ecosystem.repository.AudiobookRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class AudiobookChapterService extends BaseService<AudiobookChapter> {

    protected final AudiobookChapterRepository audiobookchapterRepository;
    private final AudiobookRepository audiobookRepository;

    public AudiobookChapterService(AudiobookChapterRepository repository,AudiobookRepository audiobookRepository)
    {
        super(repository);
        this.audiobookchapterRepository = repository;
        this.audiobookRepository = audiobookRepository;
    }

    @Override
    public AudiobookChapter save(AudiobookChapter audiobookchapter) {


    


    

    if (audiobookchapter.getAudiobook() != null
        && audiobookchapter.getAudiobook().getId() != null) {
        Audiobook existingAudiobook = audiobookRepository.findById(
        audiobookchapter.getAudiobook().getId()
        ).orElseThrow(() -> new RuntimeException("Audiobook not found"));
        audiobookchapter.setAudiobook(existingAudiobook);
        }
    

        return audiobookchapterRepository.save(audiobookchapter);
    }


    public AudiobookChapter update(Long id, AudiobookChapter audiobookchapterRequest) {
        AudiobookChapter existing = audiobookchapterRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("AudiobookChapter not found"));

    // Copier les champs simples
        existing.setTitle(audiobookchapterRequest.getTitle());
        existing.setChapterNumber(audiobookchapterRequest.getChapterNumber());
        existing.setDurationMinutes(audiobookchapterRequest.getDurationMinutes());

// Relations ManyToOne : mise à jour conditionnelle
        if (audiobookchapterRequest.getAudiobook() != null &&
        audiobookchapterRequest.getAudiobook().getId() != null) {

        Audiobook existingAudiobook = audiobookRepository.findById(
        audiobookchapterRequest.getAudiobook().getId()
        ).orElseThrow(() -> new RuntimeException("Audiobook not found"));

        existing.setAudiobook(existingAudiobook);
        } else {
        existing.setAudiobook(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    


        return audiobookchapterRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<AudiobookChapter> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

AudiobookChapter entity = entityOpt.get();

// --- Dissocier OneToMany ---

    


// --- Dissocier ManyToMany ---

    



// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    
        if (entity.getAudiobook() != null) {
        entity.setAudiobook(null);
        }
    


repository.delete(entity);
return true;
}
}