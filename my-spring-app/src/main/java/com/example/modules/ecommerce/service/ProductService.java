package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.Product;
import com.example.modules.ecommerce.repository.ProductRepository;

    
        import com.example.modules.ecommerce.model.Category;
        import com.example.modules.ecommerce.repository.CategoryRepository;
    
    


import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ProductService extends BaseService<Product> {

protected final ProductRepository productRepository;


    
        private final CategoryRepository categoryRepository;
    


public ProductService(
ProductRepository repository

    , CategoryRepository categoryRepository

) {
super(repository);
this.productRepository = repository;

    this.categoryRepository = categoryRepository;

}

@Override
public Product save(Product product) {



    
        if (product.getCategory() != null &&
        product.getCategory().getId() != null) {
        Category category = categoryRepository.findById(product.getCategory().getId())
        .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);
        }
    




    


return productRepository.save(product);
}
}
