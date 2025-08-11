package com.example.modules.social_media.service;

import com.example.core.service.BaseService;
import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.repository.ProfileRepository;
import com.example.modules.social_media.model.Post;
import com.example.modules.social_media.model.Comment;
import com.example.modules.social_media.model.Message;
import com.example.modules.social_media.model.Message;
import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.repository.ProfileRepository;
import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.repository.ProfileRepository;
import com.example.modules.social_media.model.Role;
import com.example.modules.social_media.repository.RoleRepository;
import com.example.modules.social_media.model.Group;
import com.example.modules.social_media.repository.GroupRepository;
import com.example.modules.social_media.model.Notification;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class ProfileService extends BaseService<Profile> {

    protected final ProfileRepository profileRepository;
    private final ProfileRepository followersRepository;
    private final ProfileRepository followingRepository;
    private final RoleRepository rolesRepository;
    private final GroupRepository groupsRepository;

    public ProfileService(ProfileRepository repository,ProfileRepository followersRepository,ProfileRepository followingRepository,RoleRepository rolesRepository,GroupRepository groupsRepository)
    {
        super(repository);
        this.profileRepository = repository;
        this.followersRepository = followersRepository;
        this.followingRepository = followingRepository;
        this.rolesRepository = rolesRepository;
        this.groupsRepository = groupsRepository;
    }

    @Override
    public Profile save(Profile profile) {

        if (profile.getPosts() != null) {
            for (Post item : profile.getPosts()) {
            item.setAuthor(profile);
            }
        }

        if (profile.getComments() != null) {
            for (Comment item : profile.getComments()) {
            item.setAuthor(profile);
            }
        }

        if (profile.getReceivedMessages() != null) {
            for (Message item : profile.getReceivedMessages()) {
            item.setRecipient(profile);
            }
        }

        if (profile.getSentMessages() != null) {
            for (Message item : profile.getSentMessages()) {
            item.setSender(profile);
            }
        }

        if (profile.getNotifications() != null) {
            for (Notification item : profile.getNotifications()) {
            item.setRecipient(profile);
            }
        }

        return profileRepository.save(profile);
    }


    public Profile update(Long id, Profile profileRequest) {
        Profile existing = profileRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Profile not found"));

    // Copier les champs simples
        existing.setName(profileRequest.getName());
        existing.setBio(profileRequest.getBio());
        existing.setHandle(profileRequest.getHandle());
        existing.setEmail(profileRequest.getEmail());
        existing.setRegistrationDate(profileRequest.getRegistrationDate());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

        if (profileRequest.getFollowers() != null) {
            existing.getFollowers().clear();
            List<Profile> followersList = profileRequest.getFollowers().stream()
                .map(item -> followersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Profile not found")))
                .collect(Collectors.toList());
        existing.getFollowers().addAll(followersList);
        }

        if (profileRequest.getFollowing() != null) {
            existing.getFollowing().clear();
            List<Profile> followingList = profileRequest.getFollowing().stream()
                .map(item -> followingRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Profile not found")))
                .collect(Collectors.toList());
        existing.getFollowing().addAll(followingList);
        }

        if (profileRequest.getRoles() != null) {
            existing.getRoles().clear();
            List<Role> rolesList = profileRequest.getRoles().stream()
                .map(item -> rolesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Role not found")))
                .collect(Collectors.toList());
        existing.getRoles().addAll(rolesList);
        }

        if (profileRequest.getGroups() != null) {
            existing.getGroups().clear();
            List<Group> groupsList = profileRequest.getGroups().stream()
                .map(item -> groupsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Group not found")))
                .collect(Collectors.toList());
        existing.getGroups().addAll(groupsList);
        }

// Relations OneToMany : synchronisation sécurisée

        existing.getPosts().clear();
        if (profileRequest.getPosts() != null) {
            for (var item : profileRequest.getPosts()) {
            item.setAuthor(existing);
            existing.getPosts().add(item);
            }
        }

        existing.getComments().clear();
        if (profileRequest.getComments() != null) {
            for (var item : profileRequest.getComments()) {
            item.setAuthor(existing);
            existing.getComments().add(item);
            }
        }

        existing.getReceivedMessages().clear();
        if (profileRequest.getReceivedMessages() != null) {
            for (var item : profileRequest.getReceivedMessages()) {
            item.setRecipient(existing);
            existing.getReceivedMessages().add(item);
            }
        }

        existing.getSentMessages().clear();
        if (profileRequest.getSentMessages() != null) {
            for (var item : profileRequest.getSentMessages()) {
            item.setSender(existing);
            existing.getSentMessages().add(item);
            }
        }

        existing.getNotifications().clear();
        if (profileRequest.getNotifications() != null) {
            for (var item : profileRequest.getNotifications()) {
            item.setRecipient(existing);
            existing.getNotifications().add(item);
            }
        }

        return profileRepository.save(existing);
    }
}