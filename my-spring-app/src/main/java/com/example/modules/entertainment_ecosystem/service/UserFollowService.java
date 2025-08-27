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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class UserFollowService extends BaseService<UserFollow> {

    protected final UserFollowRepository userfollowRepository;
    
    protected final UserProfileRepository followerRepository;
    
    protected final UserProfileRepository followedRepository;
    

    public UserFollowService(UserFollowRepository repository, UserProfileRepository followerRepository, UserProfileRepository followedRepository)
    {
        super(repository);
        this.userfollowRepository = repository;
        
        this.followerRepository = followerRepository;
        
        this.followedRepository = followedRepository;
        
    }

    @Transactional
    @Override
    public UserFollow save(UserFollow userfollow) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (userfollow.getFollower() != null) {
            if (userfollow.getFollower().getId() != null) {
                UserProfile existingFollower = followerRepository.findById(
                    userfollow.getFollower().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + userfollow.getFollower().getId()));
                userfollow.setFollower(existingFollower);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newFollower = followerRepository.save(userfollow.getFollower());
                userfollow.setFollower(newFollower);
            }
        }
        
        if (userfollow.getFollowed() != null) {
            if (userfollow.getFollowed().getId() != null) {
                UserProfile existingFollowed = followedRepository.findById(
                    userfollow.getFollowed().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + userfollow.getFollowed().getId()));
                userfollow.setFollowed(existingFollowed);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newFollowed = followedRepository.save(userfollow.getFollowed());
                userfollow.setFollowed(newFollowed);
            }
        }
        
    // ---------- OneToOne ----------
    return userfollowRepository.save(userfollow);
}

    @Transactional
    @Override
    public UserFollow update(Long id, UserFollow userfollowRequest) {
        UserFollow existing = userfollowRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserFollow not found"));

    // Copier les champs simples
        existing.setFollowDate(userfollowRequest.getFollowDate());

    // ---------- Relations ManyToOne ----------
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
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return userfollowRepository.save(existing);
}

    // Pagination simple
    public Page<UserFollow> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<UserFollow> search(Map<String, String> filters, Pageable pageable) {
        return super.search(UserFollow.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<UserFollow> saveAll(List<UserFollow> userfollowList) {
        return super.saveAll(userfollowList);
    }

}