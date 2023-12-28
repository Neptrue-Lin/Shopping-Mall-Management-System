package org.neptrueworks.ordermanagement.core.servicing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.neptrueworks.ordermanagement.core.exceptions.ServiceExceptionIssue;
import org.neptrueworks.ordermanagement.data.entitizing.ProductEntity;
import org.neptrueworks.ordermanagement.data.mapping.ProductEntityMappable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductEntityMappable mockProductMapper;
    private ProductService productServiceUnderTest;

    @BeforeEach
    void setUp() {
        productServiceUnderTest = new ProductService(mockProductMapper);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @ArgumentsSource(value = PaginationTestCase.ValidPaginationArgumentsProvider.class)
    void testGetPagedProducts_WithValidPagination_ReturnsNonEmptyPagedProducts
            (PaginationTestCase testCase) {
        final ProductEntity stubProduct = FakeEntities.FAKE_PRODUCT.duplicate();
        final int offset = (testCase.getPageIndex() - 1) * testCase.getPageSize();
        final int originalCount = testCase.getPageSize();
        final int actualCount = offset + testCase.getPageSize() > testCase.getAmount() ?
                testCase.getAmount() - offset :  //Partial page
                testCase.getPageSize();          //Full page

        final List<ProductEntity> stubProducts = new ArrayList<>();
        for (int i = 0; i < actualCount; i++) {
            stubProducts.add(stubProduct);
        }
        when(this.mockProductMapper.take(offset, originalCount)).thenReturn(stubProducts);

        final Iterable<ProductEntity> mockProducts = this.productServiceUnderTest.getPagedProducts
                (testCase.getPageIndex(), testCase.getPageSize());
        assertIterableEquals(stubProducts, mockProducts);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @ArgumentsSource(value = PaginationTestCase.InvalidPaginationArgumentsProvider.class)
    void testGetPagedProducts_WithInvalidPagination_ReturnsEmptyPagedProducts
            (PaginationTestCase testCase) {
        final int offset = (testCase.getPageIndex() - 1) * testCase.getPageSize();
        final int originalCount = testCase.getPageSize();
        final List<ProductEntity> stubProducts = List.of();
        when(this.mockProductMapper.take(offset, originalCount)).thenReturn(stubProducts);

        final Iterable<ProductEntity> mockProducts = this.productServiceUnderTest.getPagedProducts
                (testCase.getPageIndex(), testCase.getPageSize());
        assertIterableEquals(stubProducts, mockProducts);
    }

    @Test
    void testIdentifyProduct() {
        final ProductEntity stubProduct = FakeEntities.FAKE_PRODUCT.duplicate();
        when(this.mockProductMapper.fetchScalar(stubProduct.getId())).thenReturn(Optional.of(stubProduct));

        final ProductEntity mockProduct = this.productServiceUnderTest.identifyProduct(stubProduct.getId());

        assertEquals(stubProduct, mockProduct);
    }

    @Test
    void testIdentifyProduct_ProductNonexistent_ThrowsIllegalStateException() {
        final ProductEntity stubProduct = FakeEntities.FAKE_PRODUCT.duplicate();
        when(this.mockProductMapper.fetchScalar(stubProduct.getId())).thenReturn(Optional.empty());

        assertThrowsExactly(ServiceExceptionIssue.PRODUCT_NONEXISTENT.getExceptionClass(),
                () -> this.productServiceUnderTest.identifyProduct(stubProduct.getId()),
                ServiceExceptionIssue.PRODUCT_NONEXISTENT.getMessage());
    }

    @Test
    void testAddProduct() {
        final ProductEntity stubProduct = FakeEntities.FAKE_PRODUCT.duplicate();
        this.productServiceUnderTest.addProduct(stubProduct);

        verify(this.mockProductMapper).add(stubProduct);
    }

    @Test
    void testRemoveProduct_ProductNotBeenRemoved_RemoveProduct() {
        final ProductEntity stubProduct = FakeEntities.FAKE_PRODUCT.duplicate();
        when(this.mockProductMapper.fetchScalar(stubProduct.getId())).thenReturn(Optional.of(stubProduct));

        this.productServiceUnderTest.removeProduct(stubProduct.getId());
        verify(this.mockProductMapper).removeScalar(stubProduct.getId());
    }

    @Test
    void testResumeProduct_ProductBeenRemoved_NotRemoveProduct() {
        final ProductEntity stubProduct = FakeEntities.FAKE_PRODUCT.duplicate();
        stubProduct.setDeleted(Boolean.TRUE);
        when(this.mockProductMapper.fetchScalar(stubProduct.getId())).thenReturn(Optional.of(stubProduct));

        this.productServiceUnderTest.resumeProduct(stubProduct.getId());
        verify(this.mockProductMapper, never()).removeScalar(stubProduct.getId());
    }

    @Test
    void testResumeProduct_ProductBeenRemoved_ResumeProduct() {
        final ProductEntity stubProduct = FakeEntities.FAKE_PRODUCT.duplicate();
        stubProduct.setDeleted(Boolean.TRUE);
        when(this.mockProductMapper.fetchScalar(stubProduct.getId())).thenReturn(Optional.of(stubProduct));

        this.productServiceUnderTest.resumeProduct(stubProduct.getId());
        verify(this.mockProductMapper).resumeScalar(stubProduct.getId());
    }

    @Test
    void testResumeProduct_ProductNotBeenRemoved_NotResumeProduct() {
        final ProductEntity stubProduct = FakeEntities.FAKE_PRODUCT.duplicate();
        when(this.mockProductMapper.fetchScalar(stubProduct.getId())).thenReturn(Optional.of(stubProduct));

        this.productServiceUnderTest.resumeProduct(stubProduct.getId());
        verify(this.mockProductMapper, never()).resumeScalar(stubProduct.getId());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testDestock_VariationLargerThanZero_UpdateStock(int decrement) {
        final ProductEntity stubProduct = FakeEntities.FAKE_PRODUCT.duplicate();
        when(this.mockProductMapper.fetchScalar(stubProduct.getId())).thenReturn(Optional.of(stubProduct));

        this.productServiceUnderTest.destock(stubProduct.getId(), decrement);

        verify(this.mockProductMapper).updateStock(stubProduct);
    }

    @Test
    void testDestock_VariationEqualsZero_NotUpdateStock() {
        final ProductEntity stubProduct = FakeEntities.FAKE_PRODUCT.duplicate();
        when(this.mockProductMapper.fetchScalar(stubProduct.getId())).thenReturn(Optional.of(stubProduct));

        this.productServiceUnderTest.destock(stubProduct.getId(), 0);

        verify(this.mockProductMapper,never()).updateStock(stubProduct);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @ArgumentsSource(ProductInvalidStockingTestCase.InvalidDestockArgumentsProvider.class)
    void testDestock_InvalidStockOrVariation_ThrowsIllegalArgumentException
            (ProductInvalidStockingTestCase testCase) {
        final ProductEntity stubProduct = FakeEntities.FAKE_PRODUCT.duplicate();
        stubProduct.setStock(testCase.getStock());
        when(this.mockProductMapper.fetchScalar(stubProduct.getId())).thenReturn(Optional.of(stubProduct));

        assertThrowsExactly(testCase.getExceptionIssue().getExceptionClass(),
                () -> this.productServiceUnderTest.destock(stubProduct.getId(), testCase.getVariation()),
                testCase.getExceptionIssue().getMessage());

        verify(this.mockProductMapper, never()).updateStock(stubProduct);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testRestock_VariationLargerThanZero_UpdateStock(int increment) {
        final ProductEntity stubProduct = FakeEntities.FAKE_PRODUCT.duplicate();
        when(this.mockProductMapper.fetchScalar(stubProduct.getId())).thenReturn(Optional.of(stubProduct));

        this.productServiceUnderTest.restock(stubProduct.getId(), increment);

        verify(this.mockProductMapper).updateStock(stubProduct);
    }

    @Test
    void testRestock_VariationEqualsZero_NotUpdateStock() {
        final ProductEntity stubProduct = FakeEntities.FAKE_PRODUCT.duplicate();
        when(this.mockProductMapper.fetchScalar(stubProduct.getId())).thenReturn(Optional.of(stubProduct));

        this.productServiceUnderTest.restock(stubProduct.getId(), 0);

        verify(this.mockProductMapper,never()).updateStock(stubProduct);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @ArgumentsSource(ProductInvalidStockingTestCase.InvalidRestockArgumentsProvider.class)
    void testRestock_InvalidStockOrVariation_ThrowsIllegalArgumentException
            (ProductInvalidStockingTestCase testCase) {
        final ProductEntity stubProduct = FakeEntities.FAKE_PRODUCT.duplicate();
        stubProduct.setStock(testCase.getStock());
        when(this.mockProductMapper.fetchScalar(stubProduct.getId())).thenReturn(Optional.of(stubProduct));

        assertThrowsExactly(testCase.getExceptionIssue().getExceptionClass(),
                () -> this.productServiceUnderTest.restock(stubProduct.getId(), testCase.getVariation()),
                testCase.getExceptionIssue().getMessage());

        verify(this.mockProductMapper, never()).updateStock(stubProduct);
    }
}
