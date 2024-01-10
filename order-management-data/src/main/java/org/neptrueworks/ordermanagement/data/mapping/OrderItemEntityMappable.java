package org.neptrueworks.ordermanagement.data.mapping;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.neptrueworks.ordermanagement.data.entitizing.OrderItemEntity;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataManipulable;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataMappable;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataRemovable;
import org.neptrueworks.ordermanagement.data.reposition.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface OrderItemEntityMappable extends IDataMappable, IDataRemovable<OrderItemEntity, Integer>,
        IDataPageable<OrderItemEntity, Integer>, IDataCountable<OrderItemEntity, Integer>,
        IDataQueryable<OrderItemEntity, Integer>, IDataManipulable<OrderItemEntity, Integer> {
    List<OrderItemEntity> findManifestItems(@Param("manifestId") Integer manifestId);

    List<OrderItemEntity> sortManifestItems(@Param("manifestId") Integer manifestId, @Param("level") DataSortLevel level);

    List<OrderItemEntity> lookupItemsByProductId(@Param("productId") Integer productId, @Param("keyset") DataSeekSpecification keyset,
                                                 @Param("level") DataSortLevel level);

    Optional<OrderItemEntity> findItemByProductId(@Param("manifestId") Integer manifestId, @Param("productId") Integer productId);
}
