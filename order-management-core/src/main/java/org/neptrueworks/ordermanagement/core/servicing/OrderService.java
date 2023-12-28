package org.neptrueworks.ordermanagement.core.servicing;

import lombok.SneakyThrows;
import org.neptrueworks.ordermanagement.common.exceptions.ExceptionCashier;
import org.neptrueworks.ordermanagement.core.exceptions.ServiceExceptionIssue;
import org.neptrueworks.ordermanagement.data.entitizing.OrderItemEntity;
import org.neptrueworks.ordermanagement.data.entitizing.OrderManifestEntity;
import org.neptrueworks.ordermanagement.data.entitizing.ProductEntity;
import org.neptrueworks.ordermanagement.data.mapping.OrderItemEntityMappable;
import org.neptrueworks.ordermanagement.data.mapping.OrderManifestEntityMappable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements OrderServiceable {
    private final OrderManifestEntityMappable manifestMapper;
    private final OrderItemEntityMappable itemMapper;
    private final ProductService productService;

    public OrderService(OrderManifestEntityMappable manifestMapper,
                        OrderItemEntityMappable itemMapper,
                        ProductService productService) {
        this.manifestMapper = manifestMapper;
        this.itemMapper = itemMapper;
        this.productService = productService;
    }


    @Override
    public Iterable<OrderManifestEntity> getAllOrderManifests() {
        return this.manifestMapper.fetchAll();
    }

    @Override
    public Iterable<OrderManifestEntity> getPagedOrderManifests(int pageIndex, int pageSize) {
        if (pageIndex < 0 || pageSize < 0)
            return List.of();

        int offset = pageSize * (pageIndex - 1);
        return this.manifestMapper.take(offset, pageSize);
    }

    @Override
    public long countAllOrderManifests() {
        return this.manifestMapper.countAll();
    }

    @Override
    public boolean existsOrderManifest(int manifestId) {
        return this.manifestMapper.countScalar(manifestId) > 0;
    }

    @Override
    public boolean existsOrderItem(int itemId) {
        return this.manifestMapper.countScalar(itemId) > 0;
    }

    @Override
    public Iterable<OrderItemEntity> getOrderItems(int manifestId) {
        return this.itemMapper.findManifestItems(manifestId);
    }

    @Override
    @SneakyThrows
    public OrderManifestEntity identifyOrderManifest(int manifestId) {
        Optional<OrderManifestEntity> manifest = this.manifestMapper.fetchScalar(manifestId);
        if (manifest.isEmpty())
            throw ExceptionCashier.DEFAULT.checkout(ServiceExceptionIssue.ORDER_MANIFEST_NOT_FOUND);
        return manifest.get();
    }

    @Override
    @SneakyThrows
    public OrderItemEntity identifyOrderItem(int itemId) {
        Optional<OrderItemEntity> item = this.itemMapper.fetchScalar(itemId);
        if (item.isEmpty())
            throw ExceptionCashier.DEFAULT.checkout(ServiceExceptionIssue.ORDER_ITEM_NOT_FOUND);
        return item.get();
    }

    @Override
    public Optional<OrderItemEntity> identifyOrderItemByProductId(int manifestId, int productId) {
        return this.itemMapper.findItemByProductId(manifestId, productId);
    }

    @Override
    @Transactional
    public void placeOrder(OrderManifestEntity orderManifest, Iterable<OrderItemEntity> items) {
        this.manifestMapper.add(orderManifest);

        Iterator<OrderItemEntity> iterator = items.iterator();
        double grossAmount = 0.0;
        OrderItemEntity item;
        while (iterator.hasNext()) {
            item = iterator.next();
            item.setOrderManifestId(orderManifest.getId());
            this.orderItem(item);

            grossAmount += this.productService.identifyProduct(item.getProductId()).getPrice() * item.getQuantity();
        }

        orderManifest.setGrossAmount(grossAmount);
        this.manifestMapper.updateGrossAmount(orderManifest);
    }

    @Override
    public void cancelOrder(int manifestId) {
        OrderManifestEntity orderManifest = this.identifyOrderManifest(manifestId);
        if (orderManifest.isDeleted())
            return;
        this.manifestMapper.removeScalar(orderManifest.getId());
    }

    @Override
    public void resumeOrder(int manifestId) {
        OrderManifestEntity orderManifest = this.identifyOrderManifest(manifestId);
        if (!orderManifest.isDeleted())
            return;
        this.manifestMapper.resumeScalar(orderManifest.getId());
    }

    @Override
    @SneakyThrows
    public void orderItem(OrderItemEntity entity) {
        int quantity = entity.getQuantity();
        ProductEntity product = this.productService.identifyProduct(entity.getProductId());
        if (quantity <= 0)
            throw ExceptionCashier.DEFAULT.checkout(ServiceExceptionIssue.QUANTITY_NOT_GREATER_THAN_ZERO);
        else if (product.getStock() - quantity < 0)
            throw ExceptionCashier.DEFAULT.checkout(ServiceExceptionIssue.OUT_OF_STOCK);

        this.itemMapper.add(entity);
    }

    @Override
    public void removeItem(int itemId) {
        OrderItemEntity orderItem = this.identifyOrderItem(itemId);
        if (orderItem.isDeleted())
            return;
        this.itemMapper.removeScalar(orderItem.getId());
    }

    @Override
    public void restoreItem(int itemId) {
        OrderItemEntity orderItem = this.identifyOrderItem(itemId);
        if (!orderItem.isDeleted())
            return;
        this.itemMapper.resumeScalar(orderItem.getId());
    }
}
