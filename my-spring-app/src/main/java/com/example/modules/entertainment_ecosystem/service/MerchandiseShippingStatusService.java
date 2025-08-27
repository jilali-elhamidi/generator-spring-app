package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseShippingStatus;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseShippingStatusRepository;

import com.example.modules.entertainment_ecosystem.model.MerchandiseShipping;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseShippingRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MerchandiseShippingStatusService extends BaseService<MerchandiseShippingStatus> {

    protected final MerchandiseShippingStatusRepository merchandiseshippingstatusRepository;
    
    protected final MerchandiseShippingRepository shipmentRepository;
    

    public MerchandiseShippingStatusService(MerchandiseShippingStatusRepository repository, MerchandiseShippingRepository shipmentRepository)
    {
        super(repository);
        this.merchandiseshippingstatusRepository = repository;
        
        this.shipmentRepository = shipmentRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return merchandiseshippingstatusRepository.save(existing);
}

    // Pagination simple
    public Page<MerchandiseShippingStatus> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<MerchandiseShippingStatus> search(Map<String, String> filters, Pageable pageable) {
        return super.search(MerchandiseShippingStatus.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<MerchandiseShippingStatus> saveAll(List<MerchandiseShippingStatus> merchandiseshippingstatusList) {
        return super.saveAll(merchandiseshippingstatusList);
    }

}