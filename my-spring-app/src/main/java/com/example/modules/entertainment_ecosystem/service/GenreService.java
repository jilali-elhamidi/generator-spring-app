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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class GenreService extends BaseService<Genre> {

    protected final GenreRepository genreRepository;
    
    protected final MovieRepository moviesRepository;
    
    protected final TVShowRepository tvShowsRepository;
    
    protected final BookRepository bookGenresRepository;
    
    protected final MusicTrackRepository musicTracksRepository;
    
    protected final UserProfileRepository favoriteUsersRepository;
    
    protected final VideoGameRepository videoGamesRepository;
    
    protected final PodcastRepository podcastsRepository;
    
    protected final AlbumRepository albumsRepository;
    
    protected final MusicGenreCategoryRepository categoryRepository;
    

    public GenreService(GenreRepository repository, MovieRepository moviesRepository, TVShowRepository tvShowsRepository, BookRepository bookGenresRepository, MusicTrackRepository musicTracksRepository, UserProfileRepository favoriteUsersRepository, VideoGameRepository videoGamesRepository, PodcastRepository podcastsRepository, AlbumRepository albumsRepository, MusicGenreCategoryRepository categoryRepository)
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

    @Transactional
    @Override
    public Genre save(Genre genre) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (genre.getMovies() != null &&
            !genre.getMovies().isEmpty()) {

            List<Movie> attachedMovies = new ArrayList<>();
            for (Movie item : genre.getMovies()) {
                if (item.getId() != null) {
                    Movie existingItem = moviesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Movie not found with id " + item.getId()));
                    attachedMovies.add(existingItem);
                } else {

                    Movie newItem = moviesRepository.save(item);
                    attachedMovies.add(newItem);
                }
            }

            genre.setMovies(attachedMovies);

            // côté propriétaire (Movie → Genre)
            attachedMovies.forEach(it -> it.getGenres().add(genre));
        }
        
        if (genre.getTvShows() != null &&
            !genre.getTvShows().isEmpty()) {

            List<TVShow> attachedTvShows = new ArrayList<>();
            for (TVShow item : genre.getTvShows()) {
                if (item.getId() != null) {
                    TVShow existingItem = tvShowsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("TVShow not found with id " + item.getId()));
                    attachedTvShows.add(existingItem);
                } else {

                    TVShow newItem = tvShowsRepository.save(item);
                    attachedTvShows.add(newItem);
                }
            }

            genre.setTvShows(attachedTvShows);

            // côté propriétaire (TVShow → Genre)
            attachedTvShows.forEach(it -> it.getGenres().add(genre));
        }
        
        if (genre.getBookGenres() != null &&
            !genre.getBookGenres().isEmpty()) {

            List<Book> attachedBookGenres = new ArrayList<>();
            for (Book item : genre.getBookGenres()) {
                if (item.getId() != null) {
                    Book existingItem = bookGenresRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Book not found with id " + item.getId()));
                    attachedBookGenres.add(existingItem);
                } else {

                    Book newItem = bookGenresRepository.save(item);
                    attachedBookGenres.add(newItem);
                }
            }

            genre.setBookGenres(attachedBookGenres);

            // côté propriétaire (Book → Genre)
            attachedBookGenres.forEach(it -> it.getGenres().add(genre));
        }
        
        if (genre.getMusicTracks() != null &&
            !genre.getMusicTracks().isEmpty()) {

            List<MusicTrack> attachedMusicTracks = new ArrayList<>();
            for (MusicTrack item : genre.getMusicTracks()) {
                if (item.getId() != null) {
                    MusicTrack existingItem = musicTracksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MusicTrack not found with id " + item.getId()));
                    attachedMusicTracks.add(existingItem);
                } else {

                    MusicTrack newItem = musicTracksRepository.save(item);
                    attachedMusicTracks.add(newItem);
                }
            }

            genre.setMusicTracks(attachedMusicTracks);

            // côté propriétaire (MusicTrack → Genre)
            attachedMusicTracks.forEach(it -> it.getGenres().add(genre));
        }
        
        if (genre.getFavoriteUsers() != null &&
            !genre.getFavoriteUsers().isEmpty()) {

            List<UserProfile> attachedFavoriteUsers = new ArrayList<>();
            for (UserProfile item : genre.getFavoriteUsers()) {
                if (item.getId() != null) {
                    UserProfile existingItem = favoriteUsersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserProfile not found with id " + item.getId()));
                    attachedFavoriteUsers.add(existingItem);
                } else {

                    UserProfile newItem = favoriteUsersRepository.save(item);
                    attachedFavoriteUsers.add(newItem);
                }
            }

            genre.setFavoriteUsers(attachedFavoriteUsers);

            // côté propriétaire (UserProfile → Genre)
            attachedFavoriteUsers.forEach(it -> it.getFavoriteGenres().add(genre));
        }
        
        if (genre.getVideoGames() != null &&
            !genre.getVideoGames().isEmpty()) {

            List<VideoGame> attachedVideoGames = new ArrayList<>();
            for (VideoGame item : genre.getVideoGames()) {
                if (item.getId() != null) {
                    VideoGame existingItem = videoGamesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("VideoGame not found with id " + item.getId()));
                    attachedVideoGames.add(existingItem);
                } else {

                    VideoGame newItem = videoGamesRepository.save(item);
                    attachedVideoGames.add(newItem);
                }
            }

            genre.setVideoGames(attachedVideoGames);

            // côté propriétaire (VideoGame → Genre)
            attachedVideoGames.forEach(it -> it.getGenres().add(genre));
        }
        
        if (genre.getPodcasts() != null &&
            !genre.getPodcasts().isEmpty()) {

            List<Podcast> attachedPodcasts = new ArrayList<>();
            for (Podcast item : genre.getPodcasts()) {
                if (item.getId() != null) {
                    Podcast existingItem = podcastsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Podcast not found with id " + item.getId()));
                    attachedPodcasts.add(existingItem);
                } else {

                    Podcast newItem = podcastsRepository.save(item);
                    attachedPodcasts.add(newItem);
                }
            }

            genre.setPodcasts(attachedPodcasts);

            // côté propriétaire (Podcast → Genre)
            attachedPodcasts.forEach(it -> it.getGenres().add(genre));
        }
        
        if (genre.getAlbums() != null &&
            !genre.getAlbums().isEmpty()) {

            List<Album> attachedAlbums = new ArrayList<>();
            for (Album item : genre.getAlbums()) {
                if (item.getId() != null) {
                    Album existingItem = albumsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Album not found with id " + item.getId()));
                    attachedAlbums.add(existingItem);
                } else {

                    Album newItem = albumsRepository.save(item);
                    attachedAlbums.add(newItem);
                }
            }

            genre.setAlbums(attachedAlbums);

            // côté propriétaire (Album → Genre)
            attachedAlbums.forEach(it -> it.getGenres().add(genre));
        }
        
    // ---------- ManyToOne ----------
        if (genre.getCategory() != null) {
            if (genre.getCategory().getId() != null) {
                MusicGenreCategory existingCategory = categoryRepository.findById(
                    genre.getCategory().getId()
                ).orElseThrow(() -> new RuntimeException("MusicGenreCategory not found with id "
                    + genre.getCategory().getId()));
                genre.setCategory(existingCategory);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                MusicGenreCategory newCategory = categoryRepository.save(genre.getCategory());
                genre.setCategory(newCategory);
            }
        }
        
    // ---------- OneToOne ----------
    return genreRepository.save(genre);
}

    @Transactional
    @Override
    public Genre update(Long id, Genre genreRequest) {
        Genre existing = genreRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Genre not found"));

    // Copier les champs simples
        existing.setName(genreRequest.getName());

    // ---------- Relations ManyToOne ----------
        if (genreRequest.getCategory() != null &&
            genreRequest.getCategory().getId() != null) {

            MusicGenreCategory existingCategory = categoryRepository.findById(
                genreRequest.getCategory().getId()
            ).orElseThrow(() -> new RuntimeException("MusicGenreCategory not found"));

            existing.setCategory(existingCategory);
        } else {
            existing.setCategory(null);
        }
        
    // ---------- Relations ManyToMany ----------
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
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return genreRepository.save(existing);
}

    // Pagination simple
    public Page<Genre> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Genre> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Genre.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Genre> saveAll(List<Genre> genreList) {
        return super.saveAll(genreList);
    }

}