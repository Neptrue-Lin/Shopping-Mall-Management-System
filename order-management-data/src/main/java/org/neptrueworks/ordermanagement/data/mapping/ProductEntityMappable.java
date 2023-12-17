package org.neptrueworks.ordermanagement.data.mapping;


import org.apache.ibatis.annotations.Mapper;
import org.neptrueworks.ordermanagement.data.entitizing.ProductEntity;
import org.neptrueworks.ordermanagement.data.maneuvering.*;
import org.neptrueworks.ordermanagement.data.reposition.IDataCountable;
import org.neptrueworks.ordermanagement.data.reposition.IDataLimitable;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductEntityMappable
        extends IDataMappable, IDataOperable<ProductEntity, Integer>, IDataRemovable<ProductEntity, Integer>,
        IDataLimitable<ProductEntity, Integer>, IDataCountable<ProductEntity, Integer> {
    List<ProductEntity> fetch(Map<String, Object> conditions);

    void updateStock(ProductEntity entity);
}
