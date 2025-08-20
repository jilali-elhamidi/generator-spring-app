package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.PaymentMethod;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends BaseRepository<PaymentMethod, Long> {
}
