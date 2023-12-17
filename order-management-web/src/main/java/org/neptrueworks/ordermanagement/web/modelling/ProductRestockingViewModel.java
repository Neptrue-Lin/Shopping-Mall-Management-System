package org.neptrueworks.ordermanagement.web.modelling;

import lombok.Data;

@Data
public class ProductRestockingViewModel implements IViewModellable{
    private Integer id;
    private Integer increment;
    private String restockedBy;
}
