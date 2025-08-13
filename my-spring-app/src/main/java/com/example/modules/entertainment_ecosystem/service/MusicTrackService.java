package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import com.example.modules.entertainment_ecosystem.repository.MusicTrackRepository;
import com.example.modules.entertainment_ecosystem.model.Album;
import com.example.modules.entertainment_ecosystem.repository.AlbumRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;
import com.example.modules.entertainment_ecosystem.model.Genre;
import com.example.modules.entertainment_ecosystem.repository.GenreRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.PlaylistItem;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MusicTrackService extends BaseService<MusicTrack> {

    protected final MusicTrackRepository musictrackRepository;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final GenreRepository genreRepository;
    private final UserProfileRepository listenedByUsersRepository;

    public MusicTrackService(MusicTrackRepository repository,AlbumRepository albumRepository,ArtistRepository artistRepository,GenreRepository genreRepository,UserProfileRepository listenedByUsersRepository)
    {
        super(repository);
        this.musictrackRepository = repository;
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.genreRepository = genreRepository;
        this.listenedByUsersRepository = listenedByUsersRepository;
    }

    @Override
    public MusicTrack save(MusicTrack musictrack) {

        if (musictrack.getAlbum() != null && musictrack.getAlbum().getId() != null) {
        Album album = albumRepository.findById(musictrack.getAlbum().getId())
                .orElseThrow(() -> new RuntimeException("Album not found"));
        musictrack.setAlbum(album);
        }

        if (musictrack.getArtist() != null && musictrack.getArtist().getId() != null) {
        Artist artist = artistRepository.findById(musictrack.getArtist().getId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        musictrack.setArtist(artist);
        }

        if (musictrack.getGenre() != null && musictrack.getGenre().getId() != null) {
        Genre genre = genreRepository.findById(musictrack.getGenre().getId())
                .orElseThrow(() -> new RuntimeException("Genre not found"));
        musictrack.setGenre(genre);
        }

        if (musictrack.getPlaylistItems() != null) {
            for (PlaylistItem item : musictrack.getPlaylistItems()) {
            item.setTrack(musictrack);
            }
        }

        return musictrackRepository.save(musictrack);
    }


    public MusicTrack update(Long id, MusicTrack musictrackRequest) {
        MusicTrack existing = musictrackRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MusicTrack not found"));

    // Copier les champs simples
        existing.setTitle(musictrackRequest.getTitle());
        existing.setDurationSeconds(musictrackRequest.getDurationSeconds());
        existing.setReleaseDate(musictrackRequest.getReleaseDate());

// Relations ManyToOne : mise à jour conditionnelle

        if (musictrackRequest.getAlbum() != null && musictrackRequest.getAlbum().getId() != null) {
        Album album = albumRepository.findById(musictrackRequest.getAlbum().getId())
                .orElseThrow(() -> new RuntimeException("Album not found"));
        existing.setAlbum(album);
        }

        if (musictrackRequest.getArtist() != null && musictrackRequest.getArtist().getId() != null) {
        Artist artist = artistRepository.findById(musictrackRequest.getArtist().getId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        existing.setArtist(artist);
        }

        if (musictrackRequest.getGenre() != null && musictrackRequest.getGenre().getId() != null) {
        Genre genre = genreRepository.findById(musictrackRequest.getGenre().getId())
                .orElseThrow(() -> new RuntimeException("Genre not found"));
        existing.setGenre(genre);
        }

// Relations ManyToMany : synchronisation sécurisée

        if (musictrackRequest.getListenedByUsers() != null) {
            existing.getListenedByUsers().clear();
            List<UserProfile> listenedByUsersList = musictrackRequest.getListenedByUsers().stream()
                .map(item -> listenedByUsersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());
        existing.getListenedByUsers().addAll(listenedByUsersList);
        }

// Relations OneToMany : synchronisation sécurisée

        existing.getPlaylistItems().clear();
        if (musictrackRequest.getPlaylistItems() != null) {
            for (var item : musictrackRequest.getPlaylistItems()) {
            item.setTrack(existing);
            existing.getPlaylistItems().add(item);
            }
        }

    

    

    

    

    


        return musictrackRepository.save(existing);
    }
}