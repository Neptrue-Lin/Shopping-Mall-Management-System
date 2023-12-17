package org.neptrueworks.ordermanagement.data.maneuvering;

import org.neptrueworks.ordermanagement.data.entitizing.IDataEntitizable;
import org.neptrueworks.ordermanagement.data.reposition.IDataQueryable;

public interface IDataOperable<TEntity extends IDataEntitizable<TIdentifier>, TIdentifier extends Comparable<TIdentifier>>
        extends IDataMappable, IDataQueryable<TEntity, TIdentifier>, IDataManipulable<TEntity, TIdentifier> {
}
