package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ReviewableContent;
import com.example.modules.entertainment_ecosystem.repository.ReviewableContentRepository;
import com.example.modules.entertainment_ecosystem.model.Review;
import com.example.modules.entertainment_ecosystem.model.Tag;
import com.example.modules.entertainment_ecosystem.repository.TagRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class ReviewableContentService extends BaseService<ReviewableContent> {

    protected final ReviewableContentRepository reviewablecontentRepository;
    private final TagRepository tagsRepository;

    public ReviewableContentService(ReviewableContentRepository repository,TagRepository tagsRepository)
    {
        super(repository);
        this.reviewablecontentRepository = repository;
        this.tagsRepository = tagsRepository;
    }

    @Override
    public ReviewableContent save(ReviewableContent reviewablecontent) {

        if (reviewablecontent.getReviews() != null) {
            for (Review item : reviewablecontent.getReviews()) {
            item.setReviewableContent(reviewablecontent);
            }
        }

        return reviewablecontentRepository.save(reviewablecontent);
    }


    public ReviewableContent update(Long id, ReviewableContent reviewablecontentRequest) {
        ReviewableContent existing = reviewablecontentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ReviewableContent not found"));

    // Copier les champs simples
        existing.setTitle(reviewablecontentRequest.getTitle());
        existing.setContentType(reviewablecontentRequest.getContentType());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

        if (reviewablecontentRequest.getTags() != null) {
            existing.getTags().clear();
            List<Tag> tagsList = reviewablecontentRequest.getTags().stream()
                .map(item -> tagsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Tag not found")))
                .collect(Collectors.toList());
        existing.getTags().addAll(tagsList);
        }

// Relations OneToMany : synchronisation sécurisée

        existing.getReviews().clear();
        if (reviewablecontentRequest.getReviews() != null) {
            for (var item : reviewablecontentRequest.getReviews()) {
            item.setReviewableContent(existing);
            existing.getReviews().add(item);
            }
        }

        return reviewablecontentRepository.save(existing);
    }
}