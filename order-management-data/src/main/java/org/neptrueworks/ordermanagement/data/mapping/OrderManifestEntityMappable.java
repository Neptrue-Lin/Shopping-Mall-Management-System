package org.neptrueworks.ordermanagement.data.mapping;

import org.apache.ibatis.annotations.Mapper;
import org.neptrueworks.ordermanagement.data.entitizing.OrderManifestEntity;
import org.neptrueworks.ordermanagement.data.maneuvering.*;
import org.neptrueworks.ordermanagement.data.reposition.IDataCountable;
import org.neptrueworks.ordermanagement.data.reposition.IDataLimitable;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderManifestEntityMappable
        extends IDataMappable, IDataOperable<OrderManifestEntity, Integer>, IDataRemovable<OrderManifestEntity, Integer>,
        IDataLimitable<OrderManifestEntity, Integer>, IDataCountable<OrderManifestEntity, Integer> {
    List<OrderManifestEntity> fetch(Map<String, Object> conditions);

    void updateGrossAmount(OrderManifestEntity orderManifest);
}
