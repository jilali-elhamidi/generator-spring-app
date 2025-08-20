package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserConnection;
import com.example.modules.entertainment_ecosystem.repository.UserConnectionRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.ConnectionType;
import com.example.modules.entertainment_ecosystem.repository.ConnectionTypeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class UserConnectionService extends BaseService<UserConnection> {

    protected final UserConnectionRepository userconnectionRepository;
    private final UserProfileRepository user1Repository;
    private final UserProfileRepository user2Repository;
    private final ConnectionTypeRepository typeRepository;

    public UserConnectionService(UserConnectionRepository repository,UserProfileRepository user1Repository,UserProfileRepository user2Repository,ConnectionTypeRepository typeRepository)
    {
        super(repository);
        this.userconnectionRepository = repository;
        this.user1Repository = user1Repository;
        this.user2Repository = user2Repository;
        this.typeRepository = typeRepository;
    }

    @Override
    public UserConnection save(UserConnection userconnection) {


    

    

    


    

    

    

    if (userconnection.getUser1() != null
        && userconnection.getUser1().getId() != null) {
        UserProfile existingUser1 = user1Repository.findById(
        userconnection.getUser1().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        userconnection.setUser1(existingUser1);
        }
    
    if (userconnection.getUser2() != null
        && userconnection.getUser2().getId() != null) {
        UserProfile existingUser2 = user2Repository.findById(
        userconnection.getUser2().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        userconnection.setUser2(existingUser2);
        }
    
    if (userconnection.getType() != null
        && userconnection.getType().getId() != null) {
        ConnectionType existingType = typeRepository.findById(
        userconnection.getType().getId()
        ).orElseThrow(() -> new RuntimeException("ConnectionType not found"));
        userconnection.setType(existingType);
        }
    

        return userconnectionRepository.save(userconnection);
    }


    public UserConnection update(Long id, UserConnection userconnectionRequest) {
        UserConnection existing = userconnectionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserConnection not found"));

    // Copier les champs simples
        existing.setConnectionDate(userconnectionRequest.getConnectionDate());

// Relations ManyToOne : mise à jour conditionnelle
        if (userconnectionRequest.getUser1() != null &&
        userconnectionRequest.getUser1().getId() != null) {

        UserProfile existingUser1 = user1Repository.findById(
        userconnectionRequest.getUser1().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

        existing.setUser1(existingUser1);
        } else {
        existing.setUser1(null);
        }
        if (userconnectionRequest.getUser2() != null &&
        userconnectionRequest.getUser2().getId() != null) {

        UserProfile existingUser2 = user2Repository.findById(
        userconnectionRequest.getUser2().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

        existing.setUser2(existingUser2);
        } else {
        existing.setUser2(null);
        }
        if (userconnectionRequest.getType() != null &&
        userconnectionRequest.getType().getId() != null) {

        ConnectionType existingType = typeRepository.findById(
        userconnectionRequest.getType().getId()
        ).orElseThrow(() -> new RuntimeException("ConnectionType not found"));

        existing.setType(existingType);
        } else {
        existing.setType(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    

    


        return userconnectionRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<UserConnection> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

UserConnection entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    

    


// --- Dissocier ManyToMany ---

    

    

    



// --- Dissocier OneToOne ---

    

    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getUser1() != null) {
        entity.setUser1(null);
        }
    

    
        if (entity.getUser2() != null) {
        entity.setUser2(null);
        }
    

    
        if (entity.getType() != null) {
        entity.setType(null);
        }
    


repository.delete(entity);
return true;
}
}