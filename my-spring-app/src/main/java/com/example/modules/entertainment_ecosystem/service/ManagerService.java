package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Manager;
import com.example.modules.entertainment_ecosystem.repository.ManagerRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class ManagerService extends BaseService<Manager> {

    protected final ManagerRepository managerRepository;

    public ManagerService(ManagerRepository repository)
    {
        super(repository);
        this.managerRepository = repository;
    }

    @Override
    public Manager save(Manager manager) {

        if (manager.getArtists() != null) {
            for (Artist item : manager.getArtists()) {
            item.setManager(manager);
            }
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

        existing.getArtists().clear();
        if (managerRequest.getArtists() != null) {
            for (var item : managerRequest.getArtists()) {
            item.setManager(existing);
            existing.getArtists().add(item);
            }
        }

        return managerRepository.save(existing);
    }
}