package org.neptrueworks.ordermanagement.data.maneuvering;

import org.neptrueworks.ordermanagement.data.entitizing.IDataEntitizable;

public interface IDataManipulable<TEntity extends IDataEntitizable<TIdentifier>, TIdentifier extends Comparable<TIdentifier>>
        extends IDataMappable, IDataAddable<TEntity, TIdentifier>,  IDataDeletable<TEntity, TIdentifier>{
}
