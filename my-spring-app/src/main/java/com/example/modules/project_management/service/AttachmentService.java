package com.example.modules.project_management.service;

import com.example.core.service.BaseService;
import com.example.modules.project_management.model.Attachment;
import com.example.modules.project_management.repository.AttachmentRepository;
import com.example.modules.project_management.model.Task;
import com.example.modules.project_management.repository.TaskRepository;
import com.example.modules.project_management.model.TeamMember;
import com.example.modules.project_management.repository.TeamMemberRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class AttachmentService extends BaseService<Attachment> {

    protected final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    private final TeamMemberRepository uploadedByRepository;

    public AttachmentService(AttachmentRepository repository, TaskRepository taskRepository, TeamMemberRepository uploadedByRepository)
    {
        super(repository);
        this.attachmentRepository = repository;
        this.taskRepository = taskRepository;
        this.uploadedByRepository = uploadedByRepository;
    }

    @Override
    public Attachment save(Attachment attachment) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (attachment.getTask() != null &&
            attachment.getTask().getId() != null) {

            Task existingTask = taskRepository.findById(
                attachment.getTask().getId()
            ).orElseThrow(() -> new RuntimeException("Task not found"));

            attachment.setTask(existingTask);
        }
        
        if (attachment.getUploadedBy() != null &&
            attachment.getUploadedBy().getId() != null) {

            TeamMember existingUploadedBy = uploadedByRepository.findById(
                attachment.getUploadedBy().getId()
            ).orElseThrow(() -> new RuntimeException("TeamMember not found"));

            attachment.setUploadedBy(existingUploadedBy);
        }
        
    // ---------- OneToOne ----------
    return attachmentRepository.save(attachment);
}


    public Attachment update(Long id, Attachment attachmentRequest) {
        Attachment existing = attachmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Attachment not found"));

    // Copier les champs simples
        existing.setFileName(attachmentRequest.getFileName());
        existing.setFileUrl(attachmentRequest.getFileUrl());
        existing.setUploadDate(attachmentRequest.getUploadDate());

    // ---------- Relations ManyToOne ----------
        if (attachmentRequest.getTask() != null &&
            attachmentRequest.getTask().getId() != null) {

            Task existingTask = taskRepository.findById(
                attachmentRequest.getTask().getId()
            ).orElseThrow(() -> new RuntimeException("Task not found"));

            existing.setTask(existingTask);
        } else {
            existing.setTask(null);
        }
        
        if (attachmentRequest.getUploadedBy() != null &&
            attachmentRequest.getUploadedBy().getId() != null) {

            TeamMember existingUploadedBy = uploadedByRepository.findById(
                attachmentRequest.getUploadedBy().getId()
            ).orElseThrow(() -> new RuntimeException("TeamMember not found"));

            existing.setUploadedBy(existingUploadedBy);
        } else {
            existing.setUploadedBy(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return attachmentRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Attachment> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Attachment entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getTask() != null) {
            entity.setTask(null);
        }
        
        if (entity.getUploadedBy() != null) {
            entity.setUploadedBy(null);
        }
        
        repository.delete(entity);
        return true;
    }
}