/*package com.ruth.shop.service.Impl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import com.ruth.shop.entity.Product;
import com.ruth.shop.repository.ProductRepository;
import com.ruth.shop.dto.ProductRequest;
import com.ruth.shop.dto.ProductResponse;
import com.ruth.shop.service.ProductService;

@Service
@RequiredArgsConstructor

public class ProductServiceImpl implements ProductService{
    
    private final ProductRepository productRepository;

    @Override
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

    @Override
    public List<ProductResponse> getAll(){
        
    }
    
}
*/