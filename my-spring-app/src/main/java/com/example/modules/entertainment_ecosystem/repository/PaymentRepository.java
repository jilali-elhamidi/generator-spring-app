package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.Payment;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends BaseRepository<Payment, Long> {
}
