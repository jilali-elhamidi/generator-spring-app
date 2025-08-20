package com.example.modules.healthcare_management.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.healthcare_management.model.Reimbursement;
import org.springframework.stereotype.Repository;

@Repository
public interface ReimbursementRepository extends BaseRepository<Reimbursement, Long> {

}
