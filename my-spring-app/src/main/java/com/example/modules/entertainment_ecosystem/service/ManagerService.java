package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Manager;
import com.example.modules.entertainment_ecosystem.repository.ManagerRepository;

import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ManagerService extends BaseService<Manager> {

    protected final ManagerRepository managerRepository;
    
    protected final ArtistRepository artistsRepository;
    

    public ManagerService(ManagerRepository repository, ArtistRepository artistsRepository)
    {
        super(repository);
        this.managerRepository = repository;
        
        this.artistsRepository = artistsRepository;
        
    }

    @Transactional
    @Override
    public Manager save(Manager manager) {
    // ---------- OneToMany ----------
        if (manager.getArtists() != null) {
            List<Artist> managedArtists = new ArrayList<>();
            for (Artist item : manager.getArtists()) {
                if (item.getId() != null) {
                    Artist existingItem = artistsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Artist not found"));

                     existingItem.setManager(manager);
                     managedArtists.add(existingItem);
                } else {
                    item.setManager(manager);
                    managedArtists.add(item);
                }
            }
            manager.setArtists(managedArtists);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return managerRepository.save(manager);
}

    @Transactional
    @Override
    public Manager update(Long id, Manager managerRequest) {
        Manager existing = managerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Manager not found"));

    // Copier les champs simples
        existing.setName(managerRequest.getName());
        existing.setEmail(managerRequest.getEmail());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getArtists().clear();

        if (managerRequest.getArtists() != null) {
            for (var item : managerRequest.getArtists()) {
                Artist existingItem;
                if (item.getId() != null) {
                    existingItem = artistsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Artist not found"));
                } else {
                existingItem = item;
                }

                existingItem.setManager(existing);
                existing.getArtists().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return managerRepository.save(existing);
}

    // Pagination simple
    public Page<Manager> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Manager> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Manager.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Manager> saveAll(List<Manager> managerList) {
        return super.saveAll(managerList);
    }

}