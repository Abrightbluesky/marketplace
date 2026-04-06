package com.ruth.shop.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.ruth.shop.dto.OrderItemResponse;
import com.ruth.shop.dto.OrderResponse;
import com.ruth.shop.entity.*;
import com.ruth.shop.repository.*;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository; // 🔥 WAJIB

    // checkout
    public String checkout(String email){

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Cart> carts = cartRepository.findByUser(user);

        if(carts.isEmpty()){
            throw new RuntimeException("Cart kosong");
        }

        // 🔥 hitung total
        double total = carts.stream()
                .mapToDouble(c -> c.getProduct().getPrice() * c.getQuantity())
                .sum();

        // 🔥 save order dulu
        Order order = Order.builder()
                .user(user)
                .totalPrice(total)
                .status("PENDING")
                .build();

        orderRepository.save(order);

        // 🔥 convert Cart → OrderItem (INI YANG KAMU SALAH TADI)
        List<OrderItem> items = carts.stream()
                .map(c -> OrderItem.builder()
                        .productName(c.getProduct().getName())
                        .price(c.getProduct().getPrice())
                        .quantity(c.getQuantity())
                        .order(order)
                        .build()
                ).toList();

        orderItemRepository.saveAll(items);

        // 🔥 set ke order
        order.setItems(items);

        // 🔥 hapus cart
        cartRepository.deleteAll(carts);

        return "Checkout success";


    }

    // METHOD PAYMENT

    public String pay(Long orderId, String email){  

    Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));

  
    System.out.println("ORDER USER: " + order.getUser().getEmail());
    System.out.println("LOGIN USER: " + email);
    System.out.println("STATUS: " +     order.getStatus());        


     if(order.getUser() == null){
        throw new RuntimeException("Order tidak punya user");

    } 


    // 🔥 pastikan milik user
    if(!order.getUser().getEmail().equals(email)){
        throw new RuntimeException("Bukan order kamu");
    }

    // 🔥 cek status
    if("PAID".equals(order.getStatus())){
        throw new RuntimeException("Order sudah dibayar");
    }

     // fix null status 
    if(order.getStatus() == null){
        order.setStatus("PENDING");
    }

    // 🔥 update status
    order.setStatus("PAID");

    orderRepository.save(order);

    return "Payment success";
}

    // user dto(dto)
    public List<OrderResponse> getOrders(String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream()
                    .map(this::mapToResponse)
                    .toList();
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    // mapping dto 

    /* private OrderResponse mapToResponse(Order order){

        return OrderResponse.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .items(
                    order.getItems().stream()
                            .map(item -> OrderItemResponse.builder()
                                        .productName(item.getProductName()))
                                        .price(item.getPrice())
                                        .quantity(item.getQuantity())
                                        .build()
                ).toList()
            .build();
               
    } */

     // versi final nya
     
     private OrderResponse mapToResponse(Order order){

        return OrderResponse.builder()
            .id(order.getId())
            .totalPrice(order.getTotalPrice())
            .status(order.getStatus())
            .items(
                order.getItems().stream()
                        .map(item -> OrderItemResponse.builder()
                                .productName(item.getProductName())
                                .price(item.getPrice())
                                .quantity(item.getQuantity())
                                .build()
                        )
                        .toList()
            )
            .build();
        }


    // get orders
     /* ini gak usah dihapus
     public List<Order> getOrders(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUser(user);
    } */
}