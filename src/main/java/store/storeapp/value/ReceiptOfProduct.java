package store.storeapp.value;

import store.storeapp.model.MembershipDiscountPrice;
import store.storeapp.model.Product;
import store.storeapp.model.PromotionDiscountPrice;

public final class ReceiptOfProduct {

    private final BuyProduct buyProduct;
    private final PromotionProduct promotionProduct;
    private final PromotionDiscountPrice promotionDiscountPrice;
    private final MembershipDiscountPrice membershipDiscountPrice;

    private ReceiptOfProduct(BuyProduct buyProduct,
                             PromotionProduct promotionProduct,
                             PromotionDiscountPrice promotionDiscountPrice,
                             MembershipDiscountPrice membershipDiscountPrice) {
        this.buyProduct = buyProduct;
        this.promotionProduct = promotionProduct;
        this.promotionDiscountPrice = promotionDiscountPrice;
        this.membershipDiscountPrice = membershipDiscountPrice;
    }

    public static ReceiptOfProduct of(BuyProduct buyProduct,
                                      PromotionProduct promotionProduct,
                                      PromotionDiscountPrice promotionDiscountPrice,
                                      MembershipDiscountPrice membershipDiscountPrice) {
        thrownIfAnyNullParameter(buyProduct, promotionProduct, promotionDiscountPrice, membershipDiscountPrice);
        return new ReceiptOfProduct(buyProduct,
                promotionProduct,
                promotionDiscountPrice,
                membershipDiscountPrice);
    }

    public static ReceiptOfProduct from(Product target,
                                        QuantityOfPaidAndProvided paidAndProvided,
                                        MembershipDiscountPrice membershipDiscountPrice) {
        BuyProduct buyProduct = BuyProduct.of(target.name(),
                paidAndProvided.getTotal(),
                target.price());
        PromotionProduct promotionProduct = PromotionProduct.of(target.name(), paidAndProvided.providedQuantity());
        PromotionDiscountPrice promotionPrice = PromotionDiscountPrice.from(
                target.price().multiply(paidAndProvided.providedQuantity()));
        return new ReceiptOfProduct(buyProduct, promotionProduct, promotionPrice, membershipDiscountPrice);
    }

    private static void thrownIfAnyNullParameter(BuyProduct buyProduct,
                                                 PromotionProduct promotionProduct,
                                                 PromotionDiscountPrice promotionDiscountPrice,
                                                 MembershipDiscountPrice membershipDiscountPrice) {
        if (buyProduct == null ||
                promotionProduct == null ||
                promotionDiscountPrice == null ||
                membershipDiscountPrice == null) {
            ReceiptOfProductException.CANNOT_BE_INITIALIZED_TO_NULL_VALUE.raise();
        }
    }

    public BuyProduct buyProduct() {
        return buyProduct;
    }

    public PromotionProduct promotionProduct() {
        return promotionProduct;
    }

    public PromotionDiscountPrice promotionDiscountPrice() {
        return promotionDiscountPrice;
    }

    public MembershipDiscountPrice membershipDiscountPrice() {
        return membershipDiscountPrice;
    }

    private enum ReceiptOfProductException {

        CANNOT_BE_INITIALIZED_TO_NULL_VALUE(() -> {
            throw new IllegalArgumentException("영수증은 null로 초기화할 수 없습니다.");
        }),
        NULL_CANNOT_BE_ENTERED(() -> {
            throw new IllegalArgumentException("null이 입력될 수 없습니다.");
        });

        private final Runnable thrown;

        ReceiptOfProductException(Runnable thrown) {
            this.thrown = thrown;
        }

        private void raise() {
            this.thrown.run();
        }

    }

}
