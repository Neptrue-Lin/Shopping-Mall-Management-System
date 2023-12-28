package org.neptrueworks.ordermanagement.common.exceptions;

public interface IExceptionIssuable {
    Class<? extends Exception> getExceptionClass();

    void setExceptionClass(Class<? extends Exception> exceptionClass);

    String getMessage();

    void setMessage(String message);

    Exception getCauseException();

    void setCauseException(Exception causeException);
}
