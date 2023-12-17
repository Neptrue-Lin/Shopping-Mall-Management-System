package org.neptrueworks.ordermanagement.web.modelling;

import lombok.Data;

@Data
public class OrderManifestCancellationViewModel implements IViewModellable{
    private Integer id;
    private String cancelledBy;
}
