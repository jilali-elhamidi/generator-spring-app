package com.example.modules.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

    import java.util.Date;



    
        import com.example.modules.ecommerce.dto.OrderSimpleDto;
    


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

private Long id;


    private String method;

    private Date paymentDate;

    private Double amount;



    
            private OrderSimpleDto order;
    

    }