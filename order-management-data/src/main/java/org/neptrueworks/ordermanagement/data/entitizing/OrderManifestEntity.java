package org.neptrueworks.ordermanagement.data.entitizing;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class OrderManifestEntity implements IDataAuditingEntitizable<Integer> {
    private Integer id;
    @NonNull
    private Double grossAmount;
    private Date paidAt;
    private Date createdAt;
    private String createdBy = "TEST";
    private Date lastModifiedAt;
    private String lastModifiedBy = "TEST";
    private boolean isDeleted = false;

    @SneakyThrows
    @Override
    public OrderManifestEntity duplicate() {
        return (OrderManifestEntity) this.clone();
    }

    @Override
    public boolean isIsotropic(IDataEntitizable<Integer> entity) {
        if (entity == this) {
            return true;
        }
        if (!(entity instanceof OrderManifestEntity orderManifestEntity)) {
            return false;
        }
        return this.getGrossAmount().equals(orderManifestEntity.getGrossAmount())
                && this.getPaidAt().equals(orderManifestEntity.getPaidAt());
    }
}
