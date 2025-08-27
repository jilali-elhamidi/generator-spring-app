package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.User;
import com.example.modules.ecommerce.repository.UserRepository;

import com.example.modules.ecommerce.model.Address;
import com.example.modules.ecommerce.repository.AddressRepository;

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
public class UserService extends BaseService<User> {

    protected final UserRepository userRepository;
    
    protected final AddressRepository addressesRepository;
    
    protected final OrderRepository ordersRepository;
    

    public UserService(UserRepository repository, AddressRepository addressesRepository, OrderRepository ordersRepository)
    {
        super(repository);
        this.userRepository = repository;
        
        this.addressesRepository = addressesRepository;
        
        this.ordersRepository = ordersRepository;
        
    }

    @Transactional
    @Override
    public User save(User user) {
    // ---------- OneToMany ----------
        if (user.getAddresses() != null) {
            List<Address> managedAddresses = new ArrayList<>();
            for (Address item : user.getAddresses()) {
                if (item.getId() != null) {
                    Address existingItem = addressesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Address not found"));

                     existingItem.setUser(user);
                     managedAddresses.add(existingItem);
                } else {
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

                     existingItem.setUser(user);
                     managedOrders.add(existingItem);
                } else {
                    item.setUser(user);
                    managedOrders.add(item);
                }
            }
            user.setOrders(managedOrders);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return userRepository.save(user);
}

    @Transactional
    @Override
    public User update(Long id, User userRequest) {
        User existing = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

    // Copier les champs simples
        existing.setUsername(userRequest.getUsername());
        existing.setEmail(userRequest.getEmail());
        existing.setPhone(userRequest.getPhone());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getAddresses().clear();

        if (userRequest.getAddresses() != null) {
            for (var item : userRequest.getAddresses()) {
                Address existingItem;
                if (item.getId() != null) {
                    existingItem = addressesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Address not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getAddresses().add(existingItem);
            }
        }
        
        existing.getOrders().clear();

        if (userRequest.getOrders() != null) {
            for (var item : userRequest.getOrders()) {
                Order existingItem;
                if (item.getId() != null) {
                    existingItem = ordersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Order not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getOrders().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return userRepository.save(existing);
}

    // Pagination simple
    public Page<User> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<User> search(Map<String, String> filters, Pageable pageable) {
        return super.search(User.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<User> saveAll(List<User> userList) {
        return super.saveAll(userList);
    }

}