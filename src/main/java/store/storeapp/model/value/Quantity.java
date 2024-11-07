package store.storeapp.model.value;

public record Quantity(Integer quantity) implements Comparable<Quantity> {

    public Quantity {
        if (quantity == null) {
            QuantityException.NULL_CANNOT_BE_ENTERED.raise();
        }
        if (quantity < 0) {
            QuantityException.CANNOT_BE_INITIALIZED_TO_WRONG_VALUE.raise();
        }
    }

    @Override
    public int compareTo(Quantity o) {
        if (o == null) {
            QuantityException.NULL_CANNOT_BE_ENTERED.raise();
        }
        return this.quantity.compareTo(o.quantity());
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
