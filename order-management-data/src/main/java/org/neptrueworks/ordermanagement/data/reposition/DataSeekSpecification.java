package org.neptrueworks.ordermanagement.data.reposition;

import lombok.Data;

@Data
public class DataSeekSpecification {
    private String key;
    private Comparable<?> value;
    private DataSeekOrientation orientation;
}
