package org.neptrueworks.ordermanagement.web.modelling;

import lombok.Data;
import org.neptrueworks.ordermanagement.data.entitizing.OrderItemEntity;

import java.util.List;

@Data
public class OrderManifestPlacementViewModel implements IViewModellable{
    private List<OrderItemEntity> items;
    private String placedBy;
}
