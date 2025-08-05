package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.Address;
import com.example.modules.ecommerce.repository.AddressRepository;

    
        import com.example.modules.ecommerce.model.User;
        import com.example.modules.ecommerce.repository.UserRepository;
    
    


import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AddressService extends BaseService<Address> {

protected final AddressRepository addressRepository;


    
        private final UserRepository userRepository;
    


public AddressService(
AddressRepository repository

    , UserRepository userRepository

) {
super(repository);
this.addressRepository = repository;

    this.userRepository = userRepository;

}

@Override
public Address save(Address address) {



    
        if (address.getUser() != null &&
        address.getUser().getId() != null) {
        User user = userRepository.findById(address.getUser().getId())
        .orElseThrow(() -> new RuntimeException("User not found"));
        address.setUser(user);
        }
    




    


return addressRepository.save(address);
}
}
