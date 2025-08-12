package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.User;
import com.example.modules.ecommerce.repository.UserRepository;
import com.example.modules.ecommerce.model.Address;
import com.example.modules.ecommerce.model.Order;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class UserService extends BaseService<User> {

    protected final UserRepository userRepository;

    public UserService(UserRepository repository)
    {
        super(repository);
        this.userRepository = repository;
    }

    @Override
    public User save(User user) {

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