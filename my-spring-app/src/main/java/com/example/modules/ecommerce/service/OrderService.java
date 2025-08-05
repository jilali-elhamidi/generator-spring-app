package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.Order;
import com.example.modules.ecommerce.repository.OrderRepository;

    
        import com.example.modules.ecommerce.model.User;
        import com.example.modules.ecommerce.repository.UserRepository;
    
    

    
    
        import com.example.modules.ecommerce.model.OrderItem;
    

    
    

    
    


import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class OrderService extends BaseService<Order> {

protected final OrderRepository orderRepository;


    
        private final UserRepository userRepository;
    

    

    

    


public OrderService(
OrderRepository repository

    , UserRepository userRepository

    

    

    

) {
super(repository);
this.orderRepository = repository;

    this.userRepository = userRepository;

    

    

    

}

@Override
public Order save(Order order) {



    
        if (order.getUser() != null &&
        order.getUser().getId() != null) {
        User user = userRepository.findById(order.getUser().getId())
        .orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(user);
        }
    

    

    

    




    

    
        if (order.getOrderItems() != null) {
        for (OrderItem item : order.getOrderItems()) {
        item.setOrder(order);
        }
        }
    

    

    


return orderRepository.save(order);
}
}
