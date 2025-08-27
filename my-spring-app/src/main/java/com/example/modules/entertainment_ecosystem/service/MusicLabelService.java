package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MusicLabel;
import com.example.modules.entertainment_ecosystem.repository.MusicLabelRepository;

import com.example.modules.entertainment_ecosystem.model.Album;
import com.example.modules.entertainment_ecosystem.repository.AlbumRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MusicLabelService extends BaseService<MusicLabel> {

    protected final MusicLabelRepository musiclabelRepository;
    
    protected final AlbumRepository albumsRepository;
    

    public MusicLabelService(MusicLabelRepository repository, AlbumRepository albumsRepository)
    {
        super(repository);
        this.musiclabelRepository = repository;
        
        this.albumsRepository = albumsRepository;
        
    }

    @Transactional
    @Override
    public MusicLabel save(MusicLabel musiclabel) {
    // ---------- OneToMany ----------
        if (musiclabel.getAlbums() != null) {
            List<Album> managedAlbums = new ArrayList<>();
            for (Album item : musiclabel.getAlbums()) {
                if (item.getId() != null) {
                    Album existingItem = albumsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Album not found"));

                     existingItem.setMusicLabel(musiclabel);
                     managedAlbums.add(existingItem);
                } else {
                    item.setMusicLabel(musiclabel);
                    managedAlbums.add(item);
                }
            }
            musiclabel.setAlbums(managedAlbums);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return musiclabelRepository.save(musiclabel);
}

    @Transactional
    @Override
    public MusicLabel update(Long id, MusicLabel musiclabelRequest) {
        MusicLabel existing = musiclabelRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MusicLabel not found"));

    // Copier les champs simples
        existing.setName(musiclabelRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getAlbums().clear();

        if (musiclabelRequest.getAlbums() != null) {
            for (var item : musiclabelRequest.getAlbums()) {
                Album existingItem;
                if (item.getId() != null) {
                    existingItem = albumsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Album not found"));
                } else {
                existingItem = item;
                }

                existingItem.setMusicLabel(existing);
                existing.getAlbums().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return musiclabelRepository.save(existing);
}

    // Pagination simple
    public Page<MusicLabel> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<MusicLabel> search(Map<String, String> filters, Pageable pageable) {
        return super.search(MusicLabel.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<MusicLabel> saveAll(List<MusicLabel> musiclabelList) {
        return super.saveAll(musiclabelList);
    }

}