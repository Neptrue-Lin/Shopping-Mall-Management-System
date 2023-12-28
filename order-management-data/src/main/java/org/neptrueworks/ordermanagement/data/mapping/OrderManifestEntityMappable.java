package org.neptrueworks.ordermanagement.data.mapping;

import org.apache.ibatis.annotations.Mapper;
import org.neptrueworks.ordermanagement.data.entitizing.OrderItemEntity;
import org.neptrueworks.ordermanagement.data.entitizing.OrderManifestEntity;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataMappable;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataOperable;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataRemovable;
import org.neptrueworks.ordermanagement.data.reposition.DataSeekSpecification;
import org.neptrueworks.ordermanagement.data.reposition.DataSortLevel;
import org.neptrueworks.ordermanagement.data.reposition.IDataCountable;
import org.neptrueworks.ordermanagement.data.reposition.IDataPageable;

import java.util.Map;

@Mapper
public interface OrderManifestEntityMappable
        extends IDataMappable, IDataOperable<OrderManifestEntity, Integer>, IDataRemovable<OrderManifestEntity, Integer>,
        IDataPageable<OrderManifestEntity, Integer>, IDataCountable<OrderManifestEntity, Integer> {
    Iterable<OrderManifestEntity> fetch(Map<String, Object> conditions);
    Iterable<OrderItemEntity> sortManifests(DataSortLevel level, DataSeekSpecification keyset);
    void updateGrossAmount(OrderManifestEntity orderManifest);
}
