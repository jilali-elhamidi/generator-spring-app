package com.example.modules.social_media.service;

import com.example.core.service.BaseService;
import com.example.modules.social_media.model.Role;
import com.example.modules.social_media.repository.RoleRepository;

import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.repository.ProfileRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class RoleService extends BaseService<Role> {

    protected final RoleRepository roleRepository;
    
    protected final ProfileRepository profilesRepository;
    

    public RoleService(RoleRepository repository, ProfileRepository profilesRepository)
    {
        super(repository);
        this.roleRepository = repository;
        
        this.profilesRepository = profilesRepository;
        
    }

    @Transactional
    @Override
    public Role save(Role role) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (role.getProfiles() != null &&
            !role.getProfiles().isEmpty()) {

            List<Profile> attachedProfiles = new ArrayList<>();
            for (Profile item : role.getProfiles()) {
                if (item.getId() != null) {
                    Profile existingItem = profilesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Profile not found with id " + item.getId()));
                    attachedProfiles.add(existingItem);
                } else {

                    Profile newItem = profilesRepository.save(item);
                    attachedProfiles.add(newItem);
                }
            }

            role.setProfiles(attachedProfiles);

            // côté propriétaire (Profile → Role)
            attachedProfiles.forEach(it -> it.getRoles().add(role));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return roleRepository.save(role);
}

    @Transactional
    @Override
    public Role update(Long id, Role roleRequest) {
        Role existing = roleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Role not found"));

    // Copier les champs simples
        existing.setName(roleRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
        if (roleRequest.getProfiles() != null) {
            existing.getProfiles().clear();

            List<Profile> profilesList = roleRequest.getProfiles().stream()
                .map(item -> profilesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Profile not found")))
                .collect(Collectors.toList());

            existing.getProfiles().addAll(profilesList);

            // Mettre à jour le côté inverse
            profilesList.forEach(it -> {
                if (!it.getRoles().contains(existing)) {
                    it.getRoles().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return roleRepository.save(existing);
}

    // Pagination simple
    public Page<Role> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Role> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Role.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Role> saveAll(List<Role> roleList) {
        return super.saveAll(roleList);
    }

}