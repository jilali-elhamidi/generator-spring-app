package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.Category;
import com.example.modules.ecommerce.repository.CategoryRepository;

    
    
        import com.example.modules.ecommerce.model.Product;
    


import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CategoryService extends BaseService<Category> {

protected final CategoryRepository categoryRepository;


    


public CategoryService(
CategoryRepository repository

    

) {
super(repository);
this.categoryRepository = repository;

    

}

@Override
public Category save(Category category) {



    




    
        if (category.getProducts() != null) {
        for (Product item : category.getProducts()) {
        item.setCategory(category);
        }
        }
    


return categoryRepository.save(category);
}
}
