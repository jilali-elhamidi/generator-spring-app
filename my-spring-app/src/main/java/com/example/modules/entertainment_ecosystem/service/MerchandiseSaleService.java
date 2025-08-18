package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseSale;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseSaleRepository;
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MerchandiseSaleService extends BaseService<MerchandiseSale> {

    protected final MerchandiseSaleRepository merchandisesaleRepository;
    private final MerchandiseRepository merchandiseItemRepository;
    private final UserProfileRepository userRepository;

    public MerchandiseSaleService(MerchandiseSaleRepository repository,MerchandiseRepository merchandiseItemRepository,UserProfileRepository userRepository)
    {
        super(repository);
        this.merchandisesaleRepository = repository;
        this.merchandiseItemRepository = merchandiseItemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MerchandiseSale save(MerchandiseSale merchandisesale) {


    

    

    if (merchandisesale.getMerchandiseItem() != null
        && merchandisesale.getMerchandiseItem().getId() != null) {
        Merchandise existingMerchandiseItem = merchandiseItemRepository.findById(
        merchandisesale.getMerchandiseItem().getId()
        ).orElseThrow(() -> new RuntimeException("Merchandise not found"));
        merchandisesale.setMerchandiseItem(existingMerchandiseItem);
        }
    
    if (merchandisesale.getUser() != null
        && merchandisesale.getUser().getId() != null) {
        UserProfile existingUser = userRepository.findById(
        merchandisesale.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        merchandisesale.setUser(existingUser);
        }
    

        return merchandisesaleRepository.save(merchandisesale);
    }


    public MerchandiseSale update(Long id, MerchandiseSale merchandisesaleRequest) {
        MerchandiseSale existing = merchandisesaleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseSale not found"));

    // Copier les champs simples
        existing.setSaleDate(merchandisesaleRequest.getSaleDate());
        existing.setQuantity(merchandisesaleRequest.getQuantity());

// Relations ManyToOne : mise à jour conditionnelle
        if (merchandisesaleRequest.getMerchandiseItem() != null &&
        merchandisesaleRequest.getMerchandiseItem().getId() != null) {

        Merchandise existingMerchandiseItem = merchandiseItemRepository.findById(
        merchandisesaleRequest.getMerchandiseItem().getId()
        ).orElseThrow(() -> new RuntimeException("Merchandise not found"));

        existing.setMerchandiseItem(existingMerchandiseItem);
        } else {
        existing.setMerchandiseItem(null);
        }
        if (merchandisesaleRequest.getUser() != null &&
        merchandisesaleRequest.getUser().getId() != null) {

        UserProfile existingUser = userRepository.findById(
        merchandisesaleRequest.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

        existing.setUser(existingUser);
        } else {
        existing.setUser(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    


        return merchandisesaleRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<MerchandiseSale> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

MerchandiseSale entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    


// --- Dissocier ManyToMany ---

    

    



// --- Dissocier OneToOne ---

    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getMerchandiseItem() != null) {
        entity.setMerchandiseItem(null);
        }
    

    
        if (entity.getUser() != null) {
        entity.setUser(null);
        }
    


repository.delete(entity);
return true;
}
}