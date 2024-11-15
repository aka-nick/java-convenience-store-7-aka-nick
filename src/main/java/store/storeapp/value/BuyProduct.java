package store.storeapp.value;

import java.util.Objects;

public record BuyProduct(ProductName name, Quantity quantity, Price price) implements Comparable<BuyProduct> {

    public static BuyProduct of(ProductName name, Quantity quantity, Price price) {
        if (name == null || quantity == null || price == null) {
            BuyProductException.CANNOT_BE_INITIALIZED_TO_NULL_VALUE.raise();
        }
        return new BuyProduct(name, Quantity.of(quantity.get()), Price.of(price.longValue()));
    }

    public Price getTotalPrice() {
        return price.multiply(quantity);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        BuyProduct that = (BuyProduct) other;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(BuyProduct other) {
        if (other == null) {
            BuyProductException.NULL_CANNOT_BE_ENTERED.raise();
        }
        return name.compareTo(other.name);
    }

    private enum BuyProductException {

        CANNOT_BE_INITIALIZED_TO_NULL_VALUE(() -> {
            throw new IllegalArgumentException("구매한 상품은 null로 초기화할 수 없습니다.");
        }),
        NULL_CANNOT_BE_ENTERED(() -> {
            throw new IllegalArgumentException("null이 입력될 수 없습니다.");
        });
        private final Runnable thrown;

        BuyProductException(Runnable thrown) {
            this.thrown = thrown;
        }

        private void raise() {
            this.thrown.run();
        }

    }

}
