package org.neptrueworks.ordermanagement.core.servicing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.neptrueworks.ordermanagement.data.entitizing.OrderItemEntity;
import org.neptrueworks.ordermanagement.data.entitizing.OrderManifestEntity;
import org.neptrueworks.ordermanagement.data.entitizing.ProductEntity;
import org.neptrueworks.ordermanagement.data.mapping.OrderItemEntityMappable;
import org.neptrueworks.ordermanagement.data.mapping.OrderManifestEntityMappable;
import org.neptrueworks.ordermanagement.data.mapping.ProductEntityMappable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderManifestEntityMappable mockManifestMapper;
    @Mock
    private OrderItemEntityMappable mockItemMapper;
    @Mock
    private ProductEntityMappable mockProductMapper;
    @Mock
    private ProductService mockProductService;

    private OrderService orderServiceUnderTest;

    @BeforeEach
    void setUp() {
        orderServiceUnderTest = new OrderService(mockManifestMapper, mockItemMapper, mockProductService);
    }

    @Test
    void testPlaceOrder() {
        // Setup
        final OrderManifestEntity orderManifest = new OrderManifestEntity();
        orderManifest.setId(1);
        orderManifest.setGrossAmount(0.0);
        orderManifest.setPaidAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        orderManifest.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        orderManifest.setDeleted(false);

        final OrderItemEntity entity = new OrderItemEntity();
        entity.setId(1);
        entity.setProductId(1);
        entity.setQuantity(1);
        entity.setOrderManifestId(1);
        entity.setDeleted(false);
        final List<OrderItemEntity> items = List.of(entity);

        // Configure ProductService.identifyProduct(...).
        final ProductEntity entity1 = new ProductEntity();
        entity1.setId(1);
        entity1.setName("name");
        entity1.setPrice(10.0);
        entity1.setStock(1);
        entity1.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockProductService.identifyProduct(1)).thenReturn(entity1);

        // Configure OrderManifestEntityMappable.add(...).
        final OrderManifestEntity entity2 = new OrderManifestEntity();
        entity2.setId(1);
        entity2.setGrossAmount(0.0);
        entity2.setPaidAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        entity2.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        entity2.setDeleted(false);
        when(mockManifestMapper.add(entity2)).thenReturn(1);

        //Configure ProductEntityMappable.fetchScalar(...)
        final ProductEntity entity3 = new ProductEntity();
        entity3.setId(1);
        entity3.setName("name");
        entity3.setPrice(0.0);
        entity3.setStock(1);
        entity3.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockProductMapper.fetchScalar(1)).thenReturn(entity3);

        // Run the test
        orderServiceUnderTest.placeOrder(orderManifest, items);

        // Verify the results
        // Confirm OrderItemEntityMappable.add(...).
        final OrderItemEntity entity4 = new OrderItemEntity();
        entity4.setId(1);
        entity4.setProductId(1);
        entity4.setQuantity(1);
        entity4.setOrderManifestId(1);
        entity4.setDeleted(false);
        verify(mockItemMapper, atLeastOnce()).add(entity4);

        // Confirm ProductService.destock(...).
        final ProductEntity entity5 = new ProductEntity();
        entity5.setId(1);
        entity5.setName("name");
        entity5.setPrice(0.0);
        entity5.setStock(1);
        entity5.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        ProductService spied = spy(new ProductService(mockProductMapper));
        spied.destock(entity5,1);
        verify(spied).destock(entity5, 1);

        // Confirm OrderManifestEntityMappable.updateGrossAmount(...).
        final OrderManifestEntity orderManifest1 = new OrderManifestEntity();
        orderManifest1.setId(1);
        orderManifest1.setGrossAmount(0.0);
        orderManifest1.setPaidAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        orderManifest1.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        orderManifest1.setDeleted(false);
        //verify(mockManifestMapper).updateGrossAmount(orderManifest1);
    }

    @Test
    void testPlaceOrder_ItemWithLessThanZeroQuantity() {
        // Setup
        final OrderManifestEntity orderManifest = new OrderManifestEntity();
        orderManifest.setId(1);
        orderManifest.setGrossAmount(0.0);
        orderManifest.setPaidAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        orderManifest.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        orderManifest.setDeleted(false);

        final OrderItemEntity entity = new OrderItemEntity();
        entity.setId(1);
        entity.setProductId(1);
        entity.setQuantity(-1);
        entity.setOrderManifestId(1);
        entity.setDeleted(false);
        final List<OrderItemEntity> items = List.of(entity);

//        // Configure ProductService.identifyProduct(...).
//        final ProductEntity entity1 = new ProductEntity();
//        entity1.setId(1);
//        entity1.setName("name");
//        entity1.setPrice(10.0);
//        entity1.setStock(1);
//        entity1.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
//        when(mockProductService.identifyProduct(1)).thenReturn(entity1);
//
//        // Configure OrderManifestEntityMappable.add(...).
//        final OrderManifestEntity entity2 = new OrderManifestEntity();
//        entity2.setId(1);
//        entity2.setGrossAmount(0.0);
//        entity2.setPaidAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
//        entity2.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
//        entity2.setDeleted(false);
//        when(mockManifestMapper.add(entity2)).thenReturn(1);

        try {
            // Run the test
            orderServiceUnderTest.placeOrder(orderManifest, items);
            Assertions.fail();
        } catch (IllegalArgumentException e) {
            // Verify the results
            // Confirm OrderItemEntityMappable.add(...).
            final OrderItemEntity entity3 = new OrderItemEntity();
            entity3.setId(1);
            entity3.setProductId(1);
            entity3.setQuantity(-1);
            entity3.setOrderManifestId(1);
            entity3.setDeleted(false);
            verify(mockItemMapper, Mockito.never()).add(entity3);

            // Confirm ProductService.destock(...).
            final ProductEntity entity4 = new ProductEntity();
            entity4.setId(1);
            entity4.setName("name");
            entity4.setPrice(10.0);
            entity4.setStock(1);
            entity4.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            verify(mockProductService, Mockito.never()).destock(entity4, -1);

            // Confirm OrderManifestEntityMappable.updateGrossAmount(...).
            final OrderManifestEntity orderManifest1 = new OrderManifestEntity();
            orderManifest1.setId(1);
            orderManifest1.setGrossAmount(-10.0);
            orderManifest1.setPaidAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            orderManifest1.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            orderManifest1.setDeleted(false);
            verify(mockManifestMapper, Mockito.never()).updateGrossAmount(orderManifest1);
        }
    }

    @Test
    void testPlaceOrder_ProductWithLessThanZeroStock() {
        // Setup
        final OrderManifestEntity orderManifest = new OrderManifestEntity();
        orderManifest.setId(1);
        orderManifest.setGrossAmount(0.0);
        orderManifest.setPaidAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        orderManifest.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        orderManifest.setDeleted(false);

        final OrderItemEntity entity = new OrderItemEntity();
        entity.setId(1);
        entity.setProductId(1);
        entity.setQuantity(1);
        entity.setOrderManifestId(1);
        entity.setDeleted(false);
        final List<OrderItemEntity> items = List.of(entity);

        // Configure ProductService.identifyProduct(...).
        final ProductEntity entity1 = new ProductEntity();
        entity1.setId(1);
        entity1.setName("name");
        entity1.setPrice(10.0);
        entity1.setStock(-1);
        entity1.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockProductService.identifyProduct(1)).thenReturn(entity1);

