package org.neptrueworks.ordermanagement.data.reposition;

import org.neptrueworks.ordermanagement.data.entitizing.IDataEntitizable;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataMappable;

import java.util.Optional;

public interface IDataQueryable<TEntity extends IDataEntitizable<TIdentifier>, TIdentifier extends Comparable<TIdentifier>>
        extends IDataMappable {
    Optional<TEntity> fetchScalar(TIdentifier id);
    Iterable<TEntity> fetchAll();
}
