package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserBlockedList;
import com.example.modules.entertainment_ecosystem.repository.UserBlockedListRepository;
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
public class UserBlockedListService extends BaseService<UserBlockedList> {

    protected final UserBlockedListRepository userblockedlistRepository;
    private final UserProfileRepository blockerRepository;
    private final UserProfileRepository blockedRepository;

    public UserBlockedListService(UserBlockedListRepository repository, UserProfileRepository blockerRepository, UserProfileRepository blockedRepository)
    {
        super(repository);
        this.userblockedlistRepository = repository;
        this.blockerRepository = blockerRepository;
        this.blockedRepository = blockedRepository;
    }

    @Override
    public UserBlockedList save(UserBlockedList userblockedlist) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (userblockedlist.getBlocker() != null &&
            userblockedlist.getBlocker().getId() != null) {

            UserProfile existingBlocker = blockerRepository.findById(
                userblockedlist.getBlocker().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            userblockedlist.setBlocker(existingBlocker);
        }
        
        if (userblockedlist.getBlocked() != null &&
            userblockedlist.getBlocked().getId() != null) {

            UserProfile existingBlocked = blockedRepository.findById(
                userblockedlist.getBlocked().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            userblockedlist.setBlocked(existingBlocked);
        }
        
    // ---------- OneToOne ----------

    return userblockedlistRepository.save(userblockedlist);
}


    public UserBlockedList update(Long id, UserBlockedList userblockedlistRequest) {
        UserBlockedList existing = userblockedlistRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserBlockedList not found"));

    // Copier les champs simples
        existing.setBlockedDate(userblockedlistRequest.getBlockedDate());

    // ---------- Relations ManyToOne ----------
        if (userblockedlistRequest.getBlocker() != null &&
            userblockedlistRequest.getBlocker().getId() != null) {

            UserProfile existingBlocker = blockerRepository.findById(
                userblockedlistRequest.getBlocker().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setBlocker(existingBlocker);
        } else {
            existing.setBlocker(null);
        }
        
        if (userblockedlistRequest.getBlocked() != null &&
            userblockedlistRequest.getBlocked().getId() != null) {

            UserProfile existingBlocked = blockedRepository.findById(
                userblockedlistRequest.getBlocked().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setBlocked(existingBlocked);
        } else {
            existing.setBlocked(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------

    return userblockedlistRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<UserBlockedList> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        UserBlockedList entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getBlocker() != null) {
            entity.setBlocker(null);
        }
        
        if (entity.getBlocked() != null) {
            entity.setBlocked(null);
        }
        
        repository.delete(entity);
        return true;
    }
}