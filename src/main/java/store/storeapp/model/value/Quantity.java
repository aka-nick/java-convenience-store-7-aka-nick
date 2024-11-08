package store.storeapp.model.value;

import java.text.DecimalFormat;
import java.util.Objects;

public class Quantity implements Comparable<Quantity> {

    private static final String PATTERN_OF_THOUSANDS_UNIT = "#,###";

    private final Integer quantity;

    public Quantity(Integer quantity) {
        if (quantity == null) {
            QuantityException.NULL_CANNOT_BE_ENTERED.raise();
        }
        if (quantity < 0) {
            QuantityException.CANNOT_BE_INITIALIZED_TO_WRONG_VALUE.raise();
        }
        this.quantity = quantity;
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
