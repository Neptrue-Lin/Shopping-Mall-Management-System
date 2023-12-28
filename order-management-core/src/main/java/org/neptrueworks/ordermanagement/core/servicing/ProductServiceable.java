package org.neptrueworks.ordermanagement.core.servicing;


import org.neptrueworks.ordermanagement.data.entitizing.ProductEntity;

public interface ProductServiceable extends IServiceable{
    void addProduct(ProductEntity product);
    void removeProduct(int productId);
    void resumeProduct(int productId);

    Iterable<ProductEntity> getAllProduct();
    Iterable<ProductEntity> getPagedProducts(int pageIndex, int pageSize);

    long countAllProducts();

    ProductEntity identifyProduct(int id);

    void restock(int id, int increment);
    void destock(int id, int decrement);
}
