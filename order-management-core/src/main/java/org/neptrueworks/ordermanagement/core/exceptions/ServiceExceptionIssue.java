package org.neptrueworks.ordermanagement.core.exceptions;

import lombok.Getter;
import org.neptrueworks.ordermanagement.common.exceptions.IExceptionIssuable;

@Getter
public enum ServiceExceptionIssue implements IExceptionIssuable {
    QUANTITY_NOT_GREATER_THAN_ZERO(IllegalArgumentException.class, "Quantity could not be less than or equal to 0.", null),
    PRODUCT_NONEXISTENT(IllegalStateException.class, "Product does not exist.", null),
    OUT_OF_STOCK(IllegalArgumentException.class, "Out of stock.", null),
    VARIATION_LESS_THAN_ZERO(IllegalArgumentException.class, "Variation should not be less than 0.", null),
    ORDER_MANIFEST_NOT_FOUND(IllegalStateException.class, "Order manifest was not found.", null),
    ORDER_ITEM_NOT_FOUND(IllegalStateException.class, "Order item was not found.", null);

    private Class<? extends Exception> exceptionClass;
    private String message;
    private Exception causeException;


    ServiceExceptionIssue(Class<? extends Exception> clazz, String message, Exception causeException) {
        this.setExceptionClass(clazz);
        this.setMessage(message);
        this.setCauseException(causeException);
    }

    public void setExceptionClass(Class<? extends Exception> exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCauseException(Exception causeException) {
        this.causeException = causeException;
    }
}
