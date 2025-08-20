package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.Address;
import com.example.modules.ecommerce.repository.AddressRepository;
import com.example.modules.ecommerce.model.User;
import com.example.modules.ecommerce.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class AddressService extends BaseService<Address> {

    protected final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository repository, UserRepository userRepository)
    {
        super(repository);
        this.addressRepository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public Address save(Address address) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (address.getUser() != null &&
            address.getUser().getId() != null) {

            User existingUser = userRepository.findById(
                address.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("User not found"));

            address.setUser(existingUser);
        }
        
    // ---------- OneToOne ----------
    return addressRepository.save(address);
}


    public Address update(Long id, Address addressRequest) {
        Address existing = addressRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Address not found"));

    // Copier les champs simples
        existing.setStreet(addressRequest.getStreet());
        existing.setCity(addressRequest.getCity());
        existing.setPostalCode(addressRequest.getPostalCode());
        existing.setCountry(addressRequest.getCountry());

    // ---------- Relations ManyToOne ----------
        if (addressRequest.getUser() != null &&
            addressRequest.getUser().getId() != null) {

            User existingUser = userRepository.findById(
                addressRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("User not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return addressRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Address> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Address entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getUser() != null) {
            entity.setUser(null);
        }
        
        repository.delete(entity);
        return true;
    }
}