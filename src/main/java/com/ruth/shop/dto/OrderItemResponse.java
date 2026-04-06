package com.ruth.shop.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class OrderItemResponse {

    private String productName;
    private Double price;
    private Integer quantity;
    
}
