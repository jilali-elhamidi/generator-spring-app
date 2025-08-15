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
    private final GenreRepository genreRepository;
    private final UserProfileRepository listenedByUsersRepository;
    private final PlaylistItemRepository playlistItemsRepository;
    private final DigitalPurchaseRepository purchasesRepository;
    private final MusicFormatRepository formatsRepository;
    private final StreamingContentLicenseRepository streamingLicensesRepository;
    private final ContentProviderRepository providerRepository;

    public MusicTrackService(MusicTrackRepository repository,AlbumRepository albumRepository,ArtistRepository artistRepository,GenreRepository genreRepository,UserProfileRepository listenedByUsersRepository,PlaylistItemRepository playlistItemsRepository,DigitalPurchaseRepository purchasesRepository,MusicFormatRepository formatsRepository,StreamingContentLicenseRepository streamingLicensesRepository,ContentProviderRepository providerRepository)
    {
        super(repository);
        this.musictrackRepository = repository;
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.genreRepository = genreRepository;
        this.listenedByUsersRepository = listenedByUsersRepository;
        this.playlistItemsRepository = playlistItemsRepository;
        this.purchasesRepository = purchasesRepository;
        this.formatsRepository = formatsRepository;
        this.streamingLicensesRepository = streamingLicensesRepository;
        this.providerRepository = providerRepository;
    }

    @Override
    public MusicTrack save(MusicTrack musictrack) {


    

    

    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (musictrack.getPlaylistItems() != null) {
            List<PlaylistItem> managedPlaylistItems = new ArrayList<>();
            for (PlaylistItem item : musictrack.getPlaylistItems()) {
            if (item.getId() != null) {
            PlaylistItem existingItem = playlistItemsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("PlaylistItem not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setTrack(musictrack);
            managedPlaylistItems.add(existingItem);
            } else {
            item.setTrack(musictrack);
            managedPlaylistItems.add(item);
            }
            }
            musictrack.setPlaylistItems(managedPlaylistItems);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (musictrack.getPurchases() != null) {
            List<DigitalPurchase> managedPurchases = new ArrayList<>();
            for (DigitalPurchase item : musictrack.getPurchases()) {
            if (item.getId() != null) {
            DigitalPurchase existingItem = purchasesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("DigitalPurchase not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setMusicTrack(musictrack);
            managedPurchases.add(existingItem);
            } else {
            item.setMusicTrack(musictrack);
            managedPurchases.add(item);
            }
            }
            musictrack.setPurchases(managedPurchases);
            }
        
    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (musictrack.getStreamingLicenses() != null) {
            List<StreamingContentLicense> managedStreamingLicenses = new ArrayList<>();
            for (StreamingContentLicense item : musictrack.getStreamingLicenses()) {
            if (item.getId() != null) {
            StreamingContentLicense existingItem = streamingLicensesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("StreamingContentLicense not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setMusicTrack(musictrack);
            managedStreamingLicenses.add(existingItem);
            } else {
            item.setMusicTrack(musictrack);
            managedStreamingLicenses.add(item);
            }
            }
            musictrack.setStreamingLicenses(managedStreamingLicenses);
            }
        
    

    

    if (musictrack.getAlbum() != null
        && musictrack.getAlbum().getId() != null) {
        Album existingAlbum = albumRepository.findById(
        musictrack.getAlbum().getId()
        ).orElseThrow(() -> new RuntimeException("Album not found"));
        musictrack.setAlbum(existingAlbum);
        }
    
    if (musictrack.getArtist() != null
        && musictrack.getArtist().getId() != null) {
        Artist existingArtist = artistRepository.findById(
        musictrack.getArtist().getId()
        ).orElseThrow(() -> new RuntimeException("Artist not found"));
        musictrack.setArtist(existingArtist);
        }
    
    if (musictrack.getGenre() != null
        && musictrack.getGenre().getId() != null) {
        Genre existingGenre = genreRepository.findById(
        musictrack.getGenre().getId()
        ).orElseThrow(() -> new RuntimeException("Genre not found"));
        musictrack.setGenre(existingGenre);
        }
    
    
    
    
    
    
    if (musictrack.getProvider() != null
        && musictrack.getProvider().getId() != null) {
        ContentProvider existingProvider = providerRepository.findById(
        musictrack.getProvider().getId()
        ).orElseThrow(() -> new RuntimeException("ContentProvider not found"));
        musictrack.setProvider(existingProvider);
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
        if (musictrackRequest.getGenre() != null &&
        musictrackRequest.getGenre().getId() != null) {

        Genre existingGenre = genreRepository.findById(
        musictrackRequest.getGenre().getId()
        ).orElseThrow(() -> new RuntimeException("Genre not found"));

        existing.setGenre(existingGenre);
        } else {
        existing.setGenre(null);
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
        // Vider la collection existante
        existing.getPlaylistItems().clear();

        if (musictrackRequest.getPlaylistItems() != null) {
        for (var item : musictrackRequest.getPlaylistItems()) {
        PlaylistItem existingItem;
        if (item.getId() != null) {
        existingItem = playlistItemsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("PlaylistItem not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setTrack(existing);

        // Ajouter directement dans la collection existante
        existing.getPlaylistItems().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getPurchases().clear();

        if (musictrackRequest.getPurchases() != null) {
        for (var item : musictrackRequest.getPurchases()) {
        DigitalPurchase existingItem;
        if (item.getId() != null) {
        existingItem = purchasesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("DigitalPurchase not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setMusicTrack(existing);

        // Ajouter directement dans la collection existante
        existing.getPurchases().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getStreamingLicenses().clear();

        if (musictrackRequest.getStreamingLicenses() != null) {
        for (var item : musictrackRequest.getStreamingLicenses()) {
        StreamingContentLicense existingItem;
        if (item.getId() != null) {
        existingItem = streamingLicensesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("StreamingContentLicense not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setMusicTrack(existing);

        // Ajouter directement dans la collection existante
        existing.getStreamingLicenses().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

    

    

    

    

    

    

    


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
    

    


// --- Dissocier ManyToMany ---

    

    

    

    
        if (entity.getListenedByUsers() != null) {
        entity.getListenedByUsers().clear();
        }
    

    

    

    
        if (entity.getFormats() != null) {
        entity.getFormats().clear();
        }
    

    

    


// --- Dissocier OneToOne ---

    

    

    

    

    

    

    

    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getAlbum() != null) {
        entity.setAlbum(null);
        }
    

    
        if (entity.getArtist() != null) {
        entity.setArtist(null);
        }
    

    
        if (entity.getGenre() != null) {
        entity.setGenre(null);
        }
    

    

    

    

    

    

    
        if (entity.getProvider() != null) {
        entity.setProvider(null);
        }
    


repository.delete(entity);
return true;
}
}