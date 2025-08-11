package com.example.modules.social_media.service;

import com.example.core.service.BaseService;
import com.example.modules.social_media.model.Tag;
import com.example.modules.social_media.repository.TagRepository;
import com.example.modules.social_media.model.Post;
import com.example.modules.social_media.repository.PostRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class TagService extends BaseService<Tag> {

    protected final TagRepository tagRepository;
    private final PostRepository postsRepository;

    public TagService(TagRepository repository,PostRepository postsRepository)
    {
        super(repository);
        this.tagRepository = repository;
        this.postsRepository = postsRepository;
    }

    @Override
    public Tag save(Tag tag) {

        return tagRepository.save(tag);
    }


    public Tag update(Long id, Tag tagRequest) {
        Tag existing = tagRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tag not found"));

    // Copier les champs simples
        existing.setName(tagRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

        if (tagRequest.getPosts() != null) {
            existing.getPosts().clear();
            List<Post> postsList = tagRequest.getPosts().stream()
                .map(item -> postsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Post not found")))
                .collect(Collectors.toList());
        existing.getPosts().addAll(postsList);
        }

// Relations OneToMany : synchronisation sécurisée

        return tagRepository.save(existing);
    }
}