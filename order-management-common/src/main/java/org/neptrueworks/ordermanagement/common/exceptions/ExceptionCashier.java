package org.neptrueworks.ordermanagement.common.exceptions;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExceptionCashier {
    public static final ExceptionCashier DEFAULT = new ExceptionCashier();
    private static final Logger logger = LogManager.getLogger(ExceptionCashier.class);

    private ExceptionCashier() {
    }

    /**
     * Create and the exception in the issue provided with inner exception and log the exception.
     * @param issue Exception issue to be propagated.
     * @param causeException The inner exception to the exception to be thrown.
     * @return The wrapped exception to be thrown.
     */
    @SneakyThrows
    public Exception checkout(IExceptionIssuable issue, Exception causeException) {
        issue.setCauseException(causeException);
        Exception exception = issue.getExceptionClass().getDeclaredConstructor(String.class, Throwable.class)
                .newInstance(issue.getMessage(), issue.getCauseException());
        logger.throwing(exception);
        return exception;
    }

    /**
     * Create and the exception in the issue provided and log the exception.
     * @param issue Exception issue to be propagated.
     * @return The exception to be thrown.
     */
    @SneakyThrows
    public Exception checkout(IExceptionIssuable issue) {
        return this.checkout(issue, null);
    }
}
