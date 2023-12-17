package org.neptrueworks.ordermanagement.core.servicing;


import org.neptrueworks.ordermanagement.data.entitizing.ProductEntity;

import java.util.List;

public interface ProductServiceable extends IServiceable{
    void addProduct(ProductEntity product);
    void removeProduct(ProductEntity entity);
    void resumeProduct(ProductEntity entity);

    List<ProductEntity> getAllProduct();
    List<ProductEntity> getLimitedProducts(int pageIndex, int pageSize);

    int countAllProducts();

    ProductEntity identifyProduct(int id);

    void restock(ProductEntity entity, int increment);
    void destock(ProductEntity entity, int decrement);
}
