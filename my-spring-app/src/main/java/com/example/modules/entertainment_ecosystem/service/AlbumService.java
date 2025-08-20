package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Album;
import com.example.modules.entertainment_ecosystem.repository.AlbumRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;
import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import com.example.modules.entertainment_ecosystem.repository.MusicTrackRepository;
import com.example.modules.entertainment_ecosystem.model.Genre;
import com.example.modules.entertainment_ecosystem.repository.GenreRepository;
import com.example.modules.entertainment_ecosystem.model.MusicLabel;
import com.example.modules.entertainment_ecosystem.repository.MusicLabelRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class AlbumService extends BaseService<Album> {

    protected final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final MusicTrackRepository tracksRepository;
    private final GenreRepository genresRepository;
    private final MusicLabelRepository musicLabelRepository;

    public AlbumService(AlbumRepository repository,ArtistRepository artistRepository,MusicTrackRepository tracksRepository,GenreRepository genresRepository,MusicLabelRepository musicLabelRepository)
    {
        super(repository);
        this.albumRepository = repository;
        this.artistRepository = artistRepository;
        this.tracksRepository = tracksRepository;
        this.genresRepository = genresRepository;
        this.musicLabelRepository = musicLabelRepository;
    }

    @Override
    public Album save(Album album) {


    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (album.getTracks() != null) {
            List<MusicTrack> managedTracks = new ArrayList<>();
            for (MusicTrack item : album.getTracks()) {
            if (item.getId() != null) {
            MusicTrack existingItem = tracksRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("MusicTrack not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setAlbum(album);
            managedTracks.add(existingItem);
            } else {
            item.setAlbum(album);
            managedTracks.add(item);
            }
            }
            album.setTracks(managedTracks);
            }
        
    

    

    


    

    

    
        if (album.getGenres() != null
        && !album.getGenres().isEmpty()) {

        List<Genre> attachedGenres = album.getGenres().stream()
        .map(item -> genresRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Genre not found with id " + item.getId())))
        .toList();

        album.setGenres(attachedGenres);

        // côté propriétaire (Genre → Album)
        attachedGenres.forEach(it -> it.getAlbums().add(album));
        }
    

    

    if (album.getArtist() != null
        && album.getArtist().getId() != null) {
        Artist existingArtist = artistRepository.findById(
        album.getArtist().getId()
        ).orElseThrow(() -> new RuntimeException("Artist not found"));
        album.setArtist(existingArtist);
        }
    
    
    
    if (album.getMusicLabel() != null
        && album.getMusicLabel().getId() != null) {
        MusicLabel existingMusicLabel = musicLabelRepository.findById(
        album.getMusicLabel().getId()
        ).orElseThrow(() -> new RuntimeException("MusicLabel not found"));
        album.setMusicLabel(existingMusicLabel);
        }
    

        return albumRepository.save(album);
    }


    public Album update(Long id, Album albumRequest) {
        Album existing = albumRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Album not found"));

    // Copier les champs simples
        existing.setTitle(albumRequest.getTitle());
        existing.setReleaseDate(albumRequest.getReleaseDate());

// Relations ManyToOne : mise à jour conditionnelle
        if (albumRequest.getArtist() != null &&
        albumRequest.getArtist().getId() != null) {

        Artist existingArtist = artistRepository.findById(
        albumRequest.getArtist().getId()
        ).orElseThrow(() -> new RuntimeException("Artist not found"));

        existing.setArtist(existingArtist);
        } else {
        existing.setArtist(null);
        }
        if (albumRequest.getMusicLabel() != null &&
        albumRequest.getMusicLabel().getId() != null) {

        MusicLabel existingMusicLabel = musicLabelRepository.findById(
        albumRequest.getMusicLabel().getId()
        ).orElseThrow(() -> new RuntimeException("MusicLabel not found"));

        existing.setMusicLabel(existingMusicLabel);
        } else {
        existing.setMusicLabel(null);
        }

// Relations ManyToMany : synchronisation sécurisée
        if (albumRequest.getGenres() != null) {
        existing.getGenres().clear();

        List<Genre> genresList = albumRequest.getGenres().stream()
        .map(item -> genresRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Genre not found")))
        .collect(Collectors.toList());

        existing.getGenres().addAll(genresList);

        // Mettre à jour le côté inverse
        genresList.forEach(it -> {
        if (!it.getAlbums().contains(existing)) {
        it.getAlbums().add(existing);
        }
        });
        }

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getTracks().clear();

        if (albumRequest.getTracks() != null) {
        for (var item : albumRequest.getTracks()) {
        MusicTrack existingItem;
        if (item.getId() != null) {
        existingItem = tracksRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("MusicTrack not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setAlbum(existing);

        // Ajouter directement dans la collection existante
        existing.getTracks().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

    

    


        return albumRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<Album> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

Album entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    
        if (entity.getTracks() != null) {
        for (var child : entity.getTracks()) {
        
            child.setAlbum(null); // retirer la référence inverse
        
        }
        entity.getTracks().clear();
        }
    

    

    


// --- Dissocier ManyToMany ---

    

    

    
        if (entity.getGenres() != null) {
        for (Genre item : new ArrayList<>(entity.getGenres())) {
        
            item.getAlbums().remove(entity); // retire côté inverse
        
        }
        entity.getGenres().clear(); // puis vide côté courant
        }
    

    



// --- Dissocier OneToOne ---

    

    

    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getArtist() != null) {
        entity.setArtist(null);
        }
    

    

    

    
        if (entity.getMusicLabel() != null) {
        entity.setMusicLabel(null);
        }
    


repository.delete(entity);
return true;
}
}