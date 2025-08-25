package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseShippingStatus;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseShippingStatusRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseShipping;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseShippingRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MerchandiseShippingStatusService extends BaseService<MerchandiseShippingStatus> {

    protected final MerchandiseShippingStatusRepository merchandiseshippingstatusRepository;
    private final MerchandiseShippingRepository shipmentRepository;

    public MerchandiseShippingStatusService(MerchandiseShippingStatusRepository repository, MerchandiseShippingRepository shipmentRepository)
    {
        super(repository);
        this.merchandiseshippingstatusRepository = repository;
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public MerchandiseShippingStatus save(MerchandiseShippingStatus merchandiseshippingstatus) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (merchandiseshippingstatus.getShipment() != null) {
            if (merchandiseshippingstatus.getShipment().getId() != null) {
                MerchandiseShipping existingShipment = shipmentRepository.findById(
                    merchandiseshippingstatus.getShipment().getId()
                ).orElseThrow(() -> new RuntimeException("MerchandiseShipping not found with id "
                    + merchandiseshippingstatus.getShipment().getId()));
                merchandiseshippingstatus.setShipment(existingShipment);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                MerchandiseShipping newShipment = shipmentRepository.save(merchandiseshippingstatus.getShipment());
                merchandiseshippingstatus.setShipment(newShipment);
            }
        }
        
    // ---------- OneToOne ----------
    return merchandiseshippingstatusRepository.save(merchandiseshippingstatus);
}


    public MerchandiseShippingStatus update(Long id, MerchandiseShippingStatus merchandiseshippingstatusRequest) {
        MerchandiseShippingStatus existing = merchandiseshippingstatusRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseShippingStatus not found"));

    // Copier les champs simples
        existing.setStatus(merchandiseshippingstatusRequest.getStatus());
        existing.setDate(merchandiseshippingstatusRequest.getDate());

    // ---------- Relations ManyToOne ----------
        if (merchandiseshippingstatusRequest.getShipment() != null &&
            merchandiseshippingstatusRequest.getShipment().getId() != null) {

            MerchandiseShipping existingShipment = shipmentRepository.findById(
                merchandiseshippingstatusRequest.getShipment().getId()
            ).orElseThrow(() -> new RuntimeException("MerchandiseShipping not found"));

            existing.setShipment(existingShipment);
        } else {
            existing.setShipment(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return merchandiseshippingstatusRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<MerchandiseShippingStatus> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        MerchandiseShippingStatus entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getShipment() != null) {
            entity.setShipment(null);
        }
        
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<MerchandiseShippingStatus> saveAll(List<MerchandiseShippingStatus> merchandiseshippingstatusList) {

        return merchandiseshippingstatusRepository.saveAll(merchandiseshippingstatusList);
    }

}