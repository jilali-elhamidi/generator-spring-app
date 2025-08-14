package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MusicLabel;
import com.example.modules.entertainment_ecosystem.repository.MusicLabelRepository;
import com.example.modules.entertainment_ecosystem.model.Album;
import com.example.modules.entertainment_ecosystem.repository.AlbumRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MusicLabelService extends BaseService<MusicLabel> {

    protected final MusicLabelRepository musiclabelRepository;
    private final AlbumRepository albumsRepository;

    public MusicLabelService(MusicLabelRepository repository,AlbumRepository albumsRepository)
    {
        super(repository);
        this.musiclabelRepository = repository;
        this.albumsRepository = albumsRepository;
    }

    @Override
    public MusicLabel save(MusicLabel musiclabel) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (musiclabel.getAlbums() != null) {
            List<Album> managedAlbums = new ArrayList<>();
            for (Album item : musiclabel.getAlbums()) {
            if (item.getId() != null) {
            Album existingItem = albumsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Album not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setMusicLabel(musiclabel);
            managedAlbums.add(existingItem);
            } else {
            item.setMusicLabel(musiclabel);
            managedAlbums.add(item);
            }
            }
            musiclabel.setAlbums(managedAlbums);
            }
        
    

    

        return musiclabelRepository.save(musiclabel);
    }


    public MusicLabel update(Long id, MusicLabel musiclabelRequest) {
        MusicLabel existing = musiclabelRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MusicLabel not found"));

    // Copier les champs simples
        existing.setName(musiclabelRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        existing.getAlbums().clear();

        if (musiclabelRequest.getAlbums() != null) {
        List<Album> managedAlbums = new ArrayList<>();

        for (var item : musiclabelRequest.getAlbums()) {
        if (item.getId() != null) {
        Album existingItem = albumsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Album not found"));
        existingItem.setMusicLabel(existing);
        managedAlbums.add(existingItem);
        } else {
        item.setMusicLabel(existing);
        managedAlbums.add(item);
        }
        }
        existing.setAlbums(managedAlbums);
        }

    


        return musiclabelRepository.save(existing);
    }


}