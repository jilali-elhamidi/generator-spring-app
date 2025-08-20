package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseSupplier;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseSupplierRepository;
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MerchandiseSupplierService extends BaseService<MerchandiseSupplier> {

    protected final MerchandiseSupplierRepository merchandisesupplierRepository;
    private final MerchandiseRepository suppliedMerchandiseRepository;

    public MerchandiseSupplierService(MerchandiseSupplierRepository repository, MerchandiseRepository suppliedMerchandiseRepository)
    {
        super(repository);
        this.merchandisesupplierRepository = repository;
        this.suppliedMerchandiseRepository = suppliedMerchandiseRepository;
    }

    @Override
    public MerchandiseSupplier save(MerchandiseSupplier merchandisesupplier) {
    // ---------- OneToMany ----------
        if (merchandisesupplier.getSuppliedMerchandise() != null) {
            List<Merchandise> managedSuppliedMerchandise = new ArrayList<>();
            for (Merchandise item : merchandisesupplier.getSuppliedMerchandise()) {
                if (item.getId() != null) {
                    Merchandise existingItem = suppliedMerchandiseRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Merchandise not found"));

                     existingItem.setSupplier(merchandisesupplier);
                     managedSuppliedMerchandise.add(existingItem);
                } else {
                    item.setSupplier(merchandisesupplier);
                    managedSuppliedMerchandise.add(item);
                }
            }
            merchandisesupplier.setSuppliedMerchandise(managedSuppliedMerchandise);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------

    return merchandisesupplierRepository.save(merchandisesupplier);
}


    public MerchandiseSupplier update(Long id, MerchandiseSupplier merchandisesupplierRequest) {
        MerchandiseSupplier existing = merchandisesupplierRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseSupplier not found"));

    // Copier les champs simples
        existing.setName(merchandisesupplierRequest.getName());
        existing.setContactEmail(merchandisesupplierRequest.getContactEmail());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getSuppliedMerchandise().clear();

        if (merchandisesupplierRequest.getSuppliedMerchandise() != null) {
            for (var item : merchandisesupplierRequest.getSuppliedMerchandise()) {
                Merchandise existingItem;
                if (item.getId() != null) {
                    existingItem = suppliedMerchandiseRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Merchandise not found"));
                } else {
                existingItem = item;
                }

                existingItem.setSupplier(existing);
                existing.getSuppliedMerchandise().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------

    return merchandisesupplierRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<MerchandiseSupplier> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        MerchandiseSupplier entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getSuppliedMerchandise() != null) {
            for (var child : entity.getSuppliedMerchandise()) {
                
                child.setSupplier(null); // retirer la référence inverse
                
            }
            entity.getSuppliedMerchandise().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}