package com.example.modules.ecommerce.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.ecommerce.model.Payment;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends BaseRepository<Payment, Long> {
}
