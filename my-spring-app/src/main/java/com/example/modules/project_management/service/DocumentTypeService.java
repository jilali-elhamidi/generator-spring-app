package com.example.modules.project_management.service;

import com.example.core.service.BaseService;
import com.example.modules.project_management.model.DocumentType;
import com.example.modules.project_management.repository.DocumentTypeRepository;
import com.example.modules.project_management.model.Document;
import com.example.modules.project_management.repository.DocumentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class DocumentTypeService extends BaseService<DocumentType> {

    protected final DocumentTypeRepository documenttypeRepository;
    private final DocumentRepository documentsRepository;

    public DocumentTypeService(DocumentTypeRepository repository, DocumentRepository documentsRepository)
    {
        super(repository);
        this.documenttypeRepository = repository;
        this.documentsRepository = documentsRepository;
    }

    @Override
    public DocumentType save(DocumentType documenttype) {
    // ---------- OneToMany ----------
        if (documenttype.getDocuments() != null) {
            List<Document> managedDocuments = new ArrayList<>();
            for (Document item : documenttype.getDocuments()) {
                if (item.getId() != null) {
                    Document existingItem = documentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Document not found"));

                     existingItem.setType(documenttype);
                     managedDocuments.add(existingItem);
                } else {
                    item.setType(documenttype);
                    managedDocuments.add(item);
                }
            }
            documenttype.setDocuments(managedDocuments);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return documenttypeRepository.save(documenttype);
}


    public DocumentType update(Long id, DocumentType documenttypeRequest) {
        DocumentType existing = documenttypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("DocumentType not found"));

    // Copier les champs simples
        existing.setName(documenttypeRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getDocuments().clear();

        if (documenttypeRequest.getDocuments() != null) {
            for (var item : documenttypeRequest.getDocuments()) {
                Document existingItem;
                if (item.getId() != null) {
                    existingItem = documentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Document not found"));
                } else {
                existingItem = item;
                }

                existingItem.setType(existing);
                existing.getDocuments().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return documenttypeRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<DocumentType> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        DocumentType entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getDocuments() != null) {
            for (var child : entity.getDocuments()) {
                // retirer la référence inverse
                child.setType(null);
            }
            entity.getDocuments().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}