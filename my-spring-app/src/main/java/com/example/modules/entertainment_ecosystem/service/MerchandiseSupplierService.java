package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseSupplier;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseSupplierRepository;
import com.example.modules.entertainment_ecosystem.model.Merchandise;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MerchandiseSupplierService extends BaseService<MerchandiseSupplier> {

    protected final MerchandiseSupplierRepository merchandisesupplierRepository;

    public MerchandiseSupplierService(MerchandiseSupplierRepository repository)
    {
        super(repository);
        this.merchandisesupplierRepository = repository;
    }

    @Override
    public MerchandiseSupplier save(MerchandiseSupplier merchandisesupplier) {

        if (merchandisesupplier.getSuppliedMerchandise() != null) {
            for (Merchandise item : merchandisesupplier.getSuppliedMerchandise()) {
            item.setSupplier(merchandisesupplier);
            }
        }

        return merchandisesupplierRepository.save(merchandisesupplier);
    }


    public MerchandiseSupplier update(Long id, MerchandiseSupplier merchandisesupplierRequest) {
        MerchandiseSupplier existing = merchandisesupplierRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseSupplier not found"));

    // Copier les champs simples
        existing.setName(merchandisesupplierRequest.getName());
        existing.setContactEmail(merchandisesupplierRequest.getContactEmail());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getSuppliedMerchandise().clear();
        if (merchandisesupplierRequest.getSuppliedMerchandise() != null) {
            for (var item : merchandisesupplierRequest.getSuppliedMerchandise()) {
            item.setSupplier(existing);
            existing.getSuppliedMerchandise().add(item);
            }
        }

    


        return merchandisesupplierRepository.save(existing);
    }
}