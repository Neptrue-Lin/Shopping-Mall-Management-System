package org.neptrueworks.ordermanagement.data.mapping;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.neptrueworks.ordermanagement.data.entitizing.ProductEntity;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataManipulable;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataMappable;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataRemovable;
import org.neptrueworks.ordermanagement.data.reposition.*;

import java.util.List;

@Mapper
public interface ProductEntityMappable extends IDataMappable, IDataRemovable<ProductEntity, Integer>,
        IDataPageable<ProductEntity, Integer>, IDataCountable<ProductEntity, Integer>,
        IDataQueryable<ProductEntity, Integer>, IDataManipulable<ProductEntity, Integer> {
    List<ProductEntity> sortProducts(@Param("level") DataSortLevel level, @Param("keyset") DataSeekSpecification keyset);

    List<ProductEntity> lookupProductsByName(@Param("name") String name, @Param("keyset") DataSeekSpecification keyset,
                                             @Param("level") DataSortLevel level);

    void updateStock(ProductEntity entity);
}