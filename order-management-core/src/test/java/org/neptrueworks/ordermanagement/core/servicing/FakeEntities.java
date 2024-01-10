package org.neptrueworks.ordermanagement.core.servicing;

import org.neptrueworks.ordermanagement.data.entitizing.OrderItemEntity;
import org.neptrueworks.ordermanagement.data.entitizing.OrderManifestEntity;
import org.neptrueworks.ordermanagement.data.entitizing.ProductEntity;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public record FakeEntities() {
    private static final Integer PRODUCT_IDENTIFIER = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
    private static final Integer ORDER_MANIFEST_IDENTIFIER = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
    private static final Integer ORDER_ITEM_IDENTIFIER = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);

    private static final Date CREATED_AT = new Date(System.currentTimeMillis());
    private static final Date LAST_MODIFIED_AT = new Date(System.currentTimeMillis());
    private static final Date PAID_AT = new Date(System.currentTimeMillis());

    private static final String CREATED_BY = "TEST";
    private static final String LAST_MODIFIED_BY = "TEST";

    private static final Boolean IS_DELETED = Boolean.FALSE;


    public static final ProductEntity FAKE_PRODUCT = new ProductEntity(PRODUCT_IDENTIFIER,
            "Test", 1000.00, 10,
            CREATED_AT, CREATED_BY,
            LAST_MODIFIED_AT, LAST_MODIFIED_BY, IS_DELETED);
    public static final OrderManifestEntity FAKE_ORDER_MANIFEST = new OrderManifestEntity(
            ORDER_MANIFEST_IDENTIFIER, 1000.00, PAID_AT,
            CREATED_AT, CREATED_BY,
            LAST_MODIFIED_AT, LAST_MODIFIED_BY, IS_DELETED);
    public static final OrderItemEntity FAKE_ORDER_ITEM = new OrderItemEntity(
            ORDER_ITEM_IDENTIFIER, PRODUCT_IDENTIFIER, 1, ORDER_MANIFEST_IDENTIFIER,
            CREATED_AT, CREATED_BY,
            LAST_MODIFIED_AT, LAST_MODIFIED_BY, IS_DELETED);
}
