package org.neptrueworks.ordermanagement.data.mapping;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.neptrueworks.ordermanagement.data.entitizing.OrderItemEntity;
import org.neptrueworks.ordermanagement.data.entitizing.OrderManifestEntity;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataManipulable;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataMappable;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataRemovable;
import org.neptrueworks.ordermanagement.data.reposition.*;

import java.util.List;

@Mapper
public interface OrderManifestEntityMappable extends IDataMappable, IDataRemovable<OrderManifestEntity, Integer>,
        IDataPageable<OrderManifestEntity, Integer>, IDataCountable<OrderManifestEntity, Integer>,
        IDataQueryable<OrderManifestEntity, Integer>, IDataManipulable<OrderManifestEntity, Integer> {
    List<OrderItemEntity> sortManifests(@Param("level") DataSortLevel level, @Param("keyset") DataSeekSpecification keyset);

    void updateGrossAmount(OrderManifestEntity orderManifest);
}
