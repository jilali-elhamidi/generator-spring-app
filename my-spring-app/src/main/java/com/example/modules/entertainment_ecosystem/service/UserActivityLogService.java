package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserActivityLog;
import com.example.modules.entertainment_ecosystem.repository.UserActivityLogRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class UserActivityLogService extends BaseService<UserActivityLog> {

    protected final UserActivityLogRepository useractivitylogRepository;
    private final UserProfileRepository userRepository;

    public UserActivityLogService(UserActivityLogRepository repository,UserProfileRepository userRepository)
    {
        super(repository);
        this.useractivitylogRepository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public UserActivityLog save(UserActivityLog useractivitylog) {

        if (useractivitylog.getUser() != null && useractivitylog.getUser().getId() != null) {
        UserProfile user = userRepository.findById(useractivitylog.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        useractivitylog.setUser(user);
        }

        return useractivitylogRepository.save(useractivitylog);
    }


    public UserActivityLog update(Long id, UserActivityLog useractivitylogRequest) {
        UserActivityLog existing = useractivitylogRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserActivityLog not found"));

    // Copier les champs simples
        existing.setActivityType(useractivitylogRequest.getActivityType());
        existing.setActivityDate(useractivitylogRequest.getActivityDate());
        existing.setDetails(useractivitylogRequest.getDetails());

// Relations ManyToOne : mise à jour conditionnelle

        if (useractivitylogRequest.getUser() != null && useractivitylogRequest.getUser().getId() != null) {
        UserProfile user = userRepository.findById(useractivitylogRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setUser(user);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    


        return useractivitylogRepository.save(existing);
    }
}