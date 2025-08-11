package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.OrderItem;
import com.example.modules.ecommerce.repository.OrderItemRepository;
import com.example.modules.ecommerce.model.Product;
import com.example.modules.ecommerce.repository.ProductRepository;
import com.example.modules.ecommerce.model.Order;
import com.example.modules.ecommerce.repository.OrderRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class OrderItemService extends BaseService<OrderItem> {

    protected final OrderItemRepository orderitemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderItemService(OrderItemRepository repository, ProductRepository productRepository, OrderRepository orderRepository) {
    super(repository);
    this.orderitemRepository = repository;
    this.productRepository = productRepository;
    this.orderRepository = orderRepository;
}

    @Override
    public OrderItem save(OrderItem orderitem) {

        if (orderitem.getProduct() != null && orderitem.getProduct().getId() != null) {
        Product product = productRepository.findById(orderitem.getProduct().getId())
            .orElseThrow(() -> new RuntimeException("Product not found"));
        orderitem.setProduct(product);
        }

        if (orderitem.getOrder() != null && orderitem.getOrder().getId() != null) {
        Order order = orderRepository.findById(orderitem.getOrder().getId())
            .orElseThrow(() -> new RuntimeException("Order not found"));
        orderitem.setOrder(order);
        }

    return orderitemRepository.save(orderitem);
    }

    public OrderItem update(Long id, OrderItem orderitemRequest) {
    OrderItem existing = orderitemRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("OrderItem not found"));

// Copier les champs simples
    existing.setQuantity(orderitemRequest.getQuantity());
    existing.setPrice(orderitemRequest.getPrice());

// Relations ManyToOne : mise à jour conditionnelle
    if (orderitemRequest.getProduct() != null &&orderitemRequest.getProduct().getId() != null) {
    Product product = productRepository.findById(orderitemRequest.getProduct().getId())
        .orElseThrow(() -> new RuntimeException("Product not found"));
    existing.setProduct(product);
    }
    // Sinon on garde la relation existante
    if (orderitemRequest.getOrder() != null &&orderitemRequest.getOrder().getId() != null) {
    Order order = orderRepository.findById(orderitemRequest.getOrder().getId())
        .orElseThrow(() -> new RuntimeException("Order not found"));
    existing.setOrder(order);
    }
    // Sinon on garde la relation existante

// Relations ManyToMany : synchronisation sécurisée
// Relations OneToMany : synchronisation sécurisée

    return orderitemRepository.save(existing);

    }
}
