package store.storeapp.model;

import store.storeapp.value.Price;

public final class PromotionDiscountPrice {

    private final Price price;

    public PromotionDiscountPrice(Price price) {
        if (price == null) {
            PromotionDiscountPriceException.CANNOT_BE_INITIALIZED_TO_NULL_VALUE.raise();
        }
        this.price = price;
    }

    public static PromotionDiscountPrice from(Price price) {
        return new PromotionDiscountPrice(price);
    }

    public long longValue() {
        return price.longValue();
    }

    @Override
    public String toString() {
        return price.toString();
    }

    private enum PromotionDiscountPriceException {

        CANNOT_BE_INITIALIZED_TO_NULL_VALUE(() -> {
            throw new IllegalArgumentException("총구매액은 null로 초기화할 수 없습니다.");
        }),
        NULL_CANNOT_BE_ENTERED(() -> {
            throw new IllegalArgumentException("null이 입력될 수 없습니다.");
        });

        private final Runnable thrown;

        PromotionDiscountPriceException(Runnable thrown) {
            this.thrown = thrown;
        }

        private void raise() {
            this.thrown.run();
        }

    }

}
