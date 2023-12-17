package org.neptrueworks.ordermanagement.data.entitizing;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class ProductEntity implements IDataAuditingEntitizable<Integer> {
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private Double price;
    private Integer stock = 0;
    private Date createdAt;
    private String createdBy = "TEST";
    private Date lastModifiedAt;
    private String lastModifiedBy = "TEST";
    private boolean isDeleted = false;

    @Override
    public <TEntity extends IDataEntitizable<Integer>> boolean isIsotropic(TEntity entity) {
        if (entity == this) {
            return true;
        }
        if (!(entity instanceof ProductEntity productEntity)) {
            return false;
        }
        return this.getName().equals(productEntity.getName())
                && this.getPrice().equals(productEntity.getPrice())
                && this.getStock().equals(productEntity.getStock());
    }
}
