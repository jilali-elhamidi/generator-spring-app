package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Manager;
import com.example.modules.entertainment_ecosystem.repository.ManagerRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ManagerService extends BaseService<Manager> {

    protected final ManagerRepository managerRepository;
    private final ArtistRepository artistsRepository;

    public ManagerService(ManagerRepository repository,ArtistRepository artistsRepository)
    {
        super(repository);
        this.managerRepository = repository;
        this.artistsRepository = artistsRepository;
    }

    @Override
    public Manager save(Manager manager) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (manager.getArtists() != null) {
            List<Artist> managedArtists = new ArrayList<>();
            for (Artist item : manager.getArtists()) {
            if (item.getId() != null) {
            Artist existingItem = artistsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Artist not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setManager(manager);
            managedArtists.add(existingItem);
            } else {
            item.setManager(manager);
            managedArtists.add(item);
            }
            }
            manager.setArtists(managedArtists);
            }
        
    


    

    

        return managerRepository.save(manager);
    }


    public Manager update(Long id, Manager managerRequest) {
        Manager existing = managerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Manager not found"));

    // Copier les champs simples
        existing.setName(managerRequest.getName());
        existing.setEmail(managerRequest.getEmail());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getArtists().clear();

        if (managerRequest.getArtists() != null) {
        for (var item : managerRequest.getArtists()) {
        Artist existingItem;
        if (item.getId() != null) {
        existingItem = artistsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Artist not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setManager(existing);

        // Ajouter directement dans la collection existante
        existing.getArtists().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    


        return managerRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<Manager> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

Manager entity = entityOpt.get();

// --- Dissocier OneToMany ---

    
        if (entity.getArtists() != null) {
        for (var child : entity.getArtists()) {
        
            child.setManager(null); // retirer la référence inverse
        
        }
        entity.getArtists().clear();
        }
    


// --- Dissocier ManyToMany ---

    



// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}