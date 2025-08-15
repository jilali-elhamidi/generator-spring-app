package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserFollow;
import com.example.modules.entertainment_ecosystem.repository.UserFollowRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class UserFollowService extends BaseService<UserFollow> {

    protected final UserFollowRepository userfollowRepository;
    private final UserProfileRepository followerRepository;
    private final UserProfileRepository followedRepository;

    public UserFollowService(UserFollowRepository repository,UserProfileRepository followerRepository,UserProfileRepository followedRepository)
    {
        super(repository);
        this.userfollowRepository = repository;
        this.followerRepository = followerRepository;
        this.followedRepository = followedRepository;
    }

    @Override
    public UserFollow save(UserFollow userfollow) {


    

    

    if (userfollow.getFollower() != null
        && userfollow.getFollower().getId() != null) {
        UserProfile existingFollower = followerRepository.findById(
        userfollow.getFollower().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        userfollow.setFollower(existingFollower);
        }
    
    if (userfollow.getFollowed() != null
        && userfollow.getFollowed().getId() != null) {
        UserProfile existingFollowed = followedRepository.findById(
        userfollow.getFollowed().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        userfollow.setFollowed(existingFollowed);
        }
    

    

    


        return userfollowRepository.save(userfollow);
    }


    public UserFollow update(Long id, UserFollow userfollowRequest) {
        UserFollow existing = userfollowRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserFollow not found"));

    // Copier les champs simples
        existing.setFollowDate(userfollowRequest.getFollowDate());

// Relations ManyToOne : mise à jour conditionnelle
        if (userfollowRequest.getFollower() != null &&
        userfollowRequest.getFollower().getId() != null) {

        UserProfile existingFollower = followerRepository.findById(
        userfollowRequest.getFollower().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

        existing.setFollower(existingFollower);
        } else {
        existing.setFollower(null);
        }
        if (userfollowRequest.getFollowed() != null &&
        userfollowRequest.getFollowed().getId() != null) {

        UserProfile existingFollowed = followedRepository.findById(
        userfollowRequest.getFollowed().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

        existing.setFollowed(existingFollowed);
        } else {
        existing.setFollowed(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    


        return userfollowRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<UserFollow> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

UserFollow entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    


// --- Dissocier ManyToMany ---

    

    


// --- Dissocier OneToOne ---

    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getFollower() != null) {
        entity.setFollower(null);
        }
    

    
        if (entity.getFollowed() != null) {
        entity.setFollowed(null);
        }
    


repository.delete(entity);
return true;
}
}