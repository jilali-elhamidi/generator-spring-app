package com.example.modules.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

    import java.util.Date;



    
        import com.example.modules.ecommerce.dto.UserSimpleDto;
    

    
        import java.util.List;
        import com.example.modules.ecommerce.dto.OrderItemSimpleDto;
    

    
        import com.example.modules.ecommerce.dto.PaymentSimpleDto;
    

    
        import com.example.modules.ecommerce.dto.ShipmentSimpleDto;
    


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

private Long id;


    private Date orderDate;

    private String status;



    
            private UserSimpleDto user;
    

    
        private List<OrderItemSimpleDto> orderItems;
    

    
            private PaymentSimpleDto payment;
    

    
            private ShipmentSimpleDto shipment;
    

    }