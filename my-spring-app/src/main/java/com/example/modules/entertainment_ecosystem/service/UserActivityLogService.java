package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserActivityLog;
import com.example.modules.entertainment_ecosystem.repository.UserActivityLogRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class UserActivityLogService extends BaseService<UserActivityLog> {

    protected final UserActivityLogRepository useractivitylogRepository;
    private final UserProfileRepository userRepository;

    public UserActivityLogService(UserActivityLogRepository repository, UserProfileRepository userRepository)
    {
        super(repository);
        this.useractivitylogRepository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public UserActivityLog save(UserActivityLog useractivitylog) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (useractivitylog.getUser() != null &&
            useractivitylog.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                useractivitylog.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            useractivitylog.setUser(existingUser);
        }
        
    // ---------- OneToOne ----------

    return useractivitylogRepository.save(useractivitylog);
}


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
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------

    return useractivitylogRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<UserActivityLog> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        UserActivityLog entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getUser() != null) {
            entity.setUser(null);
        }
        
        repository.delete(entity);
        return true;
    }
}