package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserSetting;
import com.example.modules.entertainment_ecosystem.repository.UserSettingRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class UserSettingService extends BaseService<UserSetting> {

    protected final UserSettingRepository usersettingRepository;
    private final UserProfileRepository userRepository;

    public UserSettingService(UserSettingRepository repository,UserProfileRepository userRepository)
    {
        super(repository);
        this.usersettingRepository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public UserSetting save(UserSetting usersetting) {


    

    if (usersetting.getUser() != null
        && usersetting.getUser().getId() != null) {
        UserProfile existingUser = userRepository.findById(
        usersetting.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        usersetting.setUser(existingUser);
        }
    

        return usersettingRepository.save(usersetting);
    }


    public UserSetting update(Long id, UserSetting usersettingRequest) {
        UserSetting existing = usersettingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserSetting not found"));

    // Copier les champs simples
        existing.setSettingName(usersettingRequest.getSettingName());
        existing.setSettingValue(usersettingRequest.getSettingValue());

// Relations ManyToOne : mise à jour conditionnelle
        if (usersettingRequest.getUser() != null &&
        usersettingRequest.getUser().getId() != null) {

        UserProfile existingUser = userRepository.findById(
        usersettingRequest.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

        existing.setUser(existingUser);
        } else {
        existing.setUser(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    


        return usersettingRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<UserSetting> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

UserSetting entity = entityOpt.get();

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