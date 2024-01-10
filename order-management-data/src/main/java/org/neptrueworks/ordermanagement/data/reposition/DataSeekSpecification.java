package org.neptrueworks.ordermanagement.data.reposition;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataSeekSpecification {
    private String key;
    private Comparable<?> value;
    private DataSeekOrientation orientation;
}
