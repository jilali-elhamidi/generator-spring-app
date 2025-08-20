package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends BaseRepository<Transaction, Long> {
}
