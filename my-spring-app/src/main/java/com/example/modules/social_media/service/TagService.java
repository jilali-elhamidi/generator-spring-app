package com.example.modules.social_media.service;

import com.example.core.service.BaseService;
import com.example.modules.social_media.model.Tag;
import com.example.modules.social_media.repository.TagRepository;
import com.example.modules.social_media.model.Post;
import com.example.modules.social_media.repository.PostRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class TagService extends BaseService<Tag> {

    protected final TagRepository tagRepository;
    private final PostRepository postsRepository;

    public TagService(TagRepository repository, PostRepository postsRepository)
    {
        super(repository);
        this.tagRepository = repository;
        this.postsRepository = postsRepository;
    }

    @Override
    public Tag save(Tag tag) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (tag.getPosts() != null &&
            !tag.getPosts().isEmpty()) {

            List<Post> attachedPosts = new ArrayList<>();
            for (Post item : tag.getPosts()) {
                if (item.getId() != null) {
                    Post existingItem = postsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Post not found with id " + item.getId()));
                    attachedPosts.add(existingItem);
                } else {

                    Post newItem = postsRepository.save(item);
                    attachedPosts.add(newItem);
                }
            }

            tag.setPosts(attachedPosts);

            // côté propriétaire (Post → Tag)
            attachedPosts.forEach(it -> it.getTags().add(tag));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return tagRepository.save(tag);
}


    public Tag update(Long id, Tag tagRequest) {
        Tag existing = tagRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tag not found"));

    // Copier les champs simples
        existing.setName(tagRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
        if (tagRequest.getPosts() != null) {
            existing.getPosts().clear();

            List<Post> postsList = tagRequest.getPosts().stream()
                .map(item -> postsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Post not found")))
                .collect(Collectors.toList());

            existing.getPosts().addAll(postsList);

            // Mettre à jour le côté inverse
            postsList.forEach(it -> {
                if (!it.getTags().contains(existing)) {
                    it.getTags().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return tagRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Tag> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Tag entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
        if (entity.getPosts() != null) {
            for (Post item : new ArrayList<>(entity.getPosts())) {
                
                item.getTags().remove(entity); // retire côté inverse
            }
            entity.getPosts().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<Tag> saveAll(List<Tag> tagList) {

        return tagRepository.saveAll(tagList);
    }

}