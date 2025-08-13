package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserPlaylist;
import com.example.modules.entertainment_ecosystem.repository.UserPlaylistRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.UserPlaylistItem;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class UserPlaylistService extends BaseService<UserPlaylist> {

    protected final UserPlaylistRepository userplaylistRepository;
    private final UserProfileRepository userRepository;

    public UserPlaylistService(UserPlaylistRepository repository,UserProfileRepository userRepository)
    {
        super(repository);
        this.userplaylistRepository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public UserPlaylist save(UserPlaylist userplaylist) {

        if (userplaylist.getUser() != null && userplaylist.getUser().getId() != null) {
        UserProfile user = userRepository.findById(userplaylist.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        userplaylist.setUser(user);
        }

        if (userplaylist.getItems() != null) {
            for (UserPlaylistItem item : userplaylist.getItems()) {
            item.setPlaylist(userplaylist);
            }
        }

        return userplaylistRepository.save(userplaylist);
    }


    public UserPlaylist update(Long id, UserPlaylist userplaylistRequest) {
        UserPlaylist existing = userplaylistRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserPlaylist not found"));

    // Copier les champs simples
        existing.setName(userplaylistRequest.getName());
        existing.setIsPublic(userplaylistRequest.getIsPublic());

// Relations ManyToOne : mise à jour conditionnelle

        if (userplaylistRequest.getUser() != null && userplaylistRequest.getUser().getId() != null) {
        UserProfile user = userRepository.findById(userplaylistRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setUser(user);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getItems().clear();
        if (userplaylistRequest.getItems() != null) {
            for (var item : userplaylistRequest.getItems()) {
            item.setPlaylist(existing);
            existing.getItems().add(item);
            }
        }

    

    


        return userplaylistRepository.save(existing);
    }
}