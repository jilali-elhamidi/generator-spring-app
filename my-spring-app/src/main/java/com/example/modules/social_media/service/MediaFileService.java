package com.example.modules.social_media.service;

import com.example.core.service.BaseService;
import com.example.modules.social_media.model.MediaFile;
import com.example.modules.social_media.repository.MediaFileRepository;

import com.example.modules.social_media.model.Post;
import com.example.modules.social_media.repository.PostRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MediaFileService extends BaseService<MediaFile> {

    protected final MediaFileRepository mediafileRepository;
    
    protected final PostRepository postRepository;
    

    public MediaFileService(MediaFileRepository repository, PostRepository postRepository)
    {
        super(repository);
        this.mediafileRepository = repository;
        
        this.postRepository = postRepository;
        
    }

    @Transactional
    @Override
    public MediaFile save(MediaFile mediafile) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (mediafile.getPost() != null) {
            if (mediafile.getPost().getId() != null) {
                Post existingPost = postRepository.findById(
                    mediafile.getPost().getId()
                ).orElseThrow(() -> new RuntimeException("Post not found with id "
                    + mediafile.getPost().getId()));
                mediafile.setPost(existingPost);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Post newPost = postRepository.save(mediafile.getPost());
                mediafile.setPost(newPost);
            }
        }
        
    // ---------- OneToOne ----------
    return mediafileRepository.save(mediafile);
}

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return mediafileRepository.save(existing);
}

    // Pagination simple
    public Page<MediaFile> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<MediaFile> search(Map<String, String> filters, Pageable pageable) {
        return super.search(MediaFile.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<MediaFile> saveAll(List<MediaFile> mediafileList) {
        return super.saveAll(mediafileList);
    }

}