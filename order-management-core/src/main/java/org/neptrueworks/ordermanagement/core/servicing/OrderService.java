package org.neptrueworks.ordermanagement.core.servicing;

import org.neptrueworks.ordermanagement.data.entitizing.OrderItemEntity;
import org.neptrueworks.ordermanagement.data.entitizing.OrderManifestEntity;
import org.neptrueworks.ordermanagement.data.entitizing.ProductEntity;
import org.neptrueworks.ordermanagement.data.mapping.OrderItemEntityMappable;
import org.neptrueworks.ordermanagement.data.mapping.OrderManifestEntityMappable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public void placeOrder(OrderManifestEntity orderManifest, List<OrderItemEntity> items) {
        //Check inventory & merchandise availability
        int quantity;
        List<ProductEntity> entities = new ArrayList<>();
        for (OrderItemEntity item : items) {
            quantity = item.getQuantity();
            if (quantity <= 0) {
                throw new IllegalArgumentException();
            }

            ProductEntity entity = this.productService.identifyProduct(item.getId());
            if (entity.getStock() - quantity < 0) {
                throw new IllegalArgumentException();
            }
            entities.add(entity);
        }

        //Calculate gross amount & Reduce stocks
        int id = this.manifestMapper.add(orderManifest);

        int size = items.size();
        double grossAmount = 0.0;
        for (int i = 0; i < size; i++) {
            items.get(i).setOrderManifestId(id);
            orderItem(items.get(i));
            grossAmount += entities.get(i).getPrice() * items.get(i).getQuantity();
            this.productService.destock(entities.get(i), items.get(i).getQuantity());
        }

        orderManifest.setGrossAmount(grossAmount);
        this.manifestMapper.updateGrossAmount(orderManifest);
    }

    @Override
    public void cancelOrder(OrderManifestEntity orderManifest) {
        if (orderManifest.isDeleted()) {
            return;
        }
        this.manifestMapper.removeScalar(orderManifest.getId());
    }

    @Override
    public void resumeOrder(OrderManifestEntity orderManifest) {
        if (!orderManifest.isDeleted()) {
            return;
        }
        this.manifestMapper.resumeScalar(orderManifest.getId());
    }

    @Override
    public void orderItem(OrderItemEntity orderItem) {
        this.itemMapper.add(orderItem);
    }

    @Override
    public void removeItem(OrderItemEntity orderItem) {
        this.itemMapper.removeScalar(orderItem.getId());
    }

    @Override
    public void restoreItem(OrderItemEntity orderItem) {
        if (!orderItem.isDeleted()) {
            return;
        }
        this.itemMapper.resumeScalar(orderItem.getId());
    }

    @Override
    public List<OrderManifestEntity> getAllOrderManifests() {
        return this.manifestMapper.fetchAll();
    }

    @Override
    public List<OrderManifestEntity> getLimitedOrderManifests(int pageIndex, int pageSize) {
        int offset = pageSize * (pageIndex - 1);
        return this.manifestMapper.limitOffset(offset, pageSize);
    }

    @Override
    public int countAllOrderManifests() {
        return this.manifestMapper.countAll();
    }

    @Override
    public List<OrderItemEntity> getOrderItems(int manifestId) {
        return this.itemMapper.fetchManifestItems(manifestId);
    }
}
