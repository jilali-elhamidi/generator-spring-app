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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class UserBlockedListService extends BaseService<UserBlockedList> {

    protected final UserBlockedListRepository userblockedlistRepository;
    
    protected final UserProfileRepository blockerRepository;
    
    protected final UserProfileRepository blockedRepository;
    

    public UserBlockedListService(UserBlockedListRepository repository, UserProfileRepository blockerRepository, UserProfileRepository blockedRepository)
    {
        super(repository);
        this.userblockedlistRepository = repository;
        
        this.blockerRepository = blockerRepository;
        
        this.blockedRepository = blockedRepository;
        
    }

    @Transactional
    @Override
    public UserBlockedList save(UserBlockedList userblockedlist) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (userblockedlist.getBlocker() != null) {
            if (userblockedlist.getBlocker().getId() != null) {
                UserProfile existingBlocker = blockerRepository.findById(
                    userblockedlist.getBlocker().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + userblockedlist.getBlocker().getId()));
                userblockedlist.setBlocker(existingBlocker);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newBlocker = blockerRepository.save(userblockedlist.getBlocker());
                userblockedlist.setBlocker(newBlocker);
            }
        }
        
        if (userblockedlist.getBlocked() != null) {
            if (userblockedlist.getBlocked().getId() != null) {
                UserProfile existingBlocked = blockedRepository.findById(
                    userblockedlist.getBlocked().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + userblockedlist.getBlocked().getId()));
                userblockedlist.setBlocked(existingBlocked);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newBlocked = blockedRepository.save(userblockedlist.getBlocked());
                userblockedlist.setBlocked(newBlocked);
            }
        }
        
    // ---------- OneToOne ----------
    return userblockedlistRepository.save(userblockedlist);
}

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return userblockedlistRepository.save(existing);
}

    // Pagination simple
    public Page<UserBlockedList> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<UserBlockedList> search(Map<String, String> filters, Pageable pageable) {
        return super.search(UserBlockedList.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<UserBlockedList> saveAll(List<UserBlockedList> userblockedlistList) {
        return super.saveAll(userblockedlistList);
    }

}