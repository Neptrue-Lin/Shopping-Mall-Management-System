package org.neptrueworks.ordermanagement.data.mapping;

import org.junit.jupiter.api.Test;
import org.neptrueworks.ordermanagement.data.entitizing.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = "classpath:spring-database-config.xml")
class ProductEntityMappableTest {

    @Autowired
    private ProductEntityMappable mapper;

    @Test
    void testAdd() {
        ProductEntity entity = new ProductEntity();
        entity.setName("Test");
        entity.setPrice(100.0);
        entity.setStock(0);
        entity.setCreatedBy("Test");

        int id = this.mapper.add(entity);
//        assert this.mapper.fetchScalar(id).isIsotropic(entity);
    }

    @Test
    void testDeleteScalar() {
        ProductEntity entity = new ProductEntity();
        entity.setName("Test");
        entity.setPrice(100.0);
        entity.setStock(0);
        entity.setCreatedBy("Test");

        int id = this.mapper.add(entity);
        this.mapper.deleteScalar(entity.getId());
    }

    @Test
    void testRemoveScalar() {
    }

    @Test
    void testResumeScalar() {
    }

    @Test
    void testUpdateStock() {
    }

    @Test
    void testCountScalar() {
    }

    @Test
    void testCountAll() {
    }

    @Test
    void testLimitOffset() {
    }

    @Test
    void testLimitOutset() {
    }

    @Test
    void testLimitKeyset() {
    }

    @Test
    void testFetchScalar() {
    }

    @Test
    void testFetchAll() {
    }
}