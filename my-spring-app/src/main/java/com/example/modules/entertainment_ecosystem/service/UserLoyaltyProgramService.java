package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserLoyaltyProgram;
import com.example.modules.entertainment_ecosystem.repository.UserLoyaltyProgramRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class UserLoyaltyProgramService extends BaseService<UserLoyaltyProgram> {

    protected final UserLoyaltyProgramRepository userloyaltyprogramRepository;
    private final UserProfileRepository userRepository;

    public UserLoyaltyProgramService(UserLoyaltyProgramRepository repository, UserProfileRepository userRepository)
    {
        super(repository);
        this.userloyaltyprogramRepository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public UserLoyaltyProgram save(UserLoyaltyProgram userloyaltyprogram) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
        if (userloyaltyprogram.getUser() != null) {
            
            
                // Vérifier si l'entité est déjà persistée
            userloyaltyprogram.setUser(
                userRepository.findById(userloyaltyprogram.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("user not found"))
            );
            
            userloyaltyprogram.getUser().setLoyaltyProgram(userloyaltyprogram);
        }
        

    return userloyaltyprogramRepository.save(userloyaltyprogram);
}


    public UserLoyaltyProgram update(Long id, UserLoyaltyProgram userloyaltyprogramRequest) {
        UserLoyaltyProgram existing = userloyaltyprogramRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserLoyaltyProgram not found"));

    // Copier les champs simples
        existing.setName(userloyaltyprogramRequest.getName());
        existing.setPointsBalance(userloyaltyprogramRequest.getPointsBalance());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
            if (userloyaltyprogramRequest.getUser() != null &&
            userloyaltyprogramRequest.getUser().getId() != null) {

            UserProfile user = userRepository.findById(
                userloyaltyprogramRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setUser(user);

            
            user.setLoyaltyProgram(existing);
            
        }
        

    return userloyaltyprogramRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<UserLoyaltyProgram> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        UserLoyaltyProgram entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
        if (entity.getUser() != null) {
            entity.getUser().setLoyaltyProgram(null);
            entity.setUser(null);
        }
        
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}