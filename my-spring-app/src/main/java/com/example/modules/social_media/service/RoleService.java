package com.example.modules.social_media.service;

import com.example.core.service.BaseService;
import com.example.modules.social_media.model.Role;
import com.example.modules.social_media.repository.RoleRepository;
import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.repository.ProfileRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class RoleService extends BaseService<Role> {

    protected final RoleRepository roleRepository;
    private final ProfileRepository profilesRepository;

    public RoleService(RoleRepository repository,ProfileRepository profilesRepository)
    {
        super(repository);
        this.roleRepository = repository;
        this.profilesRepository = profilesRepository;
    }

    @Override
    public Role save(Role role) {

        return roleRepository.save(role);
    }


    public Role update(Long id, Role roleRequest) {
        Role existing = roleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Role not found"));

    // Copier les champs simples
        existing.setName(roleRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

        if (roleRequest.getProfiles() != null) {
            existing.getProfiles().clear();
            List<Profile> profilesList = roleRequest.getProfiles().stream()
                .map(item -> profilesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Profile not found")))
                .collect(Collectors.toList());
        existing.getProfiles().addAll(profilesList);
        }

// Relations OneToMany : synchronisation sécurisée

        return roleRepository.save(existing);
    }
}