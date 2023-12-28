package org.neptrueworks.ordermanagement.common.exceptions;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExceptionCashier {
    public static final ExceptionCashier DEFAULT = new ExceptionCashier();
    private static final Logger logger = LogManager.getLogger(ExceptionCashier.class);

    private ExceptionCashier() {
    }

    @SneakyThrows
    public Throwable checkout(IExceptionIssuable issue, Exception causeException) {
        issue.setCauseException(causeException);
        Exception exception = issue.getExceptionClass().getDeclaredConstructor(String.class, Throwable.class)
                .newInstance(issue.getMessage(), issue.getCauseException());
        logger.throwing(exception);
        return exception;
    }

    @SneakyThrows
    public Throwable checkout(IExceptionIssuable issue) {
        return this.checkout(issue, null);
    }
}
