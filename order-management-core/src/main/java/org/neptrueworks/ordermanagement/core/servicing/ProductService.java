package org.neptrueworks.ordermanagement.core.servicing;

import org.neptrueworks.ordermanagement.data.entitizing.ProductEntity;
import org.neptrueworks.ordermanagement.data.mapping.ProductEntityMappable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ProductService implements ProductServiceable {
    private final ProductEntityMappable productMapper;

    public ProductService(ProductEntityMappable productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public void addProduct(ProductEntity product) {
        this.productMapper.add(product);
    }

    @Override
    public void removeProduct(ProductEntity entity) {
        if(entity.isDeleted()) {
            return;
        }
        this.productMapper.removeScalar(entity.getId());
    }

    @Override
    public void resumeProduct(ProductEntity entity) {
        if(!entity.isDeleted()) {
            return;
        }
        this.productMapper.resumeScalar(entity.getId());
    }

    @Override
    public List<ProductEntity> getAllProduct() {
        return this.productMapper.fetchAll();
    }

    @Override
    public List<ProductEntity> getLimitedProducts(int pageIndex, int pageSize) {
        int offset = pageSize * (pageIndex - 1);
        return this.productMapper.limitOffset(offset, pageSize);
    }

    @Override
    public int countAllProducts() {
        return this.productMapper.countAll();
    }

    @Override
    public ProductEntity identifyProduct(int id) {
        return this.productMapper.fetchScalar(id);
    }

    @Override
    public void restock(ProductEntity entity, int increment) {
        entity.setStock(this.productMapper.fetchScalar(entity.getId()).getStock());
        if (increment < 0 || entity.getStock() < 0) {
            throw new IllegalArgumentException();
        }
        entity.setStock(entity.getStock() + increment);
        this.productMapper.updateStock(entity);
    }

    @Override
    public void destock(ProductEntity entity, int decrement) {
        entity.setStock(this.productMapper.fetchScalar(entity.getId()).getStock());
        if (decrement < 0 || entity.getStock() - decrement < 0) {
            throw new IllegalArgumentException();
        }
        entity.setStock(entity.getStock() - decrement);
        this.productMapper.updateStock(entity);
    }
}
