package org.neptrueworks.ordermanagement.web.controlling;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.neptrueworks.ordermanagement.core.servicing.ProductServiceable;
import org.neptrueworks.ordermanagement.data.entitizing.ProductEntity;
import org.neptrueworks.ordermanagement.web.modelling.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ProductController {
    private final ProductServiceable service;

    private final ObjectMapper serializer;

    public ProductController(@Qualifier("productService") ProductServiceable service, @Autowired ObjectMapper serializer) {
        this.service = service;
        this.serializer = serializer;
    }

    @GetMapping("/production/products/{pageIndex}/{pageSize}")
    public void retrieveProducts(@PathVariable("pageIndex") Integer pageIndex,
                                 @PathVariable("pageSize") Integer pageSize,
                                 HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter().print(this.serializer.writeValueAsString(this.service.getPagedProducts(pageIndex, pageSize)));
    }

    @GetMapping("/production/products/{pageSize}")
    public void paginateProducts(@PathVariable("pageSize") Integer pageSize,
                                 HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println((int) Math.ceil(this.service.countAllProducts() * 1.0 / pageSize));
    }

    @PostMapping("/production/product/add")
    public void addProduct(@RequestBody ProductAdditionViewModel model,
                           HttpServletRequest request, HttpServletResponse response) {
        ProductEntity entity = new ProductEntity();
        entity.setPrice(model.getPrice());
        entity.setStock(model.getStock());
        entity.setCreatedBy(model.getAddedBy());
        this.service.addProduct(entity);
    }

    @PutMapping("/production/product/remove")
    public void removeProduct(@RequestBody ProductRemovalViewModel model,
                              HttpServletRequest request, HttpServletResponse response) {
        ProductEntity entity = new ProductEntity();
        entity.setId(model.getId());
        entity.setCreatedBy(model.getRemovedBy());
        this.service.removeProduct(entity.getId());
    }

    @PutMapping("/production/product/renew")
    public void renewProduct(@RequestBody ProductRenewalViewModel model,
                             HttpServletRequest request, HttpServletResponse response) {
        ProductEntity entity = new ProductEntity();
        entity.setId(model.getId());
        entity.setCreatedBy(model.getRenewedBy());
        this.service.resumeProduct(entity.getId());
    }

    @PutMapping("/production/product/restock")
    public void restockProduct(@RequestBody ProductRestockingViewModel model,
                               HttpServletRequest request, HttpServletResponse response) {
        this.service.restock(model.getId(), model.getIncrement());
    }

    @PutMapping("/production/product/destock")
    public void destockProduct(@RequestBody ProductDestockingViewModel model,
                               HttpServletRequest request, HttpServletResponse response) {
        this.service.destock(model.getId(), model.getDecrement());
    }
}
