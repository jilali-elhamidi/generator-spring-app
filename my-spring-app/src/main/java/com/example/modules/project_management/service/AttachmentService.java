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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class AttachmentService extends BaseService<Attachment> {

    protected final AttachmentRepository attachmentRepository;
    
    protected final TaskRepository taskRepository;
    
    protected final TeamMemberRepository uploadedByRepository;
    

    public AttachmentService(AttachmentRepository repository, TaskRepository taskRepository, TeamMemberRepository uploadedByRepository)
    {
        super(repository);
        this.attachmentRepository = repository;
        
        this.taskRepository = taskRepository;
        
        this.uploadedByRepository = uploadedByRepository;
        
    }

    @Transactional
    @Override
    public Attachment save(Attachment attachment) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (attachment.getTask() != null) {
            if (attachment.getTask().getId() != null) {
                Task existingTask = taskRepository.findById(
                    attachment.getTask().getId()
                ).orElseThrow(() -> new RuntimeException("Task not found with id "
                    + attachment.getTask().getId()));
                attachment.setTask(existingTask);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Task newTask = taskRepository.save(attachment.getTask());
                attachment.setTask(newTask);
            }
        }
        
        if (attachment.getUploadedBy() != null) {
            if (attachment.getUploadedBy().getId() != null) {
                TeamMember existingUploadedBy = uploadedByRepository.findById(
                    attachment.getUploadedBy().getId()
                ).orElseThrow(() -> new RuntimeException("TeamMember not found with id "
                    + attachment.getUploadedBy().getId()));
                attachment.setUploadedBy(existingUploadedBy);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                TeamMember newUploadedBy = uploadedByRepository.save(attachment.getUploadedBy());
                attachment.setUploadedBy(newUploadedBy);
            }
        }
        
    // ---------- OneToOne ----------
    return attachmentRepository.save(attachment);
}

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return attachmentRepository.save(existing);
}

    // Pagination simple
    public Page<Attachment> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Attachment> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Attachment.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Attachment> saveAll(List<Attachment> attachmentList) {
        return super.saveAll(attachmentList);
    }

}