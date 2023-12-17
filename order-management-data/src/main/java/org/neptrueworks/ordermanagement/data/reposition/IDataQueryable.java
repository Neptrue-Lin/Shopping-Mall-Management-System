package org.neptrueworks.ordermanagement.data.reposition;

import org.neptrueworks.ordermanagement.data.entitizing.IDataEntitizable;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataMappable;

import java.util.List;

public interface IDataQueryable<TEntity extends IDataEntitizable<TIdentifier>, TIdentifier extends Comparable<TIdentifier>>
        extends IDataMappable {
    TEntity fetchScalar(TIdentifier id);
    List<TEntity> fetchAll();
}
