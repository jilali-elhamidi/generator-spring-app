package com.example.modules.social_media.service;

import com.example.core.service.BaseService;
import com.example.modules.social_media.model.MediaFile;
import com.example.modules.social_media.repository.MediaFileRepository;
import com.example.modules.social_media.model.Post;
import com.example.modules.social_media.repository.PostRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MediaFileService extends BaseService<MediaFile> {

    protected final MediaFileRepository mediafileRepository;
    private final PostRepository postRepository;

    public MediaFileService(MediaFileRepository repository, PostRepository postRepository)
    {
        super(repository);
        this.mediafileRepository = repository;
        this.postRepository = postRepository;
    }

    @Override
    public MediaFile save(MediaFile mediafile) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (mediafile.getPost() != null &&
            mediafile.getPost().getId() != null) {

            Post existingPost = postRepository.findById(
                mediafile.getPost().getId()
            ).orElseThrow(() -> new RuntimeException("Post not found"));

            mediafile.setPost(existingPost);
        }
        
    // ---------- OneToOne ----------
    return mediafileRepository.save(mediafile);
}


    public MediaFile update(Long id, MediaFile mediafileRequest) {
        MediaFile existing = mediafileRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MediaFile not found"));

    // Copier les champs simples
        existing.setUrl(mediafileRequest.getUrl());
        existing.setType(mediafileRequest.getType());

    // ---------- Relations ManyToOne ----------
        if (mediafileRequest.getPost() != null &&
            mediafileRequest.getPost().getId() != null) {

            Post existingPost = postRepository.findById(
                mediafileRequest.getPost().getId()
            ).orElseThrow(() -> new RuntimeException("Post not found"));

            existing.setPost(existingPost);
        } else {
            existing.setPost(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return mediafileRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<MediaFile> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        MediaFile entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getPost() != null) {
            entity.setPost(null);
        }
        
        repository.delete(entity);
        return true;
    }
}