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
import com.example.modules.entertainment_ecosystem.model.GameExpansionPack;
import com.example.modules.entertainment_ecosystem.repository.GameExpansionPackRepository;
import com.example.modules.entertainment_ecosystem.model.LiveStream;
import com.example.modules.entertainment_ecosystem.repository.LiveStreamRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final GameExpansionPackRepository expansionPacksRepository;
    private final LiveStreamRepository liveStreamsRepository;

    public VideoGameService(VideoGameRepository repository, GenreRepository genresRepository, ReviewRepository generalReviewsRepository, GameReviewRepository gameReviewsRepository, UserProfileRepository playedByRepository, GameAchievementRepository achievementsRepository, GameSessionRepository sessionsRepository, DevelopmentStudioRepository developerStudioRepository, DigitalPurchaseRepository purchasesRepository, GamePlatformRepository platformsRepository, ContentRatingRepository contentRatingRepository, ContentTagRepository tagsRepository, VideoGameRatingRepository ratingsRepository, GamePlaySessionRepository gamePlaySessionsRepository, GameReviewUpvoteRepository gameReviewUpvotesRepository, GameReviewDownvoteRepository gameReviewDownvotesRepository, ContentRatingBoardRepository contentRatingBoardRepository, GameExpansionPackRepository expansionPacksRepository, LiveStreamRepository liveStreamsRepository)
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
        this.expansionPacksRepository = expansionPacksRepository;
        this.liveStreamsRepository = liveStreamsRepository;
    }

    @Override
    public VideoGame save(VideoGame videogame) {
    // ---------- OneToMany ----------
        if (videogame.getGeneralReviews() != null) {
            List<Review> managedGeneralReviews = new ArrayList<>();
            for (Review item : videogame.getGeneralReviews()) {
                if (item.getId() != null) {
                    Review existingItem = generalReviewsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Review not found"));

                     existingItem.setVideoGame(videogame);
                     managedGeneralReviews.add(existingItem);
                } else {
                    item.setVideoGame(videogame);
                    managedGeneralReviews.add(item);
                }
            }
            videogame.setGeneralReviews(managedGeneralReviews);
        }
    
        if (videogame.getGameReviews() != null) {
            List<GameReview> managedGameReviews = new ArrayList<>();
            for (GameReview item : videogame.getGameReviews()) {
                if (item.getId() != null) {
                    GameReview existingItem = gameReviewsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameReview not found"));

                     existingItem.setGame(videogame);
                     managedGameReviews.add(existingItem);
                } else {
                    item.setGame(videogame);
                    managedGameReviews.add(item);
                }
            }
            videogame.setGameReviews(managedGameReviews);
        }
    
        if (videogame.getAchievements() != null) {
            List<GameAchievement> managedAchievements = new ArrayList<>();
            for (GameAchievement item : videogame.getAchievements()) {
                if (item.getId() != null) {
                    GameAchievement existingItem = achievementsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameAchievement not found"));

                     existingItem.setGame(videogame);
                     managedAchievements.add(existingItem);
                } else {
                    item.setGame(videogame);
                    managedAchievements.add(item);
                }
            }
            videogame.setAchievements(managedAchievements);
        }
    
        if (videogame.getSessions() != null) {
            List<GameSession> managedSessions = new ArrayList<>();
            for (GameSession item : videogame.getSessions()) {
                if (item.getId() != null) {
                    GameSession existingItem = sessionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameSession not found"));

                     existingItem.setGame(videogame);
                     managedSessions.add(existingItem);
                } else {
                    item.setGame(videogame);
                    managedSessions.add(item);
                }
            }
            videogame.setSessions(managedSessions);
        }
    
        if (videogame.getPurchases() != null) {
            List<DigitalPurchase> managedPurchases = new ArrayList<>();
            for (DigitalPurchase item : videogame.getPurchases()) {
                if (item.getId() != null) {
                    DigitalPurchase existingItem = purchasesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("DigitalPurchase not found"));

                     existingItem.setVideoGame(videogame);
                     managedPurchases.add(existingItem);
                } else {
                    item.setVideoGame(videogame);
                    managedPurchases.add(item);
                }
            }
            videogame.setPurchases(managedPurchases);
        }
    
        if (videogame.getRatings() != null) {
            List<VideoGameRating> managedRatings = new ArrayList<>();
            for (VideoGameRating item : videogame.getRatings()) {
                if (item.getId() != null) {
                    VideoGameRating existingItem = ratingsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("VideoGameRating not found"));

                     existingItem.setGame(videogame);
                     managedRatings.add(existingItem);
                } else {
                    item.setGame(videogame);
                    managedRatings.add(item);
                }
            }
            videogame.setRatings(managedRatings);
        }
    
        if (videogame.getGamePlaySessions() != null) {
            List<GamePlaySession> managedGamePlaySessions = new ArrayList<>();
            for (GamePlaySession item : videogame.getGamePlaySessions()) {
                if (item.getId() != null) {
                    GamePlaySession existingItem = gamePlaySessionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GamePlaySession not found"));

                     existingItem.setGame(videogame);
                     managedGamePlaySessions.add(existingItem);
                } else {
                    item.setGame(videogame);
                    managedGamePlaySessions.add(item);
                }
            }
            videogame.setGamePlaySessions(managedGamePlaySessions);
        }
    
        if (videogame.getGameReviewUpvotes() != null) {
            List<GameReviewUpvote> managedGameReviewUpvotes = new ArrayList<>();
            for (GameReviewUpvote item : videogame.getGameReviewUpvotes()) {
                if (item.getId() != null) {
                    GameReviewUpvote existingItem = gameReviewUpvotesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameReviewUpvote not found"));

                     existingItem.setGame(videogame);
                     managedGameReviewUpvotes.add(existingItem);
                } else {
                    item.setGame(videogame);
                    managedGameReviewUpvotes.add(item);
                }
            }
            videogame.setGameReviewUpvotes(managedGameReviewUpvotes);
        }
    
        if (videogame.getGameReviewDownvotes() != null) {
            List<GameReviewDownvote> managedGameReviewDownvotes = new ArrayList<>();
            for (GameReviewDownvote item : videogame.getGameReviewDownvotes()) {
                if (item.getId() != null) {
                    GameReviewDownvote existingItem = gameReviewDownvotesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameReviewDownvote not found"));

                     existingItem.setGame(videogame);
                     managedGameReviewDownvotes.add(existingItem);
                } else {
                    item.setGame(videogame);
                    managedGameReviewDownvotes.add(item);
                }
            }
            videogame.setGameReviewDownvotes(managedGameReviewDownvotes);
        }
    
        if (videogame.getExpansionPacks() != null) {
            List<GameExpansionPack> managedExpansionPacks = new ArrayList<>();
            for (GameExpansionPack item : videogame.getExpansionPacks()) {
                if (item.getId() != null) {
                    GameExpansionPack existingItem = expansionPacksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameExpansionPack not found"));

                     existingItem.setBaseGame(videogame);
                     managedExpansionPacks.add(existingItem);
                } else {
                    item.setBaseGame(videogame);
                    managedExpansionPacks.add(item);
                }
            }
            videogame.setExpansionPacks(managedExpansionPacks);
        }
    
        if (videogame.getLiveStreams() != null) {
            List<LiveStream> managedLiveStreams = new ArrayList<>();
            for (LiveStream item : videogame.getLiveStreams()) {
                if (item.getId() != null) {
                    LiveStream existingItem = liveStreamsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("LiveStream not found"));

                     existingItem.setGame(videogame);
                     managedLiveStreams.add(existingItem);
                } else {
                    item.setGame(videogame);
                    managedLiveStreams.add(item);
                }
            }
            videogame.setLiveStreams(managedLiveStreams);
        }
    
    // ---------- ManyToMany ----------
        if (videogame.getGenres() != null &&
            !videogame.getGenres().isEmpty()) {

            List<Genre> attachedGenres = new ArrayList<>();
            for (Genre item : videogame.getGenres()) {
                if (item.getId() != null) {
                    Genre existingItem = genresRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Genre not found with id " + item.getId()));
                    attachedGenres.add(existingItem);
                } else {

                    Genre newItem = genresRepository.save(item);
                    attachedGenres.add(newItem);
                }
            }

            videogame.setGenres(attachedGenres);

            // côté propriétaire (Genre → VideoGame)
            attachedGenres.forEach(it -> it.getVideoGames().add(videogame));
        }
        
        if (videogame.getPlayedBy() != null &&
            !videogame.getPlayedBy().isEmpty()) {

            List<UserProfile> attachedPlayedBy = new ArrayList<>();
            for (UserProfile item : videogame.getPlayedBy()) {
                if (item.getId() != null) {
                    UserProfile existingItem = playedByRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserProfile not found with id " + item.getId()));
                    attachedPlayedBy.add(existingItem);
                } else {

                    UserProfile newItem = playedByRepository.save(item);
                    attachedPlayedBy.add(newItem);
                }
            }

            videogame.setPlayedBy(attachedPlayedBy);

            // côté propriétaire (UserProfile → VideoGame)
            attachedPlayedBy.forEach(it -> it.getPlayedGames().add(videogame));
        }
        
        if (videogame.getPlatforms() != null &&
            !videogame.getPlatforms().isEmpty()) {

            List<GamePlatform> attachedPlatforms = new ArrayList<>();
            for (GamePlatform item : videogame.getPlatforms()) {
                if (item.getId() != null) {
                    GamePlatform existingItem = platformsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GamePlatform not found with id " + item.getId()));
                    attachedPlatforms.add(existingItem);
                } else {

                    GamePlatform newItem = platformsRepository.save(item);
                    attachedPlatforms.add(newItem);
                }
            }

            videogame.setPlatforms(attachedPlatforms);

            // côté propriétaire (GamePlatform → VideoGame)
            attachedPlatforms.forEach(it -> it.getVideoGames().add(videogame));
        }
        
        if (videogame.getTags() != null &&
            !videogame.getTags().isEmpty()) {

            List<ContentTag> attachedTags = new ArrayList<>();
            for (ContentTag item : videogame.getTags()) {
                if (item.getId() != null) {
                    ContentTag existingItem = tagsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ContentTag not found with id " + item.getId()));
                    attachedTags.add(existingItem);
                } else {

                    ContentTag newItem = tagsRepository.save(item);
                    attachedTags.add(newItem);
                }
            }

            videogame.setTags(attachedTags);

            // côté propriétaire (ContentTag → VideoGame)
            attachedTags.forEach(it -> it.getVideoGames().add(videogame));
        }
        
    // ---------- ManyToOne ----------
        if (videogame.getDeveloperStudio() != null) {
            if (videogame.getDeveloperStudio().getId() != null) {
                DevelopmentStudio existingDeveloperStudio = developerStudioRepository.findById(
                    videogame.getDeveloperStudio().getId()
                ).orElseThrow(() -> new RuntimeException("DevelopmentStudio not found with id "
                    + videogame.getDeveloperStudio().getId()));
                videogame.setDeveloperStudio(existingDeveloperStudio);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                DevelopmentStudio newDeveloperStudio = developerStudioRepository.save(videogame.getDeveloperStudio());
                videogame.setDeveloperStudio(newDeveloperStudio);
            }
        }
        
        if (videogame.getContentRating() != null) {
            if (videogame.getContentRating().getId() != null) {
                ContentRating existingContentRating = contentRatingRepository.findById(
                    videogame.getContentRating().getId()
                ).orElseThrow(() -> new RuntimeException("ContentRating not found with id "
                    + videogame.getContentRating().getId()));
                videogame.setContentRating(existingContentRating);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                ContentRating newContentRating = contentRatingRepository.save(videogame.getContentRating());
                videogame.setContentRating(newContentRating);
            }
        }
        
        if (videogame.getContentRatingBoard() != null) {
            if (videogame.getContentRatingBoard().getId() != null) {
                ContentRatingBoard existingContentRatingBoard = contentRatingBoardRepository.findById(
                    videogame.getContentRatingBoard().getId()
                ).orElseThrow(() -> new RuntimeException("ContentRatingBoard not found with id "
                    + videogame.getContentRatingBoard().getId()));
                videogame.setContentRatingBoard(existingContentRatingBoard);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                ContentRatingBoard newContentRatingBoard = contentRatingBoardRepository.save(videogame.getContentRatingBoard());
                videogame.setContentRatingBoard(newContentRatingBoard);
            }
        }
        
    // ---------- OneToOne ----------
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

    // ---------- Relations ManyToOne ----------
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
        
    // ---------- Relations ManyToOne ----------
        if (videogameRequest.getGenres() != null) {
            existing.getGenres().clear();

            List<Genre> genresList = videogameRequest.getGenres().stream()
                .map(item -> genresRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Genre not found")))
                .collect(Collectors.toList());

            existing.getGenres().addAll(genresList);

            // Mettre à jour le côté inverse
            genresList.forEach(it -> {
                if (!it.getVideoGames().contains(existing)) {
                    it.getVideoGames().add(existing);
                }
            });
        }
        
        if (videogameRequest.getPlayedBy() != null) {
            existing.getPlayedBy().clear();

            List<UserProfile> playedByList = videogameRequest.getPlayedBy().stream()
                .map(item -> playedByRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());

            existing.getPlayedBy().addAll(playedByList);

            // Mettre à jour le côté inverse
            playedByList.forEach(it -> {
                if (!it.getPlayedGames().contains(existing)) {
                    it.getPlayedGames().add(existing);
                }
            });
        }
        
        if (videogameRequest.getPlatforms() != null) {
            existing.getPlatforms().clear();

            List<GamePlatform> platformsList = videogameRequest.getPlatforms().stream()
                .map(item -> platformsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("GamePlatform not found")))
                .collect(Collectors.toList());

            existing.getPlatforms().addAll(platformsList);

            // Mettre à jour le côté inverse
            platformsList.forEach(it -> {
                if (!it.getVideoGames().contains(existing)) {
                    it.getVideoGames().add(existing);
                }
            });
        }
        
        if (videogameRequest.getTags() != null) {
            existing.getTags().clear();

            List<ContentTag> tagsList = videogameRequest.getTags().stream()
                .map(item -> tagsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("ContentTag not found")))
                .collect(Collectors.toList());

            existing.getTags().addAll(tagsList);

            // Mettre à jour le côté inverse
            tagsList.forEach(it -> {
                if (!it.getVideoGames().contains(existing)) {
                    it.getVideoGames().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
        existing.getGeneralReviews().clear();

        if (videogameRequest.getGeneralReviews() != null) {
            for (var item : videogameRequest.getGeneralReviews()) {
                Review existingItem;
                if (item.getId() != null) {
                    existingItem = generalReviewsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Review not found"));
                } else {
                existingItem = item;
                }

                existingItem.setVideoGame(existing);
                existing.getGeneralReviews().add(existingItem);
            }
        }
        
        existing.getGameReviews().clear();

        if (videogameRequest.getGameReviews() != null) {
            for (var item : videogameRequest.getGameReviews()) {
                GameReview existingItem;
                if (item.getId() != null) {
                    existingItem = gameReviewsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameReview not found"));
                } else {
                existingItem = item;
                }

                existingItem.setGame(existing);
                existing.getGameReviews().add(existingItem);
            }
        }
        
        existing.getAchievements().clear();

        if (videogameRequest.getAchievements() != null) {
            for (var item : videogameRequest.getAchievements()) {
                GameAchievement existingItem;
                if (item.getId() != null) {
                    existingItem = achievementsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameAchievement not found"));
                } else {
                existingItem = item;
                }

                existingItem.setGame(existing);
                existing.getAchievements().add(existingItem);
            }
        }
        
        existing.getSessions().clear();

        if (videogameRequest.getSessions() != null) {
            for (var item : videogameRequest.getSessions()) {
                GameSession existingItem;
                if (item.getId() != null) {
                    existingItem = sessionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameSession not found"));
                } else {
                existingItem = item;
                }

                existingItem.setGame(existing);
                existing.getSessions().add(existingItem);
            }
        }
        
        existing.getPurchases().clear();

        if (videogameRequest.getPurchases() != null) {
            for (var item : videogameRequest.getPurchases()) {
                DigitalPurchase existingItem;
                if (item.getId() != null) {
                    existingItem = purchasesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("DigitalPurchase not found"));
                } else {
                existingItem = item;
                }

                existingItem.setVideoGame(existing);
                existing.getPurchases().add(existingItem);
            }
        }
        
        existing.getRatings().clear();

        if (videogameRequest.getRatings() != null) {
            for (var item : videogameRequest.getRatings()) {
                VideoGameRating existingItem;
                if (item.getId() != null) {
                    existingItem = ratingsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("VideoGameRating not found"));
                } else {
                existingItem = item;
                }

                existingItem.setGame(existing);
                existing.getRatings().add(existingItem);
            }
        }
        
        existing.getGamePlaySessions().clear();

        if (videogameRequest.getGamePlaySessions() != null) {
            for (var item : videogameRequest.getGamePlaySessions()) {
                GamePlaySession existingItem;
                if (item.getId() != null) {
                    existingItem = gamePlaySessionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GamePlaySession not found"));
                } else {
                existingItem = item;
                }

                existingItem.setGame(existing);
                existing.getGamePlaySessions().add(existingItem);
            }
        }
        
        existing.getGameReviewUpvotes().clear();

        if (videogameRequest.getGameReviewUpvotes() != null) {
            for (var item : videogameRequest.getGameReviewUpvotes()) {
                GameReviewUpvote existingItem;
                if (item.getId() != null) {
                    existingItem = gameReviewUpvotesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameReviewUpvote not found"));
                } else {
                existingItem = item;
                }

                existingItem.setGame(existing);
                existing.getGameReviewUpvotes().add(existingItem);
            }
        }
        
        existing.getGameReviewDownvotes().clear();

        if (videogameRequest.getGameReviewDownvotes() != null) {
            for (var item : videogameRequest.getGameReviewDownvotes()) {
                GameReviewDownvote existingItem;
                if (item.getId() != null) {
                    existingItem = gameReviewDownvotesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameReviewDownvote not found"));
                } else {
                existingItem = item;
                }

                existingItem.setGame(existing);
                existing.getGameReviewDownvotes().add(existingItem);
            }
        }
        
        existing.getExpansionPacks().clear();

        if (videogameRequest.getExpansionPacks() != null) {
            for (var item : videogameRequest.getExpansionPacks()) {
                GameExpansionPack existingItem;
                if (item.getId() != null) {
                    existingItem = expansionPacksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameExpansionPack not found"));
                } else {
                existingItem = item;
                }

                existingItem.setBaseGame(existing);
                existing.getExpansionPacks().add(existingItem);
            }
        }
        
        existing.getLiveStreams().clear();

        if (videogameRequest.getLiveStreams() != null) {
            for (var item : videogameRequest.getLiveStreams()) {
                LiveStream existingItem;
                if (item.getId() != null) {
                    existingItem = liveStreamsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("LiveStream not found"));
                } else {
                existingItem = item;
                }

                existingItem.setGame(existing);
                existing.getLiveStreams().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return videogameRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<VideoGame> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        VideoGame entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getGeneralReviews() != null) {
            for (var child : entity.getGeneralReviews()) {
                // retirer la référence inverse
                child.setVideoGame(null);
            }
            entity.getGeneralReviews().clear();
        }
        
        if (entity.getGameReviews() != null) {
            for (var child : entity.getGameReviews()) {
                // retirer la référence inverse
                child.setGame(null);
            }
            entity.getGameReviews().clear();
        }
        
        if (entity.getAchievements() != null) {
            for (var child : entity.getAchievements()) {
                // retirer la référence inverse
                child.setGame(null);
            }
            entity.getAchievements().clear();
        }
        
        if (entity.getSessions() != null) {
            for (var child : entity.getSessions()) {
                // retirer la référence inverse
                child.setGame(null);
            }
            entity.getSessions().clear();
        }
        
        if (entity.getPurchases() != null) {
            for (var child : entity.getPurchases()) {
                // retirer la référence inverse
                child.setVideoGame(null);
            }
            entity.getPurchases().clear();
        }
        
        if (entity.getRatings() != null) {
            for (var child : entity.getRatings()) {
                // retirer la référence inverse
                child.setGame(null);
            }
            entity.getRatings().clear();
        }
        
        if (entity.getGamePlaySessions() != null) {
            for (var child : entity.getGamePlaySessions()) {
                // retirer la référence inverse
                child.setGame(null);
            }
            entity.getGamePlaySessions().clear();
        }
        
        if (entity.getGameReviewUpvotes() != null) {
            for (var child : entity.getGameReviewUpvotes()) {
                // retirer la référence inverse
                child.setGame(null);
            }
            entity.getGameReviewUpvotes().clear();
        }
        
        if (entity.getGameReviewDownvotes() != null) {
            for (var child : entity.getGameReviewDownvotes()) {
                // retirer la référence inverse
                child.setGame(null);
            }
            entity.getGameReviewDownvotes().clear();
        }
        
        if (entity.getExpansionPacks() != null) {
            for (var child : entity.getExpansionPacks()) {
                // retirer la référence inverse
                child.setBaseGame(null);
            }
            entity.getExpansionPacks().clear();
        }
        
        if (entity.getLiveStreams() != null) {
            for (var child : entity.getLiveStreams()) {
                // retirer la référence inverse
                child.setGame(null);
            }
            entity.getLiveStreams().clear();
        }
        
    // --- Dissocier ManyToMany ---
        if (entity.getGenres() != null) {
            for (Genre item : new ArrayList<>(entity.getGenres())) {
                
                item.getVideoGames().remove(entity); // retire côté inverse
            }
            entity.getGenres().clear(); // puis vide côté courant
        }
        
        if (entity.getPlayedBy() != null) {
            for (UserProfile item : new ArrayList<>(entity.getPlayedBy())) {
                
                item.getPlayedGames().remove(entity); // retire côté inverse
            }
            entity.getPlayedBy().clear(); // puis vide côté courant
        }
        
        if (entity.getPlatforms() != null) {
            for (GamePlatform item : new ArrayList<>(entity.getPlatforms())) {
                
                item.getVideoGames().remove(entity); // retire côté inverse
            }
            entity.getPlatforms().clear(); // puis vide côté courant
        }
        
        if (entity.getTags() != null) {
            for (ContentTag item : new ArrayList<>(entity.getTags())) {
                
                item.getVideoGames().remove(entity); // retire côté inverse
            }
            entity.getTags().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getDeveloperStudio() != null) {
            entity.setDeveloperStudio(null);
        }
        
        if (entity.getContentRating() != null) {
            entity.setContentRating(null);
        }
        
        if (entity.getContentRatingBoard() != null) {
            entity.setContentRatingBoard(null);
        }
        
        repository.delete(entity);
        return true;
    }
}