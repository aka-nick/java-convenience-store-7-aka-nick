package store.storeapp.model.value;

import store.storeapp.model.MembershipDiscountPrice;
import store.storeapp.model.PromotionDiscountPrice;

public final class Receipt {

    private final BuyProducts buyProducts;
    private final PromotionProducts promotionProducts;
    private final PromotionDiscountPrice promotionDiscountPrice;
    private final MembershipDiscountPrice membershipDiscountPrice;

    private Receipt(BuyProducts buyProducts,
                    PromotionProducts promotionProducts,
                    PromotionDiscountPrice promotionDiscountPrice,
                    MembershipDiscountPrice membershipDiscountPrice) {
        this.buyProducts = buyProducts;
        this.promotionProducts = promotionProducts;
        this.promotionDiscountPrice = promotionDiscountPrice;
        this.membershipDiscountPrice = membershipDiscountPrice;
    }

    public static Receipt of() {
        return new Receipt(BuyProducts.of(),
                PromotionProducts.of(),
                new PromotionDiscountPrice(),
                new MembershipDiscountPrice());
    }

    public static Receipt of(BuyProducts buyProducts,
                             PromotionProducts promotionProducts,
                             PromotionDiscountPrice promotionDiscountPrice,
                             MembershipDiscountPrice membershipDiscountPrice) {
        thrownIfAnyNullParameter(buyProducts, promotionProducts, promotionDiscountPrice, membershipDiscountPrice);
        return new Receipt(buyProducts,
                promotionProducts,
                promotionDiscountPrice,
                membershipDiscountPrice);
    }

    private static void thrownIfAnyNullParameter(BuyProducts buyProducts,
                                                 PromotionProducts promotionProducts,
                                                 PromotionDiscountPrice promotionDiscountPrice,
                                                 MembershipDiscountPrice membershipDiscountPrice) {
        if (buyProducts == null ||
                promotionProducts == null ||
                promotionDiscountPrice == null ||
                membershipDiscountPrice == null) {
            ReceiptException.CANNOT_BE_INITIALIZED_TO_NULL_VALUE.raise();
        }
    }

    record FinalPrice(Integer amount) {

    }

    private enum ReceiptException {

        CANNOT_BE_INITIALIZED_TO_NULL_VALUE(() -> {
            throw new IllegalArgumentException("영수증은 null로 초기화할 수 없습니다.");
        }),
        NULL_CANNOT_BE_ENTERED(() -> {
            throw new IllegalArgumentException("null이 입력될 수 없습니다.");
        });

        private final Runnable thrown;

        ReceiptException(Runnable thrown) {
            this.thrown = thrown;
        }

        private void raise() {
            this.thrown.run();
        }

    }

}
