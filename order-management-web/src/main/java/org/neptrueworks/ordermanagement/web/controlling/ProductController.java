package org.neptrueworks.ordermanagement.web.controlling;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.neptrueworks.ordermanagement.core.servicing.ProductServiceable;
import org.neptrueworks.ordermanagement.data.entitizing.OrderManifestEntity;
import org.neptrueworks.ordermanagement.data.entitizing.ProductEntity;
import org.neptrueworks.ordermanagement.web.modelling.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/production")
public class ProductController {
    private final ProductServiceable service;

    @GetMapping("/products/{productId}")
    public ProductEntity identifyOrderManifest(@PathVariable Integer productId) {
        return this.service.identifyProduct(productId);
    }

    @GetMapping("/products/retrieve")
    public Iterable<ProductEntity> retrieveProducts(Integer pageIndex, Integer pageSize) {
        return this.service.getPagedProducts(pageIndex, pageSize);
    }

    @GetMapping("/products/paginate")
    public Integer paginateProducts(Integer pageSize) {
        long totalCount = this.service.countAllProducts();
        return (int) Math.ceil(totalCount * 1.0 / pageSize);
    }

    @PostMapping("/product/add")
    public void addProduct(@RequestBody ProductAdditionViewModel model) {
        ProductEntity entity = new ProductEntity();
        entity.setPrice(model.getPrice());
        entity.setStock(model.getStock());
        entity.setCreatedBy(model.getAddedBy());
        this.service.addProduct(entity);
    }

    @PostMapping("/product/remove")
    public void removeProduct(@RequestBody ProductRemovalViewModel model) {
        this.service.removeProduct(model.getId());
    }

    @PostMapping("/product/renew")
    public void renewProduct(@RequestBody ProductRenewalViewModel model) {
        this.service.resumeProduct(model.getId());
    }

    @PutMapping("/product/restock")
    public void restockProduct(@RequestBody ProductRestockingViewModel model) {
        this.service.restock(model.getId(), model.getIncrement());
    }

    @PutMapping("/product/destock")
    public void destockProduct(@RequestBody ProductDestockingViewModel model) {
        this.service.destock(model.getId(), model.getDecrement());
    }
}
