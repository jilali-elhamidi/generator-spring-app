package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.User;
import com.example.modules.ecommerce.repository.UserRepository;

    
    
        import com.example.modules.ecommerce.model.Address;
    

    
    
        import com.example.modules.ecommerce.model.Order;
    


import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService extends BaseService<User> {

protected final UserRepository userRepository;


    

    


public UserService(
UserRepository repository

    

    

) {
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
}
