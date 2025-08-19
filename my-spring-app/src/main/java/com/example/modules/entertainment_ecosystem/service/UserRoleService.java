package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserRole;
import com.example.modules.entertainment_ecosystem.repository.UserRoleRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class UserRoleService extends BaseService<UserRole> {

    protected final UserRoleRepository userroleRepository;
    private final UserProfileRepository usersRepository;

    public UserRoleService(UserRoleRepository repository,UserProfileRepository usersRepository)
    {
        super(repository);
        this.userroleRepository = repository;
        this.usersRepository = usersRepository;
    }

    @Override
    public UserRole save(UserRole userrole) {


    


    
        if (userrole.getUsers() != null
        && !userrole.getUsers().isEmpty()) {

        List<UserProfile> attachedUsers = userrole.getUsers().stream()
        .map(item -> usersRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserProfile not found with id " + item.getId())))
        .toList();

        userrole.setUsers(attachedUsers);

        // côté propriétaire (UserProfile → UserRole)
        attachedUsers.forEach(it -> it.getUserRoles().add(userrole));
        }
    

    

        return userroleRepository.save(userrole);
    }


    public UserRole update(Long id, UserRole userroleRequest) {
        UserRole existing = userroleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserRole not found"));

    // Copier les champs simples
        existing.setName(userroleRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée
        if (userroleRequest.getUsers() != null) {
        existing.getUsers().clear();

        List<UserProfile> usersList = userroleRequest.getUsers().stream()
        .map(item -> usersRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserProfile not found")))
        .collect(Collectors.toList());

        existing.getUsers().addAll(usersList);

        // Mettre à jour le côté inverse
        usersList.forEach(it -> {
        if (!it.getUserRoles().contains(existing)) {
        it.getUserRoles().add(existing);
        }
        });
        }

// Relations OneToMany : synchronisation sécurisée

    


        return userroleRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<UserRole> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

UserRole entity = entityOpt.get();

// --- Dissocier OneToMany ---

    


// --- Dissocier ManyToMany ---

    
        if (entity.getUsers() != null) {
        for (UserProfile item : new ArrayList<>(entity.getUsers())) {
        
            item.getUserRoles().remove(entity); // retire côté inverse
        
        }
        entity.getUsers().clear(); // puis vide côté courant
        }
    



// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}