package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserActivityLog;
import com.example.modules.entertainment_ecosystem.repository.UserActivityLogRepository;

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
public class UserActivityLogService extends BaseService<UserActivityLog> {

    protected final UserActivityLogRepository useractivitylogRepository;
    
    protected final UserProfileRepository userRepository;
    

    public UserActivityLogService(UserActivityLogRepository repository, UserProfileRepository userRepository)
    {
        super(repository);
        this.useractivitylogRepository = repository;
        
        this.userRepository = userRepository;
        
    }

    @Transactional
    @Override
    public UserActivityLog save(UserActivityLog useractivitylog) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (useractivitylog.getUser() != null) {
            if (useractivitylog.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    useractivitylog.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + useractivitylog.getUser().getId()));
                useractivitylog.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(useractivitylog.getUser());
                useractivitylog.setUser(newUser);
            }
        }
        
    // ---------- OneToOne ----------
    return useractivitylogRepository.save(useractivitylog);
}

    @Transactional
    @Override
    public UserActivityLog update(Long id, UserActivityLog useractivitylogRequest) {
        UserActivityLog existing = useractivitylogRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserActivityLog not found"));

    // Copier les champs simples
        existing.setActivityType(useractivitylogRequest.getActivityType());
        existing.setActivityDate(useractivitylogRequest.getActivityDate());
        existing.setDetails(useractivitylogRequest.getDetails());

    // ---------- Relations ManyToOne ----------
        if (useractivitylogRequest.getUser() != null &&
            useractivitylogRequest.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                useractivitylogRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return useractivitylogRepository.save(existing);
}

    // Pagination simple
    public Page<UserActivityLog> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<UserActivityLog> search(Map<String, String> filters, Pageable pageable) {
        return super.search(UserActivityLog.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<UserActivityLog> saveAll(List<UserActivityLog> useractivitylogList) {
        return super.saveAll(useractivitylogList);
    }

}