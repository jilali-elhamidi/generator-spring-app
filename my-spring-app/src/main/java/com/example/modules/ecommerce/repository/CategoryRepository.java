package com.example.modules.ecommerce.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.ecommerce.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long> {
}
