package org.neptrueworks.ordermanagement.web.modelling;

import lombok.Data;

@Data
public class ProductDestockingViewModel implements IViewModellable {
    private Integer id;
    private Integer decrement;
    private String destockedBy;
}
