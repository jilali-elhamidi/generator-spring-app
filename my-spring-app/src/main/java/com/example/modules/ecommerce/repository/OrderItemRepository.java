package com.example.modules.ecommerce.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.ecommerce.model.OrderItem;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends BaseRepository<OrderItem, Long> {
}
