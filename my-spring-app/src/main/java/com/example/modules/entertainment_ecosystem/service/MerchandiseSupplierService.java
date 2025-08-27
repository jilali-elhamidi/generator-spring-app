package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseSupplier;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseSupplierRepository;

import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MerchandiseSupplierService extends BaseService<MerchandiseSupplier> {

    protected final MerchandiseSupplierRepository merchandisesupplierRepository;
    
    protected final MerchandiseRepository suppliedMerchandiseRepository;
    

    public MerchandiseSupplierService(MerchandiseSupplierRepository repository, MerchandiseRepository suppliedMerchandiseRepository)
    {
        super(repository);
        this.merchandisesupplierRepository = repository;
        
        this.suppliedMerchandiseRepository = suppliedMerchandiseRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
    public MerchandiseSupplier update(Long id, MerchandiseSupplier merchandisesupplierRequest) {
        MerchandiseSupplier existing = merchandisesupplierRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseSupplier not found"));

    // Copier les champs simples
        existing.setName(merchandisesupplierRequest.getName());
        existing.setContactEmail(merchandisesupplierRequest.getContactEmail());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<MerchandiseSupplier> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<MerchandiseSupplier> search(Map<String, String> filters, Pageable pageable) {
        return super.search(MerchandiseSupplier.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<MerchandiseSupplier> saveAll(List<MerchandiseSupplier> merchandisesupplierList) {
        return super.saveAll(merchandisesupplierList);
    }

}