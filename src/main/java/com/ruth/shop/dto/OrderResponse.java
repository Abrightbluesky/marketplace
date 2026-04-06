package com.ruth.shop.dto;

import lombok.*;
import java.util.List;




@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class OrderResponse {


    private Long id;
    private Double totalPrice;
    private String status;
    private List<OrderItemResponse> items;
}
