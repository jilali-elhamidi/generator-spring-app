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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class PostService extends BaseService<Post> {

    protected final PostRepository postRepository;
    
    protected final ProfileRepository authorRepository;
    
    protected final CommentRepository commentsRepository;
    
    protected final TagRepository tagsRepository;
    
    protected final MediaFileRepository mediaRepository;
    
    protected final GroupRepository groupRepository;
    

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

    @Transactional
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

            List<Tag> attachedTags = new ArrayList<>();
            for (Tag item : post.getTags()) {
                if (item.getId() != null) {
                    Tag existingItem = tagsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Tag not found with id " + item.getId()));
                    attachedTags.add(existingItem);
                } else {

                    Tag newItem = tagsRepository.save(item);
                    attachedTags.add(newItem);
                }
            }

            post.setTags(attachedTags);

            // côté propriétaire (Tag → Post)
            attachedTags.forEach(it -> it.getPosts().add(post));
        }
        
    // ---------- ManyToOne ----------
        if (post.getAuthor() != null) {
            if (post.getAuthor().getId() != null) {
                Profile existingAuthor = authorRepository.findById(
                    post.getAuthor().getId()
                ).orElseThrow(() -> new RuntimeException("Profile not found with id "
                    + post.getAuthor().getId()));
                post.setAuthor(existingAuthor);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Profile newAuthor = authorRepository.save(post.getAuthor());
                post.setAuthor(newAuthor);
            }
        }
        
        if (post.getGroup() != null) {
            if (post.getGroup().getId() != null) {
                Group existingGroup = groupRepository.findById(
                    post.getGroup().getId()
                ).orElseThrow(() -> new RuntimeException("Group not found with id "
                    + post.getGroup().getId()));
                post.setGroup(existingGroup);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Group newGroup = groupRepository.save(post.getGroup());
                post.setGroup(newGroup);
            }
        }
        
    // ---------- OneToOne ----------
    return postRepository.save(post);
}

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<Post> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Post> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Post.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Post> saveAll(List<Post> postList) {
        return super.saveAll(postList);
    }

}