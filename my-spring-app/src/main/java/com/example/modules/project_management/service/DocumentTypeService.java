package com.example.modules.project_management.service;

import com.example.core.service.BaseService;
import com.example.modules.project_management.model.DocumentType;
import com.example.modules.project_management.repository.DocumentTypeRepository;

import com.example.modules.project_management.model.Document;
import com.example.modules.project_management.repository.DocumentRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class DocumentTypeService extends BaseService<DocumentType> {

    protected final DocumentTypeRepository documenttypeRepository;
    
    protected final DocumentRepository documentsRepository;
    

    public DocumentTypeService(DocumentTypeRepository repository, DocumentRepository documentsRepository)
    {
        super(repository);
        this.documenttypeRepository = repository;
        
        this.documentsRepository = documentsRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
    public DocumentType update(Long id, DocumentType documenttypeRequest) {
        DocumentType existing = documenttypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("DocumentType not found"));

    // Copier les champs simples
        existing.setName(documenttypeRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<DocumentType> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<DocumentType> search(Map<String, String> filters, Pageable pageable) {
        return super.search(DocumentType.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<DocumentType> saveAll(List<DocumentType> documenttypeList) {
        return super.saveAll(documenttypeList);
    }

}