package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MusicLabel;
import com.example.modules.entertainment_ecosystem.repository.MusicLabelRepository;
import com.example.modules.entertainment_ecosystem.model.Album;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MusicLabelService extends BaseService<MusicLabel> {

    protected final MusicLabelRepository musiclabelRepository;

    public MusicLabelService(MusicLabelRepository repository)
    {
        super(repository);
        this.musiclabelRepository = repository;
    }

    @Override
    public MusicLabel save(MusicLabel musiclabel) {

        if (musiclabel.getAlbums() != null) {
            for (Album item : musiclabel.getAlbums()) {
            item.setMusicLabel(musiclabel);
            }
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
            for (var item : musiclabelRequest.getAlbums()) {
            item.setMusicLabel(existing);
            existing.getAlbums().add(item);
            }
        }

    


        return musiclabelRepository.save(existing);
    }
}