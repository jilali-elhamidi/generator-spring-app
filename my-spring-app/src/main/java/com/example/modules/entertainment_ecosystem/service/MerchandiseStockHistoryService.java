package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseStockHistory;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseStockHistoryRepository;
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MerchandiseStockHistoryService extends BaseService<MerchandiseStockHistory> {

    protected final MerchandiseStockHistoryRepository merchandisestockhistoryRepository;
    private final MerchandiseRepository merchandiseItemRepository;

    public MerchandiseStockHistoryService(MerchandiseStockHistoryRepository repository,MerchandiseRepository merchandiseItemRepository)
    {
        super(repository);
        this.merchandisestockhistoryRepository = repository;
        this.merchandiseItemRepository = merchandiseItemRepository;
    }

    @Override
    public MerchandiseStockHistory save(MerchandiseStockHistory merchandisestockhistory) {


    


    

    if (merchandisestockhistory.getMerchandiseItem() != null
        && merchandisestockhistory.getMerchandiseItem().getId() != null) {
        Merchandise existingMerchandiseItem = merchandiseItemRepository.findById(
        merchandisestockhistory.getMerchandiseItem().getId()
        ).orElseThrow(() -> new RuntimeException("Merchandise not found"));
        merchandisestockhistory.setMerchandiseItem(existingMerchandiseItem);
        }
    

        return merchandisestockhistoryRepository.save(merchandisestockhistory);
    }


    public MerchandiseStockHistory update(Long id, MerchandiseStockHistory merchandisestockhistoryRequest) {
        MerchandiseStockHistory existing = merchandisestockhistoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseStockHistory not found"));

    // Copier les champs simples
        existing.setChangeDate(merchandisestockhistoryRequest.getChangeDate());
        existing.setQuantityChange(merchandisestockhistoryRequest.getQuantityChange());
        existing.setReason(merchandisestockhistoryRequest.getReason());

// Relations ManyToOne : mise à jour conditionnelle
        if (merchandisestockhistoryRequest.getMerchandiseItem() != null &&
        merchandisestockhistoryRequest.getMerchandiseItem().getId() != null) {

        Merchandise existingMerchandiseItem = merchandiseItemRepository.findById(
        merchandisestockhistoryRequest.getMerchandiseItem().getId()
        ).orElseThrow(() -> new RuntimeException("Merchandise not found"));

        existing.setMerchandiseItem(existingMerchandiseItem);
        } else {
        existing.setMerchandiseItem(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    


        return merchandisestockhistoryRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<MerchandiseStockHistory> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

MerchandiseStockHistory entity = entityOpt.get();

// --- Dissocier OneToMany ---

    


// --- Dissocier ManyToMany ---

    



// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    
        if (entity.getMerchandiseItem() != null) {
        entity.setMerchandiseItem(null);
        }
    


repository.delete(entity);
return true;
}
}