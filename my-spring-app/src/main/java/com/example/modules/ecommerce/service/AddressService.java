package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.Address;
import com.example.modules.ecommerce.repository.AddressRepository;

import com.example.modules.ecommerce.model.User;
import com.example.modules.ecommerce.repository.UserRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class AddressService extends BaseService<Address> {

    protected final AddressRepository addressRepository;
    
    protected final UserRepository userRepository;
    

    public AddressService(AddressRepository repository, UserRepository userRepository)
    {
        super(repository);
        this.addressRepository = repository;
        
        this.userRepository = userRepository;
        
    }

    @Transactional
    @Override
    public Address save(Address address) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (address.getUser() != null) {
            if (address.getUser().getId() != null) {
                User existingUser = userRepository.findById(
                    address.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("User not found with id "
                    + address.getUser().getId()));
                address.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                User newUser = userRepository.save(address.getUser());
                address.setUser(newUser);
            }
        }
        
    // ---------- OneToOne ----------
    return addressRepository.save(address);
}

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return addressRepository.save(existing);
}

    // Pagination simple
    public Page<Address> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Address> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Address.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Address> saveAll(List<Address> addressList) {
        return super.saveAll(addressList);
    }

}