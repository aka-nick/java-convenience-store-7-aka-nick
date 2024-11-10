package store.storeapp.model;

import store.storeapp.value.Price;
import store.storeapp.value.Won;

public final class MembershipDiscountPrice {

    private Price price;

    public MembershipDiscountPrice() {
        this.price = Price.of(0);
    }

    public MembershipDiscountPrice(Price price) {
        if (price == null) {
            MembershipDiscountPriceException.CANNOT_BE_INITIALIZED_TO_NULL_VALUE.raise();
        }
        this.price = price;
    }

    public Price minus(Price other) {
        if (other == null) {
            MembershipDiscountPriceException.NULL_CANNOT_BE_ENTERED.raise();
        }
        price = price.minus(other);
        return price;
    }

    public Price minus(Won other) {
        if (other == null) {
            MembershipDiscountPriceException.NULL_CANNOT_BE_ENTERED.raise();
        }
        price = price.minus(Price.of(other));
        return price;
    }

    @Override
    public String toString() {
        return price.toString();
    }

    private enum MembershipDiscountPriceException {

        CANNOT_BE_INITIALIZED_TO_NULL_VALUE(() -> {
            throw new IllegalArgumentException("총구매액은 null로 초기화할 수 없습니다.");
        }),
        NULL_CANNOT_BE_ENTERED(() -> {
            throw new IllegalArgumentException("null이 입력될 수 없습니다.");
        });

        private final Runnable thrown;

        MembershipDiscountPriceException(Runnable thrown) {
            this.thrown = thrown;
        }

        private void raise() {
            this.thrown.run();
        }

    }

}
