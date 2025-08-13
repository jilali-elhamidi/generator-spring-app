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
import com.example.modules.entertainment_ecosystem.model.DigitalPurchase;
import com.example.modules.entertainment_ecosystem.model.MusicFormat;
import com.example.modules.entertainment_ecosystem.repository.MusicFormatRepository;
import com.example.modules.entertainment_ecosystem.model.StreamingContentLicense;
import com.example.modules.entertainment_ecosystem.model.ContentProvider;
import com.example.modules.entertainment_ecosystem.repository.ContentProviderRepository;

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
    private final MusicFormatRepository formatsRepository;
    private final ContentProviderRepository providerRepository;

    public MusicTrackService(MusicTrackRepository repository,AlbumRepository albumRepository,ArtistRepository artistRepository,GenreRepository genreRepository,UserProfileRepository listenedByUsersRepository,MusicFormatRepository formatsRepository,ContentProviderRepository providerRepository)
    {
        super(repository);
        this.musictrackRepository = repository;
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.genreRepository = genreRepository;
        this.listenedByUsersRepository = listenedByUsersRepository;
        this.formatsRepository = formatsRepository;
        this.providerRepository = providerRepository;
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

        if (musictrack.getProvider() != null && musictrack.getProvider().getId() != null) {
        ContentProvider provider = providerRepository.findById(musictrack.getProvider().getId())
                .orElseThrow(() -> new RuntimeException("ContentProvider not found"));
        musictrack.setProvider(provider);
        }

        if (musictrack.getPlaylistItems() != null) {
            for (PlaylistItem item : musictrack.getPlaylistItems()) {
            item.setTrack(musictrack);
            }
        }

        if (musictrack.getPurchases() != null) {
            for (DigitalPurchase item : musictrack.getPurchases()) {
            item.setMusicTrack(musictrack);
            }
        }

        if (musictrack.getStreamingLicenses() != null) {
            for (StreamingContentLicense item : musictrack.getStreamingLicenses()) {
            item.setMusicTrack(musictrack);
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

        if (musictrackRequest.getProvider() != null && musictrackRequest.getProvider().getId() != null) {
        ContentProvider provider = providerRepository.findById(musictrackRequest.getProvider().getId())
                .orElseThrow(() -> new RuntimeException("ContentProvider not found"));
        existing.setProvider(provider);
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

        if (musictrackRequest.getFormats() != null) {
            existing.getFormats().clear();
            List<MusicFormat> formatsList = musictrackRequest.getFormats().stream()
                .map(item -> formatsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("MusicFormat not found")))
                .collect(Collectors.toList());
        existing.getFormats().addAll(formatsList);
        }

// Relations OneToMany : synchronisation sécurisée

        existing.getPlaylistItems().clear();
        if (musictrackRequest.getPlaylistItems() != null) {
            for (var item : musictrackRequest.getPlaylistItems()) {
            item.setTrack(existing);
            existing.getPlaylistItems().add(item);
            }
        }

        existing.getPurchases().clear();
        if (musictrackRequest.getPurchases() != null) {
            for (var item : musictrackRequest.getPurchases()) {
            item.setMusicTrack(existing);
            existing.getPurchases().add(item);
            }
        }

        existing.getStreamingLicenses().clear();
        if (musictrackRequest.getStreamingLicenses() != null) {
            for (var item : musictrackRequest.getStreamingLicenses()) {
            item.setMusicTrack(existing);
            existing.getStreamingLicenses().add(item);
            }
        }

    

    

    

    

    

    

    

    

    


        return musictrackRepository.save(existing);
    }
}