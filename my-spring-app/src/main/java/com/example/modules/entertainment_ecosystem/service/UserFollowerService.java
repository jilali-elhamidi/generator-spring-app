package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserFollower;
import com.example.modules.entertainment_ecosystem.repository.UserFollowerRepository;

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
public class UserFollowerService extends BaseService<UserFollower> {

    protected final UserFollowerRepository userfollowerRepository;
    
    protected final UserProfileRepository followerRepository;
    
    protected final UserProfileRepository followedRepository;
    

    public UserFollowerService(UserFollowerRepository repository, UserProfileRepository followerRepository, UserProfileRepository followedRepository)
    {
        super(repository);
        this.userfollowerRepository = repository;
        
        this.followerRepository = followerRepository;
        
        this.followedRepository = followedRepository;
        
    }

    @Transactional
    @Override
    public UserFollower save(UserFollower userfollower) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (userfollower.getFollower() != null) {
            if (userfollower.getFollower().getId() != null) {
                UserProfile existingFollower = followerRepository.findById(
                    userfollower.getFollower().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + userfollower.getFollower().getId()));
                userfollower.setFollower(existingFollower);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newFollower = followerRepository.save(userfollower.getFollower());
                userfollower.setFollower(newFollower);
            }
        }
        
        if (userfollower.getFollowed() != null) {
            if (userfollower.getFollowed().getId() != null) {
                UserProfile existingFollowed = followedRepository.findById(
                    userfollower.getFollowed().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + userfollower.getFollowed().getId()));
                userfollower.setFollowed(existingFollowed);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newFollowed = followedRepository.save(userfollower.getFollowed());
                userfollower.setFollowed(newFollowed);
            }
        }
        
    // ---------- OneToOne ----------
    return userfollowerRepository.save(userfollower);
}

    @Transactional
    @Override
    public UserFollower update(Long id, UserFollower userfollowerRequest) {
        UserFollower existing = userfollowerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserFollower not found"));

    // Copier les champs simples
        existing.setFollowDate(userfollowerRequest.getFollowDate());

    // ---------- Relations ManyToOne ----------
        if (userfollowerRequest.getFollower() != null &&
            userfollowerRequest.getFollower().getId() != null) {

            UserProfile existingFollower = followerRepository.findById(
                userfollowerRequest.getFollower().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setFollower(existingFollower);
        } else {
            existing.setFollower(null);
        }
        
        if (userfollowerRequest.getFollowed() != null &&
            userfollowerRequest.getFollowed().getId() != null) {

            UserProfile existingFollowed = followedRepository.findById(
                userfollowerRequest.getFollowed().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setFollowed(existingFollowed);
        } else {
            existing.setFollowed(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return userfollowerRepository.save(existing);
}

    // Pagination simple
    public Page<UserFollower> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<UserFollower> search(Map<String, String> filters, Pageable pageable) {
        return super.search(UserFollower.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<UserFollower> saveAll(List<UserFollower> userfollowerList) {
        return super.saveAll(userfollowerList);
    }

}