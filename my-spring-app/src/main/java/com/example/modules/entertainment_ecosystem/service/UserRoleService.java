package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserRole;
import com.example.modules.entertainment_ecosystem.repository.UserRoleRepository;

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
public class UserRoleService extends BaseService<UserRole> {

    protected final UserRoleRepository userroleRepository;
    
    protected final UserProfileRepository usersRepository;
    

    public UserRoleService(UserRoleRepository repository, UserProfileRepository usersRepository)
    {
        super(repository);
        this.userroleRepository = repository;
        
        this.usersRepository = usersRepository;
        
    }

    @Transactional
    @Override
    public UserRole save(UserRole userrole) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (userrole.getUsers() != null &&
            !userrole.getUsers().isEmpty()) {

            List<UserProfile> attachedUsers = new ArrayList<>();
            for (UserProfile item : userrole.getUsers()) {
                if (item.getId() != null) {
                    UserProfile existingItem = usersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserProfile not found with id " + item.getId()));
                    attachedUsers.add(existingItem);
                } else {

                    UserProfile newItem = usersRepository.save(item);
                    attachedUsers.add(newItem);
                }
            }

            userrole.setUsers(attachedUsers);

            // côté propriétaire (UserProfile → UserRole)
            attachedUsers.forEach(it -> it.getUserRoles().add(userrole));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return userroleRepository.save(userrole);
}

    @Transactional
    @Override
    public UserRole update(Long id, UserRole userroleRequest) {
        UserRole existing = userroleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserRole not found"));

    // Copier les champs simples
        existing.setName(userroleRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return userroleRepository.save(existing);
}

    // Pagination simple
    public Page<UserRole> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<UserRole> search(Map<String, String> filters, Pageable pageable) {
        return super.search(UserRole.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<UserRole> saveAll(List<UserRole> userroleList) {
        return super.saveAll(userroleList);
    }

}