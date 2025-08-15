package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MusicLabel;
import com.example.modules.entertainment_ecosystem.repository.MusicLabelRepository;
import com.example.modules.entertainment_ecosystem.model.Album;
import com.example.modules.entertainment_ecosystem.repository.AlbumRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        // Vider la collection existante
        existing.getAlbums().clear();

        if (musiclabelRequest.getAlbums() != null) {
        for (var item : musiclabelRequest.getAlbums()) {
        Album existingItem;
        if (item.getId() != null) {
        existingItem = albumsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Album not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setMusicLabel(existing);

        // Ajouter directement dans la collection existante
        existing.getAlbums().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    


        return musiclabelRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<MusicLabel> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

MusicLabel entity = entityOpt.get();

// --- Dissocier OneToMany ---

    
        if (entity.getAlbums() != null) {
        for (var child : entity.getAlbums()) {
        
            child.setMusicLabel(null); // retirer la référence inverse
        
        }
        entity.getAlbums().clear();
        }
    


// --- Dissocier ManyToMany ---

    


// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}