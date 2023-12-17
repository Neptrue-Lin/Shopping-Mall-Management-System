package org.neptrueworks.ordermanagement.data.reposition;

import org.neptrueworks.ordermanagement.data.entitizing.IDataEntitizable;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataMappable;

public interface IDataCountable <TEntity extends IDataEntitizable<TIdentifier>, TIdentifier extends Comparable<TIdentifier>>
        extends IDataMappable {
    int countScalar(TIdentifier id);
    int countAll();
 }
