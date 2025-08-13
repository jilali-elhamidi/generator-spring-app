package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MediaFile;
import com.example.modules.entertainment_ecosystem.repository.MediaFileRepository;
import com.example.modules.entertainment_ecosystem.model.Review;
import com.example.modules.entertainment_ecosystem.repository.ReviewRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MediaFileService extends BaseService<MediaFile> {

    protected final MediaFileRepository mediafileRepository;
    private final ReviewRepository reviewRepository;

    public MediaFileService(MediaFileRepository repository,ReviewRepository reviewRepository)
    {
        super(repository);
        this.mediafileRepository = repository;
            this.reviewRepository = reviewRepository;
    }

    @Override
    public MediaFile save(MediaFile mediafile) {
        if (mediafile.getReview() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            mediafile.setReview(
            reviewRepository.findById(mediafile.getReview().getId())
            .orElseThrow(() -> new RuntimeException("review not found"))
            );
        
        mediafile.getReview().setMediaFile(mediafile);
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

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

        if (mediafileRequest.getReview() != null
        && mediafileRequest.getReview().getId() != null) {

        Review review = reviewRepository.findById(
        mediafileRequest.getReview().getId()
        ).orElseThrow(() -> new RuntimeException("Review not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setReview(review);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            review.setMediaFile(existing);
        
        }

    


        return mediafileRepository.save(existing);
    }
}