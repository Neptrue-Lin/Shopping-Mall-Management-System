package org.neptrueworks.ordermanagement.web.modelling;

import lombok.Data;

@Data
public class ProductAdditionViewModel implements IViewModellable{
    private Double price;
    private Integer stock;
    private String addedBy;
}
