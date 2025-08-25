package com.example.modules.social_media.service;

import com.example.core.service.BaseService;
import com.example.modules.social_media.model.Group;
import com.example.modules.social_media.repository.GroupRepository;
import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.repository.ProfileRepository;
import com.example.modules.social_media.model.Post;
import com.example.modules.social_media.repository.PostRepository;
import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.repository.ProfileRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class GroupService extends BaseService<Group> {

    protected final GroupRepository groupRepository;
    private final ProfileRepository membersRepository;
    private final PostRepository postsRepository;
    private final ProfileRepository ownerRepository;

    public GroupService(GroupRepository repository, ProfileRepository membersRepository, PostRepository postsRepository, ProfileRepository ownerRepository)
    {
        super(repository);
        this.groupRepository = repository;
        this.membersRepository = membersRepository;
        this.postsRepository = postsRepository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Group save(Group group) {
    // ---------- OneToMany ----------
        if (group.getPosts() != null) {
            List<Post> managedPosts = new ArrayList<>();
            for (Post item : group.getPosts()) {
                if (item.getId() != null) {
                    Post existingItem = postsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Post not found"));

                     existingItem.setGroup(group);
                     managedPosts.add(existingItem);
                } else {
                    item.setGroup(group);
                    managedPosts.add(item);
                }
            }
            group.setPosts(managedPosts);
        }
    
    // ---------- ManyToMany ----------
        if (group.getMembers() != null &&
            !group.getMembers().isEmpty()) {

            List<Profile> attachedMembers = new ArrayList<>();
            for (Profile item : group.getMembers()) {
                if (item.getId() != null) {
                    Profile existingItem = membersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Profile not found with id " + item.getId()));
                    attachedMembers.add(existingItem);
                } else {

                    Profile newItem = membersRepository.save(item);
                    attachedMembers.add(newItem);
                }
            }

            group.setMembers(attachedMembers);

            // côté propriétaire (Profile → Group)
            attachedMembers.forEach(it -> it.getGroups().add(group));
        }
        
    // ---------- ManyToOne ----------
        if (group.getOwner() != null) {
            if (group.getOwner().getId() != null) {
                Profile existingOwner = ownerRepository.findById(
                    group.getOwner().getId()
                ).orElseThrow(() -> new RuntimeException("Profile not found with id "
                    + group.getOwner().getId()));
                group.setOwner(existingOwner);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Profile newOwner = ownerRepository.save(group.getOwner());
                group.setOwner(newOwner);
            }
        }
        
    // ---------- OneToOne ----------
    return groupRepository.save(group);
}


    public Group update(Long id, Group groupRequest) {
        Group existing = groupRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Group not found"));

    // Copier les champs simples
        existing.setName(groupRequest.getName());
        existing.setDescription(groupRequest.getDescription());

    // ---------- Relations ManyToOne ----------
        if (groupRequest.getOwner() != null &&
            groupRequest.getOwner().getId() != null) {

            Profile existingOwner = ownerRepository.findById(
                groupRequest.getOwner().getId()
            ).orElseThrow(() -> new RuntimeException("Profile not found"));

            existing.setOwner(existingOwner);
        } else {
            existing.setOwner(null);
        }
        
    // ---------- Relations ManyToOne ----------
        if (groupRequest.getMembers() != null) {
            existing.getMembers().clear();

            List<Profile> membersList = groupRequest.getMembers().stream()
                .map(item -> membersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Profile not found")))
                .collect(Collectors.toList());

            existing.getMembers().addAll(membersList);

            // Mettre à jour le côté inverse
            membersList.forEach(it -> {
                if (!it.getGroups().contains(existing)) {
                    it.getGroups().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
        existing.getPosts().clear();

        if (groupRequest.getPosts() != null) {
            for (var item : groupRequest.getPosts()) {
                Post existingItem;
                if (item.getId() != null) {
                    existingItem = postsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Post not found"));
                } else {
                existingItem = item;
                }

                existingItem.setGroup(existing);
                existing.getPosts().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return groupRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Group> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Group entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getPosts() != null) {
            for (var child : entity.getPosts()) {
                // retirer la référence inverse
                child.setGroup(null);
            }
            entity.getPosts().clear();
        }
        
    // --- Dissocier ManyToMany ---
        if (entity.getMembers() != null) {
            for (Profile item : new ArrayList<>(entity.getMembers())) {
                
                item.getGroups().remove(entity); // retire côté inverse
            }
            entity.getMembers().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getOwner() != null) {
            entity.setOwner(null);
        }
        
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<Group> saveAll(List<Group> groupList) {

        return groupRepository.saveAll(groupList);
    }

}