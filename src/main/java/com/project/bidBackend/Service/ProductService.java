package com.project.bidBackend.Service;

import com.project.bidBackend.Model.Product;
import com.project.bidBackend.Repo.ProductRepo;
import com.project.bidBackend.dto.CreateProductRequest;
import com.project.bidBackend.dto.ProductResponse;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public ProductResponse createProduct(CreateProductRequest request) {

        if(request.getBasePrice() <= 0)
        {
            throw new RuntimeException("BasePrice must be greater than 0");
        }

        Product product = new Product();
        product.setProductName(request.getName());
        product.setProductDescription(request.getDescription());
        product.setBasePrice(request.getBasePrice());
        product.setImageUrl(request.getImageUrl());

        Product savedProduct = productRepo.save(product);

        ProductResponse response = new ProductResponse();
        response.setId(savedProduct.getId());
        response.setName(savedProduct.getProductName());
        response.setDescription(savedProduct.getProductDescription());
        response.setBasePrice(savedProduct.getBasePrice());
        response.setImageUrl(savedProduct.getImageUrl());

        return response;
    }
}
