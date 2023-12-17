package org.neptrueworks.ordermanagement.data.mapping;

import org.apache.ibatis.annotations.Mapper;
import org.neptrueworks.ordermanagement.data.entitizing.OrderItemEntity;
import org.neptrueworks.ordermanagement.data.maneuvering.*;
import org.neptrueworks.ordermanagement.data.reposition.IDataCountable;
import org.neptrueworks.ordermanagement.data.reposition.IDataLimitable;

import java.util.List;

@Mapper
public interface OrderItemEntityMappable
        extends IDataMappable, IDataOperable<OrderItemEntity, Integer>, IDataRemovable<OrderItemEntity, Integer>,
        IDataLimitable<OrderItemEntity, Integer>, IDataCountable<OrderItemEntity, Integer> {

    List<OrderItemEntity> fetchManifestItems(Integer manifestId);
}
