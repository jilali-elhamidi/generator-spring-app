package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserRating;
import com.example.modules.entertainment_ecosystem.repository.UserRatingRepository;
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
public class UserRatingService extends BaseService<UserRating> {

    protected final UserRatingRepository userratingRepository;
    private final UserProfileRepository userRepository;
    private final UserProfileRepository ratedUserRepository;

    public UserRatingService(UserRatingRepository repository, UserProfileRepository userRepository, UserProfileRepository ratedUserRepository)
    {
        super(repository);
        this.userratingRepository = repository;
        this.userRepository = userRepository;
        this.ratedUserRepository = ratedUserRepository;
    }

    @Override
    public UserRating save(UserRating userrating) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (userrating.getUser() != null) {
            if (userrating.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    userrating.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + userrating.getUser().getId()));
                userrating.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(userrating.getUser());
                userrating.setUser(newUser);
            }
        }
        
        if (userrating.getRatedUser() != null) {
            if (userrating.getRatedUser().getId() != null) {
                UserProfile existingRatedUser = ratedUserRepository.findById(
                    userrating.getRatedUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + userrating.getRatedUser().getId()));
                userrating.setRatedUser(existingRatedUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newRatedUser = ratedUserRepository.save(userrating.getRatedUser());
                userrating.setRatedUser(newRatedUser);
            }
        }
        
    // ---------- OneToOne ----------
    return userratingRepository.save(userrating);
}


    public UserRating update(Long id, UserRating userratingRequest) {
        UserRating existing = userratingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserRating not found"));

    // Copier les champs simples
        existing.setRating(userratingRequest.getRating());
        existing.setRatingDate(userratingRequest.getRatingDate());

    // ---------- Relations ManyToOne ----------
        if (userratingRequest.getUser() != null &&
            userratingRequest.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                userratingRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
        if (userratingRequest.getRatedUser() != null &&
            userratingRequest.getRatedUser().getId() != null) {

            UserProfile existingRatedUser = ratedUserRepository.findById(
                userratingRequest.getRatedUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setRatedUser(existingRatedUser);
        } else {
            existing.setRatedUser(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return userratingRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<UserRating> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        UserRating entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getUser() != null) {
            entity.setUser(null);
        }
        
        if (entity.getRatedUser() != null) {
            entity.setRatedUser(null);
        }
        
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<UserRating> saveAll(List<UserRating> userratingList) {

        return userratingRepository.saveAll(userratingList);
    }

}