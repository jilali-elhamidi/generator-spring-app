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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class OrderItemService extends BaseService<OrderItem> {

    protected final OrderItemRepository orderitemRepository;
    
    protected final ProductRepository productRepository;
    
    protected final OrderRepository orderRepository;
    

    public OrderItemService(OrderItemRepository repository, ProductRepository productRepository, OrderRepository orderRepository)
    {
        super(repository);
        this.orderitemRepository = repository;
        
        this.productRepository = productRepository;
        
        this.orderRepository = orderRepository;
        
    }

    @Transactional
    @Override
    public OrderItem save(OrderItem orderitem) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (orderitem.getProduct() != null) {
            if (orderitem.getProduct().getId() != null) {
                Product existingProduct = productRepository.findById(
                    orderitem.getProduct().getId()
                ).orElseThrow(() -> new RuntimeException("Product not found with id "
                    + orderitem.getProduct().getId()));
                orderitem.setProduct(existingProduct);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Product newProduct = productRepository.save(orderitem.getProduct());
                orderitem.setProduct(newProduct);
            }
        }
        
        if (orderitem.getOrder() != null) {
            if (orderitem.getOrder().getId() != null) {
                Order existingOrder = orderRepository.findById(
                    orderitem.getOrder().getId()
                ).orElseThrow(() -> new RuntimeException("Order not found with id "
                    + orderitem.getOrder().getId()));
                orderitem.setOrder(existingOrder);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Order newOrder = orderRepository.save(orderitem.getOrder());
                orderitem.setOrder(newOrder);
            }
        }
        
    // ---------- OneToOne ----------
    return orderitemRepository.save(orderitem);
}

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return orderitemRepository.save(existing);
}

    // Pagination simple
    public Page<OrderItem> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<OrderItem> search(Map<String, String> filters, Pageable pageable) {
        return super.search(OrderItem.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<OrderItem> saveAll(List<OrderItem> orderitemList) {
        return super.saveAll(orderitemList);
    }

}