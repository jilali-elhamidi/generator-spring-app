package com.example.modules.project_management.service;

import com.example.core.service.BaseService;
import com.example.modules.project_management.model.Client;
import com.example.modules.project_management.repository.ClientRepository;
import com.example.modules.project_management.model.Project;
import com.example.modules.project_management.repository.ProjectRepository;
import com.example.modules.project_management.model.Invoice;
import com.example.modules.project_management.repository.InvoiceRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ClientService extends BaseService<Client> {

    protected final ClientRepository clientRepository;
    private final ProjectRepository projectsRepository;
    private final InvoiceRepository invoicesRepository;

    public ClientService(ClientRepository repository,ProjectRepository projectsRepository,InvoiceRepository invoicesRepository)
    {
        super(repository);
        this.clientRepository = repository;
        this.projectsRepository = projectsRepository;
        this.invoicesRepository = invoicesRepository;
    }

    @Override
    public Client save(Client client) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (client.getProjects() != null) {
            List<Project> managedProjects = new ArrayList<>();
            for (Project item : client.getProjects()) {
            if (item.getId() != null) {
            Project existingItem = projectsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Project not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setClient(client);
            managedProjects.add(existingItem);
            } else {
            item.setClient(client);
            managedProjects.add(item);
            }
            }
            client.setProjects(managedProjects);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (client.getInvoices() != null) {
            List<Invoice> managedInvoices = new ArrayList<>();
            for (Invoice item : client.getInvoices()) {
            if (item.getId() != null) {
            Invoice existingItem = invoicesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Invoice not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setClient(client);
            managedInvoices.add(existingItem);
            } else {
            item.setClient(client);
            managedInvoices.add(item);
            }
            }
            client.setInvoices(managedInvoices);
            }
        
    

    
    

        return clientRepository.save(client);
    }


    public Client update(Long id, Client clientRequest) {
        Client existing = clientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Client not found"));

    // Copier les champs simples
        existing.setCompanyName(clientRequest.getCompanyName());
        existing.setContactPerson(clientRequest.getContactPerson());
        existing.setEmail(clientRequest.getEmail());
        existing.setPhoneNumber(clientRequest.getPhoneNumber());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getProjects().clear();

        if (clientRequest.getProjects() != null) {
        for (var item : clientRequest.getProjects()) {
        Project existingItem;
        if (item.getId() != null) {
        existingItem = projectsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Project not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setClient(existing);

        // Ajouter directement dans la collection existante
        existing.getProjects().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getInvoices().clear();

        if (clientRequest.getInvoices() != null) {
        for (var item : clientRequest.getInvoices()) {
        Invoice existingItem;
        if (item.getId() != null) {
        existingItem = invoicesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Invoice not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setClient(existing);

        // Ajouter directement dans la collection existante
        existing.getInvoices().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    


        return clientRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<Client> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

Client entity = entityOpt.get();

// --- Dissocier OneToMany ---

    
        if (entity.getProjects() != null) {
        for (var child : entity.getProjects()) {
        
            child.setClient(null); // retirer la référence inverse
        
        }
        entity.getProjects().clear();
        }
    

    
        if (entity.getInvoices() != null) {
        for (var child : entity.getInvoices()) {
        
            child.setClient(null); // retirer la référence inverse
        
        }
        entity.getInvoices().clear();
        }
    


// --- Dissocier ManyToMany ---

    

    


// --- Dissocier OneToOne ---

    

    


// --- Dissocier ManyToOne ---

    

    


repository.delete(entity);
return true;
}
}