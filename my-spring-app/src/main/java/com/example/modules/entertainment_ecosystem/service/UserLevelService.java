package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserLevel;
import com.example.modules.entertainment_ecosystem.repository.UserLevelRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class UserLevelService extends BaseService<UserLevel> {

    protected final UserLevelRepository userlevelRepository;
    private final UserProfileRepository usersRepository;

    public UserLevelService(UserLevelRepository repository,UserProfileRepository usersRepository)
    {
        super(repository);
        this.userlevelRepository = repository;
        this.usersRepository = usersRepository;
    }

    @Override
    public UserLevel save(UserLevel userlevel) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userlevel.getUsers() != null) {
            List<UserProfile> managedUsers = new ArrayList<>();
            for (UserProfile item : userlevel.getUsers()) {
            if (item.getId() != null) {
            UserProfile existingItem = usersRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("UserProfile not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setUserLevel(userlevel);
            managedUsers.add(existingItem);
            } else {
            item.setUserLevel(userlevel);
            managedUsers.add(item);
            }
            }
            userlevel.setUsers(managedUsers);
            }
        
    


    

    

        return userlevelRepository.save(userlevel);
    }


    public UserLevel update(Long id, UserLevel userlevelRequest) {
        UserLevel existing = userlevelRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserLevel not found"));

    // Copier les champs simples
        existing.setLevelNumber(userlevelRequest.getLevelNumber());
        existing.setName(userlevelRequest.getName());
        existing.setPointsRequired(userlevelRequest.getPointsRequired());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getUsers().clear();

        if (userlevelRequest.getUsers() != null) {
        for (var item : userlevelRequest.getUsers()) {
        UserProfile existingItem;
        if (item.getId() != null) {
        existingItem = usersRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setUserLevel(existing);

        // Ajouter directement dans la collection existante
        existing.getUsers().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    


        return userlevelRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<UserLevel> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

UserLevel entity = entityOpt.get();

// --- Dissocier OneToMany ---

    
        if (entity.getUsers() != null) {
        for (var child : entity.getUsers()) {
        
            child.setUserLevel(null); // retirer la référence inverse
        
        }
        entity.getUsers().clear();
        }
    


// --- Dissocier ManyToMany ---

    



// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}