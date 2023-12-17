package org.neptrueworks.ordermanagement.core.servicing;

import org.neptrueworks.ordermanagement.data.entitizing.OrderItemEntity;
import org.neptrueworks.ordermanagement.data.entitizing.OrderManifestEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderServiceable extends IServiceable {
    void placeOrder(OrderManifestEntity orderManifest, List<OrderItemEntity> items);

    void cancelOrder(OrderManifestEntity orderManifest);

    void resumeOrder(OrderManifestEntity orderManifest);

    void orderItem(OrderItemEntity orderItem);

    void removeItem(OrderItemEntity orderItem);

    void restoreItem(OrderItemEntity orderItem);

    List<OrderManifestEntity> getAllOrderManifests();

    List<OrderManifestEntity> getLimitedOrderManifests(int pageIndex, int pageSize);

    int countAllOrderManifests();

    List<OrderItemEntity> getOrderItems(int manifestId);
}
