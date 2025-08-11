package com.example.modules.social_media.service;

import com.example.core.service.BaseService;
import com.example.modules.social_media.model.Group;
import com.example.modules.social_media.repository.GroupRepository;
import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.repository.ProfileRepository;
import com.example.modules.social_media.model.Post;
import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.repository.ProfileRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class GroupService extends BaseService<Group> {

    protected final GroupRepository groupRepository;
    private final ProfileRepository membersRepository;
    private final ProfileRepository ownerRepository;

    public GroupService(GroupRepository repository,ProfileRepository membersRepository,ProfileRepository ownerRepository)
    {
        super(repository);
        this.groupRepository = repository;
        this.membersRepository = membersRepository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Group save(Group group) {

        if (group.getOwner() != null && group.getOwner().getId() != null) {
        Profile owner = ownerRepository.findById(group.getOwner().getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        group.setOwner(owner);
        }

        if (group.getPosts() != null) {
            for (Post item : group.getPosts()) {
            item.setGroup(group);
            }
        }

        return groupRepository.save(group);
    }


    public Group update(Long id, Group groupRequest) {
        Group existing = groupRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Group not found"));

    // Copier les champs simples
        existing.setName(groupRequest.getName());
        existing.setDescription(groupRequest.getDescription());

// Relations ManyToOne : mise à jour conditionnelle

        if (groupRequest.getOwner() != null && groupRequest.getOwner().getId() != null) {
        Profile owner = ownerRepository.findById(groupRequest.getOwner().getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        existing.setOwner(owner);
        }

// Relations ManyToMany : synchronisation sécurisée

        if (groupRequest.getMembers() != null) {
            existing.getMembers().clear();
            List<Profile> membersList = groupRequest.getMembers().stream()
                .map(item -> membersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Profile not found")))
                .collect(Collectors.toList());
        existing.getMembers().addAll(membersList);
        }

// Relations OneToMany : synchronisation sécurisée

        existing.getPosts().clear();
        if (groupRequest.getPosts() != null) {
            for (var item : groupRequest.getPosts()) {
            item.setGroup(existing);
            existing.getPosts().add(item);
            }
        }

        return groupRepository.save(existing);
    }
}