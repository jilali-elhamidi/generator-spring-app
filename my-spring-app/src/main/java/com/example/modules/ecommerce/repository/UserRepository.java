package com.example.modules.ecommerce.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.ecommerce.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
}
