package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.LiveStream;
import com.example.modules.entertainment_ecosystem.repository.LiveStreamRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;
import com.example.modules.entertainment_ecosystem.model.LiveStreamViewer;
import com.example.modules.entertainment_ecosystem.repository.LiveStreamViewerRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class LiveStreamService extends BaseService<LiveStream> {

    protected final LiveStreamRepository livestreamRepository;
    private final UserProfileRepository hostRepository;
    private final VideoGameRepository gameRepository;
    private final LiveStreamViewerRepository viewersRepository;

    public LiveStreamService(LiveStreamRepository repository, UserProfileRepository hostRepository, VideoGameRepository gameRepository, LiveStreamViewerRepository viewersRepository)
    {
        super(repository);
        this.livestreamRepository = repository;
        this.hostRepository = hostRepository;
        this.gameRepository = gameRepository;
        this.viewersRepository = viewersRepository;
    }

    @Override
    public LiveStream save(LiveStream livestream) {
    // ---------- OneToMany ----------
        if (livestream.getViewers() != null) {
            List<LiveStreamViewer> managedViewers = new ArrayList<>();
            for (LiveStreamViewer item : livestream.getViewers()) {
                if (item.getId() != null) {
                    LiveStreamViewer existingItem = viewersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("LiveStreamViewer not found"));

                     existingItem.setLiveStream(livestream);
                     managedViewers.add(existingItem);
                } else {
                    item.setLiveStream(livestream);
                    managedViewers.add(item);
                }
            }
            livestream.setViewers(managedViewers);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (livestream.getHost() != null) {
            if (livestream.getHost().getId() != null) {
                UserProfile existingHost = hostRepository.findById(
                    livestream.getHost().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + livestream.getHost().getId()));
                livestream.setHost(existingHost);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newHost = hostRepository.save(livestream.getHost());
                livestream.setHost(newHost);
            }
        }
        
        if (livestream.getGame() != null) {
            if (livestream.getGame().getId() != null) {
                VideoGame existingGame = gameRepository.findById(
                    livestream.getGame().getId()
                ).orElseThrow(() -> new RuntimeException("VideoGame not found with id "
                    + livestream.getGame().getId()));
                livestream.setGame(existingGame);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                VideoGame newGame = gameRepository.save(livestream.getGame());
                livestream.setGame(newGame);
            }
        }
        
    // ---------- OneToOne ----------
    return livestreamRepository.save(livestream);
}


    public LiveStream update(Long id, LiveStream livestreamRequest) {
        LiveStream existing = livestreamRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("LiveStream not found"));

    // Copier les champs simples
        existing.setTitle(livestreamRequest.getTitle());
        existing.setStartDate(livestreamRequest.getStartDate());
        existing.setPlatformUrl(livestreamRequest.getPlatformUrl());

    // ---------- Relations ManyToOne ----------
        if (livestreamRequest.getHost() != null &&
            livestreamRequest.getHost().getId() != null) {

            UserProfile existingHost = hostRepository.findById(
                livestreamRequest.getHost().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setHost(existingHost);
        } else {
            existing.setHost(null);
        }
        
        if (livestreamRequest.getGame() != null &&
            livestreamRequest.getGame().getId() != null) {

            VideoGame existingGame = gameRepository.findById(
                livestreamRequest.getGame().getId()
            ).orElseThrow(() -> new RuntimeException("VideoGame not found"));

            existing.setGame(existingGame);
        } else {
            existing.setGame(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getViewers().clear();

        if (livestreamRequest.getViewers() != null) {
            for (var item : livestreamRequest.getViewers()) {
                LiveStreamViewer existingItem;
                if (item.getId() != null) {
                    existingItem = viewersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("LiveStreamViewer not found"));
                } else {
                existingItem = item;
                }

                existingItem.setLiveStream(existing);
                existing.getViewers().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return livestreamRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<LiveStream> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        LiveStream entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getViewers() != null) {
            for (var child : entity.getViewers()) {
                // retirer la référence inverse
                child.setLiveStream(null);
            }
            entity.getViewers().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getHost() != null) {
            entity.setHost(null);
        }
        
        if (entity.getGame() != null) {
            entity.setGame(null);
        }
        
        repository.delete(entity);
        return true;
    }
}