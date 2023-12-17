package org.neptrueworks.ordermanagement.data.entitizing;

import java.io.Serializable;

public interface IDataEntitizable<TIdentifier extends Comparable<TIdentifier>> extends Serializable {
    TIdentifier getId();

    void setId(TIdentifier id);

    default <TEntity extends IDataEntitizable<TIdentifier>> boolean isIdentical(TEntity entity) {
        //Identity
        if (entity == this) {
            return true;
        }

        //Uniqueness
        return entity != null &&
                entity.getClass().getTypeName().equals(this.getClass().getTypeName())
                && entity.getId() == this.getId();
    }

    <TEntity extends IDataEntitizable<TIdentifier>> boolean isIsotropic(TEntity entity);
}
