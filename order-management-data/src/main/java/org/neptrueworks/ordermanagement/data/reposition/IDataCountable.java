package org.neptrueworks.ordermanagement.data.reposition;

import org.neptrueworks.ordermanagement.data.entitizing.IDataEntitizable;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataMappable;

public interface IDataCountable <TEntity extends IDataEntitizable<TIdentifier>, TIdentifier extends Comparable<TIdentifier>>
        extends IDataMappable {
    Long countScalar(TIdentifier id);
    Long countAll();
 }
