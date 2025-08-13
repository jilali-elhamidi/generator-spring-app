package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.Contract;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends BaseRepository<Contract, Long> {
}
