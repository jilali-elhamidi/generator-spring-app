package com.example.modules.project_management.service;

import com.example.core.service.BaseService;
import com.example.modules.project_management.model.Tag;
import com.example.modules.project_management.repository.TagRepository;

import com.example.modules.project_management.model.Task;
import com.example.modules.project_management.repository.TaskRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class TagService extends BaseService<Tag> {

    protected final TagRepository tagRepository;
    
    protected final TaskRepository tasksRepository;
    

    public TagService(TagRepository repository, TaskRepository tasksRepository)
    {
        super(repository);
        this.tagRepository = repository;
        
        this.tasksRepository = tasksRepository;
        
    }

    @Transactional
    @Override
    public Tag save(Tag tag) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (tag.getTasks() != null &&
            !tag.getTasks().isEmpty()) {

            List<Task> attachedTasks = new ArrayList<>();
            for (Task item : tag.getTasks()) {
                if (item.getId() != null) {
                    Task existingItem = tasksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Task not found with id " + item.getId()));
                    attachedTasks.add(existingItem);
                } else {

                    Task newItem = tasksRepository.save(item);
                    attachedTasks.add(newItem);
                }
            }

            tag.setTasks(attachedTasks);

            // côté propriétaire (Task → Tag)
            attachedTasks.forEach(it -> it.getTags().add(tag));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return tagRepository.save(tag);
}

    @Transactional
    @Override
    public Tag update(Long id, Tag tagRequest) {
        Tag existing = tagRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tag not found"));

    // Copier les champs simples
        existing.setName(tagRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
        if (tagRequest.getTasks() != null) {
            existing.getTasks().clear();

            List<Task> tasksList = tagRequest.getTasks().stream()
                .map(item -> tasksRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Task not found")))
                .collect(Collectors.toList());

            existing.getTasks().addAll(tasksList);

            // Mettre à jour le côté inverse
            tasksList.forEach(it -> {
                if (!it.getTags().contains(existing)) {
                    it.getTags().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return tagRepository.save(existing);
}

    // Pagination simple
    public Page<Tag> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Tag> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Tag.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Tag> saveAll(List<Tag> tagList) {
        return super.saveAll(tagList);
    }

}