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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class UserFollowerService extends BaseService<UserFollower> {

    protected final UserFollowerRepository userfollowerRepository;
    private final UserProfileRepository followerRepository;
    private final UserProfileRepository followedRepository;

    public UserFollowerService(UserFollowerRepository repository,UserProfileRepository followerRepository,UserProfileRepository followedRepository)
    {
        super(repository);
        this.userfollowerRepository = repository;
        this.followerRepository = followerRepository;
        this.followedRepository = followedRepository;
    }

    @Override
    public UserFollower save(UserFollower userfollower) {


    

    

    if (userfollower.getFollower() != null
        && userfollower.getFollower().getId() != null) {
        UserProfile existingFollower = followerRepository.findById(
        userfollower.getFollower().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        userfollower.setFollower(existingFollower);
        }
    
    if (userfollower.getFollowed() != null
        && userfollower.getFollowed().getId() != null) {
        UserProfile existingFollowed = followedRepository.findById(
        userfollower.getFollowed().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        userfollower.setFollowed(existingFollowed);
        }
    

    

    


        return userfollowerRepository.save(userfollower);
    }


    public UserFollower update(Long id, UserFollower userfollowerRequest) {
        UserFollower existing = userfollowerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserFollower not found"));

    // Copier les champs simples
        existing.setFollowDate(userfollowerRequest.getFollowDate());

// Relations ManyToOne : mise à jour conditionnelle
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

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    


        return userfollowerRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<UserFollower> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

UserFollower entity = entityOpt.get();

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