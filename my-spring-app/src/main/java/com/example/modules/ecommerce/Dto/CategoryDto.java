package com.example.modules.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;



    
        import java.util.List;
        import com.example.modules.ecommerce.dto.ProductSimpleDto;
    


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

private Long id;


    private String name;

    private String description;



    
        private List<ProductSimpleDto> products;
    

    }