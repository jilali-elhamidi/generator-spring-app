package com.example.modules.social_media.service;

import com.example.core.service.BaseService;
import com.example.modules.social_media.model.Role;
import com.example.modules.social_media.repository.RoleRepository;
import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.repository.ProfileRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class RoleService extends BaseService<Role> {

    protected final RoleRepository roleRepository;
    private final ProfileRepository profilesRepository;

    public RoleService(RoleRepository repository, ProfileRepository profilesRepository)
    {
        super(repository);
        this.roleRepository = repository;
        this.profilesRepository = profilesRepository;
    }

    @Override
    public Role save(Role role) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (role.getProfiles() != null &&
            !role.getProfiles().isEmpty()) {

            List<Profile> attachedProfiles = role.getProfiles().stream()
            .map(item -> profilesRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("Profile not found with id " + item.getId())))
            .toList();

            role.setProfiles(attachedProfiles);

            // côté propriétaire (Profile → Role)
            attachedProfiles.forEach(it -> it.getRoles().add(role));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return roleRepository.save(role);
}


    public Role update(Long id, Role roleRequest) {
        Role existing = roleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Role not found"));

    // Copier les champs simples
        existing.setName(roleRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
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
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Role> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Role entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
        if (entity.getProfiles() != null) {
            for (Profile item : new ArrayList<>(entity.getProfiles())) {
                
                item.getRoles().remove(entity); // retire côté inverse
            }
            entity.getProfiles().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}