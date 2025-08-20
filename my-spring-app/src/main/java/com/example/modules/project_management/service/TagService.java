package com.example.modules.project_management.service;

import com.example.core.service.BaseService;
import com.example.modules.project_management.model.Tag;
import com.example.modules.project_management.repository.TagRepository;
import com.example.modules.project_management.model.Task;
import com.example.modules.project_management.repository.TaskRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class TagService extends BaseService<Tag> {

    protected final TagRepository tagRepository;
    private final TaskRepository tasksRepository;

    public TagService(TagRepository repository, TaskRepository tasksRepository)
    {
        super(repository);
        this.tagRepository = repository;
        this.tasksRepository = tasksRepository;
    }

    @Override
    public Tag save(Tag tag) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (tag.getTasks() != null &&
            !tag.getTasks().isEmpty()) {

            List<Task> attachedTasks = tag.getTasks().stream()
            .map(item -> tasksRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("Task not found with id " + item.getId())))
            .toList();

            tag.setTasks(attachedTasks);

            // côté propriétaire (Task → Tag)
            attachedTasks.forEach(it -> it.getTags().add(tag));
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
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Tag> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Tag entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
        if (entity.getTasks() != null) {
            for (Task item : new ArrayList<>(entity.getTasks())) {
                
                item.getTags().remove(entity); // retire côté inverse
            }
            entity.getTasks().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}