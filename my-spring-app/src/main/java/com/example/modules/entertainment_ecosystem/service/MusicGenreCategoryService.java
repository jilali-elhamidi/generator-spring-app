package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MusicGenreCategory;
import com.example.modules.entertainment_ecosystem.repository.MusicGenreCategoryRepository;

import com.example.modules.entertainment_ecosystem.model.Genre;
import com.example.modules.entertainment_ecosystem.repository.GenreRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MusicGenreCategoryService extends BaseService<MusicGenreCategory> {

    protected final MusicGenreCategoryRepository musicgenrecategoryRepository;
    
    protected final GenreRepository genresRepository;
    

    public MusicGenreCategoryService(MusicGenreCategoryRepository repository, GenreRepository genresRepository)
    {
        super(repository);
        this.musicgenrecategoryRepository = repository;
        
        this.genresRepository = genresRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
    public MusicGenreCategory update(Long id, MusicGenreCategory musicgenrecategoryRequest) {
        MusicGenreCategory existing = musicgenrecategoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MusicGenreCategory not found"));

    // Copier les champs simples
        existing.setName(musicgenrecategoryRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<MusicGenreCategory> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<MusicGenreCategory> search(Map<String, String> filters, Pageable pageable) {
        return super.search(MusicGenreCategory.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<MusicGenreCategory> saveAll(List<MusicGenreCategory> musicgenrecategoryList) {
        return super.saveAll(musicgenrecategoryList);
    }

}