package com.example.modules.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;



    
        import com.example.modules.ecommerce.dto.ProductSimpleDto;
    

    
        import com.example.modules.ecommerce.dto.OrderSimpleDto;
    


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

private Long id;


    private Integer quantity;

    private Double price;



    
            private ProductSimpleDto product;
    

    
            private OrderSimpleDto order;
    

    }