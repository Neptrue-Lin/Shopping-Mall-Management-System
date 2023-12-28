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
public enum ProductInvalidStockingTestCase {

    VARIATION_LESS_THAN_ZERO(ThreadLocalRandom.current().nextInt(-10, 0),
            ThreadLocalRandom.current().nextInt(1, 10),
            ServiceExceptionIssue.VARIATION_LESS_THAN_ZERO),
    STOCK_LESS_THAN_ZERO(ThreadLocalRandom.current().nextInt(1, 10),
            ThreadLocalRandom.current().nextInt(-10, 0),
            ServiceExceptionIssue.OUT_OF_STOCK),
    VARIATION_AND_STOCK_LESS_THAN_ZERO(ThreadLocalRandom.current().nextInt(-10, 0),
            ThreadLocalRandom.current().nextInt(-10, 0),
            ServiceExceptionIssue.VARIATION_LESS_THAN_ZERO),
    VARIATION_LARGER_THAN_STOCK(ThreadLocalRandom.current().nextInt(5, 10),
            ThreadLocalRandom.current().nextInt(1, 5),
            ServiceExceptionIssue.OUT_OF_STOCK);

    private final int variation;
    private final int stock;
    private final ServiceExceptionIssue exceptionIssue;

    ProductInvalidStockingTestCase(int variation, int stock, ServiceExceptionIssue issue) {
        this.variation = variation;
        this.stock = stock;
        this.exceptionIssue = issue;
    }

    public static class InvalidDestockArgumentsProvider implements ArgumentsProvider{
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                    arguments(named(ProductInvalidStockingTestCase.VARIATION_LESS_THAN_ZERO.toString(),
                            ProductInvalidStockingTestCase.VARIATION_LESS_THAN_ZERO)),
                    arguments(named(ProductInvalidStockingTestCase.STOCK_LESS_THAN_ZERO.toString(),
                            ProductInvalidStockingTestCase.STOCK_LESS_THAN_ZERO)),
                    arguments(named(ProductInvalidStockingTestCase.VARIATION_AND_STOCK_LESS_THAN_ZERO.toString(),
                            ProductInvalidStockingTestCase.VARIATION_AND_STOCK_LESS_THAN_ZERO)),
                    arguments(named(ProductInvalidStockingTestCase.VARIATION_LARGER_THAN_STOCK.toString(),
                            ProductInvalidStockingTestCase.VARIATION_LARGER_THAN_STOCK))
            );
        }
    }

    public static class InvalidRestockArgumentsProvider implements ArgumentsProvider{
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                    arguments(named(ProductInvalidStockingTestCase.VARIATION_LESS_THAN_ZERO.toString(),
                            ProductInvalidStockingTestCase.VARIATION_LESS_THAN_ZERO)),
                    arguments(named(ProductInvalidStockingTestCase.STOCK_LESS_THAN_ZERO.toString(),
                            ProductInvalidStockingTestCase.STOCK_LESS_THAN_ZERO)),
                    arguments(named(ProductInvalidStockingTestCase.VARIATION_AND_STOCK_LESS_THAN_ZERO.toString(),
                            ProductInvalidStockingTestCase.VARIATION_AND_STOCK_LESS_THAN_ZERO))
            );
        }
    }
}
