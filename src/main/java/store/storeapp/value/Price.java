package store.storeapp.value;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Objects;

public class Price implements Comparable<Price> {

    private static final String PATTERN_OF_THOUSANDS_UNIT = "#,###";

    private final BigInteger amount;

    public Price(BigInteger amount) {
        if (amount == null) {
            PriceException.CANNOT_BE_INITIALIZED_TO_NULL_VALUE.raise();
        }
        this.amount = amount;
    }

    public static Price of(Won amount) {
        if (amount == null) {
            PriceException.CANNOT_BE_INITIALIZED_TO_NULL_VALUE.raise();
        }
        return new Price(amount.get());
    }

    public static Price of(Long amount) {
        if (amount == null) {
            PriceException.CANNOT_BE_INITIALIZED_TO_NULL_VALUE.raise();
        }
        return new Price(BigInteger.valueOf(amount));
    }

    public static Price of(Integer amount) {
        if (amount == null) {
            PriceException.CANNOT_BE_INITIALIZED_TO_NULL_VALUE.raise();
        }
        return of(Long.valueOf(amount));
    }

    public Price add(Price other) {
        if (other == null) {
            PriceException.NULL_CANNOT_BE_ENTERED.raise();
        }
        return new Price(amount.add(other.amount));
    }

    public Price minus(Price other) {
        if (other == null) {
            PriceException.NULL_CANNOT_BE_ENTERED.raise();
        }
        return new Price(amount.subtract(other.amount));
    }

    public Price multiply(Price other) {
        if (other == null) {
            PriceException.NULL_CANNOT_BE_ENTERED.raise();
        }
        return new Price(amount.multiply(other.amount));
    }

    public Price multiply(long other) {
        return new Price(amount.multiply(BigInteger.valueOf(other)));
    }

    public Price multiply(Quantity other) {
        if (other == null) {
            PriceException.NULL_CANNOT_BE_ENTERED.raise();
        }
        return new Price(amount.multiply(BigInteger.valueOf(other.get())));
    }

    public Price divide(long other) {
        return new Price(amount.divide(BigInteger.valueOf(other)));
    }

    public boolean isGreaterThan(Price other) {
        if (other == null) {
            PriceException.NULL_CANNOT_BE_ENTERED.raise();
        }
        return 0 < amount.compareTo(other.amount);
    }

    public BigInteger get() {
        return amount;
    }

    public Long longValue() {
        return amount.longValue();
    }

    @Override
    public int compareTo(Price o) {
        if (o == null) {
            PriceException.NULL_CANNOT_BE_ENTERED.raise();
        }
        return amount.compareTo(o.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Price price = (Price) o;
        return Objects.equals(amount, price.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }

    @Override
    public String toString() {
        return new DecimalFormat(PATTERN_OF_THOUSANDS_UNIT)
                .format(amount);
    }

    private enum PriceException {

        CANNOT_BE_INITIALIZED_TO_NULL_VALUE(() -> {
            throw new IllegalArgumentException("가격은 null로 초기화할 수 없습니다.");
        }),
        NULL_CANNOT_BE_ENTERED(() -> {
            throw new IllegalArgumentException("null이 입력될 수 없습니다.");
        });

        private final Runnable thrown;

        PriceException(Runnable thrown) {
            this.thrown = thrown;
        }

        private void raise() {
            this.thrown.run();
        }

    }

}
