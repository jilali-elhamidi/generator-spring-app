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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class LiveStreamService extends BaseService<LiveStream> {

    protected final LiveStreamRepository livestreamRepository;
    
    protected final UserProfileRepository hostRepository;
    
    protected final VideoGameRepository gameRepository;
    
    protected final LiveStreamViewerRepository viewersRepository;
    

    public LiveStreamService(LiveStreamRepository repository, UserProfileRepository hostRepository, VideoGameRepository gameRepository, LiveStreamViewerRepository viewersRepository)
    {
        super(repository);
        this.livestreamRepository = repository;
        
        this.hostRepository = hostRepository;
        
        this.gameRepository = gameRepository;
        
        this.viewersRepository = viewersRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<LiveStream> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<LiveStream> search(Map<String, String> filters, Pageable pageable) {
        return super.search(LiveStream.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<LiveStream> saveAll(List<LiveStream> livestreamList) {
        return super.saveAll(livestreamList);
    }

}