package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserFollow;
import com.example.modules.entertainment_ecosystem.repository.UserFollowRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

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

        if (userfollow.getFollower() != null && userfollow.getFollower().getId() != null) {
        UserProfile follower = followerRepository.findById(userfollow.getFollower().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        userfollow.setFollower(follower);
        }

        if (userfollow.getFollowed() != null && userfollow.getFollowed().getId() != null) {
        UserProfile followed = followedRepository.findById(userfollow.getFollowed().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        userfollow.setFollowed(followed);
        }

        return userfollowRepository.save(userfollow);
    }


    public UserFollow update(Long id, UserFollow userfollowRequest) {
        UserFollow existing = userfollowRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserFollow not found"));

    // Copier les champs simples
        existing.setFollowDate(userfollowRequest.getFollowDate());

// Relations ManyToOne : mise à jour conditionnelle

        if (userfollowRequest.getFollower() != null && userfollowRequest.getFollower().getId() != null) {
        UserProfile follower = followerRepository.findById(userfollowRequest.getFollower().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setFollower(follower);
        }

        if (userfollowRequest.getFollowed() != null && userfollowRequest.getFollowed().getId() != null) {
        UserProfile followed = followedRepository.findById(userfollowRequest.getFollowed().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setFollowed(followed);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    


        return userfollowRepository.save(existing);
    }
}