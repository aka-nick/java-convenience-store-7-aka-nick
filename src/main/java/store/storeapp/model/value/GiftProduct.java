package store.storeapp.model.value;

import java.util.Objects;

public record GiftProduct(ProductName name, Quantity quantity) implements Comparable<GiftProduct> {

    public static GiftProduct of(ProductName name, Quantity quantity) {
        if (name == null || quantity == null) {
            GiftProductException.CANNOT_BE_INITIALIZED_TO_NULL_VALUE.raise();
        }
        return new GiftProduct(name, quantity);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        GiftProduct that = (GiftProduct) other;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(GiftProduct other) {
        if (other == null) {
            GiftProductException.NULL_CANNOT_BE_ENTERED.raise();
        }
        return name.compareTo(other.name);
    }

    private enum GiftProductException {

        CANNOT_BE_INITIALIZED_TO_NULL_VALUE(() -> {
            throw new IllegalArgumentException("증정상품은 null로 초기화할 수 없습니다.");
        }),
        NULL_CANNOT_BE_ENTERED(() -> {
            throw new IllegalArgumentException("null이 입력될 수 없습니다.");
        });
        private final Runnable thrown;

        GiftProductException(Runnable thrown) {
            this.thrown = thrown;
        }

        private void raise() {
            this.thrown.run();
        }

    }

}
