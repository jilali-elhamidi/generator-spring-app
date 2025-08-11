package com.example.modules.social_media.service;

import com.example.core.service.BaseService;
import com.example.modules.social_media.model.Post;
import com.example.modules.social_media.repository.PostRepository;
import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.repository.ProfileRepository;
import com.example.modules.social_media.model.Comment;
import com.example.modules.social_media.model.Tag;
import com.example.modules.social_media.repository.TagRepository;
import com.example.modules.social_media.model.MediaFile;
import com.example.modules.social_media.model.Group;
import com.example.modules.social_media.repository.GroupRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class PostService extends BaseService<Post> {

    protected final PostRepository postRepository;
    private final ProfileRepository authorRepository;
    private final TagRepository tagsRepository;
    private final GroupRepository groupRepository;

    public PostService(PostRepository repository,ProfileRepository authorRepository,TagRepository tagsRepository,GroupRepository groupRepository)
    {
        super(repository);
        this.postRepository = repository;
        this.authorRepository = authorRepository;
        this.tagsRepository = tagsRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public Post save(Post post) {

        if (post.getAuthor() != null && post.getAuthor().getId() != null) {
        Profile author = authorRepository.findById(post.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        post.setAuthor(author);
        }

        if (post.getGroup() != null && post.getGroup().getId() != null) {
        Group group = groupRepository.findById(post.getGroup().getId())
                .orElseThrow(() -> new RuntimeException("Group not found"));
        post.setGroup(group);
        }

        if (post.getComments() != null) {
            for (Comment item : post.getComments()) {
            item.setPost(post);
            }
        }

        if (post.getMedia() != null) {
            for (MediaFile item : post.getMedia()) {
            item.setPost(post);
            }
        }

        return postRepository.save(post);
    }


    public Post update(Long id, Post postRequest) {
        Post existing = postRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Post not found"));

    // Copier les champs simples
        existing.setContent(postRequest.getContent());
        existing.setPostDate(postRequest.getPostDate());

// Relations ManyToOne : mise à jour conditionnelle

        if (postRequest.getAuthor() != null && postRequest.getAuthor().getId() != null) {
        Profile author = authorRepository.findById(postRequest.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        existing.setAuthor(author);
        }

        if (postRequest.getGroup() != null && postRequest.getGroup().getId() != null) {
        Group group = groupRepository.findById(postRequest.getGroup().getId())
                .orElseThrow(() -> new RuntimeException("Group not found"));
        existing.setGroup(group);
        }

// Relations ManyToMany : synchronisation sécurisée

        if (postRequest.getTags() != null) {
            existing.getTags().clear();
            List<Tag> tagsList = postRequest.getTags().stream()
                .map(item -> tagsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Tag not found")))
                .collect(Collectors.toList());
        existing.getTags().addAll(tagsList);
        }

// Relations OneToMany : synchronisation sécurisée

        existing.getComments().clear();
        if (postRequest.getComments() != null) {
            for (var item : postRequest.getComments()) {
            item.setPost(existing);
            existing.getComments().add(item);
            }
        }

        existing.getMedia().clear();
        if (postRequest.getMedia() != null) {
            for (var item : postRequest.getMedia()) {
            item.setPost(existing);
            existing.getMedia().add(item);
            }
        }

        return postRepository.save(existing);
    }
}