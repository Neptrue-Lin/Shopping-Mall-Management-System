package org.neptrueworks.ordermanagement.core.servicing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.neptrueworks.ordermanagement.data.entitizing.ProductEntity;
import org.neptrueworks.ordermanagement.data.mapping.ProductEntityMappable;

import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductEntityMappable mockProductMapper;

    private ProductService productServiceUnderTest;

    @BeforeEach
    void setUp() {
        productServiceUnderTest = new ProductService(mockProductMapper);
    }

    @Test
    void testAddProduct() {
        // Setup
        final ProductEntity product = new ProductEntity();
        product.setId(1);
        product.setName("name");
        product.setPrice(0.0);
        product.setStock(1);
        product.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Run the test
        productServiceUnderTest.addProduct(product);

        // Verify the results
        // Confirm ProductEntityMappable.add(...).
        final ProductEntity entity = new ProductEntity();
        entity.setId(1);
        entity.setName("name");
        entity.setPrice(0.0);
        entity.setStock(1);
        entity.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        verify(mockProductMapper).add(entity);
    }

    @Test
    void testRemoveProduct() {
        // Setup
        final ProductEntity product = new ProductEntity();
        product.setId(1);
        product.setName("name");
        product.setPrice(0.0);
        product.setStock(1);
        product.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product.setDeleted(false);

        // Run the test
        productServiceUnderTest.removeProduct(product);

        // Verify the results
        verify(mockProductMapper).removeScalar(1);
    }

    @Test
    void testResumeProduct() {
        // Setup
        final ProductEntity product = new ProductEntity();
        product.setId(1);
        product.setName("name");
        product.setPrice(0.0);
        product.setStock(1);
        product.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product.setDeleted(true);

        // Run the test
        productServiceUnderTest.resumeProduct(product);

        // Verify the results
        verify(mockProductMapper).resumeScalar(1);
    }

    @Test
    void testGetAllProduct() {
        // Setup
        final ProductEntity entity = new ProductEntity();
        entity.setId(1);
        entity.setName("name");
        entity.setPrice(0.0);
        entity.setStock(1);
        entity.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<ProductEntity> expectedResult = List.of(entity);

        // Configure ProductEntityMappable.fetchAll(...).
        final ProductEntity entity1 = new ProductEntity();
        entity1.setId(1);
        entity1.setName("name");
        entity1.setPrice(0.0);
        entity1.setStock(1);
        entity1.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<ProductEntity> productEntities = List.of(entity1);
        when(mockProductMapper.fetchAll()).thenReturn(productEntities);

        // Run the test
        final List<ProductEntity> result = productServiceUnderTest.getAllProduct();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetAllProduct_ProductEntityMappableReturnsNoItems() {
        // Setup
        when(mockProductMapper.fetchAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<ProductEntity> result = productServiceUnderTest.getAllProduct();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void testGetLimitedProduct() {
        // Setup
        final ProductEntity entity = new ProductEntity();
        entity.setId(1);
        entity.setName("name");
        entity.setPrice(0.0);
        entity.setStock(1);
        entity.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<ProductEntity> expectedResult = List.of(entity);

        // Configure ProductEntityMappable.limitOffset(...).
        final ProductEntity entity1 = new ProductEntity();
        entity1.setId(1);
        entity1.setName("name");
        entity1.setPrice(0.0);
        entity1.setStock(1);
        entity1.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<ProductEntity> productEntities = List.of(entity1);
        when(mockProductMapper.limitOffset(10, 10)).thenReturn(productEntities);

        // Run the test
        final List<ProductEntity> result = productServiceUnderTest.getLimitedProducts(2, 10);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testIdentifyProduct() {
        // Setup
        final ProductEntity expectedResult = new ProductEntity();
        expectedResult.setId(1);
        expectedResult.setName("name");
        expectedResult.setPrice(0.0);
        expectedResult.setStock(1);
        expectedResult.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure ProductEntityMappable.fetchScalar(...).
        final ProductEntity entity = new ProductEntity();
        entity.setId(1);
        entity.setName("name");
        entity.setPrice(0.0);
        entity.setStock(1);
        entity.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockProductMapper.fetchScalar(1)).thenReturn(entity);

        // Run the test
        final ProductEntity result = productServiceUnderTest.identifyProduct(1);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testRestock() {
        // Setup
        final ProductEntity entity = new ProductEntity();
        entity.setId(1);
        entity.setName("name");
        entity.setPrice(0.0);
        entity.setStock(1);
        entity.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        //Configure ProductEntityMappable.fetchScalar(...)
        final ProductEntity entity3 = new ProductEntity();
        entity3.setId(1);
        entity3.setName("name");
        entity3.setPrice(0.0);
        entity3.setStock(1);
        entity3.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockProductMapper.fetchScalar(1)).thenReturn(entity3);

        // Run the test
        productServiceUnderTest.restock(entity, 1);

        // Verify the results
        // Confirm ProductEntityMappable.updateStock(...).
        final ProductEntity entity1 = new ProductEntity();
        entity1.setId(1);
        entity1.setName("name");
        entity1.setPrice(0.0);
        entity1.setStock(2);
        entity1.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        verify(mockProductMapper).updateStock(entity1);
    }

    @Test
    void testRestock_IncrementLessThanZero() {
        // Setup
        final ProductEntity entity = new ProductEntity();
        entity.setId(1);
        entity.setName("name");
        entity.setPrice(0.0);
        entity.setStock(1);
        entity.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        //Configure ProductEntityMappable.fetchScalar(...)
        final ProductEntity entity3 = new ProductEntity();
        entity3.setId(1);
        entity3.setName("name");
        entity3.setPrice(0.0);
        entity3.setStock(1);
        entity3.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockProductMapper.fetchScalar(1)).thenReturn(entity3);

        try {
            // Run the test
            productServiceUnderTest.restock(entity, -1);
            Assertions.fail();
        } catch (IllegalArgumentException e) {
            // Verify the results
            // Confirm ProductEntityMappable.updateStock(...).
            final ProductEntity entity1 = new ProductEntity();
            entity1.setId(1);
            entity1.setName("name");
            entity1.setPrice(0.0);
            entity1.setStock(0);
            entity1.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            verify(mockProductMapper, Mockito.never()).updateStock(entity1);
        }
    }

    @Test
    void testRestock_StockWithLessThanZero() {
        // Setup
        final ProductEntity entity = new ProductEntity();
        entity.setId(1);
        entity.setName("name");
        entity.setPrice(0.0);
        entity.setStock(-1);
        entity.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        //Configure ProductEntityMappable.fetchScalar(...)
        final ProductEntity entity3 = new ProductEntity();
        entity3.setId(1);
        entity3.setName("name");
        entity3.setPrice(0.0);
        entity3.setStock(1);
        entity3.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockProductMapper.fetchScalar(1)).thenReturn(entity3);

        // Run the test
        productServiceUnderTest.restock(entity, 1);

        // Verify the results
        // Confirm ProductEntityMappable.updateStock(...).
        final ProductEntity entity1 = new ProductEntity();
        entity1.setId(1);
        entity1.setName("name");
        entity1.setPrice(0.0);
        entity1.setStock(0);
        entity1.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        verify(mockProductMapper, Mockito.never()).updateStock(entity1);
    }

    @Test
    void testDestock() {
        // Setup
        final ProductEntity entity = new ProductEntity();
        entity.setId(1);
        entity.setName("name");
        entity.setPrice(0.0);
        entity.setStock(1);
        entity.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        //Configure ProductEntityMappable.fetchScalar(...)
        final ProductEntity entity3 = new ProductEntity();
        entity3.setId(1);
        entity3.setName("name");
        entity3.setPrice(0.0);
        entity3.setStock(1);
        entity3.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockProductMapper.fetchScalar(1)).thenReturn(entity3);

        // Run the test
        productServiceUnderTest.destock(entity, 1);

        // Verify the results
        // Confirm ProductEntityMappable.updateStock(...).
        final ProductEntity entity1 = new ProductEntity();
        entity1.setId(1);
        entity1.setName("name");
        entity1.setPrice(0.0);
        entity1.setStock(0);
        entity1.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        verify(mockProductMapper).updateStock(entity1);
    }

    @Test
    void testDestock_IncrementLessThanZero() {
        // Setup
        final ProductEntity entity = new ProductEntity();
        entity.setId(1);
        entity.setName("name");
        entity.setPrice(0.0);
        entity.setStock(1);
        entity.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        //Configure ProductEntityMappable.fetchScalar(...)
        final ProductEntity entity3 = new ProductEntity();
        entity3.setId(1);
        entity3.setName("name");
        entity3.setPrice(0.0);
        entity3.setStock(1);
        entity3.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockProductMapper.fetchScalar(1)).thenReturn(entity3);

        try {
            // Run the test
            productServiceUnderTest.destock(entity, -1);
        } catch (IllegalArgumentException e) {
            // Verify the results
            // Confirm ProductEntityMappable.updateStock(...).
            final ProductEntity entity1 = new ProductEntity();
            entity1.setId(1);
            entity1.setName("name");
            entity1.setPrice(0.0);
            entity1.setStock(2);
            entity1.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            verify(mockProductMapper, Mockito.never()).updateStock(entity1);
        }
    }

    @Test
    void testDestock_StockWithLessThanZero() {
        // Setup
        final ProductEntity entity = new ProductEntity();
        entity.setId(1);
        entity.setName("name");
        entity.setPrice(0.0);
        entity.setStock(-1);
        entity.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        //Configure ProductEntityMappable.fetchScalar(...)
        final ProductEntity entity3 = new ProductEntity();
        entity3.setId(1);
        entity3.setName("name");
        entity3.setPrice(0.0);
        entity3.setStock(1);
        entity3.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockProductMapper.fetchScalar(1)).thenReturn(entity3);

        // Run the test
        productServiceUnderTest.destock(entity, 1);

        // Verify the results
        // Confirm ProductEntityMappable.updateStock(...).
        final ProductEntity entity1 = new ProductEntity();
        entity1.setId(1);
        entity1.setName("name");
        entity1.setPrice(0.0);
        entity1.setStock(-2);
        entity1.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        verify(mockProductMapper, Mockito.never()).updateStock(entity1);
    }

    @Test
    void testCountAllProducts() {
        // Setup
        final ProductEntity entity = new ProductEntity();
        entity.setId(1);
        entity.setName("name");
        entity.setPrice(0.0);
        entity.setStock(1);
        entity.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        //Configure ProductEntityMappable.fetchScalar(...)
        when(mockProductMapper.countAll()).thenReturn(1);

        // Run the test
        productServiceUnderTest.countAllProducts();

        // Verify the results
        // Confirm ProductEntityMappable.updateStock(...).
        final ProductEntity entity1 = new ProductEntity();
        entity1.setId(1);
        entity1.setName("name");
        entity1.setPrice(0.0);
        entity1.setStock(1);
        entity1.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        verify(mockProductMapper).countAll();
    }
}
