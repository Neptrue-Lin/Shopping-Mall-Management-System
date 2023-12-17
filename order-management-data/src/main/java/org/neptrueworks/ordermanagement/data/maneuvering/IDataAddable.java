package org.neptrueworks.ordermanagement.data.maneuvering;

import org.neptrueworks.ordermanagement.data.entitizing.IDataEntitizable;

public interface IDataAddable<TEntity extends IDataEntitizable<TIdentifier>, TIdentifier extends Comparable<TIdentifier>>
        extends IDataMappable {
    TIdentifier add(TEntity entity);
}
