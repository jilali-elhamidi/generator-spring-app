package com.example.modules.project_management.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.project_management.model.Client;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends BaseRepository<Client, Long> {
}
