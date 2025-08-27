package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseOrderItem;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseOrderItemRepository;

import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;

import com.example.modules.entertainment_ecosystem.model.MerchandiseOrder;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseOrderRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MerchandiseOrderItemService extends BaseService<MerchandiseOrderItem> {

    protected final MerchandiseOrderItemRepository merchandiseorderitemRepository;
    
    protected final MerchandiseRepository merchandiseItemRepository;
    
    protected final MerchandiseOrderRepository orderRepository;
    

    public MerchandiseOrderItemService(MerchandiseOrderItemRepository repository, MerchandiseRepository merchandiseItemRepository, MerchandiseOrderRepository orderRepository)
    {
        super(repository);
        this.merchandiseorderitemRepository = repository;
        
        this.merchandiseItemRepository = merchandiseItemRepository;
        
        this.orderRepository = orderRepository;
        
    }

    @Transactional
    @Override
    public MerchandiseOrderItem save(MerchandiseOrderItem merchandiseorderitem) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (merchandiseorderitem.getMerchandiseItem() != null) {
            if (merchandiseorderitem.getMerchandiseItem().getId() != null) {
                Merchandise existingMerchandiseItem = merchandiseItemRepository.findById(
                    merchandiseorderitem.getMerchandiseItem().getId()
                ).orElseThrow(() -> new RuntimeException("Merchandise not found with id "
                    + merchandiseorderitem.getMerchandiseItem().getId()));
                merchandiseorderitem.setMerchandiseItem(existingMerchandiseItem);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Merchandise newMerchandiseItem = merchandiseItemRepository.save(merchandiseorderitem.getMerchandiseItem());
                merchandiseorderitem.setMerchandiseItem(newMerchandiseItem);
            }
        }
        
        if (merchandiseorderitem.getOrder() != null) {
            if (merchandiseorderitem.getOrder().getId() != null) {
                MerchandiseOrder existingOrder = orderRepository.findById(
                    merchandiseorderitem.getOrder().getId()
                ).orElseThrow(() -> new RuntimeException("MerchandiseOrder not found with id "
                    + merchandiseorderitem.getOrder().getId()));
                merchandiseorderitem.setOrder(existingOrder);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                MerchandiseOrder newOrder = orderRepository.save(merchandiseorderitem.getOrder());
                merchandiseorderitem.setOrder(newOrder);
            }
        }
        
    // ---------- OneToOne ----------
    return merchandiseorderitemRepository.save(merchandiseorderitem);
}

    @Transactional
    @Override
    public MerchandiseOrderItem update(Long id, MerchandiseOrderItem merchandiseorderitemRequest) {
        MerchandiseOrderItem existing = merchandiseorderitemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseOrderItem not found"));

    // Copier les champs simples
        existing.setQuantity(merchandiseorderitemRequest.getQuantity());
        existing.setPriceAtPurchase(merchandiseorderitemRequest.getPriceAtPurchase());

    // ---------- Relations ManyToOne ----------
        if (merchandiseorderitemRequest.getMerchandiseItem() != null &&
            merchandiseorderitemRequest.getMerchandiseItem().getId() != null) {

            Merchandise existingMerchandiseItem = merchandiseItemRepository.findById(
                merchandiseorderitemRequest.getMerchandiseItem().getId()
            ).orElseThrow(() -> new RuntimeException("Merchandise not found"));

            existing.setMerchandiseItem(existingMerchandiseItem);
        } else {
            existing.setMerchandiseItem(null);
        }
        
        if (merchandiseorderitemRequest.getOrder() != null &&
            merchandiseorderitemRequest.getOrder().getId() != null) {

            MerchandiseOrder existingOrder = orderRepository.findById(
                merchandiseorderitemRequest.getOrder().getId()
            ).orElseThrow(() -> new RuntimeException("MerchandiseOrder not found"));

            existing.setOrder(existingOrder);
        } else {
            existing.setOrder(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return merchandiseorderitemRepository.save(existing);
}

    // Pagination simple
    public Page<MerchandiseOrderItem> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<MerchandiseOrderItem> search(Map<String, String> filters, Pageable pageable) {
        return super.search(MerchandiseOrderItem.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<MerchandiseOrderItem> saveAll(List<MerchandiseOrderItem> merchandiseorderitemList) {
        return super.saveAll(merchandiseorderitemList);
    }

}