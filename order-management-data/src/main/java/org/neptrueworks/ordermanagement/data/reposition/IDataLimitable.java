package org.neptrueworks.ordermanagement.data.reposition;

import org.apache.ibatis.annotations.Param;
import org.neptrueworks.ordermanagement.data.entitizing.IDataEntitizable;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataMappable;

import java.util.List;
import java.util.Map;

//Seek & Skip
public interface IDataLimitable <TEntity extends IDataEntitizable<TIdentifier>, TIdentifier extends Comparable<TIdentifier>>
        extends IDataMappable {
    List<TEntity> limitOffset(@Param("offset") int offset, @Param("count") int count);
    List<TEntity> limitOutset(@Param("count") int count);
    List<TEntity> limitKeyset(@Param("keyset") Map<String, Object> keyset, @Param("count") int count);
}
