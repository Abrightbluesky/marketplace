package com.ruth.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.ruth.shop.entity.Product;
import com.ruth.shop.repository.ProductRepository;


import com.ruth.shop.dto.ProductRequest;
import com.ruth.shop.dto.ProductResponse;


@Service
@RequiredArgsConstructor


public class ProductService {

    private final ProductRepository productRepository;
    
    public ProductResponse create(ProductRequest request){
        Product product = Product.builder()
        .name(request.getName())
        .description(request.getDescription())
        .price(request.getPrice())
        .stock(request.getStock())
        .build();

        productRepository.save(product);

        return mapToResponse(product);
    }

    public List<ProductResponse> getAll(){
        return productRepository.findAll()
        .stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
    }

    private ProductResponse mapToResponse(Product product){
        return ProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .price((product.getPrice()))
        .stock(product.getStock())
        .build();
    }

    
}
