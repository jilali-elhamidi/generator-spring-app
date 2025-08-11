package com.example.modules.social_media.service;

import com.example.core.service.BaseService;
import com.example.modules.social_media.model.MediaFile;
import com.example.modules.social_media.repository.MediaFileRepository;
import com.example.modules.social_media.model.Post;
import com.example.modules.social_media.repository.PostRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MediaFileService extends BaseService<MediaFile> {

    protected final MediaFileRepository mediafileRepository;
    private final PostRepository postRepository;

    public MediaFileService(MediaFileRepository repository,PostRepository postRepository)
    {
        super(repository);
        this.mediafileRepository = repository;
        this.postRepository = postRepository;
    }

    @Override
    public MediaFile save(MediaFile mediafile) {

        if (mediafile.getPost() != null && mediafile.getPost().getId() != null) {
        Post post = postRepository.findById(mediafile.getPost().getId())
                .orElseThrow(() -> new RuntimeException("Post not found"));
        mediafile.setPost(post);
        }

        return mediafileRepository.save(mediafile);
    }


    public MediaFile update(Long id, MediaFile mediafileRequest) {
        MediaFile existing = mediafileRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MediaFile not found"));

    // Copier les champs simples
        existing.setUrl(mediafileRequest.getUrl());
        existing.setType(mediafileRequest.getType());

// Relations ManyToOne : mise à jour conditionnelle

        if (mediafileRequest.getPost() != null && mediafileRequest.getPost().getId() != null) {
        Post post = postRepository.findById(mediafileRequest.getPost().getId())
                .orElseThrow(() -> new RuntimeException("Post not found"));
        existing.setPost(post);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        return mediafileRepository.save(existing);
    }
}