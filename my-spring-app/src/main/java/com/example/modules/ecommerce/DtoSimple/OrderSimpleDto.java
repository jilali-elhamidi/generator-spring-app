package com.example.modules.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

    import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSimpleDto {
private Long id;

    private Date orderDate;

    private String status;

}