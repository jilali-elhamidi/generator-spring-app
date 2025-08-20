package com.example.modules.social_media.service;

import com.example.core.service.BaseService;
import com.example.modules.social_media.model.Post;
import com.example.modules.social_media.repository.PostRepository;
import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.repository.ProfileRepository;
import com.example.modules.social_media.model.Comment;
import com.example.modules.social_media.repository.CommentRepository;
import com.example.modules.social_media.model.Tag;
import com.example.modules.social_media.repository.TagRepository;
import com.example.modules.social_media.model.MediaFile;
import com.example.modules.social_media.repository.MediaFileRepository;
import com.example.modules.social_media.model.Group;
import com.example.modules.social_media.repository.GroupRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class PostService extends BaseService<Post> {

    protected final PostRepository postRepository;
    private final ProfileRepository authorRepository;
    private final CommentRepository commentsRepository;
    private final TagRepository tagsRepository;
    private final MediaFileRepository mediaRepository;
    private final GroupRepository groupRepository;

    public PostService(PostRepository repository, ProfileRepository authorRepository, CommentRepository commentsRepository, TagRepository tagsRepository, MediaFileRepository mediaRepository, GroupRepository groupRepository)
    {
        super(repository);
        this.postRepository = repository;
        this.authorRepository = authorRepository;
        this.commentsRepository = commentsRepository;
        this.tagsRepository = tagsRepository;
        this.mediaRepository = mediaRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public Post save(Post post) {
    // ---------- OneToMany ----------
        if (post.getComments() != null) {
            List<Comment> managedComments = new ArrayList<>();
            for (Comment item : post.getComments()) {
                if (item.getId() != null) {
                    Comment existingItem = commentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Comment not found"));

                     existingItem.setPost(post);
                     managedComments.add(existingItem);
                } else {
                    item.setPost(post);
                    managedComments.add(item);
                }
            }
            post.setComments(managedComments);
        }
    
        if (post.getMedia() != null) {
            List<MediaFile> managedMedia = new ArrayList<>();
            for (MediaFile item : post.getMedia()) {
                if (item.getId() != null) {
                    MediaFile existingItem = mediaRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MediaFile not found"));

                     existingItem.setPost(post);
                     managedMedia.add(existingItem);
                } else {
                    item.setPost(post);
                    managedMedia.add(item);
                }
            }
            post.setMedia(managedMedia);
        }
    
    // ---------- ManyToMany ----------
        if (post.getTags() != null &&
            !post.getTags().isEmpty()) {

            List<Tag> attachedTags = post.getTags().stream()
            .map(item -> tagsRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("Tag not found with id " + item.getId())))
            .toList();

            post.setTags(attachedTags);

            // côté propriétaire (Tag → Post)
            attachedTags.forEach(it -> it.getPosts().add(post));
        }
        
    // ---------- ManyToOne ----------
        if (post.getAuthor() != null &&
            post.getAuthor().getId() != null) {

            Profile existingAuthor = authorRepository.findById(
                post.getAuthor().getId()
            ).orElseThrow(() -> new RuntimeException("Profile not found"));

            post.setAuthor(existingAuthor);
        }
        
        if (post.getGroup() != null &&
            post.getGroup().getId() != null) {

            Group existingGroup = groupRepository.findById(
                post.getGroup().getId()
            ).orElseThrow(() -> new RuntimeException("Group not found"));

            post.setGroup(existingGroup);
        }
        
    // ---------- OneToOne ----------
    return postRepository.save(post);
}


    public Post update(Long id, Post postRequest) {
        Post existing = postRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Post not found"));

    // Copier les champs simples
        existing.setContent(postRequest.getContent());
        existing.setPostDate(postRequest.getPostDate());

    // ---------- Relations ManyToOne ----------
        if (postRequest.getAuthor() != null &&
            postRequest.getAuthor().getId() != null) {

            Profile existingAuthor = authorRepository.findById(
                postRequest.getAuthor().getId()
            ).orElseThrow(() -> new RuntimeException("Profile not found"));

            existing.setAuthor(existingAuthor);
        } else {
            existing.setAuthor(null);
        }
        
        if (postRequest.getGroup() != null &&
            postRequest.getGroup().getId() != null) {

            Group existingGroup = groupRepository.findById(
                postRequest.getGroup().getId()
            ).orElseThrow(() -> new RuntimeException("Group not found"));

            existing.setGroup(existingGroup);
        } else {
            existing.setGroup(null);
        }
        
    // ---------- Relations ManyToOne ----------
        if (postRequest.getTags() != null) {
            existing.getTags().clear();

            List<Tag> tagsList = postRequest.getTags().stream()
                .map(item -> tagsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Tag not found")))
                .collect(Collectors.toList());

            existing.getTags().addAll(tagsList);

            // Mettre à jour le côté inverse
            tagsList.forEach(it -> {
                if (!it.getPosts().contains(existing)) {
                    it.getPosts().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
        existing.getComments().clear();

        if (postRequest.getComments() != null) {
            for (var item : postRequest.getComments()) {
                Comment existingItem;
                if (item.getId() != null) {
                    existingItem = commentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Comment not found"));
                } else {
                existingItem = item;
                }

                existingItem.setPost(existing);
                existing.getComments().add(existingItem);
            }
        }
        
        existing.getMedia().clear();

        if (postRequest.getMedia() != null) {
            for (var item : postRequest.getMedia()) {
                MediaFile existingItem;
                if (item.getId() != null) {
                    existingItem = mediaRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MediaFile not found"));
                } else {
                existingItem = item;
                }

                existingItem.setPost(existing);
                existing.getMedia().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return postRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Post> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Post entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getComments() != null) {
            for (var child : entity.getComments()) {
                // retirer la référence inverse
                child.setPost(null);
            }
            entity.getComments().clear();
        }
        
        if (entity.getMedia() != null) {
            for (var child : entity.getMedia()) {
                // retirer la référence inverse
                child.setPost(null);
            }
            entity.getMedia().clear();
        }
        
    // --- Dissocier ManyToMany ---
        if (entity.getTags() != null) {
            for (Tag item : new ArrayList<>(entity.getTags())) {
                
                item.getPosts().remove(entity); // retire côté inverse
            }
            entity.getTags().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getAuthor() != null) {
            entity.setAuthor(null);
        }
        
        if (entity.getGroup() != null) {
            entity.setGroup(null);
        }
        
        repository.delete(entity);
        return true;
    }
}