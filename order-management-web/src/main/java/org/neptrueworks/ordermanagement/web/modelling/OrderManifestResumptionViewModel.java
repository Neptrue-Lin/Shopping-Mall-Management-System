package org.neptrueworks.ordermanagement.web.modelling;

import lombok.Data;

@Data
public class OrderManifestResumptionViewModel implements IViewModellable{
    private Integer id;
    private String resumedBy;
}
