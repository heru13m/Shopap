/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shopap.product;

/**
 *
 * @author Mateusz
 */
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductResponse create(ProductCreateRequest request) {
        Product p = new Product();
        p.setName(request.getName());
        p.setNetPrice(request.getNetPrice());
        p.setVatRate(request.getVatRate());

        Product saved = repository.save(p);
        return toResponse(saved);
    }

    public Product getByIdOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + id));
    }

    private ProductResponse toResponse(Product p) {
        ProductResponse r = new ProductResponse();
        r.setId(p.getId());
        r.setName(p.getName());
        r.setNetPrice(p.getNetPrice());
        r.setVatRate(p.getVatRate());
        return r;
    }
}
