package org.neptrueworks.ordermanagement.core.servicing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.neptrueworks.ordermanagement.core.exceptions.ServiceExceptionIssue;
import org.neptrueworks.ordermanagement.data.entitizing.OrderItemEntity;
import org.neptrueworks.ordermanagement.data.entitizing.OrderManifestEntity;
import org.neptrueworks.ordermanagement.data.entitizing.ProductEntity;
import org.neptrueworks.ordermanagement.data.mapping.OrderItemEntityMappable;
import org.neptrueworks.ordermanagement.data.mapping.OrderManifestEntityMappable;
import org.neptrueworks.ordermanagement.data.mapping.ProductEntityMappable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
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
        this.orderServiceUnderTest = new OrderService(this.mockManifestMapper, this.mockItemMapper, this.mockProductService);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @ArgumentsSource(value = PaginationTestCase.ValidPaginationArgumentsProvider.class)
    void testGetPagedOrderManifests_WithValidPagination_ReturnsNonEmptyPagedOrderManifests
            (PaginationTestCase testCase) {
        final OrderManifestEntity stubOrderManifest = FakeEntities.FAKE_ORDER_MANIFEST.duplicate();
        final int offset = (testCase.getPageIndex() - 1) * testCase.getPageSize();
        final int originalCount = testCase.getPageSize();
        final int actualCount = offset + testCase.getPageSize() > testCase.getAmount() ?
                testCase.getAmount() - offset :  //Partial page
                testCase.getPageSize();          //Full page

        final List<OrderManifestEntity> stubOrderManifests = new ArrayList<>();
        for (int i = 0; i < actualCount; i++) {
            stubOrderManifests.add(stubOrderManifest);
        }
        when(this.mockManifestMapper.take(offset, originalCount)).thenReturn(stubOrderManifests);

        final Iterable<OrderManifestEntity> mockOrderManifests = this.orderServiceUnderTest.getPagedOrderManifests
                (testCase.getPageIndex(), testCase.getPageSize());
        assertIterableEquals(stubOrderManifests, mockOrderManifests);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @ArgumentsSource(value = PaginationTestCase.ValidPaginationArgumentsProvider.class)
    void testGetPagedOrderManifests_WithInvalidPagination_ReturnsEmptyPagedOrderManifests
            (PaginationTestCase testCase) {
        final int offset = (testCase.getPageIndex() - 1) * testCase.getPageSize();
        final int originalCount = testCase.getPageSize();
        final List<OrderManifestEntity> stubOrderManifests = List.of();
        when(this.mockManifestMapper.take(offset, originalCount)).thenReturn(stubOrderManifests);

        final Iterable<OrderManifestEntity> mockOrderManifests = this.orderServiceUnderTest.getPagedOrderManifests
                (testCase.getPageIndex(), testCase.getPageSize());
        assertIterableEquals(stubOrderManifests, mockOrderManifests);
    }

    @Test
    void testIdentifyOrderManifest() {
        final OrderManifestEntity stubOrderManifest = FakeEntities.FAKE_ORDER_MANIFEST.duplicate();
        when(this.mockManifestMapper.fetchScalar(stubOrderManifest.getId())).thenReturn(Optional.of(stubOrderManifest));

        final OrderManifestEntity mockOrderManifest = this.orderServiceUnderTest.identifyOrderManifest(stubOrderManifest.getId());

        assertEquals(stubOrderManifest, mockOrderManifest);
    }

    @Test
    void testIdentifyOrderManifest_ManifestNotFound_ThrowsIllegalArgumentException() {
        final OrderManifestEntity stubOrderManifest = FakeEntities.FAKE_ORDER_MANIFEST.duplicate();
        when(this.mockManifestMapper.fetchScalar(stubOrderManifest.getId())).thenReturn(Optional.empty());
        assertThrowsExactly(ServiceExceptionIssue.ORDER_MANIFEST_NOT_FOUND.getExceptionClass(),
                () -> this.orderServiceUnderTest.identifyOrderManifest(stubOrderManifest.getId()),
                ServiceExceptionIssue.ORDER_MANIFEST_NOT_FOUND.getMessage());
        verifyNoInteractions(this.mockProductMapper);
    }

    @Test
    void testIdentifyOrderItem() {
        final OrderItemEntity stubOrderItem = FakeEntities.FAKE_ORDER_ITEM.duplicate();
        when(this.mockItemMapper.fetchScalar(stubOrderItem.getId())).thenReturn(Optional.of(stubOrderItem));

        final OrderItemEntity mockOrderItem = this.orderServiceUnderTest.identifyOrderItem(stubOrderItem.getId());
        assertEquals(stubOrderItem, mockOrderItem);
    }

    @Test
    void testIdentifyOrderItem_ManifestNotFound_ThrowsIllegalArgumentException() {
        final OrderItemEntity stubOrderItem = FakeEntities.FAKE_ORDER_ITEM.duplicate();
        when(this.mockItemMapper.fetchScalar(stubOrderItem.getId())).thenReturn(Optional.empty());

        assertThrowsExactly(ServiceExceptionIssue.ORDER_ITEM_NOT_FOUND.getExceptionClass(),
                () -> this.orderServiceUnderTest.identifyOrderItem(stubOrderItem.getId()),
                ServiceExceptionIssue.ORDER_ITEM_NOT_FOUND.getMessage());
        verifyNoInteractions(this.mockProductMapper);
    }

    @Test
    void testIdentifyOrderItemByProductId() {
        final OrderItemEntity stubOrderItem = FakeEntities.FAKE_ORDER_ITEM.duplicate();

        when(this.mockItemMapper.findItemByProductId(stubOrderItem.getId(), stubOrderItem.getId()))
                .thenReturn(Optional.of(stubOrderItem));
        final Optional<OrderItemEntity> mockOrderItem = this.orderServiceUnderTest.identifyOrderItemByProductId
                (stubOrderItem.getId(), stubOrderItem.getId());

        assertEquals(Optional.of(stubOrderItem), mockOrderItem);
    }

    @Test
    void testPlaceOrder() {
        final ProductEntity stubProduct = FakeEntities.FAKE_PRODUCT.duplicate();
        when(this.mockProductService.identifyProduct(anyInt())).thenReturn(stubProduct);

        final OrderManifestEntity stubOrderManifest = FakeEntities.FAKE_ORDER_MANIFEST.duplicate();
        final OrderItemEntity stubOrderItem = FakeEntities.FAKE_ORDER_ITEM.duplicate();
        this.orderServiceUnderTest.placeOrder(stubOrderManifest, List.of(stubOrderItem));

        verify(this.mockManifestMapper).add(stubOrderManifest);
        verify(this.mockItemMapper).add(stubOrderItem);
        verify(this.mockManifestMapper).updateGrossAmount(stubOrderManifest);
    }

    @Test
    void testCancelOrder_ManifestNotBeenRemoved_RemoveOrder() {
        final OrderManifestEntity stubOrderManifest = FakeEntities.FAKE_ORDER_MANIFEST.duplicate();
        when(this.mockManifestMapper.fetchScalar(stubOrderManifest.getId())).thenReturn(Optional.of(stubOrderManifest));

        this.orderServiceUnderTest.cancelOrder(stubOrderManifest.getId());
        verify(this.mockManifestMapper).removeScalar(stubOrderManifest.getId());
    }

    @Test
    void testCancelOrder_ManifestBeenRemoved_NotRemoveOrder() {
        final OrderManifestEntity stubOrderManifest = FakeEntities.FAKE_ORDER_MANIFEST.duplicate();
        stubOrderManifest.setDeleted(Boolean.TRUE);
        when(this.mockManifestMapper.fetchScalar(stubOrderManifest.getId())).thenReturn(Optional.of(stubOrderManifest));

        this.orderServiceUnderTest.cancelOrder(stubOrderManifest.getId());
        verify(this.mockManifestMapper, never()).removeScalar(stubOrderManifest.getId());
    }

    @Test
    void testResumeOrder_ManifestBeenRemoved_ResumeOrder() {
        final OrderManifestEntity stubOrderManifest = FakeEntities.FAKE_ORDER_MANIFEST.duplicate();
        stubOrderManifest.setDeleted(Boolean.TRUE);
        when(this.mockManifestMapper.fetchScalar(stubOrderManifest.getId())).thenReturn(Optional.of(stubOrderManifest));

        this.orderServiceUnderTest.resumeOrder(stubOrderManifest.getId());
        verify(this.mockManifestMapper).resumeScalar(stubOrderManifest.getId());
    }

    @Test
    void testResumeOrder_ManifestNotBeenRemoved_NotResumeOrder() {
        final OrderManifestEntity stubOrderManifest = FakeEntities.FAKE_ORDER_MANIFEST.duplicate();
        when(this.mockManifestMapper.fetchScalar(stubOrderManifest.getId())).thenReturn(Optional.of(stubOrderManifest));

        this.orderServiceUnderTest.resumeOrder(stubOrderManifest.getId());
        verify(this.mockManifestMapper, never()).resumeScalar(stubOrderManifest.getId());
    }

    @Test
    void testOrderItem() {
        final ProductEntity stubProduct = FakeEntities.FAKE_PRODUCT.duplicate();
        final OrderItemEntity stubOrderItem = FakeEntities.FAKE_ORDER_ITEM.duplicate();
        when(this.mockProductService.identifyProduct(stubOrderItem.getProductId())).thenReturn(stubProduct);

        this.orderServiceUnderTest.orderItem(stubOrderItem);

        verify(this.mockItemMapper).add(stubOrderItem);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @ArgumentsSource(ItemOrderingTestCase.InvalidItemOrderingArgumentsProvider.class)
    void testOrderItem_InvalidQuantity_ThrowsIllegalException
            (ItemOrderingTestCase testCase) {
        final OrderItemEntity stubOrderItem = FakeEntities.FAKE_ORDER_ITEM.duplicate();
        stubOrderItem.setQuantity(testCase.getQuantity());

        final ProductEntity stubProduct = FakeEntities.FAKE_PRODUCT.duplicate();
        stubProduct.setStock(testCase.getStock());
        when(this.mockProductService.identifyProduct(stubOrderItem.getProductId())).thenReturn(stubProduct);


        assertThrowsExactly(testCase.getExceptionIssue().getExceptionClass(),
                () -> this.orderServiceUnderTest.orderItem(stubOrderItem),
                testCase.getExceptionIssue().getMessage());

        verify(this.mockItemMapper, never()).add(stubOrderItem);
    }

    @Test
    void testRemoveItem_ItemNotBeenRemoved_RemoveItem() {
        final OrderItemEntity stubOrderItem = FakeEntities.FAKE_ORDER_ITEM.duplicate();
        when(this.mockItemMapper.fetchScalar(stubOrderItem.getId())).thenReturn(Optional.of(stubOrderItem));

        this.orderServiceUnderTest.removeItem(stubOrderItem.getId());
        verify(this.mockItemMapper).removeScalar(stubOrderItem.getId());
    }

    @Test
    void testResumeItem_ItemBeenRemoved_NotRemoveItem() {
        final OrderItemEntity stubOrderItem = FakeEntities.FAKE_ORDER_ITEM.duplicate();
        stubOrderItem.setDeleted(true);
        when(this.mockItemMapper.fetchScalar(stubOrderItem.getId())).thenReturn(Optional.of(stubOrderItem));

        this.orderServiceUnderTest.restoreItem(stubOrderItem.getId());
        verify(this.mockItemMapper, never()).removeScalar(stubOrderItem.getId());
    }

    @Test
    void testRestoreItem_ItemBeenRemoved_RestoreItem() {
        final OrderItemEntity stubOrderItem = FakeEntities.FAKE_ORDER_ITEM.duplicate();
        stubOrderItem.setDeleted(true);
        when(this.mockItemMapper.fetchScalar(stubOrderItem.getId())).thenReturn(Optional.of(stubOrderItem));

        this.orderServiceUnderTest.restoreItem(stubOrderItem.getId());
        verify(this.mockItemMapper).resumeScalar(stubOrderItem.getId());
    }

    @Test
    void testRestoreItem_ItemNotBeenRemoved_NotRestoreItem() {
        final OrderItemEntity stubOrderItem = FakeEntities.FAKE_ORDER_ITEM.duplicate();
        when(this.mockItemMapper.fetchScalar(stubOrderItem.getId())).thenReturn(Optional.of(stubOrderItem));

        this.orderServiceUnderTest.restoreItem(stubOrderItem.getId());
        verify(this.mockItemMapper, never()).resumeScalar(stubOrderItem.getId());
    }
}
