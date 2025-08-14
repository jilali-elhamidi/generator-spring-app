package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;
import com.example.modules.entertainment_ecosystem.model.Genre;
import com.example.modules.entertainment_ecosystem.repository.GenreRepository;
import com.example.modules.entertainment_ecosystem.model.Review;
import com.example.modules.entertainment_ecosystem.repository.ReviewRepository;
import com.example.modules.entertainment_ecosystem.model.GameReview;
import com.example.modules.entertainment_ecosystem.repository.GameReviewRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.GameAchievement;
import com.example.modules.entertainment_ecosystem.repository.GameAchievementRepository;
import com.example.modules.entertainment_ecosystem.model.GameSession;
import com.example.modules.entertainment_ecosystem.repository.GameSessionRepository;
import com.example.modules.entertainment_ecosystem.model.DevelopmentStudio;
import com.example.modules.entertainment_ecosystem.repository.DevelopmentStudioRepository;
import com.example.modules.entertainment_ecosystem.model.DigitalPurchase;
import com.example.modules.entertainment_ecosystem.repository.DigitalPurchaseRepository;
import com.example.modules.entertainment_ecosystem.model.GamePlatform;
import com.example.modules.entertainment_ecosystem.repository.GamePlatformRepository;
import com.example.modules.entertainment_ecosystem.model.ContentRating;
import com.example.modules.entertainment_ecosystem.repository.ContentRatingRepository;
import com.example.modules.entertainment_ecosystem.model.ContentTag;
import com.example.modules.entertainment_ecosystem.repository.ContentTagRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGameRating;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRatingRepository;
import com.example.modules.entertainment_ecosystem.model.GamePlaySession;
import com.example.modules.entertainment_ecosystem.repository.GamePlaySessionRepository;
import com.example.modules.entertainment_ecosystem.model.GameReviewUpvote;
import com.example.modules.entertainment_ecosystem.repository.GameReviewUpvoteRepository;
import com.example.modules.entertainment_ecosystem.model.GameReviewDownvote;
import com.example.modules.entertainment_ecosystem.repository.GameReviewDownvoteRepository;
import com.example.modules.entertainment_ecosystem.model.ContentRatingBoard;
import com.example.modules.entertainment_ecosystem.repository.ContentRatingBoardRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class VideoGameService extends BaseService<VideoGame> {

    protected final VideoGameRepository videogameRepository;
    private final GenreRepository genresRepository;
    private final ReviewRepository generalReviewsRepository;
    private final GameReviewRepository gameReviewsRepository;
    private final UserProfileRepository playedByRepository;
    private final GameAchievementRepository achievementsRepository;
    private final GameSessionRepository sessionsRepository;
    private final DevelopmentStudioRepository developerStudioRepository;
    private final DigitalPurchaseRepository purchasesRepository;
    private final GamePlatformRepository platformsRepository;
    private final ContentRatingRepository contentRatingRepository;
    private final ContentTagRepository tagsRepository;
    private final VideoGameRatingRepository ratingsRepository;
    private final GamePlaySessionRepository gamePlaySessionsRepository;
    private final GameReviewUpvoteRepository gameReviewUpvotesRepository;
    private final GameReviewDownvoteRepository gameReviewDownvotesRepository;
    private final ContentRatingBoardRepository contentRatingBoardRepository;

    public VideoGameService(VideoGameRepository repository,GenreRepository genresRepository,ReviewRepository generalReviewsRepository,GameReviewRepository gameReviewsRepository,UserProfileRepository playedByRepository,GameAchievementRepository achievementsRepository,GameSessionRepository sessionsRepository,DevelopmentStudioRepository developerStudioRepository,DigitalPurchaseRepository purchasesRepository,GamePlatformRepository platformsRepository,ContentRatingRepository contentRatingRepository,ContentTagRepository tagsRepository,VideoGameRatingRepository ratingsRepository,GamePlaySessionRepository gamePlaySessionsRepository,GameReviewUpvoteRepository gameReviewUpvotesRepository,GameReviewDownvoteRepository gameReviewDownvotesRepository,ContentRatingBoardRepository contentRatingBoardRepository)
    {
        super(repository);
        this.videogameRepository = repository;
        this.genresRepository = genresRepository;
        this.generalReviewsRepository = generalReviewsRepository;
        this.gameReviewsRepository = gameReviewsRepository;
        this.playedByRepository = playedByRepository;
        this.achievementsRepository = achievementsRepository;
        this.sessionsRepository = sessionsRepository;
        this.developerStudioRepository = developerStudioRepository;
        this.purchasesRepository = purchasesRepository;
        this.platformsRepository = platformsRepository;
        this.contentRatingRepository = contentRatingRepository;
        this.tagsRepository = tagsRepository;
        this.ratingsRepository = ratingsRepository;
        this.gamePlaySessionsRepository = gamePlaySessionsRepository;
        this.gameReviewUpvotesRepository = gameReviewUpvotesRepository;
        this.gameReviewDownvotesRepository = gameReviewDownvotesRepository;
        this.contentRatingBoardRepository = contentRatingBoardRepository;
    }

    @Override
    public VideoGame save(VideoGame videogame) {


    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (videogame.getGeneralReviews() != null) {
            List<Review> managedGeneralReviews = new ArrayList<>();
            for (Review item : videogame.getGeneralReviews()) {
            if (item.getId() != null) {
            Review existingItem = generalReviewsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Review not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setVideoGame(videogame);
            managedGeneralReviews.add(existingItem);
            } else {
            item.setVideoGame(videogame);
            managedGeneralReviews.add(item);
            }
            }
            videogame.setGeneralReviews(managedGeneralReviews);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (videogame.getGameReviews() != null) {
            List<GameReview> managedGameReviews = new ArrayList<>();
            for (GameReview item : videogame.getGameReviews()) {
            if (item.getId() != null) {
            GameReview existingItem = gameReviewsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("GameReview not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setGame(videogame);
            managedGameReviews.add(existingItem);
            } else {
            item.setGame(videogame);
            managedGameReviews.add(item);
            }
            }
            videogame.setGameReviews(managedGameReviews);
            }
        
    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (videogame.getAchievements() != null) {
            List<GameAchievement> managedAchievements = new ArrayList<>();
            for (GameAchievement item : videogame.getAchievements()) {
            if (item.getId() != null) {
            GameAchievement existingItem = achievementsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("GameAchievement not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setGame(videogame);
            managedAchievements.add(existingItem);
            } else {
            item.setGame(videogame);
            managedAchievements.add(item);
            }
            }
            videogame.setAchievements(managedAchievements);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (videogame.getSessions() != null) {
            List<GameSession> managedSessions = new ArrayList<>();
            for (GameSession item : videogame.getSessions()) {
            if (item.getId() != null) {
            GameSession existingItem = sessionsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("GameSession not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setGame(videogame);
            managedSessions.add(existingItem);
            } else {
            item.setGame(videogame);
            managedSessions.add(item);
            }
            }
            videogame.setSessions(managedSessions);
            }
        
    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (videogame.getPurchases() != null) {
            List<DigitalPurchase> managedPurchases = new ArrayList<>();
            for (DigitalPurchase item : videogame.getPurchases()) {
            if (item.getId() != null) {
            DigitalPurchase existingItem = purchasesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("DigitalPurchase not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setVideoGame(videogame);
            managedPurchases.add(existingItem);
            } else {
            item.setVideoGame(videogame);
            managedPurchases.add(item);
            }
            }
            videogame.setPurchases(managedPurchases);
            }
        
    

    

    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (videogame.getRatings() != null) {
            List<VideoGameRating> managedRatings = new ArrayList<>();
            for (VideoGameRating item : videogame.getRatings()) {
            if (item.getId() != null) {
            VideoGameRating existingItem = ratingsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("VideoGameRating not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setGame(videogame);
            managedRatings.add(existingItem);
            } else {
            item.setGame(videogame);
            managedRatings.add(item);
            }
            }
            videogame.setRatings(managedRatings);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (videogame.getGamePlaySessions() != null) {
            List<GamePlaySession> managedGamePlaySessions = new ArrayList<>();
            for (GamePlaySession item : videogame.getGamePlaySessions()) {
            if (item.getId() != null) {
            GamePlaySession existingItem = gamePlaySessionsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("GamePlaySession not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setGame(videogame);
            managedGamePlaySessions.add(existingItem);
            } else {
            item.setGame(videogame);
            managedGamePlaySessions.add(item);
            }
            }
            videogame.setGamePlaySessions(managedGamePlaySessions);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (videogame.getGameReviewUpvotes() != null) {
            List<GameReviewUpvote> managedGameReviewUpvotes = new ArrayList<>();
            for (GameReviewUpvote item : videogame.getGameReviewUpvotes()) {
            if (item.getId() != null) {
            GameReviewUpvote existingItem = gameReviewUpvotesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("GameReviewUpvote not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setGame(videogame);
            managedGameReviewUpvotes.add(existingItem);
            } else {
            item.setGame(videogame);
            managedGameReviewUpvotes.add(item);
            }
            }
            videogame.setGameReviewUpvotes(managedGameReviewUpvotes);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
    

    

    
    
    
    
    
    
    if (videogame.getDeveloperStudio() != null
        && videogame.getDeveloperStudio().getId() != null) {
        DevelopmentStudio existingDeveloperStudio = developerStudioRepository.findById(
        videogame.getDeveloperStudio().getId()
        ).orElseThrow(() -> new RuntimeException("DevelopmentStudio not found"));
        videogame.setDeveloperStudio(existingDeveloperStudio);
        }
    
    
    
    if (videogame.getContentRating() != null
        && videogame.getContentRating().getId() != null) {
        ContentRating existingContentRating = contentRatingRepository.findById(
        videogame.getContentRating().getId()
        ).orElseThrow(() -> new RuntimeException("ContentRating not found"));
        videogame.setContentRating(existingContentRating);
        }
    
    
    
    
    
    
    if (videogame.getContentRatingBoard() != null
        && videogame.getContentRatingBoard().getId() != null) {
        ContentRatingBoard existingContentRatingBoard = contentRatingBoardRepository.findById(
        videogame.getContentRatingBoard().getId()
        ).orElseThrow(() -> new RuntimeException("ContentRatingBoard not found"));
        videogame.setContentRatingBoard(existingContentRatingBoard);
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
        if (videogameRequest.getDeveloperStudio() != null &&
        videogameRequest.getDeveloperStudio().getId() != null) {

        DevelopmentStudio existingDeveloperStudio = developerStudioRepository.findById(
        videogameRequest.getDeveloperStudio().getId()
        ).orElseThrow(() -> new RuntimeException("DevelopmentStudio not found"));

        existing.setDeveloperStudio(existingDeveloperStudio);
        } else {
        existing.setDeveloperStudio(null);
        }
        if (videogameRequest.getContentRating() != null &&
        videogameRequest.getContentRating().getId() != null) {

        ContentRating existingContentRating = contentRatingRepository.findById(
        videogameRequest.getContentRating().getId()
        ).orElseThrow(() -> new RuntimeException("ContentRating not found"));

        existing.setContentRating(existingContentRating);
        } else {
        existing.setContentRating(null);
        }
        if (videogameRequest.getContentRatingBoard() != null &&
        videogameRequest.getContentRatingBoard().getId() != null) {

        ContentRatingBoard existingContentRatingBoard = contentRatingBoardRepository.findById(
        videogameRequest.getContentRatingBoard().getId()
        ).orElseThrow(() -> new RuntimeException("ContentRatingBoard not found"));

        existing.setContentRatingBoard(existingContentRatingBoard);
        } else {
        existing.setContentRatingBoard(null);
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
        List<Review> managedGeneralReviews = new ArrayList<>();

        for (var item : videogameRequest.getGeneralReviews()) {
        if (item.getId() != null) {
        Review existingItem = generalReviewsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Review not found"));
        existingItem.setVideoGame(existing);
        managedGeneralReviews.add(existingItem);
        } else {
        item.setVideoGame(existing);
        managedGeneralReviews.add(item);
        }
        }
        existing.setGeneralReviews(managedGeneralReviews);
        }
        existing.getGameReviews().clear();

        if (videogameRequest.getGameReviews() != null) {
        List<GameReview> managedGameReviews = new ArrayList<>();

        for (var item : videogameRequest.getGameReviews()) {
        if (item.getId() != null) {
        GameReview existingItem = gameReviewsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("GameReview not found"));
        existingItem.setGame(existing);
        managedGameReviews.add(existingItem);
        } else {
        item.setGame(existing);
        managedGameReviews.add(item);
        }
        }
        existing.setGameReviews(managedGameReviews);
        }
        existing.getAchievements().clear();

        if (videogameRequest.getAchievements() != null) {
        List<GameAchievement> managedAchievements = new ArrayList<>();

        for (var item : videogameRequest.getAchievements()) {
        if (item.getId() != null) {
        GameAchievement existingItem = achievementsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("GameAchievement not found"));
        existingItem.setGame(existing);
        managedAchievements.add(existingItem);
        } else {
        item.setGame(existing);
        managedAchievements.add(item);
        }
        }
        existing.setAchievements(managedAchievements);
        }
        existing.getSessions().clear();

        if (videogameRequest.getSessions() != null) {
        List<GameSession> managedSessions = new ArrayList<>();

        for (var item : videogameRequest.getSessions()) {
        if (item.getId() != null) {
        GameSession existingItem = sessionsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("GameSession not found"));
        existingItem.setGame(existing);
        managedSessions.add(existingItem);
        } else {
        item.setGame(existing);
        managedSessions.add(item);
        }
        }
        existing.setSessions(managedSessions);
        }
        existing.getPurchases().clear();

        if (videogameRequest.getPurchases() != null) {
        List<DigitalPurchase> managedPurchases = new ArrayList<>();

        for (var item : videogameRequest.getPurchases()) {
        if (item.getId() != null) {
        DigitalPurchase existingItem = purchasesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("DigitalPurchase not found"));
        existingItem.setVideoGame(existing);
        managedPurchases.add(existingItem);
        } else {
        item.setVideoGame(existing);
        managedPurchases.add(item);
        }
        }
        existing.setPurchases(managedPurchases);
        }
        existing.getRatings().clear();

        if (videogameRequest.getRatings() != null) {
        List<VideoGameRating> managedRatings = new ArrayList<>();

        for (var item : videogameRequest.getRatings()) {
        if (item.getId() != null) {
        VideoGameRating existingItem = ratingsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("VideoGameRating not found"));
        existingItem.setGame(existing);
        managedRatings.add(existingItem);
        } else {
        item.setGame(existing);
        managedRatings.add(item);
        }
        }
        existing.setRatings(managedRatings);
        }
        existing.getGamePlaySessions().clear();

        if (videogameRequest.getGamePlaySessions() != null) {
        List<GamePlaySession> managedGamePlaySessions = new ArrayList<>();

        for (var item : videogameRequest.getGamePlaySessions()) {
        if (item.getId() != null) {
        GamePlaySession existingItem = gamePlaySessionsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("GamePlaySession not found"));
        existingItem.setGame(existing);
        managedGamePlaySessions.add(existingItem);
        } else {
        item.setGame(existing);
        managedGamePlaySessions.add(item);
        }
        }
        existing.setGamePlaySessions(managedGamePlaySessions);
        }
        existing.getGameReviewUpvotes().clear();

        if (videogameRequest.getGameReviewUpvotes() != null) {
        List<GameReviewUpvote> managedGameReviewUpvotes = new ArrayList<>();

        for (var item : videogameRequest.getGameReviewUpvotes()) {
        if (item.getId() != null) {
        GameReviewUpvote existingItem = gameReviewUpvotesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("GameReviewUpvote not found"));
        existingItem.setGame(existing);
        managedGameReviewUpvotes.add(existingItem);
        } else {
        item.setGame(existing);
        managedGameReviewUpvotes.add(item);
        }
        }
        existing.setGameReviewUpvotes(managedGameReviewUpvotes);
        }
        existing.getGameReviewDownvotes().clear();

        if (videogameRequest.getGameReviewDownvotes() != null) {
        List<GameReviewDownvote> managedGameReviewDownvotes = new ArrayList<>();

        for (var item : videogameRequest.getGameReviewDownvotes()) {
        if (item.getId() != null) {
        GameReviewDownvote existingItem = gameReviewDownvotesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("GameReviewDownvote not found"));
        existingItem.setGame(existing);
        managedGameReviewDownvotes.add(existingItem);
        } else {
        item.setGame(existing);
        managedGameReviewDownvotes.add(item);
        }
        }
        existing.setGameReviewDownvotes(managedGameReviewDownvotes);
        }

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    


        return videogameRepository.save(existing);
    }


}