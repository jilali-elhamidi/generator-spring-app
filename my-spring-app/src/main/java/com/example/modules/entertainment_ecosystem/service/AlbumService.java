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

    public AlbumService(AlbumRepository repository, ArtistRepository artistRepository, MusicTrackRepository tracksRepository, GenreRepository genresRepository, MusicLabelRepository musicLabelRepository)
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
    // ---------- OneToMany ----------
        if (album.getTracks() != null) {
            List<MusicTrack> managedTracks = new ArrayList<>();
            for (MusicTrack item : album.getTracks()) {
                if (item.getId() != null) {
                    MusicTrack existingItem = tracksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MusicTrack not found"));

                     existingItem.setAlbum(album);
                     managedTracks.add(existingItem);
                } else {
                    item.setAlbum(album);
                    managedTracks.add(item);
                }
            }
            album.setTracks(managedTracks);
        }
    
    // ---------- ManyToMany ----------
        if (album.getGenres() != null &&
            !album.getGenres().isEmpty()) {

            List<Genre> attachedGenres = new ArrayList<>();
            for (Genre item : album.getGenres()) {
                if (item.getId() != null) {
                    Genre existingItem = genresRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Genre not found with id " + item.getId()));
                    attachedGenres.add(existingItem);
                } else {

                    Genre newItem = genresRepository.save(item);
                    attachedGenres.add(newItem);
                }
            }

            album.setGenres(attachedGenres);

            // côté propriétaire (Genre → Album)
            attachedGenres.forEach(it -> it.getAlbums().add(album));
        }
        
    // ---------- ManyToOne ----------
        if (album.getArtist() != null) {
            if (album.getArtist().getId() != null) {
                Artist existingArtist = artistRepository.findById(
                    album.getArtist().getId()
                ).orElseThrow(() -> new RuntimeException("Artist not found with id "
                    + album.getArtist().getId()));
                album.setArtist(existingArtist);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Artist newArtist = artistRepository.save(album.getArtist());
                album.setArtist(newArtist);
            }
        }
        
        if (album.getMusicLabel() != null) {
            if (album.getMusicLabel().getId() != null) {
                MusicLabel existingMusicLabel = musicLabelRepository.findById(
                    album.getMusicLabel().getId()
                ).orElseThrow(() -> new RuntimeException("MusicLabel not found with id "
                    + album.getMusicLabel().getId()));
                album.setMusicLabel(existingMusicLabel);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                MusicLabel newMusicLabel = musicLabelRepository.save(album.getMusicLabel());
                album.setMusicLabel(newMusicLabel);
            }
        }
        
    // ---------- OneToOne ----------
    return albumRepository.save(album);
}


    public Album update(Long id, Album albumRequest) {
        Album existing = albumRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Album not found"));

    // Copier les champs simples
        existing.setTitle(albumRequest.getTitle());
        existing.setReleaseDate(albumRequest.getReleaseDate());

    // ---------- Relations ManyToOne ----------
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
        
    // ---------- Relations ManyToOne ----------
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
        
    // ---------- Relations OneToMany ----------
        existing.getTracks().clear();

        if (albumRequest.getTracks() != null) {
            for (var item : albumRequest.getTracks()) {
                MusicTrack existingItem;
                if (item.getId() != null) {
                    existingItem = tracksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MusicTrack not found"));
                } else {
                existingItem = item;
                }

                existingItem.setAlbum(existing);
                existing.getTracks().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
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
                // retirer la référence inverse
                child.setAlbum(null);
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