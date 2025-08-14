package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.User;
import com.example.modules.ecommerce.repository.UserRepository;
import com.example.modules.ecommerce.model.Address;
import com.example.modules.ecommerce.repository.AddressRepository;
import com.example.modules.ecommerce.model.Order;
import com.example.modules.ecommerce.repository.OrderRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class UserService extends BaseService<User> {

    protected final UserRepository userRepository;
    private final AddressRepository addressesRepository;
    private final OrderRepository ordersRepository;

    public UserService(UserRepository repository,AddressRepository addressesRepository,OrderRepository ordersRepository)
    {
        super(repository);
        this.userRepository = repository;
        this.addressesRepository = addressesRepository;
        this.ordersRepository = ordersRepository;
    }

    @Override
    public User save(User user) {


    
        if (user.getAddresses() != null) {
        List<Address> managedAddresses = new ArrayList<>();
        for (Address item : user.getAddresses()) {
        if (item.getId() != null) {
        Address existingItem = addressesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Address not found"));
        // Set the parent reference on the existing item
        existingItem.setUser(user);
        managedAddresses.add(existingItem);
        } else {
        // Set the parent reference on the new item
        item.setUser(user);
        managedAddresses.add(item);
        }
        }
        user.setAddresses(managedAddresses);
        }
    

    
        if (user.getOrders() != null) {
        List<Order> managedOrders = new ArrayList<>();
        for (Order item : user.getOrders()) {
        if (item.getId() != null) {
        Order existingItem = ordersRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Order not found"));
        // Set the parent reference on the existing item
        existingItem.setUser(user);
        managedOrders.add(existingItem);
        } else {
        // Set the parent reference on the new item
        item.setUser(user);
        managedOrders.add(item);
        }
        }
        user.setOrders(managedOrders);
        }
    


        if (user.getAddresses() != null) {
            for (Address item : user.getAddresses()) {
            item.setUser(user);
            }
        }

        if (user.getOrders() != null) {
            for (Order item : user.getOrders()) {
            item.setUser(user);
            }
        }

        return userRepository.save(user);
    }


    public User update(Long id, User userRequest) {
        User existing = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

    // Copier les champs simples
        existing.setUsername(userRequest.getUsername());
        existing.setEmail(userRequest.getEmail());
        existing.setPhone(userRequest.getPhone());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getAddresses().clear();
        if (userRequest.getAddresses() != null) {
            for (var item : userRequest.getAddresses()) {
            item.setUser(existing);
            existing.getAddresses().add(item);
            }
        }

        existing.getOrders().clear();
        if (userRequest.getOrders() != null) {
            for (var item : userRequest.getOrders()) {
            item.setUser(existing);
            existing.getOrders().add(item);
            }
        }

    

    


        return userRepository.save(existing);
    }
}