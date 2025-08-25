package com.example.modules.project_management.service;

import com.example.core.service.BaseService;
import com.example.modules.project_management.model.Invoice;
import com.example.modules.project_management.repository.InvoiceRepository;
import com.example.modules.project_management.model.Project;
import com.example.modules.project_management.repository.ProjectRepository;
import com.example.modules.project_management.model.Client;
import com.example.modules.project_management.repository.ClientRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class InvoiceService extends BaseService<Invoice> {

    protected final InvoiceRepository invoiceRepository;
    private final ProjectRepository projectRepository;
    private final ClientRepository clientRepository;

    public InvoiceService(InvoiceRepository repository, ProjectRepository projectRepository, ClientRepository clientRepository)
    {
        super(repository);
        this.invoiceRepository = repository;
        this.projectRepository = projectRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Invoice save(Invoice invoice) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (invoice.getProject() != null) {
            if (invoice.getProject().getId() != null) {
                Project existingProject = projectRepository.findById(
                    invoice.getProject().getId()
                ).orElseThrow(() -> new RuntimeException("Project not found with id "
                    + invoice.getProject().getId()));
                invoice.setProject(existingProject);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Project newProject = projectRepository.save(invoice.getProject());
                invoice.setProject(newProject);
            }
        }
        
        if (invoice.getClient() != null) {
            if (invoice.getClient().getId() != null) {
                Client existingClient = clientRepository.findById(
                    invoice.getClient().getId()
                ).orElseThrow(() -> new RuntimeException("Client not found with id "
                    + invoice.getClient().getId()));
                invoice.setClient(existingClient);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Client newClient = clientRepository.save(invoice.getClient());
                invoice.setClient(newClient);
            }
        }
        
    // ---------- OneToOne ----------
    return invoiceRepository.save(invoice);
}


    public Invoice update(Long id, Invoice invoiceRequest) {
        Invoice existing = invoiceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Invoice not found"));

    // Copier les champs simples
        existing.setInvoiceDate(invoiceRequest.getInvoiceDate());
        existing.setAmount(invoiceRequest.getAmount());
        existing.setStatus(invoiceRequest.getStatus());

    // ---------- Relations ManyToOne ----------
        if (invoiceRequest.getProject() != null &&
            invoiceRequest.getProject().getId() != null) {

            Project existingProject = projectRepository.findById(
                invoiceRequest.getProject().getId()
            ).orElseThrow(() -> new RuntimeException("Project not found"));

            existing.setProject(existingProject);
        } else {
            existing.setProject(null);
        }
        
        if (invoiceRequest.getClient() != null &&
            invoiceRequest.getClient().getId() != null) {

            Client existingClient = clientRepository.findById(
                invoiceRequest.getClient().getId()
            ).orElseThrow(() -> new RuntimeException("Client not found"));

            existing.setClient(existingClient);
        } else {
            existing.setClient(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return invoiceRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Invoice> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Invoice entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getProject() != null) {
            entity.setProject(null);
        }
        
        if (entity.getClient() != null) {
            entity.setClient(null);
        }
        
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<Invoice> saveAll(List<Invoice> invoiceList) {

        return invoiceRepository.saveAll(invoiceList);
    }

}