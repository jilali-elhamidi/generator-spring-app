package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Genre;
import com.example.modules.entertainment_ecosystem.repository.GenreRepository;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;
import com.example.modules.entertainment_ecosystem.model.Book;
import com.example.modules.entertainment_ecosystem.repository.BookRepository;
import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import com.example.modules.entertainment_ecosystem.repository.MusicTrackRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;
import com.example.modules.entertainment_ecosystem.model.Podcast;
import com.example.modules.entertainment_ecosystem.repository.PodcastRepository;
import com.example.modules.entertainment_ecosystem.model.Album;
import com.example.modules.entertainment_ecosystem.repository.AlbumRepository;
import com.example.modules.entertainment_ecosystem.model.MusicGenreCategory;
import com.example.modules.entertainment_ecosystem.repository.MusicGenreCategoryRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class GenreService extends BaseService<Genre> {

    protected final GenreRepository genreRepository;
    private final MovieRepository moviesRepository;
    private final TVShowRepository tvShowsRepository;
    private final BookRepository bookGenresRepository;
    private final MusicTrackRepository musicTracksRepository;
    private final UserProfileRepository favoriteUsersRepository;
    private final VideoGameRepository videoGamesRepository;
    private final PodcastRepository podcastsRepository;
    private final AlbumRepository albumsRepository;
    private final MusicGenreCategoryRepository categoryRepository;

    public GenreService(GenreRepository repository,MovieRepository moviesRepository,TVShowRepository tvShowsRepository,BookRepository bookGenresRepository,MusicTrackRepository musicTracksRepository,UserProfileRepository favoriteUsersRepository,VideoGameRepository videoGamesRepository,PodcastRepository podcastsRepository,AlbumRepository albumsRepository,MusicGenreCategoryRepository categoryRepository)
    {
        super(repository);
        this.genreRepository = repository;
        this.moviesRepository = moviesRepository;
        this.tvShowsRepository = tvShowsRepository;
        this.bookGenresRepository = bookGenresRepository;
        this.musicTracksRepository = musicTracksRepository;
        this.favoriteUsersRepository = favoriteUsersRepository;
        this.videoGamesRepository = videoGamesRepository;
        this.podcastsRepository = podcastsRepository;
        this.albumsRepository = albumsRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Genre save(Genre genre) {


    

    

    

    

    

    

    

    

    


    
        if (genre.getMovies() != null
        && !genre.getMovies().isEmpty()) {

        List<Movie> attachedMovies = genre.getMovies().stream()
        .map(item -> moviesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Movie not found with id " + item.getId())))
        .toList();

        genre.setMovies(attachedMovies);

        // côté propriétaire (Movie → Genre)
        attachedMovies.forEach(it -> it.getGenres().add(genre));
        }
    

    
        if (genre.getTvShows() != null
        && !genre.getTvShows().isEmpty()) {

        List<TVShow> attachedTvShows = genre.getTvShows().stream()
        .map(item -> tvShowsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("TVShow not found with id " + item.getId())))
        .toList();

        genre.setTvShows(attachedTvShows);

        // côté propriétaire (TVShow → Genre)
        attachedTvShows.forEach(it -> it.getGenres().add(genre));
        }
    

    
        if (genre.getBookGenres() != null
        && !genre.getBookGenres().isEmpty()) {

        List<Book> attachedBookGenres = genre.getBookGenres().stream()
        .map(item -> bookGenresRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Book not found with id " + item.getId())))
        .toList();

        genre.setBookGenres(attachedBookGenres);

        // côté propriétaire (Book → Genre)
        attachedBookGenres.forEach(it -> it.getGenres().add(genre));
        }
    

    
        if (genre.getMusicTracks() != null
        && !genre.getMusicTracks().isEmpty()) {

        List<MusicTrack> attachedMusicTracks = genre.getMusicTracks().stream()
        .map(item -> musicTracksRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("MusicTrack not found with id " + item.getId())))
        .toList();

        genre.setMusicTracks(attachedMusicTracks);

        // côté propriétaire (MusicTrack → Genre)
        attachedMusicTracks.forEach(it -> it.getGenres().add(genre));
        }
    

    
        if (genre.getFavoriteUsers() != null
        && !genre.getFavoriteUsers().isEmpty()) {

        List<UserProfile> attachedFavoriteUsers = genre.getFavoriteUsers().stream()
        .map(item -> favoriteUsersRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserProfile not found with id " + item.getId())))
        .toList();

        genre.setFavoriteUsers(attachedFavoriteUsers);

        // côté propriétaire (UserProfile → Genre)
        attachedFavoriteUsers.forEach(it -> it.getFavoriteGenres().add(genre));
        }
    

    
        if (genre.getVideoGames() != null
        && !genre.getVideoGames().isEmpty()) {

        List<VideoGame> attachedVideoGames = genre.getVideoGames().stream()
        .map(item -> videoGamesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("VideoGame not found with id " + item.getId())))
        .toList();

        genre.setVideoGames(attachedVideoGames);

        // côté propriétaire (VideoGame → Genre)
        attachedVideoGames.forEach(it -> it.getGenres().add(genre));
        }
    

    
        if (genre.getPodcasts() != null
        && !genre.getPodcasts().isEmpty()) {

        List<Podcast> attachedPodcasts = genre.getPodcasts().stream()
        .map(item -> podcastsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Podcast not found with id " + item.getId())))
        .toList();

        genre.setPodcasts(attachedPodcasts);

        // côté propriétaire (Podcast → Genre)
        attachedPodcasts.forEach(it -> it.getGenres().add(genre));
        }
    

    
        if (genre.getAlbums() != null
        && !genre.getAlbums().isEmpty()) {

        List<Album> attachedAlbums = genre.getAlbums().stream()
        .map(item -> albumsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Album not found with id " + item.getId())))
        .toList();

        genre.setAlbums(attachedAlbums);

        // côté propriétaire (Album → Genre)
        attachedAlbums.forEach(it -> it.getGenres().add(genre));
        }
    

    

    
    
    
    
    
    
    
    
    if (genre.getCategory() != null
        && genre.getCategory().getId() != null) {
        MusicGenreCategory existingCategory = categoryRepository.findById(
        genre.getCategory().getId()
        ).orElseThrow(() -> new RuntimeException("MusicGenreCategory not found"));
        genre.setCategory(existingCategory);
        }
    

        return genreRepository.save(genre);
    }


    public Genre update(Long id, Genre genreRequest) {
        Genre existing = genreRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Genre not found"));

    // Copier les champs simples
        existing.setName(genreRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle
        if (genreRequest.getCategory() != null &&
        genreRequest.getCategory().getId() != null) {

        MusicGenreCategory existingCategory = categoryRepository.findById(
        genreRequest.getCategory().getId()
        ).orElseThrow(() -> new RuntimeException("MusicGenreCategory not found"));

        existing.setCategory(existingCategory);
        } else {
        existing.setCategory(null);
        }

// Relations ManyToMany : synchronisation sécurisée
        if (genreRequest.getMovies() != null) {
        existing.getMovies().clear();

        List<Movie> moviesList = genreRequest.getMovies().stream()
        .map(item -> moviesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Movie not found")))
        .collect(Collectors.toList());

        existing.getMovies().addAll(moviesList);

        // Mettre à jour le côté inverse
        moviesList.forEach(it -> {
        if (!it.getGenres().contains(existing)) {
        it.getGenres().add(existing);
        }
        });
        }
        if (genreRequest.getTvShows() != null) {
        existing.getTvShows().clear();

        List<TVShow> tvShowsList = genreRequest.getTvShows().stream()
        .map(item -> tvShowsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("TVShow not found")))
        .collect(Collectors.toList());

        existing.getTvShows().addAll(tvShowsList);

        // Mettre à jour le côté inverse
        tvShowsList.forEach(it -> {
        if (!it.getGenres().contains(existing)) {
        it.getGenres().add(existing);
        }
        });
        }
        if (genreRequest.getBookGenres() != null) {
        existing.getBookGenres().clear();

        List<Book> bookGenresList = genreRequest.getBookGenres().stream()
        .map(item -> bookGenresRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Book not found")))
        .collect(Collectors.toList());

        existing.getBookGenres().addAll(bookGenresList);

        // Mettre à jour le côté inverse
        bookGenresList.forEach(it -> {
        if (!it.getGenres().contains(existing)) {
        it.getGenres().add(existing);
        }
        });
        }
        if (genreRequest.getMusicTracks() != null) {
        existing.getMusicTracks().clear();

        List<MusicTrack> musicTracksList = genreRequest.getMusicTracks().stream()
        .map(item -> musicTracksRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("MusicTrack not found")))
        .collect(Collectors.toList());

        existing.getMusicTracks().addAll(musicTracksList);

        // Mettre à jour le côté inverse
        musicTracksList.forEach(it -> {
        if (!it.getGenres().contains(existing)) {
        it.getGenres().add(existing);
        }
        });
        }
        if (genreRequest.getFavoriteUsers() != null) {
        existing.getFavoriteUsers().clear();

        List<UserProfile> favoriteUsersList = genreRequest.getFavoriteUsers().stream()
        .map(item -> favoriteUsersRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserProfile not found")))
        .collect(Collectors.toList());

        existing.getFavoriteUsers().addAll(favoriteUsersList);

        // Mettre à jour le côté inverse
        favoriteUsersList.forEach(it -> {
        if (!it.getFavoriteGenres().contains(existing)) {
        it.getFavoriteGenres().add(existing);
        }
        });
        }
        if (genreRequest.getVideoGames() != null) {
        existing.getVideoGames().clear();

        List<VideoGame> videoGamesList = genreRequest.getVideoGames().stream()
        .map(item -> videoGamesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("VideoGame not found")))
        .collect(Collectors.toList());

        existing.getVideoGames().addAll(videoGamesList);

        // Mettre à jour le côté inverse
        videoGamesList.forEach(it -> {
        if (!it.getGenres().contains(existing)) {
        it.getGenres().add(existing);
        }
        });
        }
        if (genreRequest.getPodcasts() != null) {
        existing.getPodcasts().clear();

        List<Podcast> podcastsList = genreRequest.getPodcasts().stream()
        .map(item -> podcastsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Podcast not found")))
        .collect(Collectors.toList());

        existing.getPodcasts().addAll(podcastsList);

        // Mettre à jour le côté inverse
        podcastsList.forEach(it -> {
        if (!it.getGenres().contains(existing)) {
        it.getGenres().add(existing);
        }
        });
        }
        if (genreRequest.getAlbums() != null) {
        existing.getAlbums().clear();

        List<Album> albumsList = genreRequest.getAlbums().stream()
        .map(item -> albumsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Album not found")))
        .collect(Collectors.toList());

        existing.getAlbums().addAll(albumsList);

        // Mettre à jour le côté inverse
        albumsList.forEach(it -> {
        if (!it.getGenres().contains(existing)) {
        it.getGenres().add(existing);
        }
        });
        }

// Relations OneToMany : synchronisation sécurisée

    

    

    

    

    

    

    

    

    


        return genreRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<Genre> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

Genre entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    

    

    

    

    

    

    

    


// --- Dissocier ManyToMany ---

    
        if (entity.getMovies() != null) {
        for (Movie item : new ArrayList<>(entity.getMovies())) {
        
            item.getGenres().remove(entity); // retire côté inverse
        
        }
        entity.getMovies().clear(); // puis vide côté courant
        }
    

    
        if (entity.getTvShows() != null) {
        for (TVShow item : new ArrayList<>(entity.getTvShows())) {
        
            item.getGenres().remove(entity); // retire côté inverse
        
        }
        entity.getTvShows().clear(); // puis vide côté courant
        }
    

    
        if (entity.getBookGenres() != null) {
        for (Book item : new ArrayList<>(entity.getBookGenres())) {
        
            item.getGenres().remove(entity); // retire côté inverse
        
        }
        entity.getBookGenres().clear(); // puis vide côté courant
        }
    

    
        if (entity.getMusicTracks() != null) {
        for (MusicTrack item : new ArrayList<>(entity.getMusicTracks())) {
        
            item.getGenres().remove(entity); // retire côté inverse
        
        }
        entity.getMusicTracks().clear(); // puis vide côté courant
        }
    

    
        if (entity.getFavoriteUsers() != null) {
        for (UserProfile item : new ArrayList<>(entity.getFavoriteUsers())) {
        
            item.getFavoriteGenres().remove(entity); // retire côté inverse
        
        }
        entity.getFavoriteUsers().clear(); // puis vide côté courant
        }
    

    
        if (entity.getVideoGames() != null) {
        for (VideoGame item : new ArrayList<>(entity.getVideoGames())) {
        
            item.getGenres().remove(entity); // retire côté inverse
        
        }
        entity.getVideoGames().clear(); // puis vide côté courant
        }
    

    
        if (entity.getPodcasts() != null) {
        for (Podcast item : new ArrayList<>(entity.getPodcasts())) {
        
            item.getGenres().remove(entity); // retire côté inverse
        
        }
        entity.getPodcasts().clear(); // puis vide côté courant
        }
    

    
        if (entity.getAlbums() != null) {
        for (Album item : new ArrayList<>(entity.getAlbums())) {
        
            item.getGenres().remove(entity); // retire côté inverse
        
        }
        entity.getAlbums().clear(); // puis vide côté courant
        }
    

    



// --- Dissocier OneToOne ---

    

    

    

    

    

    

    

    

    


// --- Dissocier ManyToOne ---

    

    

    

    

    

    

    

    

    
        if (entity.getCategory() != null) {
        entity.setCategory(null);
        }
    


repository.delete(entity);
return true;
}
}