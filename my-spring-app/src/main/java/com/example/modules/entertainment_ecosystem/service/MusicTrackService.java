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
import com.example.modules.entertainment_ecosystem.repository.PlaylistItemRepository;
import com.example.modules.entertainment_ecosystem.model.DigitalPurchase;
import com.example.modules.entertainment_ecosystem.repository.DigitalPurchaseRepository;
import com.example.modules.entertainment_ecosystem.model.MusicFormat;
import com.example.modules.entertainment_ecosystem.repository.MusicFormatRepository;
import com.example.modules.entertainment_ecosystem.model.StreamingContentLicense;
import com.example.modules.entertainment_ecosystem.repository.StreamingContentLicenseRepository;
import com.example.modules.entertainment_ecosystem.model.ContentProvider;
import com.example.modules.entertainment_ecosystem.repository.ContentProviderRepository;
import com.example.modules.entertainment_ecosystem.model.MovieSoundtrack;
import com.example.modules.entertainment_ecosystem.repository.MovieSoundtrackRepository;
import com.example.modules.entertainment_ecosystem.model.MusicVideo;
import com.example.modules.entertainment_ecosystem.repository.MusicVideoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MusicTrackService extends BaseService<MusicTrack> {

    protected final MusicTrackRepository musictrackRepository;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final GenreRepository genresRepository;
    private final UserProfileRepository listenedByUsersRepository;
    private final PlaylistItemRepository playlistItemsRepository;
    private final DigitalPurchaseRepository purchasesRepository;
    private final MusicFormatRepository formatsRepository;
    private final StreamingContentLicenseRepository streamingLicensesRepository;
    private final ContentProviderRepository providerRepository;
    private final MovieSoundtrackRepository soundtrackRepository;
    private final MusicVideoRepository musicVideosRepository;

    public MusicTrackService(MusicTrackRepository repository, AlbumRepository albumRepository, ArtistRepository artistRepository, GenreRepository genresRepository, UserProfileRepository listenedByUsersRepository, PlaylistItemRepository playlistItemsRepository, DigitalPurchaseRepository purchasesRepository, MusicFormatRepository formatsRepository, StreamingContentLicenseRepository streamingLicensesRepository, ContentProviderRepository providerRepository, MovieSoundtrackRepository soundtrackRepository, MusicVideoRepository musicVideosRepository)
    {
        super(repository);
        this.musictrackRepository = repository;
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.genresRepository = genresRepository;
        this.listenedByUsersRepository = listenedByUsersRepository;
        this.playlistItemsRepository = playlistItemsRepository;
        this.purchasesRepository = purchasesRepository;
        this.formatsRepository = formatsRepository;
        this.streamingLicensesRepository = streamingLicensesRepository;
        this.providerRepository = providerRepository;
        this.soundtrackRepository = soundtrackRepository;
        this.musicVideosRepository = musicVideosRepository;
    }

    @Override
    public MusicTrack save(MusicTrack musictrack) {
    // ---------- OneToMany ----------
        if (musictrack.getPlaylistItems() != null) {
            List<PlaylistItem> managedPlaylistItems = new ArrayList<>();
            for (PlaylistItem item : musictrack.getPlaylistItems()) {
                if (item.getId() != null) {
                    PlaylistItem existingItem = playlistItemsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("PlaylistItem not found"));

                     existingItem.setTrack(musictrack);
                     managedPlaylistItems.add(existingItem);
                } else {
                    item.setTrack(musictrack);
                    managedPlaylistItems.add(item);
                }
            }
            musictrack.setPlaylistItems(managedPlaylistItems);
        }
    
        if (musictrack.getPurchases() != null) {
            List<DigitalPurchase> managedPurchases = new ArrayList<>();
            for (DigitalPurchase item : musictrack.getPurchases()) {
                if (item.getId() != null) {
                    DigitalPurchase existingItem = purchasesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("DigitalPurchase not found"));

                     existingItem.setMusicTrack(musictrack);
                     managedPurchases.add(existingItem);
                } else {
                    item.setMusicTrack(musictrack);
                    managedPurchases.add(item);
                }
            }
            musictrack.setPurchases(managedPurchases);
        }
    
        if (musictrack.getStreamingLicenses() != null) {
            List<StreamingContentLicense> managedStreamingLicenses = new ArrayList<>();
            for (StreamingContentLicense item : musictrack.getStreamingLicenses()) {
                if (item.getId() != null) {
                    StreamingContentLicense existingItem = streamingLicensesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("StreamingContentLicense not found"));

                     existingItem.setMusicTrack(musictrack);
                     managedStreamingLicenses.add(existingItem);
                } else {
                    item.setMusicTrack(musictrack);
                    managedStreamingLicenses.add(item);
                }
            }
            musictrack.setStreamingLicenses(managedStreamingLicenses);
        }
    
        if (musictrack.getMusicVideos() != null) {
            List<MusicVideo> managedMusicVideos = new ArrayList<>();
            for (MusicVideo item : musictrack.getMusicVideos()) {
                if (item.getId() != null) {
                    MusicVideo existingItem = musicVideosRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MusicVideo not found"));

                     existingItem.setMusicTrack(musictrack);
                     managedMusicVideos.add(existingItem);
                } else {
                    item.setMusicTrack(musictrack);
                    managedMusicVideos.add(item);
                }
            }
            musictrack.setMusicVideos(managedMusicVideos);
        }
    
    // ---------- ManyToMany ----------
        if (musictrack.getGenres() != null &&
            !musictrack.getGenres().isEmpty()) {

            List<Genre> attachedGenres = musictrack.getGenres().stream()
            .map(item -> genresRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("Genre not found with id " + item.getId())))
            .toList();

            musictrack.setGenres(attachedGenres);

            // côté propriétaire (Genre → MusicTrack)
            attachedGenres.forEach(it -> it.getMusicTracks().add(musictrack));
        }
        
        if (musictrack.getListenedByUsers() != null &&
            !musictrack.getListenedByUsers().isEmpty()) {

            List<UserProfile> attachedListenedByUsers = musictrack.getListenedByUsers().stream()
            .map(item -> listenedByUsersRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found with id " + item.getId())))
            .toList();

            musictrack.setListenedByUsers(attachedListenedByUsers);

            // côté propriétaire (UserProfile → MusicTrack)
            attachedListenedByUsers.forEach(it -> it.getListenedMusic().add(musictrack));
        }
        
        if (musictrack.getFormats() != null &&
            !musictrack.getFormats().isEmpty()) {

            List<MusicFormat> attachedFormats = musictrack.getFormats().stream()
            .map(item -> formatsRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("MusicFormat not found with id " + item.getId())))
            .toList();

            musictrack.setFormats(attachedFormats);

            // côté propriétaire (MusicFormat → MusicTrack)
            attachedFormats.forEach(it -> it.getMusicTracks().add(musictrack));
        }
        
    // ---------- ManyToOne ----------
        if (musictrack.getAlbum() != null &&
            musictrack.getAlbum().getId() != null) {

            Album existingAlbum = albumRepository.findById(
                musictrack.getAlbum().getId()
            ).orElseThrow(() -> new RuntimeException("Album not found"));

            musictrack.setAlbum(existingAlbum);
        }
        
        if (musictrack.getArtist() != null &&
            musictrack.getArtist().getId() != null) {

            Artist existingArtist = artistRepository.findById(
                musictrack.getArtist().getId()
            ).orElseThrow(() -> new RuntimeException("Artist not found"));

            musictrack.setArtist(existingArtist);
        }
        
        if (musictrack.getProvider() != null &&
            musictrack.getProvider().getId() != null) {

            ContentProvider existingProvider = providerRepository.findById(
                musictrack.getProvider().getId()
            ).orElseThrow(() -> new RuntimeException("ContentProvider not found"));

            musictrack.setProvider(existingProvider);
        }
        
        if (musictrack.getSoundtrack() != null &&
            musictrack.getSoundtrack().getId() != null) {

            MovieSoundtrack existingSoundtrack = soundtrackRepository.findById(
                musictrack.getSoundtrack().getId()
            ).orElseThrow(() -> new RuntimeException("MovieSoundtrack not found"));

            musictrack.setSoundtrack(existingSoundtrack);
        }
        
    // ---------- OneToOne ----------

    return musictrackRepository.save(musictrack);
}


    public MusicTrack update(Long id, MusicTrack musictrackRequest) {
        MusicTrack existing = musictrackRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MusicTrack not found"));

    // Copier les champs simples
        existing.setTitle(musictrackRequest.getTitle());
        existing.setDurationSeconds(musictrackRequest.getDurationSeconds());
        existing.setReleaseDate(musictrackRequest.getReleaseDate());

    // ---------- Relations ManyToOne ----------
        if (musictrackRequest.getAlbum() != null &&
            musictrackRequest.getAlbum().getId() != null) {

            Album existingAlbum = albumRepository.findById(
                musictrackRequest.getAlbum().getId()
            ).orElseThrow(() -> new RuntimeException("Album not found"));

            existing.setAlbum(existingAlbum);
        } else {
            existing.setAlbum(null);
        }
        
        if (musictrackRequest.getArtist() != null &&
            musictrackRequest.getArtist().getId() != null) {

            Artist existingArtist = artistRepository.findById(
                musictrackRequest.getArtist().getId()
            ).orElseThrow(() -> new RuntimeException("Artist not found"));

            existing.setArtist(existingArtist);
        } else {
            existing.setArtist(null);
        }
        
        if (musictrackRequest.getProvider() != null &&
            musictrackRequest.getProvider().getId() != null) {

            ContentProvider existingProvider = providerRepository.findById(
                musictrackRequest.getProvider().getId()
            ).orElseThrow(() -> new RuntimeException("ContentProvider not found"));

            existing.setProvider(existingProvider);
        } else {
            existing.setProvider(null);
        }
        
        if (musictrackRequest.getSoundtrack() != null &&
            musictrackRequest.getSoundtrack().getId() != null) {

            MovieSoundtrack existingSoundtrack = soundtrackRepository.findById(
                musictrackRequest.getSoundtrack().getId()
            ).orElseThrow(() -> new RuntimeException("MovieSoundtrack not found"));

            existing.setSoundtrack(existingSoundtrack);
        } else {
            existing.setSoundtrack(null);
        }
        
    // ---------- Relations ManyToOne ----------
        if (musictrackRequest.getGenres() != null) {
            existing.getGenres().clear();

            List<Genre> genresList = musictrackRequest.getGenres().stream()
                .map(item -> genresRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Genre not found")))
                .collect(Collectors.toList());

            existing.getGenres().addAll(genresList);

            // Mettre à jour le côté inverse
            genresList.forEach(it -> {
                if (!it.getMusicTracks().contains(existing)) {
                    it.getMusicTracks().add(existing);
                }
            });
        }
        
        if (musictrackRequest.getListenedByUsers() != null) {
            existing.getListenedByUsers().clear();

            List<UserProfile> listenedByUsersList = musictrackRequest.getListenedByUsers().stream()
                .map(item -> listenedByUsersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());

            existing.getListenedByUsers().addAll(listenedByUsersList);

            // Mettre à jour le côté inverse
            listenedByUsersList.forEach(it -> {
                if (!it.getListenedMusic().contains(existing)) {
                    it.getListenedMusic().add(existing);
                }
            });
        }
        
        if (musictrackRequest.getFormats() != null) {
            existing.getFormats().clear();

            List<MusicFormat> formatsList = musictrackRequest.getFormats().stream()
                .map(item -> formatsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("MusicFormat not found")))
                .collect(Collectors.toList());

            existing.getFormats().addAll(formatsList);

            // Mettre à jour le côté inverse
            formatsList.forEach(it -> {
                if (!it.getMusicTracks().contains(existing)) {
                    it.getMusicTracks().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
        existing.getPlaylistItems().clear();

        if (musictrackRequest.getPlaylistItems() != null) {
            for (var item : musictrackRequest.getPlaylistItems()) {
                PlaylistItem existingItem;
                if (item.getId() != null) {
                    existingItem = playlistItemsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("PlaylistItem not found"));
                } else {
                existingItem = item;
                }

                existingItem.setTrack(existing);
                existing.getPlaylistItems().add(existingItem);
            }
        }
        
        existing.getPurchases().clear();

        if (musictrackRequest.getPurchases() != null) {
            for (var item : musictrackRequest.getPurchases()) {
                DigitalPurchase existingItem;
                if (item.getId() != null) {
                    existingItem = purchasesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("DigitalPurchase not found"));
                } else {
                existingItem = item;
                }

                existingItem.setMusicTrack(existing);
                existing.getPurchases().add(existingItem);
            }
        }
        
        existing.getStreamingLicenses().clear();

        if (musictrackRequest.getStreamingLicenses() != null) {
            for (var item : musictrackRequest.getStreamingLicenses()) {
                StreamingContentLicense existingItem;
                if (item.getId() != null) {
                    existingItem = streamingLicensesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("StreamingContentLicense not found"));
                } else {
                existingItem = item;
                }

                existingItem.setMusicTrack(existing);
                existing.getStreamingLicenses().add(existingItem);
            }
        }
        
        existing.getMusicVideos().clear();

        if (musictrackRequest.getMusicVideos() != null) {
            for (var item : musictrackRequest.getMusicVideos()) {
                MusicVideo existingItem;
                if (item.getId() != null) {
                    existingItem = musicVideosRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MusicVideo not found"));
                } else {
                existingItem = item;
                }

                existingItem.setMusicTrack(existing);
                existing.getMusicVideos().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------

    return musictrackRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<MusicTrack> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        MusicTrack entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getPlaylistItems() != null) {
            for (var child : entity.getPlaylistItems()) {
                
                child.setTrack(null); // retirer la référence inverse
                
            }
            entity.getPlaylistItems().clear();
        }
        
        if (entity.getPurchases() != null) {
            for (var child : entity.getPurchases()) {
                
                child.setMusicTrack(null); // retirer la référence inverse
                
            }
            entity.getPurchases().clear();
        }
        
        if (entity.getStreamingLicenses() != null) {
            for (var child : entity.getStreamingLicenses()) {
                
                child.setMusicTrack(null); // retirer la référence inverse
                
            }
            entity.getStreamingLicenses().clear();
        }
        
        if (entity.getMusicVideos() != null) {
            for (var child : entity.getMusicVideos()) {
                
                child.setMusicTrack(null); // retirer la référence inverse
                
            }
            entity.getMusicVideos().clear();
        }
        
    // --- Dissocier ManyToMany ---
        if (entity.getGenres() != null) {
            for (Genre item : new ArrayList<>(entity.getGenres())) {
                
                item.getMusicTracks().remove(entity); // retire côté inverse
                
            }
            entity.getGenres().clear(); // puis vide côté courant
        }
        
        if (entity.getListenedByUsers() != null) {
            for (UserProfile item : new ArrayList<>(entity.getListenedByUsers())) {
                
                item.getListenedMusic().remove(entity); // retire côté inverse
                
            }
            entity.getListenedByUsers().clear(); // puis vide côté courant
        }
        
        if (entity.getFormats() != null) {
            for (MusicFormat item : new ArrayList<>(entity.getFormats())) {
                
                item.getMusicTracks().remove(entity); // retire côté inverse
                
            }
            entity.getFormats().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getAlbum() != null) {
            entity.setAlbum(null);
        }
        
        if (entity.getArtist() != null) {
            entity.setArtist(null);
        }
        
        if (entity.getProvider() != null) {
            entity.setProvider(null);
        }
        
        if (entity.getSoundtrack() != null) {
            entity.setSoundtrack(null);
        }
        
        repository.delete(entity);
        return true;
    }
}