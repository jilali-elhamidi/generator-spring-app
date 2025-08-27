package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserLoyaltyProgram;
import com.example.modules.entertainment_ecosystem.repository.UserLoyaltyProgramRepository;

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
public class UserLoyaltyProgramService extends BaseService<UserLoyaltyProgram> {

    protected final UserLoyaltyProgramRepository userloyaltyprogramRepository;
    
    protected final UserProfileRepository userRepository;
    

    public UserLoyaltyProgramService(UserLoyaltyProgramRepository repository, UserProfileRepository userRepository)
    {
        super(repository);
        this.userloyaltyprogramRepository = repository;
        
        this.userRepository = userRepository;
        
    }

    @Transactional
    @Override
    public UserLoyaltyProgram save(UserLoyaltyProgram userloyaltyprogram) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
        if (userloyaltyprogram.getUser() != null) {
            if (userloyaltyprogram.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(userloyaltyprogram.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                        + userloyaltyprogram.getUser().getId()));
                userloyaltyprogram.setUser(existingUser);
            } else {
                // Nouvel objet → sauvegarde d'abord
                UserProfile newUser = userRepository.save(userloyaltyprogram.getUser());
                userloyaltyprogram.setUser(newUser);
            }

            userloyaltyprogram.getUser().setLoyaltyProgram(userloyaltyprogram);
        }
        
    return userloyaltyprogramRepository.save(userloyaltyprogram);
}

    @Transactional
    @Override
    public UserLoyaltyProgram update(Long id, UserLoyaltyProgram userloyaltyprogramRequest) {
        UserLoyaltyProgram existing = userloyaltyprogramRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserLoyaltyProgram not found"));

    // Copier les champs simples
        existing.setName(userloyaltyprogramRequest.getName());
        existing.setPointsBalance(userloyaltyprogramRequest.getPointsBalance());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
        if (userloyaltyprogramRequest.getUser() != null &&userloyaltyprogramRequest.getUser().getId() != null) {

        UserProfile user = userRepository.findById(userloyaltyprogramRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));

        existing.setUser(user);
        user.setLoyaltyProgram(existing);
        }
    
    return userloyaltyprogramRepository.save(existing);
}

    // Pagination simple
    public Page<UserLoyaltyProgram> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<UserLoyaltyProgram> search(Map<String, String> filters, Pageable pageable) {
        return super.search(UserLoyaltyProgram.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<UserLoyaltyProgram> saveAll(List<UserLoyaltyProgram> userloyaltyprogramList) {
        return super.saveAll(userloyaltyprogramList);
    }

}