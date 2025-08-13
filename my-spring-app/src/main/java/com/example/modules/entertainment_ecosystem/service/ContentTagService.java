package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ContentTag;
import com.example.modules.entertainment_ecosystem.repository.ContentTagRepository;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;
import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import com.example.modules.entertainment_ecosystem.repository.LiveEventRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class ContentTagService extends BaseService<ContentTag> {

    protected final ContentTagRepository contenttagRepository;
    private final MovieRepository moviesRepository;
    private final TVShowRepository tvShowsRepository;
    private final VideoGameRepository videoGamesRepository;
    private final LiveEventRepository liveEventsRepository;

    public ContentTagService(ContentTagRepository repository,MovieRepository moviesRepository,TVShowRepository tvShowsRepository,VideoGameRepository videoGamesRepository,LiveEventRepository liveEventsRepository)
    {
        super(repository);
        this.contenttagRepository = repository;
        this.moviesRepository = moviesRepository;
        this.tvShowsRepository = tvShowsRepository;
        this.videoGamesRepository = videoGamesRepository;
        this.liveEventsRepository = liveEventsRepository;
    }

    @Override
    public ContentTag save(ContentTag contenttag) {

        return contenttagRepository.save(contenttag);
    }


    public ContentTag update(Long id, ContentTag contenttagRequest) {
        ContentTag existing = contenttagRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ContentTag not found"));

    // Copier les champs simples
        existing.setName(contenttagRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

        if (contenttagRequest.getMovies() != null) {
            existing.getMovies().clear();
            List<Movie> moviesList = contenttagRequest.getMovies().stream()
                .map(item -> moviesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Movie not found")))
                .collect(Collectors.toList());
        existing.getMovies().addAll(moviesList);
        }

        if (contenttagRequest.getTvShows() != null) {
            existing.getTvShows().clear();
            List<TVShow> tvShowsList = contenttagRequest.getTvShows().stream()
                .map(item -> tvShowsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("TVShow not found")))
                .collect(Collectors.toList());
        existing.getTvShows().addAll(tvShowsList);
        }

        if (contenttagRequest.getVideoGames() != null) {
            existing.getVideoGames().clear();
            List<VideoGame> videoGamesList = contenttagRequest.getVideoGames().stream()
                .map(item -> videoGamesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("VideoGame not found")))
                .collect(Collectors.toList());
        existing.getVideoGames().addAll(videoGamesList);
        }

        if (contenttagRequest.getLiveEvents() != null) {
            existing.getLiveEvents().clear();
            List<LiveEvent> liveEventsList = contenttagRequest.getLiveEvents().stream()
                .map(item -> liveEventsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("LiveEvent not found")))
                .collect(Collectors.toList());
        existing.getLiveEvents().addAll(liveEventsList);
        }

// Relations OneToMany : synchronisation sécurisée

    

    

    

    


        return contenttagRepository.save(existing);
    }
}