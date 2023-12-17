package org.neptrueworks.ordermanagement.web.modelling;

import lombok.Data;

@Data
public class ProductRemovalViewModel implements IViewModellable{
    private Integer id;
    private String removedBy;
}
