package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.Order;
import com.example.modules.ecommerce.repository.OrderRepository;
import com.example.modules.ecommerce.model.User;
import com.example.modules.ecommerce.repository.UserRepository;
import com.example.modules.ecommerce.model.OrderItem;
import com.example.modules.ecommerce.repository.OrderItemRepository;
import com.example.modules.ecommerce.model.Payment;
import com.example.modules.ecommerce.repository.PaymentRepository;
import com.example.modules.ecommerce.model.Shipment;
import com.example.modules.ecommerce.repository.ShipmentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class OrderService extends BaseService<Order> {

    protected final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemsRepository;
    private final PaymentRepository paymentRepository;
    private final ShipmentRepository shipmentRepository;

    public OrderService(OrderRepository repository, UserRepository userRepository, OrderItemRepository orderItemsRepository, PaymentRepository paymentRepository, ShipmentRepository shipmentRepository)
    {
        super(repository);
        this.orderRepository = repository;
        this.userRepository = userRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.paymentRepository = paymentRepository;
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public Order save(Order order) {
    // ---------- OneToMany ----------
        if (order.getOrderItems() != null) {
            List<OrderItem> managedOrderItems = new ArrayList<>();
            for (OrderItem item : order.getOrderItems()) {
                if (item.getId() != null) {
                    OrderItem existingItem = orderItemsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("OrderItem not found"));

                     existingItem.setOrder(order);
                     managedOrderItems.add(existingItem);
                } else {
                    item.setOrder(order);
                    managedOrderItems.add(item);
                }
            }
            order.setOrderItems(managedOrderItems);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (order.getUser() != null &&
            order.getUser().getId() != null) {

            User existingUser = userRepository.findById(
                order.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("User not found"));

            order.setUser(existingUser);
        }
        
    // ---------- OneToOne ----------
        if (order.getPayment() != null) {

            order.setPayment(
                paymentRepository.findById(order.getPayment().getId())
                    .orElseThrow(() -> new RuntimeException("payment not found"))
            );
            order.getPayment().setOrder(order);
        }
        
        if (order.getShipment() != null) {

            order.setShipment(
                shipmentRepository.findById(order.getShipment().getId())
                    .orElseThrow(() -> new RuntimeException("shipment not found"))
            );
            order.getShipment().setOrder(order);
        }
        
    return orderRepository.save(order);
}


    public Order update(Long id, Order orderRequest) {
        Order existing = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));

    // Copier les champs simples
        existing.setOrderDate(orderRequest.getOrderDate());
        existing.setStatus(orderRequest.getStatus());

    // ---------- Relations ManyToOne ----------
        if (orderRequest.getUser() != null &&
            orderRequest.getUser().getId() != null) {

            User existingUser = userRepository.findById(
                orderRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("User not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getOrderItems().clear();

        if (orderRequest.getOrderItems() != null) {
            for (var item : orderRequest.getOrderItems()) {
                OrderItem existingItem;
                if (item.getId() != null) {
                    existingItem = orderItemsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("OrderItem not found"));
                } else {
                existingItem = item;
                }

                existingItem.setOrder(existing);
                existing.getOrderItems().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
        if (orderRequest.getPayment() != null &&orderRequest.getPayment().getId() != null) {

        Payment payment = paymentRepository.findById(orderRequest.getPayment().getId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        existing.setPayment(payment);
        payment.setOrder(existing);
        }
    
        if (orderRequest.getShipment() != null &&orderRequest.getShipment().getId() != null) {

        Shipment shipment = shipmentRepository.findById(orderRequest.getShipment().getId())
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

        existing.setShipment(shipment);
        shipment.setOrder(existing);
        }
    
    return orderRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Order> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Order entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getOrderItems() != null) {
            for (var child : entity.getOrderItems()) {
                // retirer la référence inverse
                child.setOrder(null);
            }
            entity.getOrderItems().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
        if (entity.getPayment() != null) {
            entity.getPayment().setOrder(null);
            entity.setPayment(null);
        }
        
        if (entity.getShipment() != null) {
            entity.getShipment().setOrder(null);
            entity.setShipment(null);
        }
        
    // --- Dissocier ManyToOne ---
        if (entity.getUser() != null) {
            entity.setUser(null);
        }
        
        repository.delete(entity);
        return true;
    }
}