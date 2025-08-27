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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class OrderService extends BaseService<Order> {

    protected final OrderRepository orderRepository;
    
    protected final UserRepository userRepository;
    
    protected final OrderItemRepository orderItemsRepository;
    
    protected final PaymentRepository paymentRepository;
    
    protected final ShipmentRepository shipmentRepository;
    

    public OrderService(OrderRepository repository, UserRepository userRepository, OrderItemRepository orderItemsRepository, PaymentRepository paymentRepository, ShipmentRepository shipmentRepository)
    {
        super(repository);
        this.orderRepository = repository;
        
        this.userRepository = userRepository;
        
        this.orderItemsRepository = orderItemsRepository;
        
        this.paymentRepository = paymentRepository;
        
        this.shipmentRepository = shipmentRepository;
        
    }

    @Transactional
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
        if (order.getUser() != null) {
            if (order.getUser().getId() != null) {
                User existingUser = userRepository.findById(
                    order.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("User not found with id "
                    + order.getUser().getId()));
                order.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                User newUser = userRepository.save(order.getUser());
                order.setUser(newUser);
            }
        }
        
    // ---------- OneToOne ----------
        if (order.getPayment() != null) {
            if (order.getPayment().getId() != null) {
                Payment existingPayment = paymentRepository.findById(order.getPayment().getId())
                    .orElseThrow(() -> new RuntimeException("Payment not found with id "
                        + order.getPayment().getId()));
                order.setPayment(existingPayment);
            } else {
                // Nouvel objet → sauvegarde d'abord
                Payment newPayment = paymentRepository.save(order.getPayment());
                order.setPayment(newPayment);
            }

            order.getPayment().setOrder(order);
        }
        
        if (order.getShipment() != null) {
            if (order.getShipment().getId() != null) {
                Shipment existingShipment = shipmentRepository.findById(order.getShipment().getId())
                    .orElseThrow(() -> new RuntimeException("Shipment not found with id "
                        + order.getShipment().getId()));
                order.setShipment(existingShipment);
            } else {
                // Nouvel objet → sauvegarde d'abord
                Shipment newShipment = shipmentRepository.save(order.getShipment());
                order.setShipment(newShipment);
            }

            order.getShipment().setOrder(order);
        }
        
    return orderRepository.save(order);
}

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<Order> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Order> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Order.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Order> saveAll(List<Order> orderList) {
        return super.saveAll(orderList);
    }

}