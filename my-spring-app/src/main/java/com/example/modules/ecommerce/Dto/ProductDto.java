package com.example.modules.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;



    
        import com.example.modules.ecommerce.dto.CategorySimpleDto;
    


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

private Long id;


    private String name;

    private Integer stock;

    private Double price;

    private String description;



    
            private CategorySimpleDto category;
    

    }