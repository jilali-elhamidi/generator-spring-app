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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MerchandiseSaleService extends BaseService<MerchandiseSale> {

    protected final MerchandiseSaleRepository merchandisesaleRepository;
    
    protected final MerchandiseRepository merchandiseItemRepository;
    
    protected final UserProfileRepository userRepository;
    

    public MerchandiseSaleService(MerchandiseSaleRepository repository, MerchandiseRepository merchandiseItemRepository, UserProfileRepository userRepository)
    {
        super(repository);
        this.merchandisesaleRepository = repository;
        
        this.merchandiseItemRepository = merchandiseItemRepository;
        
        this.userRepository = userRepository;
        
    }

    @Transactional
    @Override
    public MerchandiseSale save(MerchandiseSale merchandisesale) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (merchandisesale.getMerchandiseItem() != null) {
            if (merchandisesale.getMerchandiseItem().getId() != null) {
                Merchandise existingMerchandiseItem = merchandiseItemRepository.findById(
                    merchandisesale.getMerchandiseItem().getId()
                ).orElseThrow(() -> new RuntimeException("Merchandise not found with id "
                    + merchandisesale.getMerchandiseItem().getId()));
                merchandisesale.setMerchandiseItem(existingMerchandiseItem);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Merchandise newMerchandiseItem = merchandiseItemRepository.save(merchandisesale.getMerchandiseItem());
                merchandisesale.setMerchandiseItem(newMerchandiseItem);
            }
        }
        
        if (merchandisesale.getUser() != null) {
            if (merchandisesale.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    merchandisesale.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + merchandisesale.getUser().getId()));
                merchandisesale.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(merchandisesale.getUser());
                merchandisesale.setUser(newUser);
            }
        }
        
    // ---------- OneToOne ----------
    return merchandisesaleRepository.save(merchandisesale);
}

    @Transactional
    @Override
    public MerchandiseSale update(Long id, MerchandiseSale merchandisesaleRequest) {
        MerchandiseSale existing = merchandisesaleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseSale not found"));

    // Copier les champs simples
        existing.setSaleDate(merchandisesaleRequest.getSaleDate());
        existing.setQuantity(merchandisesaleRequest.getQuantity());

    // ---------- Relations ManyToOne ----------
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
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return merchandisesaleRepository.save(existing);
}

    // Pagination simple
    public Page<MerchandiseSale> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<MerchandiseSale> search(Map<String, String> filters, Pageable pageable) {
        return super.search(MerchandiseSale.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<MerchandiseSale> saveAll(List<MerchandiseSale> merchandisesaleList) {
        return super.saveAll(merchandisesaleList);
    }

}