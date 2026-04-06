package com.ruth.shop.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

@Data
@Getter
@Setter
public class ProductRequest {

    @NotBlank(message = "Nama tidak boleh kosong")
    
    
    private String name;
    private String description;

    @NotNull(message = "Harga wajib diisi")
    @Positive(message = "Harga harus lebih dari 0")
    private Double price;
    private Integer stock;
}
