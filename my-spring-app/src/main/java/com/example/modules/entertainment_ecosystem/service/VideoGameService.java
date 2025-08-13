package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;
import com.example.modules.entertainment_ecosystem.model.Genre;
import com.example.modules.entertainment_ecosystem.repository.GenreRepository;
import com.example.modules.entertainment_ecosystem.model.Review;
import com.example.modules.entertainment_ecosystem.model.GameReview;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.GameAchievement;
import com.example.modules.entertainment_ecosystem.model.GameSession;
import com.example.modules.entertainment_ecosystem.model.DevelopmentStudio;
import com.example.modules.entertainment_ecosystem.repository.DevelopmentStudioRepository;
import com.example.modules.entertainment_ecosystem.model.DigitalPurchase;
import com.example.modules.entertainment_ecosystem.model.GamePlatform;
import com.example.modules.entertainment_ecosystem.repository.GamePlatformRepository;
import com.example.modules.entertainment_ecosystem.model.ContentRating;
import com.example.modules.entertainment_ecosystem.repository.ContentRatingRepository;
import com.example.modules.entertainment_ecosystem.model.ContentTag;
import com.example.modules.entertainment_ecosystem.repository.ContentTagRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGameRating;
import com.example.modules.entertainment_ecosystem.model.GamePlaySession;
import com.example.modules.entertainment_ecosystem.model.GameReviewUpvote;
import com.example.modules.entertainment_ecosystem.model.GameReviewDownvote;
import com.example.modules.entertainment_ecosystem.model.ContentRatingBoard;
import com.example.modules.entertainment_ecosystem.repository.ContentRatingBoardRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class VideoGameService extends BaseService<VideoGame> {

    protected final VideoGameRepository videogameRepository;
    private final GenreRepository genresRepository;
    private final UserProfileRepository playedByRepository;
    private final DevelopmentStudioRepository developerStudioRepository;
    private final GamePlatformRepository platformsRepository;
    private final ContentRatingRepository contentRatingRepository;
    private final ContentTagRepository tagsRepository;
    private final ContentRatingBoardRepository contentRatingBoardRepository;

    public VideoGameService(VideoGameRepository repository,GenreRepository genresRepository,UserProfileRepository playedByRepository,DevelopmentStudioRepository developerStudioRepository,GamePlatformRepository platformsRepository,ContentRatingRepository contentRatingRepository,ContentTagRepository tagsRepository,ContentRatingBoardRepository contentRatingBoardRepository)
    {
        super(repository);
        this.videogameRepository = repository;
        this.genresRepository = genresRepository;
        this.playedByRepository = playedByRepository;
        this.developerStudioRepository = developerStudioRepository;
        this.platformsRepository = platformsRepository;
        this.contentRatingRepository = contentRatingRepository;
        this.tagsRepository = tagsRepository;
        this.contentRatingBoardRepository = contentRatingBoardRepository;
    }

    @Override
    public VideoGame save(VideoGame videogame) {

        if (videogame.getDeveloperStudio() != null && videogame.getDeveloperStudio().getId() != null) {
        DevelopmentStudio developerStudio = developerStudioRepository.findById(videogame.getDeveloperStudio().getId())
                .orElseThrow(() -> new RuntimeException("DevelopmentStudio not found"));
        videogame.setDeveloperStudio(developerStudio);
        }

        if (videogame.getContentRating() != null && videogame.getContentRating().getId() != null) {
        ContentRating contentRating = contentRatingRepository.findById(videogame.getContentRating().getId())
                .orElseThrow(() -> new RuntimeException("ContentRating not found"));
        videogame.setContentRating(contentRating);
        }

        if (videogame.getContentRatingBoard() != null && videogame.getContentRatingBoard().getId() != null) {
        ContentRatingBoard contentRatingBoard = contentRatingBoardRepository.findById(videogame.getContentRatingBoard().getId())
                .orElseThrow(() -> new RuntimeException("ContentRatingBoard not found"));
        videogame.setContentRatingBoard(contentRatingBoard);
        }

        if (videogame.getGeneralReviews() != null) {
            for (Review item : videogame.getGeneralReviews()) {
            item.setVideoGame(videogame);
            }
        }

        if (videogame.getGameReviews() != null) {
            for (GameReview item : videogame.getGameReviews()) {
            item.setGame(videogame);
            }
        }

        if (videogame.getAchievements() != null) {
            for (GameAchievement item : videogame.getAchievements()) {
            item.setGame(videogame);
            }
        }

        if (videogame.getSessions() != null) {
            for (GameSession item : videogame.getSessions()) {
            item.setGame(videogame);
            }
        }

        if (videogame.getPurchases() != null) {
            for (DigitalPurchase item : videogame.getPurchases()) {
            item.setVideoGame(videogame);
            }
        }

        if (videogame.getRatings() != null) {
            for (VideoGameRating item : videogame.getRatings()) {
            item.setGame(videogame);
            }
        }

        if (videogame.getGamePlaySessions() != null) {
            for (GamePlaySession item : videogame.getGamePlaySessions()) {
            item.setGame(videogame);
            }
        }

        if (videogame.getGameReviewUpvotes() != null) {
            for (GameReviewUpvote item : videogame.getGameReviewUpvotes()) {
            item.setGame(videogame);
            }
        }

        if (videogame.getGameReviewDownvotes() != null) {
            for (GameReviewDownvote item : videogame.getGameReviewDownvotes()) {
            item.setGame(videogame);
            }
        }

        return videogameRepository.save(videogame);
    }


    public VideoGame update(Long id, VideoGame videogameRequest) {
        VideoGame existing = videogameRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("VideoGame not found"));

    // Copier les champs simples
        existing.setTitle(videogameRequest.getTitle());
        existing.setReleaseDate(videogameRequest.getReleaseDate());
        existing.setDeveloper(videogameRequest.getDeveloper());
        existing.setPublisher(videogameRequest.getPublisher());
        existing.setPlatform(videogameRequest.getPlatform());

// Relations ManyToOne : mise à jour conditionnelle

        if (videogameRequest.getDeveloperStudio() != null && videogameRequest.getDeveloperStudio().getId() != null) {
        DevelopmentStudio developerStudio = developerStudioRepository.findById(videogameRequest.getDeveloperStudio().getId())
                .orElseThrow(() -> new RuntimeException("DevelopmentStudio not found"));
        existing.setDeveloperStudio(developerStudio);
        }

        if (videogameRequest.getContentRating() != null && videogameRequest.getContentRating().getId() != null) {
        ContentRating contentRating = contentRatingRepository.findById(videogameRequest.getContentRating().getId())
                .orElseThrow(() -> new RuntimeException("ContentRating not found"));
        existing.setContentRating(contentRating);
        }

        if (videogameRequest.getContentRatingBoard() != null && videogameRequest.getContentRatingBoard().getId() != null) {
        ContentRatingBoard contentRatingBoard = contentRatingBoardRepository.findById(videogameRequest.getContentRatingBoard().getId())
                .orElseThrow(() -> new RuntimeException("ContentRatingBoard not found"));
        existing.setContentRatingBoard(contentRatingBoard);
        }

// Relations ManyToMany : synchronisation sécurisée

        if (videogameRequest.getGenres() != null) {
            existing.getGenres().clear();
            List<Genre> genresList = videogameRequest.getGenres().stream()
                .map(item -> genresRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Genre not found")))
                .collect(Collectors.toList());
        existing.getGenres().addAll(genresList);
        }

        if (videogameRequest.getPlayedBy() != null) {
            existing.getPlayedBy().clear();
            List<UserProfile> playedByList = videogameRequest.getPlayedBy().stream()
                .map(item -> playedByRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());
        existing.getPlayedBy().addAll(playedByList);
        }

        if (videogameRequest.getPlatforms() != null) {
            existing.getPlatforms().clear();
            List<GamePlatform> platformsList = videogameRequest.getPlatforms().stream()
                .map(item -> platformsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("GamePlatform not found")))
                .collect(Collectors.toList());
        existing.getPlatforms().addAll(platformsList);
        }

        if (videogameRequest.getTags() != null) {
            existing.getTags().clear();
            List<ContentTag> tagsList = videogameRequest.getTags().stream()
                .map(item -> tagsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("ContentTag not found")))
                .collect(Collectors.toList());
        existing.getTags().addAll(tagsList);
        }

