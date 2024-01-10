package org.neptrueworks.ordermanagement.core.servicing;

import lombok.SneakyThrows;
import org.neptrueworks.ordermanagement.common.exceptions.ExceptionCashier;
import org.neptrueworks.ordermanagement.core.exceptions.ServiceExceptionIssue;
import org.neptrueworks.ordermanagement.data.entitizing.ProductEntity;
import org.neptrueworks.ordermanagement.data.mapping.ProductEntityMappable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class ProductService implements ProductServiceable {
    private final ProductEntityMappable productMapper;

    public ProductService(ProductEntityMappable productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public Iterable<ProductEntity> getAllProduct() {
        return this.productMapper.fetchAll();
    }

    @Override
    public Iterable<ProductEntity> getPagedProducts(int pageIndex, int pageSize) {
        int offset = pageSize * (pageIndex - 1);
        return this.productMapper.take(offset, pageSize);
    }

    @Override
    public long countAllProducts() {
        return this.productMapper.countAll();
    }

    @Override
    @SneakyThrows
    public ProductEntity identifyProduct(int id) throws IllegalStateException {
        Optional<ProductEntity> product = this.productMapper.fetchScalar(id);
        if (product.isEmpty())
            throw ExceptionCashier.DEFAULT.checkout(ServiceExceptionIssue.PRODUCT_NONEXISTENT);
        return product.get();
    }

    @Override
    @SneakyThrows
    public void addProduct(ProductEntity product) throws IllegalStateException {
        if (product.getStock() < 0)
            throw ExceptionCashier.DEFAULT.checkout(ServiceExceptionIssue.OUT_OF_STOCK);
        if (product.getPrice() < 0.0)
            throw ExceptionCashier.DEFAULT.checkout(ServiceExceptionIssue.PRICE_LOWER_THAN_ZERO);
        this.productMapper.add(product);
    }

    @Override
    public void removeProduct(int productId) {
        ProductEntity product = this.identifyProduct(productId);
        if (product.isDeleted())
            return;

        this.productMapper.removeScalar(product.getId());
    }

    @Override
    public void resumeProduct(int productId) {
        ProductEntity product = this.identifyProduct(productId);
        if (!product.isDeleted())
            return;
        this.productMapper.resumeScalar(product.getId());
    }

    @Override
    @SneakyThrows
    public void restock(int id, int increment) throws IllegalStateException, IllegalArgumentException {
        ProductEntity entity = this.identifyProduct(id);
        if (increment == 0)
            return;
        else if (increment < 0)
            throw ExceptionCashier.DEFAULT.checkout(ServiceExceptionIssue.VARIATION_LESS_THAN_ZERO);

        entity.setStock(entity.getStock() + increment);
        this.productMapper.updateStock(entity);
    }

    @Override
    @SneakyThrows
    public void destock(int id, int decrement) throws IllegalStateException, IllegalArgumentException {
        ProductEntity entity = this.identifyProduct(id);
        if (decrement == 0)
            return;
        else if (decrement < 0)
            throw ExceptionCashier.DEFAULT.checkout(ServiceExceptionIssue.VARIATION_LESS_THAN_ZERO);
        else if (entity.getStock() - decrement < 0)
            throw ExceptionCashier.DEFAULT.checkout(ServiceExceptionIssue.OUT_OF_STOCK);

        entity.setStock(entity.getStock() - decrement);
        this.productMapper.updateStock(entity);
    }
}
