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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class LiveStreamViewerService extends BaseService<LiveStreamViewer> {

    protected final LiveStreamViewerRepository livestreamviewerRepository;
    private final UserProfileRepository userRepository;
    private final LiveStreamRepository liveStreamRepository;

    public LiveStreamViewerService(LiveStreamViewerRepository repository,UserProfileRepository userRepository,LiveStreamRepository liveStreamRepository)
    {
        super(repository);
        this.livestreamviewerRepository = repository;
        this.userRepository = userRepository;
        this.liveStreamRepository = liveStreamRepository;
    }

    @Override
    public LiveStreamViewer save(LiveStreamViewer livestreamviewer) {


    

    


    

    

    if (livestreamviewer.getUser() != null
        && livestreamviewer.getUser().getId() != null) {
        UserProfile existingUser = userRepository.findById(
        livestreamviewer.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        livestreamviewer.setUser(existingUser);
        }
    
    if (livestreamviewer.getLiveStream() != null
        && livestreamviewer.getLiveStream().getId() != null) {
        LiveStream existingLiveStream = liveStreamRepository.findById(
        livestreamviewer.getLiveStream().getId()
        ).orElseThrow(() -> new RuntimeException("LiveStream not found"));
        livestreamviewer.setLiveStream(existingLiveStream);
        }
    

        return livestreamviewerRepository.save(livestreamviewer);
    }


    public LiveStreamViewer update(Long id, LiveStreamViewer livestreamviewerRequest) {
        LiveStreamViewer existing = livestreamviewerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("LiveStreamViewer not found"));

    // Copier les champs simples
        existing.setJoinTime(livestreamviewerRequest.getJoinTime());
        existing.setLeaveTime(livestreamviewerRequest.getLeaveTime());

// Relations ManyToOne : mise à jour conditionnelle
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

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    


        return livestreamviewerRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<LiveStreamViewer> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

LiveStreamViewer entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    


// --- Dissocier ManyToMany ---

    

    



// --- Dissocier OneToOne ---

    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getUser() != null) {
        entity.setUser(null);
        }
    

    
        if (entity.getLiveStream() != null) {
        entity.setLiveStream(null);
        }
    


repository.delete(entity);
return true;
}
}