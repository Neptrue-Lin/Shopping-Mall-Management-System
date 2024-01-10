package org.neptrueworks.ordermanagement.data.reposition;

import org.apache.ibatis.annotations.Param;
import org.neptrueworks.ordermanagement.data.entitizing.IDataEntitizable;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataMappable;

public interface IDataPageable<TEntity extends IDataEntitizable<TIdentifier>, TIdentifier extends Comparable<TIdentifier>>
        extends IDataMappable {
    Iterable<TEntity> take(@Param("offset") int offset, @Param("count") int count);
    Iterable<TEntity> seek(@Param("keyset") Iterable<DataSeekSpecification> keyset, @Param("count") int count);
}
