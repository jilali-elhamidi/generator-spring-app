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

    public TagService(TagRepository repository,TaskRepository tasksRepository)
    {
        super(repository);
        this.tagRepository = repository;
        this.tasksRepository = tasksRepository;
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

        if (tagRequest.getTasks() != null) {
            existing.getTasks().clear();
            List<Task> tasksList = tagRequest.getTasks().stream()
                .map(item -> tasksRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Task not found")))
                .collect(Collectors.toList());
        existing.getTasks().addAll(tasksList);
        }

// Relations OneToMany : synchronisation sécurisée

    


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
        entity.getTasks().clear();
        }
    


// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}