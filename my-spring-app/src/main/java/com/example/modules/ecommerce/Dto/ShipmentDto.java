package com.example.modules.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

    import java.util.Date;



    
        import com.example.modules.ecommerce.dto.OrderSimpleDto;
    


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDto {

private Long id;


    private Date shipmentDate;

    private String carrier;

    private String trackingNumber;



    
            private OrderSimpleDto order;
    

    }