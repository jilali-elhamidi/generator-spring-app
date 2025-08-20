package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.OrderItem;
import com.example.modules.ecommerce.repository.OrderItemRepository;
import com.example.modules.ecommerce.model.Product;
import com.example.modules.ecommerce.repository.ProductRepository;
import com.example.modules.ecommerce.model.Order;
import com.example.modules.ecommerce.repository.OrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class OrderItemService extends BaseService<OrderItem> {

    protected final OrderItemRepository orderitemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderItemService(OrderItemRepository repository, ProductRepository productRepository, OrderRepository orderRepository)
    {
        super(repository);
        this.orderitemRepository = repository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderItem save(OrderItem orderitem) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (orderitem.getProduct() != null &&
            orderitem.getProduct().getId() != null) {

            Product existingProduct = productRepository.findById(
                orderitem.getProduct().getId()
            ).orElseThrow(() -> new RuntimeException("Product not found"));

            orderitem.setProduct(existingProduct);
        }
        
        if (orderitem.getOrder() != null &&
            orderitem.getOrder().getId() != null) {

            Order existingOrder = orderRepository.findById(
                orderitem.getOrder().getId()
            ).orElseThrow(() -> new RuntimeException("Order not found"));

            orderitem.setOrder(existingOrder);
        }
        
    // ---------- OneToOne ----------
    return orderitemRepository.save(orderitem);
}


    public OrderItem update(Long id, OrderItem orderitemRequest) {
        OrderItem existing = orderitemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("OrderItem not found"));

    // Copier les champs simples
        existing.setQuantity(orderitemRequest.getQuantity());
        existing.setPrice(orderitemRequest.getPrice());

    // ---------- Relations ManyToOne ----------
        if (orderitemRequest.getProduct() != null &&
            orderitemRequest.getProduct().getId() != null) {

            Product existingProduct = productRepository.findById(
                orderitemRequest.getProduct().getId()
            ).orElseThrow(() -> new RuntimeException("Product not found"));

            existing.setProduct(existingProduct);
        } else {
            existing.setProduct(null);
        }
        
        if (orderitemRequest.getOrder() != null &&
            orderitemRequest.getOrder().getId() != null) {

            Order existingOrder = orderRepository.findById(
                orderitemRequest.getOrder().getId()
            ).orElseThrow(() -> new RuntimeException("Order not found"));

            existing.setOrder(existingOrder);
        } else {
            existing.setOrder(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return orderitemRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<OrderItem> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        OrderItem entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getProduct() != null) {
            entity.setProduct(null);
        }
        
        if (entity.getOrder() != null) {
            entity.setOrder(null);
        }
        
        repository.delete(entity);
        return true;
    }
}