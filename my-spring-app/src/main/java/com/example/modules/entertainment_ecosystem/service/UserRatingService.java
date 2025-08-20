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

    public UserRatingService(UserRatingRepository repository,UserProfileRepository userRepository,UserProfileRepository ratedUserRepository)
    {
        super(repository);
        this.userratingRepository = repository;
        this.userRepository = userRepository;
        this.ratedUserRepository = ratedUserRepository;
    }

    @Override
    public UserRating save(UserRating userrating) {


    

    


    

    

    if (userrating.getUser() != null
        && userrating.getUser().getId() != null) {
        UserProfile existingUser = userRepository.findById(
        userrating.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        userrating.setUser(existingUser);
        }
    
    if (userrating.getRatedUser() != null
        && userrating.getRatedUser().getId() != null) {
        UserProfile existingRatedUser = ratedUserRepository.findById(
        userrating.getRatedUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        userrating.setRatedUser(existingRatedUser);
        }
    

        return userratingRepository.save(userrating);
    }


    public UserRating update(Long id, UserRating userratingRequest) {
        UserRating existing = userratingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserRating not found"));

    // Copier les champs simples
        existing.setRating(userratingRequest.getRating());
        existing.setRatingDate(userratingRequest.getRatingDate());

// Relations ManyToOne : mise à jour conditionnelle
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

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    


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
}