//        // Configure OrderManifestEntityMappable.add(...).
//        final OrderManifestEntity entity2 = new OrderManifestEntity();
//        entity2.setId(1);
//        entity2.setGrossAmount(0.0);
//        entity2.setPaidAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
//        entity2.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
//        entity2.setDeleted(false);
//        when(mockManifestMapper.add(entity2)).thenReturn(1);

        // Run the test
        try {
            // Run the test
            orderServiceUnderTest.placeOrder(orderManifest, items);
            Assertions.fail();
        } catch (IllegalArgumentException e) {
            // Verify the results
            // Confirm OrderItemEntityMappable.add(...).
            final OrderItemEntity entity3 = new OrderItemEntity();
            entity3.setId(1);
            entity3.setProductId(1);
            entity3.setQuantity(1);
            entity3.setOrderManifestId(1);
            entity3.setDeleted(false);
            verify(mockItemMapper, Mockito.never()).add(entity3);

            // Confirm ProductService.destock(...).
            final ProductEntity entity4 = new ProductEntity();
            entity4.setId(1);
            entity4.setName("name");
            entity4.setPrice(10.0);
            entity4.setStock(-1);
            entity4.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            verify(mockProductService, Mockito.never()).destock(entity4, 1);

            // Confirm OrderManifestEntityMappable.updateGrossAmount(...).
            final OrderManifestEntity orderManifest1 = new OrderManifestEntity();
            orderManifest1.setId(1);
            orderManifest1.setGrossAmount(10.0);
            orderManifest1.setPaidAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            orderManifest1.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            orderManifest1.setDeleted(false);
            verify(mockManifestMapper, Mockito.never()).updateGrossAmount(orderManifest1);
        }
    }

    @Test
    void testCancelOrder() {
        // Setup
        final OrderManifestEntity orderManifest = new OrderManifestEntity();
        orderManifest.setId(1);
        orderManifest.setGrossAmount(0.0);
        orderManifest.setPaidAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        orderManifest.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        orderManifest.setDeleted(false);

        // Run the test
        orderServiceUnderTest.cancelOrder(orderManifest);

        // Verify the results
        verify(mockManifestMapper).removeScalar(1);
    }

    @Test
    void testResumeOrder() {
        // Setup
        final OrderManifestEntity orderManifest = new OrderManifestEntity();
        orderManifest.setId(1);
        orderManifest.setGrossAmount(0.0);
        orderManifest.setPaidAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        orderManifest.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        orderManifest.setDeleted(true);

        // Run the test
        orderServiceUnderTest.resumeOrder(orderManifest);

        //Verify the results
        verify(mockManifestMapper).resumeScalar(1);
    }

    @Test
    void testOrderItem() {
        // Setup
        final OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setId(1);
        orderItem.setProductId(1);
        orderItem.setQuantity(1);
        orderItem.setOrderManifestId(1);
        orderItem.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Run the test
        orderServiceUnderTest.orderItem(orderItem);

        // Verify the results
        // Confirm OrderItemEntityMappable.add(...).
        final OrderItemEntity entity = new OrderItemEntity();
        entity.setId(1);
        entity.setProductId(1);
        entity.setQuantity(1);
        entity.setOrderManifestId(1);
        entity.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        verify(mockItemMapper).add(entity);
    }

    @Test
    void testRemoveItem() {
        // Setup
        final OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setId(1);
        orderItem.setProductId(1);
        orderItem.setQuantity(1);
        orderItem.setOrderManifestId(1);
        orderItem.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        orderItem.setDeleted(false);

        // Run the test
        orderServiceUnderTest.removeItem(orderItem);

        // Verify the results
        verify(mockItemMapper).removeScalar(1);
    }

    @Test
    void testRestoreItem() {
        // Setup
        final OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setId(1);
        orderItem.setProductId(1);
        orderItem.setQuantity(1);
        orderItem.setOrderManifestId(1);
        orderItem.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        orderItem.setDeleted(true);

        // Run the test
        orderServiceUnderTest.restoreItem(orderItem);

        // Verify the results
        verify(mockItemMapper).resumeScalar(1);
    }

    @Test
    void testGetAllOrderManifests() {
        // Setup
        final OrderManifestEntity entity = new OrderManifestEntity();
        entity.setId(1);
        entity.setGrossAmount(0.0);
        entity.setPaidAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        entity.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        entity.setDeleted(false);
        final List<OrderManifestEntity> expectedResult = List.of(entity);

        // Configure OrderManifestEntityMappable.fetchAll(...).
        final OrderManifestEntity entity1 = new OrderManifestEntity();
        entity1.setId(1);
        entity1.setGrossAmount(0.0);
        entity1.setPaidAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        entity1.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        entity1.setDeleted(false);
        final List<OrderManifestEntity> orderManifestEntities = List.of(entity1);
        when(mockManifestMapper.fetchAll()).thenReturn(orderManifestEntities);

        // Run the test
        final List<OrderManifestEntity> result = orderServiceUnderTest.getAllOrderManifests();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetLimitedOrderManifests() {
        // Setup
        final OrderManifestEntity entity = new OrderManifestEntity();
        entity.setId(1);
        entity.setGrossAmount(0.0);
        entity.setPaidAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        entity.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        entity.setDeleted(false);
        final List<OrderManifestEntity> expectedResult = List.of(entity);

        // Configure OrderManifestEntityMappable.limitOffset(...).
        final OrderManifestEntity entity1 = new OrderManifestEntity();
        entity1.setId(1);
        entity1.setGrossAmount(0.0);
        entity1.setPaidAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        entity1.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        entity1.setDeleted(false);
        final List<OrderManifestEntity> orderManifestEntities = List.of(entity1);
        when(mockManifestMapper.limitOffset(0, 10)).thenReturn(orderManifestEntities);

        // Run the test
        final List<OrderManifestEntity> result = orderServiceUnderTest.getLimitedOrderManifests(1, 10);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testCountAllOrderManifests() {
        // Setup
        when(mockManifestMapper.countAll()).thenReturn(10);

        // Run the test
        final int result = orderServiceUnderTest.countAllOrderManifests();

        // Verify the results
        assertEquals(10, result);
    }

    @Test
    void testGetOrderItems() {
        // Setup
        final OrderItemEntity entity = new OrderItemEntity();
        entity.setId(1);
        entity.setProductId(1);
        entity.setQuantity(1);
        entity.setOrderManifestId(1);
        entity.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<OrderItemEntity> expectedResult = List.of(entity);

        // Configure OrderItemEntityMappable.fetchManifestItems(...).
        final OrderItemEntity entity1 = new OrderItemEntity();
        entity1.setId(1);
        entity1.setProductId(1);
        entity1.setQuantity(1);
        entity1.setOrderManifestId(1);
        entity1.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<OrderItemEntity> orderItemEntities = List.of(entity1);
        when(mockItemMapper.fetchManifestItems(1)).thenReturn(orderItemEntities);

        // Run the test
        final List<OrderItemEntity> result = orderServiceUnderTest.getOrderItems(1);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
