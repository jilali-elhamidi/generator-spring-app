package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.LiveStreamViewer;
import com.example.modules.entertainment_ecosystem.repository.LiveStreamViewerRepository;

import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import com.example.modules.entertainment_ecosystem.model.LiveStream;
import com.example.modules.entertainment_ecosystem.repository.LiveStreamRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class LiveStreamViewerService extends BaseService<LiveStreamViewer> {

    protected final LiveStreamViewerRepository livestreamviewerRepository;
    
    protected final UserProfileRepository userRepository;
    
    protected final LiveStreamRepository liveStreamRepository;
    

    public LiveStreamViewerService(LiveStreamViewerRepository repository, UserProfileRepository userRepository, LiveStreamRepository liveStreamRepository)
    {
        super(repository);
        this.livestreamviewerRepository = repository;
        
        this.userRepository = userRepository;
        
        this.liveStreamRepository = liveStreamRepository;
        
    }

    @Transactional
    @Override
    public LiveStreamViewer save(LiveStreamViewer livestreamviewer) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (livestreamviewer.getUser() != null) {
            if (livestreamviewer.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    livestreamviewer.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + livestreamviewer.getUser().getId()));
                livestreamviewer.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(livestreamviewer.getUser());
                livestreamviewer.setUser(newUser);
            }
        }
        
        if (livestreamviewer.getLiveStream() != null) {
            if (livestreamviewer.getLiveStream().getId() != null) {
                LiveStream existingLiveStream = liveStreamRepository.findById(
                    livestreamviewer.getLiveStream().getId()
                ).orElseThrow(() -> new RuntimeException("LiveStream not found with id "
                    + livestreamviewer.getLiveStream().getId()));
                livestreamviewer.setLiveStream(existingLiveStream);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                LiveStream newLiveStream = liveStreamRepository.save(livestreamviewer.getLiveStream());
                livestreamviewer.setLiveStream(newLiveStream);
            }
        }
        
    // ---------- OneToOne ----------
    return livestreamviewerRepository.save(livestreamviewer);
}

    @Transactional
    @Override
    public LiveStreamViewer update(Long id, LiveStreamViewer livestreamviewerRequest) {
        LiveStreamViewer existing = livestreamviewerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("LiveStreamViewer not found"));

    // Copier les champs simples
        existing.setJoinTime(livestreamviewerRequest.getJoinTime());
        existing.setLeaveTime(livestreamviewerRequest.getLeaveTime());

    // ---------- Relations ManyToOne ----------
        if (livestreamviewerRequest.getUser() != null &&
            livestreamviewerRequest.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                livestreamviewerRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
        if (livestreamviewerRequest.getLiveStream() != null &&
            livestreamviewerRequest.getLiveStream().getId() != null) {

            LiveStream existingLiveStream = liveStreamRepository.findById(
                livestreamviewerRequest.getLiveStream().getId()
            ).orElseThrow(() -> new RuntimeException("LiveStream not found"));

            existing.setLiveStream(existingLiveStream);
        } else {
            existing.setLiveStream(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return livestreamviewerRepository.save(existing);
}

    // Pagination simple
    public Page<LiveStreamViewer> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<LiveStreamViewer> search(Map<String, String> filters, Pageable pageable) {
        return super.search(LiveStreamViewer.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<LiveStreamViewer> saveAll(List<LiveStreamViewer> livestreamviewerList) {
        return super.saveAll(livestreamviewerList);
    }

}