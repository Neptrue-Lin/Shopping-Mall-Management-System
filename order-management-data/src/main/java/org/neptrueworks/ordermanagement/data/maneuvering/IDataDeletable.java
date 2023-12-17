package org.neptrueworks.ordermanagement.data.maneuvering;

import org.neptrueworks.ordermanagement.data.entitizing.IDataEntitizable;

public interface IDataDeletable<TEntity extends IDataEntitizable<TIdentifier>, TIdentifier extends Comparable<TIdentifier>>
        extends IDataMappable {
    void deleteScalar(TIdentifier id);
}
