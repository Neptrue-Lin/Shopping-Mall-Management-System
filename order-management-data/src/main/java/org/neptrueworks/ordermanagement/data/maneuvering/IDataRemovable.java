package org.neptrueworks.ordermanagement.data.maneuvering;

import org.neptrueworks.ordermanagement.data.entitizing.IDataEntitizable;

public interface IDataRemovable <TEntity extends IDataEntitizable<TIdentifier>, TIdentifier extends Comparable<TIdentifier>>
        extends IDataMappable {
    void removeScalar(TIdentifier id);
    void resumeScalar(TIdentifier id);
}