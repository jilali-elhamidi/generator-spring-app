package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ConnectionType;
import com.example.modules.entertainment_ecosystem.repository.ConnectionTypeRepository;
import com.example.modules.entertainment_ecosystem.model.UserConnection;
import com.example.modules.entertainment_ecosystem.repository.UserConnectionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ConnectionTypeService extends BaseService<ConnectionType> {

    protected final ConnectionTypeRepository connectiontypeRepository;
    private final UserConnectionRepository userConnectionsRepository;

    public ConnectionTypeService(ConnectionTypeRepository repository, UserConnectionRepository userConnectionsRepository)
    {
        super(repository);
        this.connectiontypeRepository = repository;
        this.userConnectionsRepository = userConnectionsRepository;
    }

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


    public ConnectionType update(Long id, ConnectionType connectiontypeRequest) {
        ConnectionType existing = connectiontypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ConnectionType not found"));

    // Copier les champs simples
        existing.setName(connectiontypeRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
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
    @Transactional
    public boolean deleteById(Long id) {
        Optional<ConnectionType> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        ConnectionType entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getUserConnections() != null) {
            for (var child : entity.getUserConnections()) {
                
                child.setType(null); // retirer la référence inverse
                
            }
            entity.getUserConnections().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}