package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseOrderItem;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseOrderItemRepository;
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseOrder;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseOrderRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MerchandiseOrderItemService extends BaseService<MerchandiseOrderItem> {

    protected final MerchandiseOrderItemRepository merchandiseorderitemRepository;
    private final MerchandiseRepository merchandiseItemRepository;
    private final MerchandiseOrderRepository orderRepository;

    public MerchandiseOrderItemService(MerchandiseOrderItemRepository repository,MerchandiseRepository merchandiseItemRepository,MerchandiseOrderRepository orderRepository)
    {
        super(repository);
        this.merchandiseorderitemRepository = repository;
        this.merchandiseItemRepository = merchandiseItemRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public MerchandiseOrderItem save(MerchandiseOrderItem merchandiseorderitem) {

        if (merchandiseorderitem.getMerchandiseItem() != null && merchandiseorderitem.getMerchandiseItem().getId() != null) {
        Merchandise merchandiseItem = merchandiseItemRepository.findById(merchandiseorderitem.getMerchandiseItem().getId())
                .orElseThrow(() -> new RuntimeException("Merchandise not found"));
        merchandiseorderitem.setMerchandiseItem(merchandiseItem);
        }

        if (merchandiseorderitem.getOrder() != null && merchandiseorderitem.getOrder().getId() != null) {
        MerchandiseOrder order = orderRepository.findById(merchandiseorderitem.getOrder().getId())
                .orElseThrow(() -> new RuntimeException("MerchandiseOrder not found"));
        merchandiseorderitem.setOrder(order);
        }

        return merchandiseorderitemRepository.save(merchandiseorderitem);
    }


    public MerchandiseOrderItem update(Long id, MerchandiseOrderItem merchandiseorderitemRequest) {
        MerchandiseOrderItem existing = merchandiseorderitemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseOrderItem not found"));

    // Copier les champs simples
        existing.setQuantity(merchandiseorderitemRequest.getQuantity());
        existing.setPriceAtPurchase(merchandiseorderitemRequest.getPriceAtPurchase());

// Relations ManyToOne : mise à jour conditionnelle

        if (merchandiseorderitemRequest.getMerchandiseItem() != null && merchandiseorderitemRequest.getMerchandiseItem().getId() != null) {
        Merchandise merchandiseItem = merchandiseItemRepository.findById(merchandiseorderitemRequest.getMerchandiseItem().getId())
                .orElseThrow(() -> new RuntimeException("Merchandise not found"));
        existing.setMerchandiseItem(merchandiseItem);
        }

        if (merchandiseorderitemRequest.getOrder() != null && merchandiseorderitemRequest.getOrder().getId() != null) {
        MerchandiseOrder order = orderRepository.findById(merchandiseorderitemRequest.getOrder().getId())
                .orElseThrow(() -> new RuntimeException("MerchandiseOrder not found"));
        existing.setOrder(order);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    


        return merchandiseorderitemRepository.save(existing);
    }
}