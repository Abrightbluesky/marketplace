package com.ruth.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.ruth.shop.dto.ProductRequest;
import com.ruth.shop.dto.ProductResponse;
import com.ruth.shop.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponse create(@Valid  @RequestBody ProductRequest request){
        return productService.create(request);
    }

    @GetMapping
    public List<ProductResponse> getAll() {
        return productService.getAll();
    }
}