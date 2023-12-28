package org.neptrueworks.ordermanagement.core.servicing;

import lombok.Getter;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Getter
public enum PaginationTestCase {
    FULL_PAGE(ThreadLocalRandom.current().nextInt(1, 10),
            ThreadLocalRandom.current().nextInt(1, 10),
            100),
    PARTIAL_PAGE(4,
            30,
            100),

    PAGE_INDEX_LESS_THAN_ZERO(ThreadLocalRandom.current().nextInt(),
            ThreadLocalRandom.current().nextInt(1, 10),
            100),
    PAGE_SIZE_LESS_THAN_ZERO(ThreadLocalRandom.current().nextInt(-10, 0),
            ThreadLocalRandom.current().nextInt(1, 10),
            100),
    PAGE_INDEX_AND_PAGE_SIZE_LESS_THAN_ZERO(ThreadLocalRandom.current().nextInt(-10, -1),
            ThreadLocalRandom.current().nextInt(1, 10),
            100),
    AMOUNT_LESS_THAN_ZERO(ThreadLocalRandom.current().nextInt(-10, 0),
            ThreadLocalRandom.current().nextInt(1, 10),
            100);

    private final int pageIndex;
    private final int pageSize;
    private final int amount;

    PaginationTestCase(int pageIndex, int pageSize, int amount) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.amount = amount;
    }

    static class ValidPaginationArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                    arguments(named(PaginationTestCase.FULL_PAGE.toString(),
                            PaginationTestCase.FULL_PAGE)),
                    arguments(named(PaginationTestCase.PARTIAL_PAGE.toString(),
                            PaginationTestCase.PARTIAL_PAGE))
            );
        }
    }

    static class InvalidPaginationArgumentsProvider implements ArgumentsProvider{
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                    arguments(named(PaginationTestCase.PAGE_INDEX_LESS_THAN_ZERO.toString(),
                            PaginationTestCase.PAGE_INDEX_LESS_THAN_ZERO)),
                    arguments(named(PaginationTestCase.PAGE_SIZE_LESS_THAN_ZERO.toString(),
                            PaginationTestCase.PAGE_SIZE_LESS_THAN_ZERO)),
                    arguments(named(PaginationTestCase.PAGE_INDEX_AND_PAGE_SIZE_LESS_THAN_ZERO.toString(),
                            PaginationTestCase.PAGE_INDEX_AND_PAGE_SIZE_LESS_THAN_ZERO)),
                    arguments(named(PaginationTestCase.AMOUNT_LESS_THAN_ZERO.toString(),
                            PaginationTestCase.AMOUNT_LESS_THAN_ZERO))
            );
        }
    }
}
