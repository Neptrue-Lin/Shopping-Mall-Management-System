package org.neptrueworks.ordermanagement.data.mapping;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.neptrueworks.ordermanagement.data.entitizing.ProductEntity;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataMappable;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataOperable;
import org.neptrueworks.ordermanagement.data.maneuvering.IDataRemovable;
import org.neptrueworks.ordermanagement.data.reposition.DataSeekSpecification;
import org.neptrueworks.ordermanagement.data.reposition.DataSortLevel;
import org.neptrueworks.ordermanagement.data.reposition.IDataCountable;
import org.neptrueworks.ordermanagement.data.reposition.IDataPageable;

import java.util.Map;

@Mapper
public interface ProductEntityMappable
        extends IDataMappable, IDataOperable<ProductEntity, Integer>, IDataRemovable<ProductEntity, Integer>,
        IDataPageable<ProductEntity, Integer>, IDataCountable<ProductEntity, Integer> {
    Iterable<ProductEntity> fetch(Map<String, Object> conditions);
    Iterable<ProductEntity> sortProducts(@Param("level") DataSortLevel level, Iterable<DataSeekSpecification> keyset);
    Iterable<ProductEntity> lookupProductsByName(String name, DataSeekSpecification keyset);
    void updateStock(ProductEntity entity);
}