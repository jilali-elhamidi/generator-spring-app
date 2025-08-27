package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ConnectionType;
import com.example.modules.entertainment_ecosystem.repository.ConnectionTypeRepository;

import com.example.modules.entertainment_ecosystem.model.UserConnection;
import com.example.modules.entertainment_ecosystem.repository.UserConnectionRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ConnectionTypeService extends BaseService<ConnectionType> {

    protected final ConnectionTypeRepository connectiontypeRepository;
    
    protected final UserConnectionRepository userConnectionsRepository;
    

    public ConnectionTypeService(ConnectionTypeRepository repository, UserConnectionRepository userConnectionsRepository)
    {
        super(repository);
        this.connectiontypeRepository = repository;
        
        this.userConnectionsRepository = userConnectionsRepository;
        
    }

    @Transactional
    @Override
    public ConnectionType save(ConnectionType connectiontype) {
    // ---------- OneToMany ----------
        if (connectiontype.getUserConnections() != null) {
            List<UserConnection> managedUserConnections = new ArrayList<>();
            for (UserConnection item : connectiontype.getUserConnections()) {
                if (item.getId() != null) {
                    UserConnection existingItem = userConnectionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserConnection not found"));

                     existingItem.setType(connectiontype);
                     managedUserConnections.add(existingItem);
                } else {
                    item.setType(connectiontype);
                    managedUserConnections.add(item);
                }
            }
            connectiontype.setUserConnections(managedUserConnections);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return connectiontypeRepository.save(connectiontype);
}

    @Transactional
    @Override
    public ConnectionType update(Long id, ConnectionType connectiontypeRequest) {
        ConnectionType existing = connectiontypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ConnectionType not found"));

    // Copier les champs simples
        existing.setName(connectiontypeRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getUserConnections().clear();

        if (connectiontypeRequest.getUserConnections() != null) {
            for (var item : connectiontypeRequest.getUserConnections()) {
                UserConnection existingItem;
                if (item.getId() != null) {
                    existingItem = userConnectionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserConnection not found"));
                } else {
                existingItem = item;
                }

                existingItem.setType(existing);
                existing.getUserConnections().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return connectiontypeRepository.save(existing);
}

    // Pagination simple
    public Page<ConnectionType> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<ConnectionType> search(Map<String, String> filters, Pageable pageable) {
        return super.search(ConnectionType.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<ConnectionType> saveAll(List<ConnectionType> connectiontypeList) {
        return super.saveAll(connectiontypeList);
    }

}