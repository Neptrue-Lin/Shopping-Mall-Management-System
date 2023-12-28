package org.neptrueworks.ordermanagement.data.entitizing;

import org.neptrueworks.ordermanagement.common.composing.IDuplicatable;

import java.io.Serializable;

public interface IDataEntitizable<TIdentifier extends Comparable<TIdentifier>>
        extends Serializable, IDuplicatable<IDataEntitizable<TIdentifier>> {
    TIdentifier getId();

    void setId(TIdentifier id);

    /**
     * Determine whether the entity has same identity as this.
     * @param entity The entity needed for determining.
     * @return true if they have identical physical address, or they are same type and have same identifier;
     * otherwise false.
     */
    @Override
    default boolean isIdentical(IDataEntitizable<TIdentifier> entity) {
        //Identity
        if (entity == this) {
            return true;
        }

        //Uniqueness
        return entity != null &&
                entity.getClass().getTypeName().equals(this.getClass().getTypeName())
                && entity.getId() == this.getId();
    }
}
