package store.storeapp.model;

import store.config.value.YesOrNo;
import store.storeapp.value.Price;

public final class MembershipDiscountPrice {

    private static final Long DISCOUNT_PERCENTAGE = 30L;

    private final Price price;

    private MembershipDiscountPrice() {
        this(0);
    }

    private MembershipDiscountPrice(Integer price) {
        if (price == null) {
            MembershipDiscountPriceException.CANNOT_BE_INITIALIZED_TO_NULL_VALUE.raise();
        }
        this.price = Price.of(price);
    }

    private MembershipDiscountPrice(Price price) {
        if (price == null) {
            MembershipDiscountPriceException.CANNOT_BE_INITIALIZED_TO_NULL_VALUE.raise();
        }
        this.price = price;
    }

    public static MembershipDiscountPrice from(Product targetProduct, YesOrNo answerForMembershipDiscount) {
        thrownByNullParams(targetProduct, answerForMembershipDiscount);

        if (!targetProduct.promotion().isEmpty()) {
            return new MembershipDiscountPrice(0);
        }
        if (!answerForMembershipDiscount.judge()) {
            return new MembershipDiscountPrice(0);
        }
        return new MembershipDiscountPrice(
                targetProduct.price().divide(100L).multiply(DISCOUNT_PERCENTAGE));
    }

    private static void thrownByNullParams(Product targetProduct, YesOrNo answerForMembershipDiscount) {
        if (targetProduct.price() == null || answerForMembershipDiscount == null) {
            MembershipDiscountPriceException.NULL_CANNOT_BE_ENTERED.raise();
        }
    }

    public Price price() {
        return price;
    }

    public long longValue() {
        return price.longValue();
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
