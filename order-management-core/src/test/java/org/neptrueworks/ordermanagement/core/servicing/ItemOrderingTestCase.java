package org.neptrueworks.ordermanagement.core.servicing;

import lombok.Getter;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.neptrueworks.ordermanagement.core.exceptions.ServiceExceptionIssue;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Getter
public enum ItemOrderingTestCase {
    QUANTITY_LESS_THAN_OR_EQUALS_ZERO(ThreadLocalRandom.current().nextInt(-10,0),
            ThreadLocalRandom.current().nextInt(1,10),
            ServiceExceptionIssue.QUANTITY_NOT_GREATER_THAN_ZERO),
    QUANTITY_LARGER_THAN_STOCK(ThreadLocalRandom.current().nextInt(5,10),
            ThreadLocalRandom.current().nextInt(1,10),
            ServiceExceptionIssue.OUT_OF_STOCK);

    private final int quantity;
    private final int stock;
    private final ServiceExceptionIssue exceptionIssue;

    ItemOrderingTestCase(int quantity, int stock, ServiceExceptionIssue issue) {
        this.quantity = quantity;
        this.stock = stock;
        this.exceptionIssue = issue;
    }

    public static class InvalidItemOrderingArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                    arguments(named(ItemOrderingTestCase.QUANTITY_LESS_THAN_OR_EQUALS_ZERO.toString(),
                            ItemOrderingTestCase.QUANTITY_LESS_THAN_OR_EQUALS_ZERO)),
                    arguments(named(ItemOrderingTestCase.QUANTITY_LARGER_THAN_STOCK.toString(),
                            ItemOrderingTestCase.QUANTITY_LARGER_THAN_STOCK))
            );
        }
    }
}
