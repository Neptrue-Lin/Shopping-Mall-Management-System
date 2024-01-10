package org.neptrueworks.ordermanagement.core.servicing;

import org.neptrueworks.ordermanagement.data.entitizing.OrderItemEntity;
import org.neptrueworks.ordermanagement.data.entitizing.OrderManifestEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface OrderServiceable extends IServiceable {
    void placeOrder(OrderManifestEntity orderManifest, Iterable<OrderItemEntity> items);

    void cancelOrder(int manifestId);

    void resumeOrder(int manifestId);

    void orderItem(OrderItemEntity orderItem);

    void removeItem(int itemId);

    void restoreItem(int itemId);

    Iterable<OrderManifestEntity> getAllOrderManifests();

    Iterable<OrderManifestEntity> getPagedOrderManifests(int pageIndex, int pageSize);

    long countAllOrderManifests();

    boolean existsOrderManifest(int manifestId);

    boolean existsOrderItem(int itemId);

    Optional<OrderItemEntity> identifyOrderItemByProductId(int manifestId, int productId);

    Iterable<OrderItemEntity> findOrderItems(int manifestId);

    OrderManifestEntity identifyOrderManifest(int manifestId);

    OrderItemEntity identifyOrderItem(int itemId);
}
