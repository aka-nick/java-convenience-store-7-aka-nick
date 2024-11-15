package store.storeapp.value;

import java.text.DecimalFormat;
import java.util.Objects;

public class Quantity implements Comparable<Quantity> {

    public static final Quantity ZERO = new Quantity(0);
    public static final Quantity ONE = new Quantity(1);

    private static final String PATTERN_OF_THOUSANDS_UNIT = "#,###";

    private final Integer quantity;

    private Quantity(Integer quantity) {
        if (quantity == null) {
            QuantityException.NULL_CANNOT_BE_ENTERED.raise();
        }
        if (quantity < 0) {
            QuantityException.CANNOT_BE_INITIALIZED_TO_WRONG_VALUE.raise();
        }
        this.quantity = quantity;
    }

    public static Quantity of(Integer quantity) {
        if (quantity == null) {
            QuantityException.NULL_CANNOT_BE_ENTERED.raise();
        }
        return new Quantity(quantity);
    }

    public Quantity add(Quantity other) {
        return new Quantity(this.quantity + other.quantity);
    }

    public Quantity minus(Quantity other) {
        return new Quantity(this.quantity - other.quantity);
    }

    public Quantity multiply(Quantity other) {
        return new Quantity(this.quantity * other.quantity);
    }

    public Quantity divide(Quantity other) {
        return new Quantity(this.quantity / other.quantity);
    }

    public Quantity remainder(Quantity other) {
        return new Quantity(this.quantity % other.quantity);
    }

    public boolean isGreaterThan(Quantity other) {
        return this.quantity > other.quantity;
    }

    public Long longValue() {
        return quantity.longValue();
    }

    @Override
    public int compareTo(Quantity other) {
        if (other == null) {
            QuantityException.NULL_CANNOT_BE_ENTERED.raise();
        }
        return this.quantity.compareTo(other.get());
    }

    public Integer get() {
        return quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (Quantity) obj;
        return Objects.equals(this.quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }

    @Override
    public String toString() {
        return new DecimalFormat(PATTERN_OF_THOUSANDS_UNIT)
                .format(quantity);
    }


    private enum QuantityException {

        CANNOT_BE_INITIALIZED_TO_WRONG_VALUE(() -> {
            throw new IllegalArgumentException("수량은 0 또는 양의 정수로 초기화할 수 있습니다.");
        }),
        NULL_CANNOT_BE_ENTERED(() -> {
            throw new IllegalArgumentException("null이 입력될 수 없습니다.");
        });

        private final Runnable thrown;

        QuantityException(Runnable thrown) {
            this.thrown = thrown;
        }

        private void raise() {
            this.thrown.run();
        }

    }

}
