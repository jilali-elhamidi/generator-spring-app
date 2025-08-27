package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MediaFile;
import com.example.modules.entertainment_ecosystem.repository.MediaFileRepository;

import com.example.modules.entertainment_ecosystem.model.Review;
import com.example.modules.entertainment_ecosystem.repository.ReviewRepository;


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
    
    protected final ReviewRepository reviewRepository;
    

    public MediaFileService(MediaFileRepository repository, ReviewRepository reviewRepository)
    {
        super(repository);
        this.mediafileRepository = repository;
        
        this.reviewRepository = reviewRepository;
        
    }

    @Transactional
    @Override
    public MediaFile save(MediaFile mediafile) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
        if (mediafile.getReview() != null) {
            if (mediafile.getReview().getId() != null) {
                Review existingReview = reviewRepository.findById(mediafile.getReview().getId())
                    .orElseThrow(() -> new RuntimeException("Review not found with id "
                        + mediafile.getReview().getId()));
                mediafile.setReview(existingReview);
            } else {
                // Nouvel objet → sauvegarde d'abord
                Review newReview = reviewRepository.save(mediafile.getReview());
                mediafile.setReview(newReview);
            }

            mediafile.getReview().setMediaFile(mediafile);
        }
        
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
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
        if (mediafileRequest.getReview() != null &&mediafileRequest.getReview().getId() != null) {

        Review review = reviewRepository.findById(mediafileRequest.getReview().getId())
                .orElseThrow(() -> new RuntimeException("Review not found"));

        existing.setReview(review);
        review.setMediaFile(existing);
        
        }
    
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