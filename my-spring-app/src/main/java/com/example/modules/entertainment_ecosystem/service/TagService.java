package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Tag;
import com.example.modules.entertainment_ecosystem.repository.TagRepository;
import com.example.modules.entertainment_ecosystem.model.ReviewableContent;
import com.example.modules.entertainment_ecosystem.repository.ReviewableContentRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class TagService extends BaseService<Tag> {

    protected final TagRepository tagRepository;
    private final ReviewableContentRepository contentRepository;

    public TagService(TagRepository repository,ReviewableContentRepository contentRepository)
    {
        super(repository);
        this.tagRepository = repository;
        this.contentRepository = contentRepository;
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

        if (tagRequest.getContent() != null) {
            existing.getContent().clear();
            List<ReviewableContent> contentList = tagRequest.getContent().stream()
                .map(item -> contentRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("ReviewableContent not found")))
                .collect(Collectors.toList());
        existing.getContent().addAll(contentList);
        }

// Relations OneToMany : synchronisation sécurisée

        return tagRepository.save(existing);
    }
}