package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GameReview;
import com.example.modules.entertainment_ecosystem.repository.GameReviewRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;
import com.example.modules.entertainment_ecosystem.model.GameReviewComment;
import com.example.modules.entertainment_ecosystem.repository.GameReviewCommentRepository;
import com.example.modules.entertainment_ecosystem.model.GameReviewUpvote;
import com.example.modules.entertainment_ecosystem.repository.GameReviewUpvoteRepository;
import com.example.modules.entertainment_ecosystem.model.GameReviewDownvote;
import com.example.modules.entertainment_ecosystem.repository.GameReviewDownvoteRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class GameReviewService extends BaseService<GameReview> {

    protected final GameReviewRepository gamereviewRepository;
    private final UserProfileRepository userRepository;
    private final VideoGameRepository gameRepository;
    private final GameReviewCommentRepository commentsRepository;
    private final GameReviewUpvoteRepository upvotesRepository;
    private final GameReviewDownvoteRepository downvotesRepository;

    public GameReviewService(GameReviewRepository repository, UserProfileRepository userRepository, VideoGameRepository gameRepository, GameReviewCommentRepository commentsRepository, GameReviewUpvoteRepository upvotesRepository, GameReviewDownvoteRepository downvotesRepository)
    {
        super(repository);
        this.gamereviewRepository = repository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.commentsRepository = commentsRepository;
        this.upvotesRepository = upvotesRepository;
        this.downvotesRepository = downvotesRepository;
    }

    @Override
    public GameReview save(GameReview gamereview) {
    // ---------- OneToMany ----------
        if (gamereview.getComments() != null) {
            List<GameReviewComment> managedComments = new ArrayList<>();
            for (GameReviewComment item : gamereview.getComments()) {
                if (item.getId() != null) {
                    GameReviewComment existingItem = commentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameReviewComment not found"));

                     existingItem.setReview(gamereview);
                     managedComments.add(existingItem);
                } else {
                    item.setReview(gamereview);
                    managedComments.add(item);
                }
            }
            gamereview.setComments(managedComments);
        }
    
        if (gamereview.getUpvotes() != null) {
            List<GameReviewUpvote> managedUpvotes = new ArrayList<>();
            for (GameReviewUpvote item : gamereview.getUpvotes()) {
                if (item.getId() != null) {
                    GameReviewUpvote existingItem = upvotesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameReviewUpvote not found"));

                     existingItem.setReview(gamereview);
                     managedUpvotes.add(existingItem);
                } else {
                    item.setReview(gamereview);
                    managedUpvotes.add(item);
                }
            }
            gamereview.setUpvotes(managedUpvotes);
        }
    
        if (gamereview.getDownvotes() != null) {
            List<GameReviewDownvote> managedDownvotes = new ArrayList<>();
            for (GameReviewDownvote item : gamereview.getDownvotes()) {
                if (item.getId() != null) {
                    GameReviewDownvote existingItem = downvotesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameReviewDownvote not found"));

                     existingItem.setReview(gamereview);
                     managedDownvotes.add(existingItem);
                } else {
                    item.setReview(gamereview);
                    managedDownvotes.add(item);
                }
            }
            gamereview.setDownvotes(managedDownvotes);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (gamereview.getUser() != null) {
            if (gamereview.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    gamereview.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + gamereview.getUser().getId()));
                gamereview.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(gamereview.getUser());
                gamereview.setUser(newUser);
            }
        }
        
        if (gamereview.getGame() != null) {
            if (gamereview.getGame().getId() != null) {
                VideoGame existingGame = gameRepository.findById(
                    gamereview.getGame().getId()
                ).orElseThrow(() -> new RuntimeException("VideoGame not found with id "
                    + gamereview.getGame().getId()));
                gamereview.setGame(existingGame);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                VideoGame newGame = gameRepository.save(gamereview.getGame());
                gamereview.setGame(newGame);
            }
        }
        
    // ---------- OneToOne ----------
    return gamereviewRepository.save(gamereview);
}


    public GameReview update(Long id, GameReview gamereviewRequest) {
        GameReview existing = gamereviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GameReview not found"));

    // Copier les champs simples
        existing.setRating(gamereviewRequest.getRating());
        existing.setComment(gamereviewRequest.getComment());

    // ---------- Relations ManyToOne ----------
        if (gamereviewRequest.getUser() != null &&
            gamereviewRequest.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                gamereviewRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
        if (gamereviewRequest.getGame() != null &&
            gamereviewRequest.getGame().getId() != null) {

            VideoGame existingGame = gameRepository.findById(
                gamereviewRequest.getGame().getId()
            ).orElseThrow(() -> new RuntimeException("VideoGame not found"));

            existing.setGame(existingGame);
        } else {
            existing.setGame(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getComments().clear();

        if (gamereviewRequest.getComments() != null) {
            for (var item : gamereviewRequest.getComments()) {
                GameReviewComment existingItem;
                if (item.getId() != null) {
                    existingItem = commentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameReviewComment not found"));
                } else {
                existingItem = item;
                }

                existingItem.setReview(existing);
                existing.getComments().add(existingItem);
            }
        }
        
        existing.getUpvotes().clear();

        if (gamereviewRequest.getUpvotes() != null) {
            for (var item : gamereviewRequest.getUpvotes()) {
                GameReviewUpvote existingItem;
                if (item.getId() != null) {
                    existingItem = upvotesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameReviewUpvote not found"));
                } else {
                existingItem = item;
                }

                existingItem.setReview(existing);
                existing.getUpvotes().add(existingItem);
            }
        }
        
        existing.getDownvotes().clear();

        if (gamereviewRequest.getDownvotes() != null) {
            for (var item : gamereviewRequest.getDownvotes()) {
                GameReviewDownvote existingItem;
                if (item.getId() != null) {
                    existingItem = downvotesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameReviewDownvote not found"));
                } else {
                existingItem = item;
                }

                existingItem.setReview(existing);
                existing.getDownvotes().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return gamereviewRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<GameReview> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        GameReview entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getComments() != null) {
            for (var child : entity.getComments()) {
                // retirer la référence inverse
                child.setReview(null);
            }
            entity.getComments().clear();
        }
        
        if (entity.getUpvotes() != null) {
            for (var child : entity.getUpvotes()) {
                // retirer la référence inverse
                child.setReview(null);
            }
            entity.getUpvotes().clear();
        }
        
        if (entity.getDownvotes() != null) {
            for (var child : entity.getDownvotes()) {
                // retirer la référence inverse
                child.setReview(null);
            }
            entity.getDownvotes().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getUser() != null) {
            entity.setUser(null);
        }
        
        if (entity.getGame() != null) {
            entity.setGame(null);
        }
        
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<GameReview> saveAll(List<GameReview> gamereviewList) {

        return gamereviewRepository.saveAll(gamereviewList);
    }

}