package org.neptrueworks.ordermanagement.data.entitizing;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class OrderItemEntity implements IDataAuditingEntitizable<Integer> {
    private Integer id;
    @NonNull
    private Integer productId;
    @NonNull
    private Integer quantity = 0;
    @NonNull
    private Integer orderManifestId;
    private Date createdAt;
    private String createdBy = "TEST";
    private Date lastModifiedAt;
    private String lastModifiedBy = "TEST";
    private boolean isDeleted = false;

    @SneakyThrows
    @Override
    public OrderItemEntity duplicate() {
        return (OrderItemEntity) this.clone();
    }

    @Override
    public boolean isIsotropic(IDataEntitizable<Integer> entity) {
        if (entity == this)
            return true;

        if (!(entity instanceof OrderItemEntity orderItemEntity))
            return false;
        return this.getProductId().equals(orderItemEntity.getProductId())
                && this.getQuantity().equals(orderItemEntity.getQuantity())
                && this.getOrderManifestId().equals(orderItemEntity.getOrderManifestId());
    }
}
