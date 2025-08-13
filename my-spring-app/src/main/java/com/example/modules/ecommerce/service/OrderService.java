package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.Order;
import com.example.modules.ecommerce.repository.OrderRepository;
import com.example.modules.ecommerce.model.User;
import com.example.modules.ecommerce.repository.UserRepository;
import com.example.modules.ecommerce.model.OrderItem;
import com.example.modules.ecommerce.model.Payment;
import com.example.modules.ecommerce.repository.PaymentRepository;
import com.example.modules.ecommerce.model.Shipment;
import com.example.modules.ecommerce.repository.ShipmentRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class OrderService extends BaseService<Order> {

    protected final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final ShipmentRepository shipmentRepository;

    public OrderService(OrderRepository repository,UserRepository userRepository,PaymentRepository paymentRepository,ShipmentRepository shipmentRepository)
    {
        super(repository);
        this.orderRepository = repository;
        this.userRepository = userRepository;
            this.paymentRepository = paymentRepository;
            this.shipmentRepository = shipmentRepository;
    }

    @Override
    public Order save(Order order) {

        if (order.getUser() != null && order.getUser().getId() != null) {
        User user = userRepository.findById(order.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(user);
        }

        if (order.getOrderItems() != null) {
            for (OrderItem item : order.getOrderItems()) {
            item.setOrder(order);
            }
        }
        if (order.getPayment() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            order.setPayment(
            paymentRepository.findById(order.getPayment().getId())
            .orElseThrow(() -> new RuntimeException("payment not found"))
            );
        
        order.getPayment().setOrder(order);
        }
        if (order.getShipment() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
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

// Relations ManyToOne : mise à jour conditionnelle

        if (orderRequest.getUser() != null && orderRequest.getUser().getId() != null) {
        User user = userRepository.findById(orderRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        existing.setUser(user);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getOrderItems().clear();
        if (orderRequest.getOrderItems() != null) {
            for (var item : orderRequest.getOrderItems()) {
            item.setOrder(existing);
            existing.getOrderItems().add(item);
            }
        }


        if (orderRequest.getPayment() != null
        && orderRequest.getPayment().getId() != null) {

        Payment payment = paymentRepository.findById(
        orderRequest.getPayment().getId()
        ).orElseThrow(() -> new RuntimeException("Payment not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setPayment(payment);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            payment.setOrder(existing);
        
        }

    

    

        if (orderRequest.getShipment() != null
        && orderRequest.getShipment().getId() != null) {

        Shipment shipment = shipmentRepository.findById(
        orderRequest.getShipment().getId()
        ).orElseThrow(() -> new RuntimeException("Shipment not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setShipment(shipment);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            shipment.setOrder(existing);
        
        }

    


        return orderRepository.save(existing);
    }
}