package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseStockHistory;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseStockHistoryRepository;

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
public class MerchandiseStockHistoryService extends BaseService<MerchandiseStockHistory> {

    protected final MerchandiseStockHistoryRepository merchandisestockhistoryRepository;
    
    protected final MerchandiseRepository merchandiseItemRepository;
    

    public MerchandiseStockHistoryService(MerchandiseStockHistoryRepository repository, MerchandiseRepository merchandiseItemRepository)
    {
        super(repository);
        this.merchandisestockhistoryRepository = repository;
        
        this.merchandiseItemRepository = merchandiseItemRepository;
        
    }

    @Transactional
    @Override
    public MerchandiseStockHistory save(MerchandiseStockHistory merchandisestockhistory) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (merchandisestockhistory.getMerchandiseItem() != null) {
            if (merchandisestockhistory.getMerchandiseItem().getId() != null) {
                Merchandise existingMerchandiseItem = merchandiseItemRepository.findById(
                    merchandisestockhistory.getMerchandiseItem().getId()
                ).orElseThrow(() -> new RuntimeException("Merchandise not found with id "
                    + merchandisestockhistory.getMerchandiseItem().getId()));
                merchandisestockhistory.setMerchandiseItem(existingMerchandiseItem);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Merchandise newMerchandiseItem = merchandiseItemRepository.save(merchandisestockhistory.getMerchandiseItem());
                merchandisestockhistory.setMerchandiseItem(newMerchandiseItem);
            }
        }
        
    // ---------- OneToOne ----------
    return merchandisestockhistoryRepository.save(merchandisestockhistory);
}

    @Transactional
    @Override
    public MerchandiseStockHistory update(Long id, MerchandiseStockHistory merchandisestockhistoryRequest) {
        MerchandiseStockHistory existing = merchandisestockhistoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseStockHistory not found"));

    // Copier les champs simples
        existing.setChangeDate(merchandisestockhistoryRequest.getChangeDate());
        existing.setQuantityChange(merchandisestockhistoryRequest.getQuantityChange());
        existing.setReason(merchandisestockhistoryRequest.getReason());

    // ---------- Relations ManyToOne ----------
        if (merchandisestockhistoryRequest.getMerchandiseItem() != null &&
            merchandisestockhistoryRequest.getMerchandiseItem().getId() != null) {

            Merchandise existingMerchandiseItem = merchandiseItemRepository.findById(
                merchandisestockhistoryRequest.getMerchandiseItem().getId()
            ).orElseThrow(() -> new RuntimeException("Merchandise not found"));

            existing.setMerchandiseItem(existingMerchandiseItem);
        } else {
            existing.setMerchandiseItem(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return merchandisestockhistoryRepository.save(existing);
}

    // Pagination simple
    public Page<MerchandiseStockHistory> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<MerchandiseStockHistory> search(Map<String, String> filters, Pageable pageable) {
        return super.search(MerchandiseStockHistory.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<MerchandiseStockHistory> saveAll(List<MerchandiseStockHistory> merchandisestockhistoryList) {
        return super.saveAll(merchandisestockhistoryList);
    }

}