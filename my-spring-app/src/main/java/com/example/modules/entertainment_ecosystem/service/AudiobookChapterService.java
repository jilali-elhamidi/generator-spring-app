package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.AudiobookChapter;
import com.example.modules.entertainment_ecosystem.repository.AudiobookChapterRepository;

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
public class AudiobookChapterService extends BaseService<AudiobookChapter> {

    protected final AudiobookChapterRepository audiobookchapterRepository;
    
    protected final AudiobookRepository audiobookRepository;
    

    public AudiobookChapterService(AudiobookChapterRepository repository, AudiobookRepository audiobookRepository)
    {
        super(repository);
        this.audiobookchapterRepository = repository;
        
        this.audiobookRepository = audiobookRepository;
        
    }

    @Transactional
    @Override
    public AudiobookChapter save(AudiobookChapter audiobookchapter) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (audiobookchapter.getAudiobook() != null) {
            if (audiobookchapter.getAudiobook().getId() != null) {
                Audiobook existingAudiobook = audiobookRepository.findById(
                    audiobookchapter.getAudiobook().getId()
                ).orElseThrow(() -> new RuntimeException("Audiobook not found with id "
                    + audiobookchapter.getAudiobook().getId()));
                audiobookchapter.setAudiobook(existingAudiobook);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Audiobook newAudiobook = audiobookRepository.save(audiobookchapter.getAudiobook());
                audiobookchapter.setAudiobook(newAudiobook);
            }
        }
        
    // ---------- OneToOne ----------
    return audiobookchapterRepository.save(audiobookchapter);
}

    @Transactional
    @Override
    public AudiobookChapter update(Long id, AudiobookChapter audiobookchapterRequest) {
        AudiobookChapter existing = audiobookchapterRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("AudiobookChapter not found"));

    // Copier les champs simples
        existing.setTitle(audiobookchapterRequest.getTitle());
        existing.setChapterNumber(audiobookchapterRequest.getChapterNumber());
        existing.setDurationMinutes(audiobookchapterRequest.getDurationMinutes());

    // ---------- Relations ManyToOne ----------
        if (audiobookchapterRequest.getAudiobook() != null &&
            audiobookchapterRequest.getAudiobook().getId() != null) {

            Audiobook existingAudiobook = audiobookRepository.findById(
                audiobookchapterRequest.getAudiobook().getId()
            ).orElseThrow(() -> new RuntimeException("Audiobook not found"));

            existing.setAudiobook(existingAudiobook);
        } else {
            existing.setAudiobook(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return audiobookchapterRepository.save(existing);
}

    // Pagination simple
    public Page<AudiobookChapter> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<AudiobookChapter> search(Map<String, String> filters, Pageable pageable) {
        return super.search(AudiobookChapter.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<AudiobookChapter> saveAll(List<AudiobookChapter> audiobookchapterList) {
        return super.saveAll(audiobookchapterList);
    }

}