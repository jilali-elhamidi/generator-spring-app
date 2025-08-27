package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.TVShowStudio;
import com.example.modules.entertainment_ecosystem.repository.TVShowStudioRepository;

import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class TVShowStudioService extends BaseService<TVShowStudio> {

    protected final TVShowStudioRepository tvshowstudioRepository;
    
    protected final TVShowRepository tvShowsRepository;
    

    public TVShowStudioService(TVShowStudioRepository repository, TVShowRepository tvShowsRepository)
    {
        super(repository);
        this.tvshowstudioRepository = repository;
        
        this.tvShowsRepository = tvShowsRepository;
        
    }

    @Transactional
    @Override
    public TVShowStudio save(TVShowStudio tvshowstudio) {
    // ---------- OneToMany ----------
        if (tvshowstudio.getTvShows() != null) {
            List<TVShow> managedTvShows = new ArrayList<>();
            for (TVShow item : tvshowstudio.getTvShows()) {
                if (item.getId() != null) {
                    TVShow existingItem = tvShowsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("TVShow not found"));

                     existingItem.setTvShowStudio(tvshowstudio);
                     managedTvShows.add(existingItem);
                } else {
                    item.setTvShowStudio(tvshowstudio);
                    managedTvShows.add(item);
                }
            }
            tvshowstudio.setTvShows(managedTvShows);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return tvshowstudioRepository.save(tvshowstudio);
}

    @Transactional
    @Override
    public TVShowStudio update(Long id, TVShowStudio tvshowstudioRequest) {
        TVShowStudio existing = tvshowstudioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("TVShowStudio not found"));

    // Copier les champs simples
        existing.setName(tvshowstudioRequest.getName());
        existing.setLocation(tvshowstudioRequest.getLocation());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getTvShows().clear();

        if (tvshowstudioRequest.getTvShows() != null) {
            for (var item : tvshowstudioRequest.getTvShows()) {
                TVShow existingItem;
                if (item.getId() != null) {
                    existingItem = tvShowsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("TVShow not found"));
                } else {
                existingItem = item;
                }

                existingItem.setTvShowStudio(existing);
                existing.getTvShows().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return tvshowstudioRepository.save(existing);
}

    // Pagination simple
    public Page<TVShowStudio> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<TVShowStudio> search(Map<String, String> filters, Pageable pageable) {
        return super.search(TVShowStudio.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<TVShowStudio> saveAll(List<TVShowStudio> tvshowstudioList) {
        return super.saveAll(tvshowstudioList);
    }

}