package org.neptrueworks.ordermanagement.data.mapping;

import org.apache.ibatis.annotations.Mapper;
import org.neptrueworks.ordermanagement.data.entitizing.OrderItemEntity;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataMappable;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataOperable;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataRemovable;
import org.neptrueworks.ordermanagement.data.reposition.DataSeekSpecification;
import org.neptrueworks.ordermanagement.data.reposition.DataSortLevel;
import org.neptrueworks.ordermanagement.data.reposition.IDataCountable;
import org.neptrueworks.ordermanagement.data.reposition.IDataPageable;

import java.util.Optional;

@Mapper
public interface OrderItemEntityMappable
        extends IDataMappable, IDataOperable<OrderItemEntity, Integer>, IDataRemovable<OrderItemEntity, Integer>,
        IDataPageable<OrderItemEntity, Integer>, IDataCountable<OrderItemEntity, Integer> {
    Iterable<OrderItemEntity> findManifestItems(Integer manifestId);
    Iterable<OrderItemEntity> sortManifestItems(Integer manifestId, DataSortLevel level);
    Iterable<OrderItemEntity> lookupItemsByProductId(Integer productId, DataSeekSpecification keyset);
    Optional<OrderItemEntity> findItemByProductId(Integer manifestId, Integer productId);
}
