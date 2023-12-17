package org.neptrueworks.ordermanagement.web.modelling;

import lombok.Data;

@Data
public class ProductRenewalViewModel implements IViewModellable{
    private Integer id;
    private String renewedBy;
}