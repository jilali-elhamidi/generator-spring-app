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

import org.springframework.stereotype.Service;
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

    public GenreService(GenreRepository repository,MovieRepository moviesRepository,TVShowRepository tvShowsRepository,BookRepository bookGenresRepository,MusicTrackRepository musicTracksRepository,UserProfileRepository favoriteUsersRepository,VideoGameRepository videoGamesRepository,PodcastRepository podcastsRepository)
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
    }

    @Override
    public Genre save(Genre genre) {


    

    

    

    

    

    

    

    
    
    
    
    
    
    

        return genreRepository.save(genre);
    }


    public Genre update(Long id, Genre genreRequest) {
        Genre existing = genreRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Genre not found"));

    // Copier les champs simples
        existing.setName(genreRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

        if (genreRequest.getMovies() != null) {
            existing.getMovies().clear();
            List<Movie> moviesList = genreRequest.getMovies().stream()
                .map(item -> moviesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Movie not found")))
                .collect(Collectors.toList());
        existing.getMovies().addAll(moviesList);
        }

        if (genreRequest.getTvShows() != null) {
            existing.getTvShows().clear();
            List<TVShow> tvShowsList = genreRequest.getTvShows().stream()
                .map(item -> tvShowsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("TVShow not found")))
                .collect(Collectors.toList());
        existing.getTvShows().addAll(tvShowsList);
        }

        if (genreRequest.getBookGenres() != null) {
            existing.getBookGenres().clear();
            List<Book> bookGenresList = genreRequest.getBookGenres().stream()
                .map(item -> bookGenresRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Book not found")))
                .collect(Collectors.toList());
        existing.getBookGenres().addAll(bookGenresList);
        }

        if (genreRequest.getMusicTracks() != null) {
            existing.getMusicTracks().clear();
            List<MusicTrack> musicTracksList = genreRequest.getMusicTracks().stream()
                .map(item -> musicTracksRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("MusicTrack not found")))
                .collect(Collectors.toList());
        existing.getMusicTracks().addAll(musicTracksList);
        }

        if (genreRequest.getFavoriteUsers() != null) {
            existing.getFavoriteUsers().clear();
            List<UserProfile> favoriteUsersList = genreRequest.getFavoriteUsers().stream()
                .map(item -> favoriteUsersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());
        existing.getFavoriteUsers().addAll(favoriteUsersList);
        }

        if (genreRequest.getVideoGames() != null) {
            existing.getVideoGames().clear();
            List<VideoGame> videoGamesList = genreRequest.getVideoGames().stream()
                .map(item -> videoGamesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("VideoGame not found")))
                .collect(Collectors.toList());
        existing.getVideoGames().addAll(videoGamesList);
        }

        if (genreRequest.getPodcasts() != null) {
            existing.getPodcasts().clear();
            List<Podcast> podcastsList = genreRequest.getPodcasts().stream()
                .map(item -> podcastsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Podcast not found")))
                .collect(Collectors.toList());
        existing.getPodcasts().addAll(podcastsList);
        }

// Relations OneToMany : synchronisation sécurisée

    

    

    

    

    

    

    


        return genreRepository.save(existing);
    }


}