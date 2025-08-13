package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Album;
import com.example.modules.entertainment_ecosystem.repository.AlbumRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;
import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import com.example.modules.entertainment_ecosystem.model.Genre;
import com.example.modules.entertainment_ecosystem.repository.GenreRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class AlbumService extends BaseService<Album> {

    protected final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final GenreRepository genresRepository;

    public AlbumService(AlbumRepository repository,ArtistRepository artistRepository,GenreRepository genresRepository)
    {
        super(repository);
        this.albumRepository = repository;
        this.artistRepository = artistRepository;
        this.genresRepository = genresRepository;
    }

    @Override
    public Album save(Album album) {

        if (album.getArtist() != null && album.getArtist().getId() != null) {
        Artist artist = artistRepository.findById(album.getArtist().getId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        album.setArtist(artist);
        }

        if (album.getTracks() != null) {
            for (MusicTrack item : album.getTracks()) {
            item.setAlbum(album);
            }
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

        if (albumRequest.getArtist() != null && albumRequest.getArtist().getId() != null) {
        Artist artist = artistRepository.findById(albumRequest.getArtist().getId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        existing.setArtist(artist);
        }

// Relations ManyToMany : synchronisation sécurisée

        if (albumRequest.getGenres() != null) {
            existing.getGenres().clear();
            List<Genre> genresList = albumRequest.getGenres().stream()
                .map(item -> genresRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Genre not found")))
                .collect(Collectors.toList());
        existing.getGenres().addAll(genresList);
        }

// Relations OneToMany : synchronisation sécurisée

        existing.getTracks().clear();
        if (albumRequest.getTracks() != null) {
            for (var item : albumRequest.getTracks()) {
            item.setAlbum(existing);
            existing.getTracks().add(item);
            }
        }

    

    

    


        return albumRepository.save(existing);
    }
}