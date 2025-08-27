package com.example.modules.social_media.service;

import com.example.core.service.BaseService;
import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.repository.ProfileRepository;

import com.example.modules.social_media.model.Post;
import com.example.modules.social_media.repository.PostRepository;

import com.example.modules.social_media.model.Comment;
import com.example.modules.social_media.repository.CommentRepository;

import com.example.modules.social_media.model.Message;
import com.example.modules.social_media.repository.MessageRepository;

import com.example.modules.social_media.model.Message;
import com.example.modules.social_media.repository.MessageRepository;

import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.repository.ProfileRepository;

import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.repository.ProfileRepository;

import com.example.modules.social_media.model.Role;
import com.example.modules.social_media.repository.RoleRepository;

import com.example.modules.social_media.model.Group;
import com.example.modules.social_media.repository.GroupRepository;

import com.example.modules.social_media.model.Notification;
import com.example.modules.social_media.repository.NotificationRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ProfileService extends BaseService<Profile> {

    protected final ProfileRepository profileRepository;
    
    protected final PostRepository postsRepository;
    
    protected final CommentRepository commentsRepository;
    
    protected final MessageRepository receivedMessagesRepository;
    
    protected final MessageRepository sentMessagesRepository;
    
    protected final ProfileRepository followersRepository;
    
    protected final ProfileRepository followingRepository;
    
    protected final RoleRepository rolesRepository;
    
    protected final GroupRepository groupsRepository;
    
    protected final NotificationRepository notificationsRepository;
    

    public ProfileService(ProfileRepository repository, PostRepository postsRepository, CommentRepository commentsRepository, MessageRepository receivedMessagesRepository, MessageRepository sentMessagesRepository, ProfileRepository followersRepository, ProfileRepository followingRepository, RoleRepository rolesRepository, GroupRepository groupsRepository, NotificationRepository notificationsRepository)
    {
        super(repository);
        this.profileRepository = repository;
        
        this.postsRepository = postsRepository;
        
        this.commentsRepository = commentsRepository;
        
        this.receivedMessagesRepository = receivedMessagesRepository;
        
        this.sentMessagesRepository = sentMessagesRepository;
        
        this.followersRepository = followersRepository;
        
        this.followingRepository = followingRepository;
        
        this.rolesRepository = rolesRepository;
        
        this.groupsRepository = groupsRepository;
        
        this.notificationsRepository = notificationsRepository;
        
    }

    @Transactional
    @Override
    public Profile save(Profile profile) {
    // ---------- OneToMany ----------
        if (profile.getPosts() != null) {
            List<Post> managedPosts = new ArrayList<>();
            for (Post item : profile.getPosts()) {
                if (item.getId() != null) {
                    Post existingItem = postsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Post not found"));

                     existingItem.setAuthor(profile);
                     managedPosts.add(existingItem);
                } else {
                    item.setAuthor(profile);
                    managedPosts.add(item);
                }
            }
            profile.setPosts(managedPosts);
        }
    
        if (profile.getComments() != null) {
            List<Comment> managedComments = new ArrayList<>();
            for (Comment item : profile.getComments()) {
                if (item.getId() != null) {
                    Comment existingItem = commentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Comment not found"));

                     existingItem.setAuthor(profile);
                     managedComments.add(existingItem);
                } else {
                    item.setAuthor(profile);
                    managedComments.add(item);
                }
            }
            profile.setComments(managedComments);
        }
    
        if (profile.getReceivedMessages() != null) {
            List<Message> managedReceivedMessages = new ArrayList<>();
            for (Message item : profile.getReceivedMessages()) {
                if (item.getId() != null) {
                    Message existingItem = receivedMessagesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Message not found"));

                     existingItem.setRecipient(profile);
                     managedReceivedMessages.add(existingItem);
                } else {
                    item.setRecipient(profile);
                    managedReceivedMessages.add(item);
                }
            }
            profile.setReceivedMessages(managedReceivedMessages);
        }
    
        if (profile.getSentMessages() != null) {
            List<Message> managedSentMessages = new ArrayList<>();
            for (Message item : profile.getSentMessages()) {
                if (item.getId() != null) {
                    Message existingItem = sentMessagesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Message not found"));

                     existingItem.setSender(profile);
                     managedSentMessages.add(existingItem);
                } else {
                    item.setSender(profile);
                    managedSentMessages.add(item);
                }
            }
            profile.setSentMessages(managedSentMessages);
        }
    
        if (profile.getNotifications() != null) {
            List<Notification> managedNotifications = new ArrayList<>();
            for (Notification item : profile.getNotifications()) {
                if (item.getId() != null) {
                    Notification existingItem = notificationsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Notification not found"));

                     existingItem.setRecipient(profile);
                     managedNotifications.add(existingItem);
                } else {
                    item.setRecipient(profile);
                    managedNotifications.add(item);
                }
            }
            profile.setNotifications(managedNotifications);
        }
    
    // ---------- ManyToMany ----------
        if (profile.getFollowers() != null &&
            !profile.getFollowers().isEmpty()) {

            List<Profile> attachedFollowers = new ArrayList<>();
            for (Profile item : profile.getFollowers()) {
                if (item.getId() != null) {
                    Profile existingItem = followersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Profile not found with id " + item.getId()));
                    attachedFollowers.add(existingItem);
                } else {

                    Profile newItem = followersRepository.save(item);
                    attachedFollowers.add(newItem);
                }
            }

            profile.setFollowers(attachedFollowers);

            // côté propriétaire (Profile → Profile)
            attachedFollowers.forEach(it -> it.getFollowing().add(profile));
        }
        
        if (profile.getFollowing() != null &&
            !profile.getFollowing().isEmpty()) {

            List<Profile> attachedFollowing = new ArrayList<>();
            for (Profile item : profile.getFollowing()) {
                if (item.getId() != null) {
                    Profile existingItem = followingRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Profile not found with id " + item.getId()));
                    attachedFollowing.add(existingItem);
                } else {

                    Profile newItem = followingRepository.save(item);
                    attachedFollowing.add(newItem);
                }
            }

            profile.setFollowing(attachedFollowing);

            // côté propriétaire (Profile → Profile)
            attachedFollowing.forEach(it -> it.getFollowers().add(profile));
        }
        
        if (profile.getRoles() != null &&
            !profile.getRoles().isEmpty()) {

            List<Role> attachedRoles = new ArrayList<>();
            for (Role item : profile.getRoles()) {
                if (item.getId() != null) {
                    Role existingItem = rolesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Role not found with id " + item.getId()));
                    attachedRoles.add(existingItem);
                } else {

                    Role newItem = rolesRepository.save(item);
                    attachedRoles.add(newItem);
                }
            }

            profile.setRoles(attachedRoles);

            // côté propriétaire (Role → Profile)
            attachedRoles.forEach(it -> it.getProfiles().add(profile));
        }
        
        if (profile.getGroups() != null &&
            !profile.getGroups().isEmpty()) {

            List<Group> attachedGroups = new ArrayList<>();
            for (Group item : profile.getGroups()) {
                if (item.getId() != null) {
                    Group existingItem = groupsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Group not found with id " + item.getId()));
                    attachedGroups.add(existingItem);
                } else {

                    Group newItem = groupsRepository.save(item);
                    attachedGroups.add(newItem);
                }
            }

            profile.setGroups(attachedGroups);

            // côté propriétaire (Group → Profile)
            attachedGroups.forEach(it -> it.getMembers().add(profile));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return profileRepository.save(profile);
}

    @Transactional
    @Override
    public Profile update(Long id, Profile profileRequest) {
        Profile existing = profileRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Profile not found"));

    // Copier les champs simples
        existing.setName(profileRequest.getName());
        existing.setBio(profileRequest.getBio());
        existing.setHandle(profileRequest.getHandle());
        existing.setEmail(profileRequest.getEmail());
        existing.setRegistrationDate(profileRequest.getRegistrationDate());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
        if (profileRequest.getFollowers() != null) {
            existing.getFollowers().clear();

            List<Profile> followersList = profileRequest.getFollowers().stream()
                .map(item -> followersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Profile not found")))
                .collect(Collectors.toList());

            existing.getFollowers().addAll(followersList);

            // Mettre à jour le côté inverse
            followersList.forEach(it -> {
                if (!it.getFollowing().contains(existing)) {
                    it.getFollowing().add(existing);
                }
            });
        }
        
        if (profileRequest.getFollowing() != null) {
            existing.getFollowing().clear();

            List<Profile> followingList = profileRequest.getFollowing().stream()
                .map(item -> followingRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Profile not found")))
                .collect(Collectors.toList());

            existing.getFollowing().addAll(followingList);

            // Mettre à jour le côté inverse
            followingList.forEach(it -> {
                if (!it.getFollowers().contains(existing)) {
                    it.getFollowers().add(existing);
                }
            });
        }
        
        if (profileRequest.getRoles() != null) {
            existing.getRoles().clear();

            List<Role> rolesList = profileRequest.getRoles().stream()
                .map(item -> rolesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Role not found")))
                .collect(Collectors.toList());

            existing.getRoles().addAll(rolesList);

            // Mettre à jour le côté inverse
            rolesList.forEach(it -> {
                if (!it.getProfiles().contains(existing)) {
                    it.getProfiles().add(existing);
                }
            });
        }
        
        if (profileRequest.getGroups() != null) {
            existing.getGroups().clear();

            List<Group> groupsList = profileRequest.getGroups().stream()
                .map(item -> groupsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Group not found")))
                .collect(Collectors.toList());

            existing.getGroups().addAll(groupsList);

            // Mettre à jour le côté inverse
            groupsList.forEach(it -> {
                if (!it.getMembers().contains(existing)) {
                    it.getMembers().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
        existing.getPosts().clear();

        if (profileRequest.getPosts() != null) {
            for (var item : profileRequest.getPosts()) {
                Post existingItem;
                if (item.getId() != null) {
                    existingItem = postsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Post not found"));
                } else {
                existingItem = item;
                }

                existingItem.setAuthor(existing);
                existing.getPosts().add(existingItem);
            }
        }
        
        existing.getComments().clear();

        if (profileRequest.getComments() != null) {
            for (var item : profileRequest.getComments()) {
                Comment existingItem;
                if (item.getId() != null) {
                    existingItem = commentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Comment not found"));
                } else {
                existingItem = item;
                }

                existingItem.setAuthor(existing);
                existing.getComments().add(existingItem);
            }
        }
        
        existing.getReceivedMessages().clear();

        if (profileRequest.getReceivedMessages() != null) {
            for (var item : profileRequest.getReceivedMessages()) {
                Message existingItem;
                if (item.getId() != null) {
                    existingItem = receivedMessagesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Message not found"));
                } else {
                existingItem = item;
                }

                existingItem.setRecipient(existing);
                existing.getReceivedMessages().add(existingItem);
            }
        }
        
        existing.getSentMessages().clear();

        if (profileRequest.getSentMessages() != null) {
            for (var item : profileRequest.getSentMessages()) {
                Message existingItem;
                if (item.getId() != null) {
                    existingItem = sentMessagesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Message not found"));
                } else {
                existingItem = item;
                }

                existingItem.setSender(existing);
                existing.getSentMessages().add(existingItem);
            }
        }
        
        existing.getNotifications().clear();

        if (profileRequest.getNotifications() != null) {
            for (var item : profileRequest.getNotifications()) {
                Notification existingItem;
                if (item.getId() != null) {
                    existingItem = notificationsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Notification not found"));
                } else {
                existingItem = item;
                }

                existingItem.setRecipient(existing);
                existing.getNotifications().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return profileRepository.save(existing);
}

    // Pagination simple
    public Page<Profile> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Profile> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Profile.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Profile> saveAll(List<Profile> profileList) {
        return super.saveAll(profileList);
    }

}