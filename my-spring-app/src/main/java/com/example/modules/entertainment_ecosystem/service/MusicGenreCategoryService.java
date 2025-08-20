package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MusicGenreCategory;
import com.example.modules.entertainment_ecosystem.repository.MusicGenreCategoryRepository;
import com.example.modules.entertainment_ecosystem.model.Genre;
import com.example.modules.entertainment_ecosystem.repository.GenreRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MusicGenreCategoryService extends BaseService<MusicGenreCategory> {

    protected final MusicGenreCategoryRepository musicgenrecategoryRepository;
    private final GenreRepository genresRepository;

    public MusicGenreCategoryService(MusicGenreCategoryRepository repository, GenreRepository genresRepository)
    {
        super(repository);
        this.musicgenrecategoryRepository = repository;
        this.genresRepository = genresRepository;
    }

    @Override
    public MusicGenreCategory save(MusicGenreCategory musicgenrecategory) {
    // ---------- OneToMany ----------
        if (musicgenrecategory.getGenres() != null) {
            List<Genre> managedGenres = new ArrayList<>();
            for (Genre item : musicgenrecategory.getGenres()) {
                if (item.getId() != null) {
                    Genre existingItem = genresRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Genre not found"));

                     existingItem.setCategory(musicgenrecategory);
                     managedGenres.add(existingItem);
                } else {
                    item.setCategory(musicgenrecategory);
                    managedGenres.add(item);
                }
            }
            musicgenrecategory.setGenres(managedGenres);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------

    return musicgenrecategoryRepository.save(musicgenrecategory);
}


    public MusicGenreCategory update(Long id, MusicGenreCategory musicgenrecategoryRequest) {
        MusicGenreCategory existing = musicgenrecategoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MusicGenreCategory not found"));

    // Copier les champs simples
        existing.setName(musicgenrecategoryRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getGenres().clear();

        if (musicgenrecategoryRequest.getGenres() != null) {
            for (var item : musicgenrecategoryRequest.getGenres()) {
                Genre existingItem;
                if (item.getId() != null) {
                    existingItem = genresRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Genre not found"));
                } else {
                existingItem = item;
                }

                existingItem.setCategory(existing);
                existing.getGenres().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------

    return musicgenrecategoryRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<MusicGenreCategory> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        MusicGenreCategory entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getGenres() != null) {
            for (var child : entity.getGenres()) {
                
                child.setCategory(null); // retirer la référence inverse
                
            }
            entity.getGenres().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}