// Relations OneToMany : synchronisation sécurisée

        existing.getGeneralReviews().clear();
        if (videogameRequest.getGeneralReviews() != null) {
            for (var item : videogameRequest.getGeneralReviews()) {
            item.setVideoGame(existing);
            existing.getGeneralReviews().add(item);
            }
        }

        existing.getGameReviews().clear();
        if (videogameRequest.getGameReviews() != null) {
            for (var item : videogameRequest.getGameReviews()) {
            item.setGame(existing);
            existing.getGameReviews().add(item);
            }
        }

        existing.getAchievements().clear();
        if (videogameRequest.getAchievements() != null) {
            for (var item : videogameRequest.getAchievements()) {
            item.setGame(existing);
            existing.getAchievements().add(item);
            }
        }

        existing.getSessions().clear();
        if (videogameRequest.getSessions() != null) {
            for (var item : videogameRequest.getSessions()) {
            item.setGame(existing);
            existing.getSessions().add(item);
            }
        }

        existing.getPurchases().clear();
        if (videogameRequest.getPurchases() != null) {
            for (var item : videogameRequest.getPurchases()) {
            item.setVideoGame(existing);
            existing.getPurchases().add(item);
            }
        }

        existing.getRatings().clear();
        if (videogameRequest.getRatings() != null) {
            for (var item : videogameRequest.getRatings()) {
            item.setGame(existing);
            existing.getRatings().add(item);
            }
        }

        existing.getGamePlaySessions().clear();
        if (videogameRequest.getGamePlaySessions() != null) {
            for (var item : videogameRequest.getGamePlaySessions()) {
            item.setGame(existing);
            existing.getGamePlaySessions().add(item);
            }
        }

        existing.getGameReviewUpvotes().clear();
        if (videogameRequest.getGameReviewUpvotes() != null) {
            for (var item : videogameRequest.getGameReviewUpvotes()) {
            item.setGame(existing);
            existing.getGameReviewUpvotes().add(item);
            }
        }

        existing.getGameReviewDownvotes().clear();
        if (videogameRequest.getGameReviewDownvotes() != null) {
            for (var item : videogameRequest.getGameReviewDownvotes()) {
            item.setGame(existing);
            existing.getGameReviewDownvotes().add(item);
            }
        }

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    


        return videogameRepository.save(existing);
    }
}