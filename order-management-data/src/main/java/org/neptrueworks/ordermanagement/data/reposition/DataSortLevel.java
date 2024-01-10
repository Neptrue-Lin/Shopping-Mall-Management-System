package org.neptrueworks.ordermanagement.data.reposition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class DataSortLevel {
    private String target;
    private DataSortOrientation orientation;
}
